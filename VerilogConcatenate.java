// This module takes two inputs and makes one output
// It is responsible for the data init for its output and for assigning values to its output
public class VerilogConcatenate implements GenerateVerilog {

	public VerilogConcatenate() {}
	
	private Data getOut(Node n) {
		Util.ASSERT(n.getSinks().size() == 1, "ERROR: VerilogConcatenate should have one output: " + n.getFullName());
		return n.getSinks().get(0);
	}
	
	private Data getFirst(Node n) {
		boolean hasSwitch = false;
		Data swtch = null;
		for (Data d : n.getSources()) {
			if (d.hasSpecialTag(n.getFullName() + "_first")) {
				hasSwitch = true;
				swtch = d;
				break;
			}
		}
		Util.ASSERT(hasSwitch, "ERROR: One of VerilogConcatenate's inputs must be tagged as first: " + n.getFullName());
		return swtch;
	}
	
	private Data getSecond(Node n) {
		boolean hasSwitch = false;
		Data swtch = null;
		for (Data d : n.getSources()) {
			if (d.hasSpecialTag(n.getFullName() + "_second")) {
				hasSwitch = true;
				swtch = d;
				break;
			}
		}
		Util.ASSERT(hasSwitch, "ERROR: One of VerilogConcatenate's inputs must be tagged as second: " + n.getFullName());
		return swtch;
	}

	public String getIOPorts(Node n) {
		return null;
	}
	
	public String getDataInit(Node n) {
		Data out = getOut(n);
		if (out.getLen() - 1 != 0) {
			return "wire [" + (out.getLen() - 1) + ":0] " + out.getVerilog() + ";";
		} else {
			return "wire " + out.getVerilog() + ";";
		}
	}
	
	public String getAssignVals(Node n) {
		Data out = getOut(n);
		Data first = getFirst(n);
		Data second = getSecond(n);
		return "assign " + out.getVerilog() + " = {" + first.getVerilog() + ", " + second.getVerilog() + "};";
	}
	
	public String getClock(Node n) { // null/empty if none
		return null;
	}
	
	public String getDelayLoop(Node n) { // null/empty if none
		return null;
	}

}