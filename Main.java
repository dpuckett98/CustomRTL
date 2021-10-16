

public class Main {
	
	public static void main(String[] args) {
		Graph.init();
		Data crystal = Std.CONST(1, 0);
		Data rst = Std.CONST(1, 0);
		Data clk = CLKGEN(crystal, rst, 0);
		Data a = Std.CONST(5, 0);
		Data b = Std.CONST(5, 1);
		Data res = MAC(clk, a, b, rst);
		Std.MODULE_BUNDLE(new Data[]{crystal, rst, a, b}, new Data[]{res});
		
		Graph.printGraph();
		System.out.println();
		Graph.visualizeGraph();
		System.out.println();
		Graph.parseGraph();
		//System.out.println(Graph.generateVerilog());
	}
	
	public static Data CLKGEN(Data crystal, Data rst, int initialVal) {
		Data init = Std.CONST(1, initialVal);
		Data clk = Data.EMPTY();
		clk.fill(Std.DELAY(Std.MUX(clk, init, rst), crystal));
		Std.MODULE_BUNDLE(new Data[]{crystal, rst}, new Data[]{clk});
		return clk;
	}
	
	public static Data MAC(Data clk, Data in1, Data in2, Data rst) {
		Data mult = Std.MULT(in1, in2);
		Data sum = new Data(-1);
		Data acc = Std.ADD(mult, sum);
		Data val = Std.MUX(acc, Std.CONST(in1.getLen(), 0), rst);
		sum.fill(Std.DELAY(val, clk));
		Std.MODULE_BUNDLE(new Data[]{clk, in1, in2, rst}, new Data[]{sum});
		return sum;
	}
	
}