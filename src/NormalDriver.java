
public class NormalDriver implements ControlSys{

	private Base base;
	
	public NormalDriver(Wheels wheels, double[] baseData) {
		base = new Base(wheels, baseData);
	}

	@Override
	public int update(float LX, float LY, float RX, float RY, float Z, float HAT) {
		// TODO Auto-generated method stub
		if(HAT == OPEN){
			base.setw(RX);
			base.setVx(LX);
			base.setVy(LY*-1);
		}
		
		Z *= -1;
		if(Z < 0.01){
			Z = 0;
		}
		base.setSpeed(Z);
		base.updateWheels();
		//TODO manage noise (see line 245 newControlV2.py)
		return 0;
	}


	@Override
	public void APressed() {
		base.resetMotors();
	}

	@Override
	public void BPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void XPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void YPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void LBPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RBPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void BACKPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void STARTPressed() {
		base.resetMotors();
	}

	@Override
	public void LSPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RSPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void BReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void XReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void YReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void LBReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RBReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void BACKReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void STARTReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void LSReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void RSReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HATChange(float position) {
		if(position == LEFT){
			base.setVx(-1);
			base.setVy(0);
			base.setw(0);
		} else if(position == RIGHT){
			base.setVx(1);
			base.setVy(0);
			base.setw(0);
		} else if(position == UP){
			base.setVx(0);
			base.setVy(1);
			base.setw(0);
		} else if(position == DOWN){
			base.setVx(0);
			base.setVy(-1);
			base.setw(0);
		} else if(position == OPEN){
			//do nothing
		} else if (position == UPLEFT){
			base.setVx(-0.5);
			base.setVy(0.5);
			base.setw(0);
		} else if (position == UPRIGHT){
			base.setVx(0.5);
			base.setVy(0.5);
			base.setw(0);
		} else if (position == DOWNLEFT){
			base.setVx(-0.5);
			base.setVy(-0.5);
			base.setw(0);
		} else if (position == DOWNRIGHT){
			base.setVx(0.5);
			base.setVy(-0.5);
			base.setw(0);
		}
		
	}

}
