public class VerilogConstant implements GenerateVerilog {
	
	private String symbol;
	
	public VerilogConstant(String symbol) {
		this.symbol = symbol;
	}
	
	public String getVerilog(Node n) {
		return symbol;
	}
	
}