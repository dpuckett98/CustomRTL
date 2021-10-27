public class Clock extends Data {
	
	private int numTimestepsPerFlip;
	
	public Clock(int numTimestepsPerFlip) {
		super(1);
		this.numTimestepsPerFlip = numTimestepsPerFlip;
		isClock = true;
	}
	
	public int getNumTimestepsPerFlip() {
		return numTimestepsPerFlip;
	}
	
	@Override
	public String getVerilog() {
		return "Clock_" + numTimestepsPerFlip + "_" + getID();
	}
	
}