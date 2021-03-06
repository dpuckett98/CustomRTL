// This module takes two inputs and makes one output
// It is responsible for the data init for its output and for assigning values to its output
public class VerilogOperation implements GenerateVerilog {

	private String op;

	public VerilogOperation(String op) {
		this.op = op;
	}
	
	private Data getOutData(Node n) {
		Util.ASSERT(n.getSinks().size() == 1, "ERROR: VerilogOperation should have one output: " + n.getFullName());
		return n.getSinks().get(0);
	}

	public String getIOPorts(Node n) {
		return null;
	}
	
	public String getDataInit(Node n) {
		Data out = getOutData(n);
		if (out.getLen() - 1 != 0) {
			return "wire [" + (out.getLen() - 1) + ":0] " + out.getVerilog() + ";";
		} else {
			return "wire " + out.getVerilog() + ";";
		}
	}
	
	public String getAssignVals(Node n) {
		Data out = getOutData(n);
		Util.ASSERT(n.getSources().size() == 2, "ERROR: VerilogOperation should have two inputs");
		return "assign " + out.getVerilog() + " = " + n.getSources().get(0).getVerilog() + " " + op + " " + n.getSources().get(1).getVerilog() + ";";
	}
	
	public String getClock(Node n) { // null/empty if none
		return null;
	}
	
	public String getDelayLoop(Node n) { // null/empty if none
		return null;
	}

}