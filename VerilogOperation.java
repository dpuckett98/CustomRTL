public class VerilogOperation implements GenerateVerilog {
	
	private String symbol;
	
	public VerilogOperation(String symbol) {
		this.symbol = symbol;
	}
	
	public String getVerilog(Node n) {
		Util.ASSERT(n.getSources().size() == 2, "ERROR: VerilogOperation must have exactly two sources");
		Util.ASSERT(n.getSinks().size() == 1, "ERROR: VerilogOperation must have exactly one sink");
		return "assign " + n.getSinks().get(0).getVerilog() + " = " + n.getSources().get(0).getVerilog() + " " + symbol + " " + n.getSources().get(1).getVerilog();
	}
	
}