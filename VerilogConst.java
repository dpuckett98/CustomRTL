// This module has one output and no inputs; it just defines a variable and then assigns it a constant value
public class VerilogConst implements GenerateVerilog {

	private String val;

	public VerilogConst(String val) {
		this.val = val;
	}
	
	private Data getOutData(Node n) {
		Util.ASSERT(n.getSinks().size() == 1, "ERROR: VerilogConst should have one output: " + n.getFullName());
		return n.getSinks().get(0);
	}

	public String getIOPorts(Node n) {
		return null;
	}
	
	public String getDataInit(Node n) {
		String type = "wire ";
		Data out = getOutData(n);
		if (out.getLen() - 1 != 0) {
			return "wire [" + (out.getLen() - 1) + ":0] " + out.getVerilog() + ";";
		} else {
			return "wire " + out.getVerilog() + ";";
		}
	}
	
	public String getAssignVals(Node n) {
		Data out = getOutData(n);
		return "assign " + out.getVerilog() + " = " + val + ";";
	}
	
	public String getClock(Node n) { // null/empty if none
		return null;
	}
	
	public String getDelayLoop(Node n) { // null/empty if none
		return null;
	}

}