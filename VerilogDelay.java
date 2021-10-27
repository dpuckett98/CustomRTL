// This module takes two inputs and makes one output
// One input must be a clock; the output is a register
public class VerilogDelay implements GenerateVerilog {

	public VerilogDelay() {
		
	}
	
	/*private Data getDataClock(Node n) {
		boolean hasDelay = false;
		Data delay = null;
		for (Data d : n.getSinks()) {
			if (d.isClock()) {
				hasDelay = true;
				delay = d;
				break;
			}
		}
		Util.ASSERT(hasDelay, "ERROR: Delay must have a clock input: " + n.getFullName());
		return delay;
	}*/
	
	private Data getOther(Node n) {
		boolean hasOther = false;
		Data other = null;
		for (Data d : n.getSources()) {
			if (!d.isClock()) {
				hasOther = true;
				other = d;
				break;
			}
		}
		Util.ASSERT(hasOther, "ERROR: Delay must have a non-clock input: " + n.getFullName());
		return other;
	}
	
	private Data getOut(Node n) {
		Util.ASSERT(n.getSinks().size() == 1, "ERROR: Delay must have one (and only one) output: " + n.getFullName());
		return n.getSinks().get(0);
	}

	public String getIOPorts(Node n) {
		return null;
	}
	
	public String getDataInit(Node n) {
		String type = "reg ";
		Data out = getOut(n);
		if (out.getLen() - 1 != 0) {
			return "reg [" + (out.getLen() - 1) + ":0] " + out.getVerilog() + ";";
		} else {
			return "reg " + out.getVerilog() + ";";
		}
	}
	
	public String getAssignVals(Node n) {
		return null;
	}
	
	public String getClock(Node n) { // null/empty if none
		boolean hasDelay = false;
		Data delay = null;
		for (Data d : n.getSources()) {
			if (d.isClock()) {
				hasDelay = true;
				delay = d;
				break;
			}
		}
		Util.ASSERT(hasDelay, "ERROR: Delay must have a clock input: " + n.getFullName());
		return delay.getVerilog();
	}
	
	public String getDelayLoop(Node n) { // null/empty if none
		Data out = getOut(n);
		Data other = getOther(n);
		return out.getVerilog() + " <= " + other.getVerilog() + ";";
	}

}