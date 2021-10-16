public class VerilogRelation implements GenerateVerilog {
	
	private String val;
	
	public VerilogRelation(String val) {
		this.val = val;
	}
	
	public String getVerilog(Node n) {
		Util.ASSERT(n.getSources().size() == 1, "ERROR: VerilogRelation must have exactly one source");
		Util.ASSERT(n.getSinks().size() == 1, "ERROR: VerilogRelation must have exactly one sink");
		return "assign " + n.getSinks().get(0).getVerilog() + " " + val + " " + n.getSources().get(0).getVerilog();
	}
	
}