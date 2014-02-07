public class Wheels {
	
	public static final int LF = 0;
	public static final int RF = 1;
	public static final int LR = 2;
	public static final int RR = 3;
	
	private double[] vel = new double[4];
	private String[] faultStatus= new String[4];
	private Motors motors;
	

	
	//Note: this assumes the front of the couch is the way the passengers are facing
	// _______________________________________
	// |LF								   RF |
	// |									  |
	// |									  |
	// |									  |
	// |LR								   RR |
	// |______________________________________|
	
	
	public Wheels(DeviceManager deviceManager){
		motors = new Motors(deviceManager);
		for(int i = 0; i < 4; i++){
			vel[i] = 0;
			faultStatus[i] = "OK";
		}
	}
	
	//Getters and Setters
	public void setVel(int index, double velocity){
		vel[index] = velocity;

	}
	
	public void setVels(double[] velocity){
		vel = velocity;

	}
	
	public double[] getVels(){
		double[] vels = vel;
		return vels;
	}
	
	public String[] getFaultStates(){
		return faultStatus;
	}
	
	//Other Functions
	
	public void updateMotors(){
		double[] velocities = new double[4];
		for(int i = 0; i < 4; i++){
			velocities[i] = vel[i];
		}
		int result = motors.update(velocities);
		faultStatus = motors.getFaultStrings();
		if(result != Motors.OK){
			System.out.println("DATA ERROR ON UPDATE");
		}
	}
	
	public void resetMotors(){
		for(int i = 0; i < 4; i++){
			vel[i] = 0;
		}
		int result = motors.reset();
		for(int i = 0; i < 4; i++){
			vel[i] = 0;
		}
		updateMotors();
		if(result == Motors.OK){
			return;
		} else {
			//TODO figure out what to do
			System.out.print("CANNOT RESET");
			if(result == Motors.NO_MCS){
				System.out.print(" No controllers connected");
			}
			System.out.print("\n");
		}
	}
	

	
}
