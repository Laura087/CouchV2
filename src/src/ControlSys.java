
public interface ControlSys {
	
	//Hat Positions

	public double OPEN = 0.0;

	public double UP = 0.25;
	public double DOWN = 0.75;
	public double LEFT = 1.0;
	public double RIGHT = 0.5;
	
	public double UPLEFT = 0.125;
	public double UPRIGHT = 0.375;
	public double DOWNRIGHT = 0.625;
	public double DOWNLEFT = 0.875;
	
	
	//the arguments should be the stick & trigger positions
	public int update(float LX, float LY, float RX, float RY, float Z, float HAT);
	
	//Button Methods
	public void APressed();
	public void BPressed();
	public void XPressed();
	public void YPressed();
	public void LBPressed();
	public void RBPressed();
	public void BACKPressed();
	public void STARTPressed();
	public void LSPressed();
	public void RSPressed();
	public void AReleased();
	public void BReleased();
	public void XReleased();
	public void YReleased();
	public void LBReleased();
	public void RBReleased();
	public void BACKReleased();
	public void STARTReleased();
	public void LSReleased();
	public void RSReleased();
	public void HATChange(float position);
	
	//TODO
	//Cheat Code Methods 
		//hardcoded in GameController 
		//activated by holding back button 
			//(so possibly remove that from the above)
}
