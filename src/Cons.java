
public class Cons implements Display {
	boolean debug;
	
	public Cons(boolean debug){
		this.debug = debug;
	}
	
	public void print(String s){
		System.out.print(s);
	}

	@Override
	public void printLine(String s) {
		System.out.println(s);		
	}

	@Override
	public void updateWheelData(double[] vel, String[] status) {

	}

	@Override
	public void print(String s, boolean isDebugging) {
		if(debug){
			System.out.println(s);
		}
	}

	@Override
	public void printLine(String s, boolean isDebugging) {
		if(debug){
			System.out.println(s);
		}		
	}
}
