
public class SimpleDriver implements ControlSys{

	private Base base;
	
	public SimpleDriver(Wheels wheels, double[] baseData) {
		base = new Base(wheels, baseData);
	}
	public SimpleDriver(Base base){
		this.base = base;
	}

	@Override
	public int update(float LX, float LY, float RX, float RY, float Z, float HAT) {
		// TODO Auto-generated method stub
		base.updateWheels();
		return 0;
	}

	@Override
	public void APressed() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

}
