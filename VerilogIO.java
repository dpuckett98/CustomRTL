public class VerilogIO implements GenerateVerilog {

	private boolean isInput;
	
	public VerilogIO(boolean isInput) {
		this.isInput = isInput;
	}
	
	private Data getData(Node n) {
		if (isInput) {
			Util.ASSERT(n.getSinks().size() == 1, "ERROR: Input IO Port should have one sink");
			return n.getSinks().get(0);
		} else {
			Util.ASSERT(n.getSources().size() == 1, "ERROR: Output IO Port should have one source");
			return n.getSources().get(0);
		}
	}

	public String getIOPorts(Node n) {
		String start = "input wire ";
		if (!isInput) {
			start = "output "; // TODO: verify???
		}
		Data d = getData(n);
		if (d.getLen() - 1 != 0) {
			return start + "[" + (d.getLen() - 1) + ":0] " + d.getVerilog();
		} else {
			return start + d.getVerilog();
		}
	}
	
	public String getDataInit(Node n) {
		/*String type = "wire ";
		Data d = getData(n);
		if (d.getSource().isDelay()) {
			type = "reg ";
		}
		if (d.getLen() - 1 != 0) {
			return type + "[" + (d.getLen() - 1) + ":0] " + d.getVerilog() + ";";
		} else {
			return type + d.getVerilog() + ";";
		}*/
		return null;
	}
	
	public String getAssignVals(Node n) {
		return null;
	}
	
	public String getClock(Node n) { // null/empty if none
		return null;
	}
	
	public String getDelayLoop(Node n) { // null/empty if none
		return null;
	}

}