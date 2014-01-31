import net.java.games.input.*;

public class GameController{
	//TODO screen remove later
	private ComponentList compID;
	private Controller cont;
	private ControlSys contSys;
	private EventQueue events;
	private int numAnalogEvents;
	private Component[] comps;
	
	//TODO remove later this is just for testing
	public int numEvents;
	
	private double MIN_HOLD_TIME;
	
	//TODO get rid of
	private static final int ANALOG_NOISE_FILTER = 20;
	
	//TODO remove screen
	public GameController(Wheels wheels, Display screen, String[] compNames, int[] anIDs, String defaultScheme, double holdTime, double[] baseData){
		compID = new ComponentList(compNames, anIDs);
		numEvents = 0;
		numAnalogEvents = 0;
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
		screen.printLine("Configuring Controller");
		String[] compNames = new String[16];
		compNames[0] = " ";
		Event current = new Event();
		Component nextThing;
		screen.printLine("Press A");
		while(compNames[0].equals(" ")){
			cont.poll();
			while(events.getNextEvent(current)){
				nextThing = current.getComponent();
				if (nextThing != null && !nextThing.isAnalog()){
					compNames[0] = nextThing.getName();
				}
			}

		}
		screen.printLine("A has the component name "+ compNames[0]);
	}
	
	public void updateWheels(){
		contSys.update(comps[compID.AXIS_LX].getPollData(), 
						comps[compID.AXIS_LY].getPollData(), 
						comps[compID.AXIS_RX].getPollData(), 
						comps[compID.AXIS_RY].getPollData(), 
						comps[compID.AXIS_Z].getPollData(), 
						comps[compID.AXIS_HAT].getPollData());
	}
	
	public void changeSystem(){
		//TODO
		//possibly add constructors or save old systems (but reinitialise velocities etc) to save time loading all base data etc
	}
}
