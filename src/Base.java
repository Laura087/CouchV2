/**
 * 
 * @author Laura
 *
 */
public class Base {
	private Wheels wheels;
	private double Vx;
	private double Vy;
	private double w;
	private double speed;
	private double wheelDist;
	
	//TODO set these
	private double WHEELBASE; 
	private double TRACK;
	private double MAX_V;
	private double MAX_W;
	
	
	public Base(Wheels wheels, double[] baseData){
		Vx = 0;
		Vy = 0;
		w = 0;
		speed = 0;
		
		WHEELBASE = baseData[0];
		TRACK = baseData[1];
		MAX_V = baseData[2];
		MAX_W = baseData[3];
		
		wheelDist = TRACK/2 + WHEELBASE/2;
		
		this.wheels = wheels;
	}
	
	public void setVx(double Vx){
		this.Vx = Vx;
	}
	
	public void setVy(double Vy){
		this.Vy = Vy;
	}
	
	public void setw(double w){
		this.w = w*MAX_W;
	}
	
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	public double getVx(){
		return Vx;
	}
	
	public double getVy(){
		return Vy;
	}
	
	public double getw(){
		return w;
	}
	
	public void updateWheels(){
		double rot = w*(wheelDist);
		double[] vels = new double[4];
		

		
		
		vels[Wheels.LF] = Vx + Vy - rot;
		vels[Wheels.RF] = Vx - Vy + rot;
		vels[Wheels.LR] = Vx - Vy - rot;
		vels[Wheels.RR] = Vx + Vy + rot;
		vels = scale(vels);

				
		wheels.setVel(wheels.LF, vels[Wheels.LF]);
		wheels.setVel(wheels.RF, vels[Wheels.RF]);
		wheels.setVel(wheels.LR, vels[Wheels.LR]);
		wheels.setVel(wheels.RR, vels[Wheels.RR]);

		wheels.updateMotors();
	}
	
	public void resetMotors(){
		wheels.resetMotors();
	}

	private double[] scale(double[] vels){
		double largest = 0;
		for(int i = 0; i < 4; i++){
			if(vels[i] > largest || vels[i] < -1*largest){
				largest = Math.abs(vels[i]);
			}
		}
		if(largest > 1){
			for(int i = 0; i < 4; i++){
				vels[i] /= largest;
			}
		}
		for(int i = 0; i < 4; i++){
			vels[i] *= MAX_V*speed;
		}
		return vels;
	}
}
