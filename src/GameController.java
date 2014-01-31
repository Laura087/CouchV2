import net.java.games.input.*;

public class GameController{
	//TODO screen remove later
	private ComponentList compID;
	private Controller cont;
	private ControlSys contSys;
	private EventQueue events;
	private int numAnalogEvents;
	private Component[] comps;
	private boolean zInverted;
	
	//TODO remove later this is just for testing
	public int numEvents;
	
	private double MIN_HOLD_TIME;
	
	//TODO get rid of
	private static final int ANALOG_NOISE_FILTER = 20;
	
	//TODO remove screen
	public GameController(Wheels wheels, Display screen, String[] compNames, int[] anIDs, boolean zInverted, String defaultScheme, double holdTime, double[] baseData){
		compID = new ComponentList(compNames, anIDs);
		numEvents = 0;
		numAnalogEvents = 0;
		this.zInverted = zInverted;
		MIN_HOLD_TIME = holdTime;
		
		if(defaultScheme.equals("Basic Tester")){
			contSys = new BasicTester(wheels, screen);
		} else if (defaultScheme.equals("Normal Driver")){
			contSys = new NormalDriver(wheels, baseData);
		} else if (defaultScheme.equals("Simple Driver")){
			contSys = new SimpleDriver(wheels, baseData);
		} else {
			contSys = new NormalDriver(wheels, baseData);
		}
	}
	
	public String init(){
		String result;
		ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment();
		Controller[] cs = ce.getControllers();
		cont = null;
		
		for (int i = 0; i < cs.length; i++){
			if (cs[i].getType() == Controller.Type.GAMEPAD){
				if(cont == null){
					cont = cs[i];
				} else {
					result = "Error: Multiple Controllers Found";
					return result;
				}
			}
		}
		if(cont == null){
			result = "Error: No Controllers Found";
		} else {
			result = "Controller Found: " + "\n\t" + cont.getName() + ", " + cont.getType();
			 /* Get this controllers components (buttons and axis) */
		    Component[] components = cont.getComponents();
		    result = result.concat("\n\tComponent Count: "+components.length);
		    for(int j=0;j<components.length;j++){
		        result = result.concat("\n\t\tComponent "+j+": "+components[j].getName());
		    }
			cont.poll();
			events = cont.getEventQueue();
			comps = cont.getComponents();
		}
		return result;
	}
	
	public boolean hasControl(){
		return (cont != null);
	}
	
	public boolean poll(){
		return cont.poll();
	}
	
	public void scanController(){
		Component nextThing;
		String name;
		
		Event current = new Event();
		while(events.getNextEvent(current)){
			nextThing = current.getComponent();
			if(nextThing != null){
				name = nextThing.getName();
				if (!nextThing.isAnalog()){
					if (name.equals(compID.A)){
						if (nextThing.getPollData() == 1){
							contSys.APressed();

						} else {
							contSys.AReleased();

						}
					} else if (name.equals(compID.B)){
						if (nextThing.getPollData() == 1){
							contSys.BPressed();

						} else {
							contSys.BReleased();

						}
					} else if (name.equals(compID.X)){
						if (nextThing.getPollData() == 1){
							contSys.XPressed();

						} else {
							contSys.XReleased();

						}
					} else if (name.equals(compID.Y)){
						if (nextThing.getPollData() == 1){
							contSys.YPressed();

						} else {
							contSys.YReleased();

						}
					} else if (name.equals(compID.LB)){
						if (nextThing.getPollData() == 1){
							contSys.LBPressed();

						} else {
							contSys.LBReleased();

						}
					} else if (name.equals(compID.RB)){
						if (nextThing.getPollData() == 1){
							contSys.RBPressed();

						} else {
							contSys.RBReleased();

						}
					} else if (name.equals(compID.START)){
						if (nextThing.getPollData() == 1){
							contSys.STARTPressed();

						} else {
							contSys.STARTReleased();

						}
					} else if (name.equals(compID.BACK)){
						if (nextThing.getPollData() == 1){
							contSys.BACKPressed();

						} else {
							contSys.BACKReleased();

						}
					} else if (name.equals(compID.LS)){
						if (nextThing.getPollData() == 1){
							contSys.LSPressed();

						} else {
							contSys.LSReleased();

						}
					} else if (name.equals(compID.RS)){
						if (nextThing.getPollData() == 1){
							contSys.RSPressed();

						} else {
							contSys.RSReleased();

						}
					} else if (name.equals(compID.HAT)) {
						//screen.print("HAT");
						contSys.HATChange(nextThing.getPollData());
						//screen.printLine(Float.toString(nextThing.getPollData()));
					}
					numEvents++;
				}else {
					numAnalogEvents++;
					if(numAnalogEvents >= ANALOG_NOISE_FILTER){
						numEvents++;
						//screen.printLine(Integer.toString(numEvents));
						//screen.printLine(current.getComponent().toString() + ": " + Float.toString(current.getValue()));
						numAnalogEvents = 0;
						
					}
				}
			} 
		}
		//Do i want to update sticks every time they change or after an elapsed time?
	}
	
	public void configure(Display screen){
		int i, j, k;
		screen.printLine("Configuring Controller");
		String[] compNames = new String[16];
		int[] anIds = new int[6];
		int[] inverted = {1, 1, 1, 1, 1, 1};
		Event current = new Event();
		Component nextThing;
		String[] descriptors = {"A", "B", "X", "Y", "Left Bumper", "Right Bumper", "BACK", "START", "Left Stick", "Right Stick", "D-Pad to the left", "Left X right", "Left Y down", "Right X right", "Right Y down", "Right Trigger in"};
		int numComps = descriptors.length;
		Component[] components = cont.getComponents();
		for(i = 0; i < 10; i++){
		   compNames[i] = " ";
		   screen.printLine("Press " + descriptors[i]);
		   screen.printLine("");
		   while(compNames[i].equals(" ")){
		  	cont.poll();
			while(events.getNextEvent(current)){
				nextThing = current.getComponent();
				if (nextThing != null && !nextThing.isAnalog() && nextThing.getPollData() == 1.0){
					compNames[i] = nextThing.getName();
				}
			}

		   }
		   screen.printLine(descriptors[i] + " has the component name "+ compNames[i]);
		   screen.printLine("");

		}
		k = 0;
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cont.poll();
		while(i < numComps){
			compNames[i] = " ";
			screen.printLine("Move " + descriptors[i]);
			screen.printLine("");
			while(compNames[i].equals(" ")){
			   cont.poll();
			   while(events.getNextEvent(current)){
			      nextThing = current.getComponent();
				  if (nextThing != null /*&& nextThing.isAnalog()*/ && Math.abs(nextThing.getPollData()) > 0.5){
						compNames[i] = nextThing.getName();
						j = 0;
						while(nextThing != components[j]){
							j++;
						}
						anIds[k] = j;
						if(nextThing.getPollData() < 0){
							zInverted = true;
						}
						k++;
						screen.printLine("ID is " + j);
						screen.printLine("");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				  }
				}
		  }
   		 i++;

	  }
		compID = new ComponentList(compNames, anIds);
	}
	
	public void updateWheels(){
		float zValue;
		if(zInverted){
		   zValue = 1.0f - (comps[compID.AXIS_Z].getPollData()-1.0f)/-2.0f;
		} else {
		   zValue = comps[compID.AXIS_Z].getPollData();
			
		   zValue *= -1;
		   if(zValue < 0.01){
		      zValue = 0;
		   }
		}
		contSys.update(comps[compID.AXIS_LX].getPollData(), 
						comps[compID.AXIS_LY].getPollData(), 
						comps[compID.AXIS_RX].getPollData(), 
						comps[compID.AXIS_RY].getPollData(), 
						zValue, 
						comps[compID.AXIS_HAT].getPollData());
	}
	
	public void changeSystem(){
		//TODO
		//possibly add constructors or save old systems (but reinitialise velocities etc) to save time loading all base data etc
	}
}
