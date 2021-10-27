// This module takes two inputs and makes one output
// It is responsible for the data init for its output and for assigning values to its output
public class VerilogSingleOperation implements GenerateVerilog {

	private String op;

	public VerilogSingleOperation(String op) {
		this.op = op;
	}
	
	private Data getOut(Node n) {
		Util.ASSERT(n.getSinks().size() == 1, "ERROR: VerilogSingleOperation should have one output: " + n.getFullName());
		return n.getSinks().get(0);
	}
	
	private Data getIn(Node n) {
		Util.ASSERT(n.getSources().size() == 1, "ERROR: VerilogSingleOperation should have one input: " + n.getFullName());
		return n.getSources().get(0);
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
		Data in = getIn(n);
		return "assign " + out.getVerilog() + " = " + op + in.getVerilog() + ";";
	}
	
	public String getClock(Node n) { // null/empty if none
		return null;
	}
	
	public String getDelayLoop(Node n) { // null/empty if none
		return null;
	}

}