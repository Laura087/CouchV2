//import net.java.games.input.*;

public class ComponentList {
//	private String[] list = {"A", "B", "X", "Y", "Left Bumper", "Right Bumper", "START", "BACK", "Left Stick", "Right Stick"};
//	public int [] button;
	
	//buttons
	public String A; //A button
	public String B;
	public String X;
	public String Y;
	public String LB;
	public String RB;
	public String START;
	public String BACK;
	public String LS;
	public String RS;
	
	//Axes
	public String LX; //left stick x axis
	public String LY;
	public String RX;
	public String RY;
	public String Z;
	
	//Axis designations
	//Axes
	public int AXIS_LX; //left stick x axis
	public int AXIS_LY;
	public int AXIS_RX;
	public int AXIS_RY;
	public int AXIS_Z;
	public int AXIS_HAT;
	
	//other
	public String HAT;
	

	public ComponentList(String[] compNames, int[] anIDs){
		A = compNames[0];
		B = compNames[1];
		X = compNames[2];
		Y = compNames[3];
		LB = compNames[4];
		RB = compNames[5];
		BACK = compNames[6];
		START = compNames[7];
		LS = compNames[8];
		RS = compNames[9];
		HAT = compNames[10];
		LX = compNames[11];
		LY = compNames[12];
		RX = compNames[13];
		RY = compNames[14];
		Z = compNames[15];
		AXIS_LX = anIDs[0];
		AXIS_LY = anIDs[1];
		AXIS_RX = anIDs[2];
		AXIS_RY = anIDs[3];
		AXIS_Z = anIDs[4];
		AXIS_HAT = anIDs[5];
	}
	
	//TODO allow configuration of controller so you can write to the JSON file
//	public void configure(Controller controller, Display screen){
//
//		controller.poll();
//		EventQueue events = controller.getEventQueue();
//		Component[] component = controller.getComponents();
//		Component currComp;
//		int numComponents = component.length;
//		int numButtons = list.length;
//		Event current = new Event();
//		
//		while (current.getComponent() == null){
//			events.getNextEvent(current);
//		}
//		screen.printLine("found something");
//		for(int i = 0; i < numButtons; i++){
//			screen.printLine("Press " + list[i]);
//			controller.poll();
//			while(!events.getNextEvent(current)){
//				//wait until something happens
//			}
//			currComp = current.getComponent();
//			if (currComp.isAnalog()){
//				for(int j = 0; j < numComponents; j++){
//					if(currComp == component[j]){
//						button[i] = j;
//						screen.printLine("Component number " + j);
//						break;
//					}
//				}
//			} else {
//				i--;
//			}
//		}
//	}
}
