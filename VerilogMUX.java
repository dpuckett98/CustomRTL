// This module takes three inputs and makes one output
public class VerilogMUX implements GenerateVerilog {

	public VerilogMUX() {
		
	}
	
	private Data getSwitch(Node n) {
		boolean hasSwitch = false;
		Data swtch = null;
		for (Data d : n.getSources()) {
			if (d.hasSpecialTag(n.getFullName() + "_switch")) {
				hasSwitch = true;
				swtch = d;
				break;
			}
		}
		Util.ASSERT(hasSwitch, "ERROR: One of MUX's inputs must be tagged as a switch: " + n.getFullName());
		return swtch;
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
		Util.ASSERT(hasSwitch, "ERROR: One of MUX's inputs must be tagged as first: " + n.getFullName());
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
		Util.ASSERT(hasSwitch, "ERROR: One of MUX's inputs must be tagged as second: " + n.getFullName());
		return swtch;
	}
	
	private Data getOut(Node n) {
		Util.ASSERT(n.getSinks().size() == 1, "ERROR: MUX must have one output: " + n.getFullName());
		return n.getSinks().get(0);
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
		Data swtch = getSwitch(n);
		Util.ASSERT(swtch.getLen() == 1, "ERROR: MUX's switch must be one bit long: " + n.getFullName());
		Data first = getFirst(n);
		Data second = getSecond(n);
		Data out = getOut(n);
		Util.ASSERT(first.getLen() == second.getLen() && first.getLen() == out.getLen(), "ERROR: MUX's inputs and output must be the same length: " + n.getFullName());
		return "assign " + out.getVerilog() + " = " + swtch.getVerilog() + " ? " + second.getVerilog() + " : " + first.getVerilog() + ";";
	}
	
	public String getClock(Node n) { // null/empty if none
		return null;
	}
	
	public String getDelayLoop(Node n) { // null/empty if none
		return null;
	}

}