
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import javax.usb.UsbDevice;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


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
			screen.printLine(controlDebug);
		}
		if (controlDebug.startsWith("Error")){
			screen.printLine("Controller Initialisation Error");
		}	else if (config.getInt("configured") == 0){
			controller.configure(screen);
		}
		
		screen.updateWheelData(wheels.getVels(), wheels.getFaultStates());
	}
	
	public boolean hasController(){
		return (controller.hasControl());
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
	
	public void run(){
		while(controller.poll()){
			controller.scanController();
			controller.updateWheels();
			screen.updateWheelData(wheels.getVels(), wheels.getFaultStates());
		}
		screen.printLine("Controller Disconnected");
	}
}
