

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.List;

import javax.usb.UsbConst;
import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;
import javax.usb.UsbDisconnectedException;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import javax.usb.UsbServices;

import de.ailis.usb4java.libusb.Device;
import de.ailis.usb4java.libusb.DeviceDescriptor;
import de.ailis.usb4java.libusb.DeviceHandle;
import de.ailis.usb4java.libusb.DeviceList;
import de.ailis.usb4java.libusb.Interface;
import de.ailis.usb4java.libusb.LibUsb;

/**
* Tests and manages USB devices
* On Linux this can only work when your user has write permissions on all the USB
* device files in /dev/bus/usb (Running this example as root will work). On
* Windows this can only work for devices which have a libusb-compatible driver
* installed. On OSX this usually works without problems.
* 
* This class maintains a list of USB devices and IRPs, it manages all communications with usb devices
* When adding a device the client is given only a handle to refer to the device
* All interaction by client with the device is managed in this class for the purpose of abstraction
*
*/

//TODO consider making this an object bellonging to couch 
//especially if more USB devices (screen etc) want to be added later
//TODO consider moving all USB comm related stuff in here
	//Use asynchonous so multiple devices can be added
	//only give out a device ID to the rest of the program so they can just call sendData()


public class DeviceManager{
	
	private Display screen;
	private UsbServices usb;
	private UsbHub hub;
	private Device[] devices;
	private DeviceHandle[] handles;
	private int numDevices;
	private boolean debug;
	private int MAX_DEVICES;
	
	private static final int MC_INT = 0;
	private static final byte MC_OUT_ENDPOINT = 1;
	//TODO on the pi, this demands a byte
	private static final int MC_IN_ENDPOINT = 129;
	
	
	public DeviceManager(Display screen, boolean debug, int maxDevices){
		this.screen = screen;
		this.debug = debug;
		this.MAX_DEVICES = maxDevices;
		devices = new Device[MAX_DEVICES];
		handles = new DeviceHandle[MAX_DEVICES];
		numDevices = 0;
		try {
			usb = UsbHostManager.getUsbServices();
			hub = usb.getRootUsbHub();
			if(debug){
				screen.printLine("Available Devices ...", true);
				listDevices(hub);
			}
		} catch (UsbException e) {
			screen.printLine("ERROR: Unable to initalise USB");
			e.printStackTrace();
		}
		LibUsb.init(null);
	}
	
	/**
	 * Adds a device to the device manager with the given VID and PID
	 * @param VID Vendor ID of the USB device
	 * @param PID Process ID of the USB device
	 * @return a handle to refer to the device or -1 if device cannot be found or irp is not set up
	 */
	public int addDevice(int VID, int PID, String descriptor){
		int ID = numDevices;
		int result = -1;
		DeviceList list = new DeviceList();
		LibUsb.getDeviceList(null, list);
		Iterator<Device> itr = list.iterator();
		Device device;
		DeviceHandle handle = new DeviceHandle();
		DeviceDescriptor desc = new DeviceDescriptor();
		screen.printLine("Seeking " + descriptor);
		while(itr.hasNext()){
			device = (Device)itr.next();
			LibUsb.getDeviceDescriptor(device, desc);
			if(desc.idProduct() == PID && desc.idVendor() == VID){
				int claim = LibUsb.open(device, handle);
				if(claim != 0){
					screen.print("Error claiming device");
					if(claim == LibUsb.ERROR_ACCESS){
						screen.printLine("Permission Denied");
					} else if (claim == LibUsb.ERROR_NO_MEM){
						screen.printLine("Memory allocation error");
					} else if (claim == LibUsb.ERROR_NO_DEVICE){
						screen.printLine("Device Disconnected");
					} else {
						screen.printLine("Unknown");
					}
				}
				claim = LibUsb.claimInterface(handle, MC_INT);
				if(claim == 0){
					screen.printLine("Found");
					devices[ID] = device;
					handles[ID] = handle;
					result = ID;
				} else if(claim == LibUsb.ERROR_NOT_FOUND) {
					screen.printLine("ERROR: Interface not found");
				} else if(claim == LibUsb.ERROR_BUSY){
					screen.printLine("ERROR: Interface taken");
				} else if(claim == LibUsb.ERROR_NO_DEVICE){
					screen.printLine("ERROR: Device disconnected");
				}
				screen.printLine(" ");
			}
		}
		LibUsb.freeDeviceList(list, true);
		if(result == -1){
			screen.printLine("Device NOT found");

		}
		return result;
	}
	
	public void releaseDevice(int ID){
		//TODO next line sends shit to controller check with Will
		//LibUsb.releaseInterface(handles[ID], 0);
		LibUsb.close(handles[ID]);
		
	}
	
	/**
	 * Sends a packet of data using bulk transfer and gets a packet in response
	 * @param dataOut data to send
	 * @param handle handle of the usb device
	 * @return packet received from the device
	 */
	public byte[] sendData(byte[] dataOut, int ID){
		try {
		    Thread.sleep(10);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		//SEND
		ByteBuffer toSend = ByteBuffer.allocateDirect(64);
		toSend.put(dataOut);
		int result = LibUsb.bulkTransfer(handles[ID], MC_OUT_ENDPOINT, toSend, IntBuffer.wrap(new int[64]), 5000);
		if(result != 0){
			screen.print("Data Out Error : (DevMan.sendData()");
			if(result == LibUsb.ERROR_TIMEOUT){
				screen.print("Timeout");
			} else if(result == LibUsb.ERROR_PIPE){
				screen.print("Endpoint halted");
			} else if(result == LibUsb.ERROR_OVERFLOW){
				screen.print("Overflow");
			} else if(result == LibUsb.ERROR_NO_DEVICE){
				screen.print("Device Disconnected");
			}
			screen.printLine(" ");
		} 

		
		//RECIEVE
		ByteBuffer returned = ByteBuffer.allocateDirect(64);
		result = LibUsb.bulkTransfer(handles[ID], MC_IN_ENDPOINT, returned, IntBuffer.wrap(new int[64]), 5000);
		if(result != 0){
			screen.print("Data In Error : (DevMan.sendData()");
			if(result == LibUsb.ERROR_TIMEOUT){
				screen.print("Timeout");
			} else if(result == LibUsb.ERROR_PIPE){
				screen.print("Endpoint halted");
			} else if(result == LibUsb.ERROR_OVERFLOW){
				screen.print("Overflow");
			} else if(result == LibUsb.ERROR_NO_DEVICE){
				screen.print("Device Disconnected");
			} else {
				screen.print("Unknown, code " + Integer.toString(result));
			}
			screen.printLine(" ");
		} 
				
		byte[] response = new byte[64];
		returned.get(response);
		//TODO remove after testing
		//System.out.println("--> " + response[0]);
		return response;
	}
		
	/**
	* Processes the specified USB device.
	*
	* @param device
	* The USB device to process.
	*/
    private void listDevices(final UsbDevice device)
    {
        // When device is a hub then process all child devices
        if (device.isUsbHub())
        {
            final UsbHub hub = (UsbHub) device;
            for (UsbDevice child: (List<UsbDevice>) hub.getAttachedUsbDevices())
            {
                listDevices(child);
            }
        }

        // When device is not a hub then dump its name.
        else
        {
            try
            {
                listDevice(device);
            }
            catch (Exception e)
            {
                // On Linux this can fail because user has no write permission
                // on the USB device file. On Windows it can fail because
                // no libusb device driver is installed for the device
                // System.err.println("Ignoring problematic device: " + e);
            }
        }
    }
	
	/**
	* Prints the name, vendor ID and PID of the specified device to screen
	* @param device
	* The USB device.
	* @throws UnsupportedEncodingException
	* When string descriptor could not be parsed.
	* @throws UsbException
	* When string descriptor could not be read.
	*/
    private void listDevice(final UsbDevice device) throws UnsupportedEncodingException, UsbException{
        // Read the string descriptor indices from the device descriptor.
        // If they are missing then ignore the device.
        final UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
        final byte iManufacturer = desc.iManufacturer();
        final byte iProduct = desc.iProduct();
        if(!(iManufacturer == 0 || iProduct == 0)){

           // Print the device info
   	     screen.printLine(" ");
	        screen.printLine(device.getString(iManufacturer) + " " + device.getString(iProduct));
		     screen.printLine("Vendor ID:" + desc.idVendor());
		     screen.printLine("PID:" + desc.idProduct());
	     }
    }
}
