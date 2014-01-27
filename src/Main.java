import java.io.FileNotFoundException;

import javax.usb.UsbException;



public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Couch couch = new Couch();
		if(couch.hasController()){
			couch.run();
		}
	}
}
