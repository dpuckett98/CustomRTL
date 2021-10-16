public class VerilogString implements GenerateVerilog {
	
	private String val;
	
	public VerilogString(String val) {
		this.val = val;
	}
	
	public String getVerilog(Node n) {
		Util.ASSERT(n.getSources().size() == 0, "ERROR: VerilogString must have exactly zero sources");
		Util.ASSERT(n.getSinks().size() == 1, "ERROR: VerilogString must have exactly one sink");
		return "assign " + n.getSinks().get(0).getVerilog() + " = " + val;
	}
	
}