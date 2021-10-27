public class Main {
	
	public static void main(String[] args) {
		
		Graph.init();
		Clock clk = new Clock(1);
		Data rst = Std.CONST(1, 0);
		rst.setTag("rst");
		Data a = Std.CONST(5, 0);
		a.setTag("a");
		Data b = Std.CONST(5, 1);
		b.setTag("b");
		Data res = MAC(clk, a, b, rst);
		res.setTag("res");
		Data trueRes = math(a, b, res);
		Graph.parseModules(false, true, true);
	}
	
	public static Data math(Data in1, Data in2, Data in3) {
		in2.setTag("in2");
		in3.setTag("in3");
		return Std.ADD(Std.NOT(in1), Std.SPLIT(Std.CONCAT(in2, in3), 0, 4));
	}
	
	public static Data MAC(Clock clk, Data in1, Data in2, Data rst) {
		Data mult = Std.MULT(in1, in2);
		mult.setTag("mult");
		Data sum = Data.EMPTY();
		sum.setTag("sum");
		Data acc = Std.ADD(mult, sum);
		acc.setTag("acc");
		Data val = Std.MUX(acc, Std.CONST(in1.getLen(), 0).setTag("rst_val"), rst);
		val.setTag("val");
		sum.fill(Std.DELAY(val, clk));
		Data out = Std.PASS(sum);
		Std.MODULE_BUNDLE_2(new Data[]{clk, in1, in2, rst}, new Data[]{out}, "MAC");
		return out;
	}
	
}