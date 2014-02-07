public class CalibData{
	public String[] compNames;
	public int[] anIds;
	public int zInv;

	public CalibData(String[] compNames, int[] anIds, boolean zInv){
		this.compNames = compNames;
		this.anIds = anIds;
		if (zInv == true){
		   this.zInv = 1;
		} else {
	           this.zInv = 0;
		}
	}
}
