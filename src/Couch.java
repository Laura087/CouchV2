
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.File;
import java.io.IOException;

import javax.usb.UsbDevice;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.CDL;


public class Couch {
	private GameController controller;
	private Wheels wheels;
	private Display screen;
	private DeviceManager deviceManager;
	
    private JSONObject config;
    
    private boolean MODE_CHANGE;
    private boolean DEBUG;


	
	public Couch(String configFilePath) throws FileNotFoundException{
        
		Reader in = new FileReader(configFilePath);
        JSONTokener jtk = new JSONTokener(in);
        config = new JSONObject(jtk);	
        
		MODE_CHANGE = (config.getInt("modeChange") != 0);
		DEBUG = (config.getInt("debug") != 0);
				
 		String screenType = config.getString("screenType");
 		switch(screenType){
 			case "Console":
 				screen = new Cons(DEBUG);
 				break;
 			case "Screen":
 				//TODO this i am going to have to rethink using device manager since device manager just uses a screen, could just ditch device manager
 				//screen = new Screen();
 				//break;
 			case "Virtual Screen":
 				screen = new VirtualScreen(DEBUG);
 				break;
 			default:
				screen = new VirtualScreen(DEBUG);
 		}
 		
 		deviceManager = new DeviceManager(screen, DEBUG, config.getInt("maxDevices"));
 		wheels = new Wheels(deviceManager);

 		double[] baseData = {config.getDouble("wheelbase"), config.getDouble("track"), config.getDouble("maxForwardSpeed"), config.getDouble("maxRotationalSpeed")};

 		controller = new GameController(
						wheels, 
						screen, 
						convertStringArray(config.getJSONArray("compNames")), 
						convertIntArray(config.getJSONArray("anIDs")), 
						(config.getInt("zInversion") == 1),
						config.getString("defaultContSys"), 
						config.getDouble("minHoldTime"),
						baseData
					);
		String controlDebug = controller.init();
		if (DEBUG){
		//TODO decide if still want this	screen.printLine(controlDebug);
		}
		if (controlDebug.startsWith("Error")){
			screen.printLine("Controller Initialisation Error");
			screen.printLine(controlDebug);
		} else if (config.getInt("configured") == 0){
		        CalibData data = controller.configure(screen);
			config.put("configured", 1);
			config.put("compNames", convertStrings(data.compNames));
			config.put("anIDs", convertInts(data.anIds));
			config.put("zInversion", data.zInv);
			try{
				File newConfig = new File(configFilePath);
				FileWriter out = new FileWriter(newConfig);
				config.write(out);
				out.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		screen.updateWheelData(wheels.getVels(), wheels.getFaultStates());
	}
	
	public boolean hasController(){
		return (controller.hasControl());
	}

	
	public void run(){
		while(controller.poll()){
			controller.scanController();
			controller.updateWheels();
			screen.updateWheelData(wheels.getVels(), wheels.getFaultStates());
			screen.updateCouchData(wheels.getMode(), wheels.getIdiot(), wheels.getEStop());
		}
		screen.printLine("Controller Disconnected");
	}
	
	private int[] convertIntArray(JSONArray array){
		int[] result = new int[array.length()];
		for(int i = 0; i < array.length(); i++){
			result[i] = array.getInt(i);
		}
		return result;
	}
	
	private String[] convertStringArray(JSONArray array){
		String[] result = new String[array.length()];
		for(int i = 0; i < array.length(); i++){
			result[i] = array.getString(i);
		}
		return result;
	}

	private JSONArray convertStrings(String[] strings){
		String allStrings = strings[0];
		int numStrings = strings.length;
		for(int i = 1; i < numStrings; i++){
			allStrings = allStrings.concat(", " + strings[i]);
		}
		return CDL.rowToJSONArray(new JSONTokener(allStrings));
	}

	private JSONArray convertInts(int[] ints){
		String allInts = Integer.toString(ints[0]);
		int numInts = ints.length;
		for(int i = 1; i < numInts; i++){
			allInts = allInts.concat(", " + ints[i]);
		}
		return CDL.rowToJSONArray(new JSONTokener(allInts));
	}

}
