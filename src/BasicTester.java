
public class BasicTester implements ControlSys {

	private Wheels wheels;
	
	//TODO remove screen
	private Display screen;
	
	public BasicTester(Wheels wheels, Display screen) {
		this.wheels = wheels;
		this.screen = screen;
	}

	@Override
	public int update(float LX, float LY, float RX, float RY, float Z, float HAT) {
		// TODO Auto-generated method stub
		wheels.updateMotors();
		return 0;
	}

	@Override
	public void APressed() {
		screen.printLine("A Pressed");
	}

	@Override
	public void BPressed() {
		screen.printLine("B Pressed");
	}

	@Override
	public void XPressed() {
		screen.printLine("X Pressed");
		
	}

	@Override
	public void YPressed() {
		screen.printLine("Y Pressed");
		
	}

	@Override
	public void LBPressed() {
		screen.printLine("LB Pressed");

		
	}

	@Override
	public void RBPressed() {
		screen.printLine("RB Pressed");

		
	}

	@Override
	public void BACKPressed() {
		screen.printLine("BACK Pressed");

		
	}

	@Override
	public void STARTPressed() {
		screen.printLine("START Pressed");
		
	}

	@Override
	public void LSPressed() {
		screen.printLine("LS Pressed");
		
	}

	@Override
	public void RSPressed() {
		screen.printLine("RS Pressed");
		
	}

	@Override
	public void AReleased() {
		screen.printLine("A Released");
		
	}

	@Override
	public void BReleased() {
		screen.printLine("B Released");
		
	}

	@Override
	public void XReleased() {
		screen.printLine("X Released");
		
	}

	@Override
	public void YReleased() {
		screen.printLine("Y Released");
		
	}

	@Override
	public void LBReleased() {
		screen.printLine("LB Released");
		
	}

	@Override
	public void RBReleased() {
		screen.printLine("RB Released");
		
	}

	@Override
	public void BACKReleased() {
		screen.printLine("BACK Released");
		
	}

	@Override
	public void STARTReleased() {
		screen.printLine("START Released");
		
	}

	@Override
	public void LSReleased() {
		screen.printLine("LS Released");
		
	}

	@Override
	public void RSReleased() {
		screen.printLine("RS Released");
		
	}

	@Override
	public void HATChange(float position) {
		for(int i = 0; i < 4; i ++){
			wheels.setVel(i, 0.0);
		}
		if(position == LEFT){
			wheels.setVel(0, 0.2);
			screen.printLine("LEFT");
		} else if(position == RIGHT){
			wheels.setVel(3, 0.2);
			screen.printLine("RIGHT");
		} else if(position == UP){
			wheels.setVel(1, 0.2);
			screen.printLine("UP");
		} else if(position == DOWN){
			wheels.setVel(2, 0.2);
			screen.printLine("DOWN");
		} else if(position == OPEN){
			screen.print("OPEN");
		} else if (position == UPLEFT){
			screen.printLine("UP LEFT");
			wheels.setVel(1, 0.2);
			wheels.setVel(0, 0.2);
		} else if (position == UPRIGHT){
			screen.printLine("UP RIGHT");
			wheels.setVel(1, 0.2);
			wheels.setVel(3, 0.2);
		} else if (position == DOWNLEFT){
			screen.printLine("DOWN LEFT");
			wheels.setVel(2, 0.2);
			wheels.setVel(0, 0.2);
		} else if (position == DOWNRIGHT){
			screen.printLine("DOWN RIGHT");
			wheels.setVel(2, 0.2);
			wheels.setVel(3, 0.2);
		}
		
	}	

}
