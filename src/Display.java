
public interface Display {
	void print(String s);
	void printLine(String s);
	void print(String s, boolean isDebugging);
	void printLine(String s, boolean isDebugging);
	void updateWheelData(double[] vel, String[] status);
}
