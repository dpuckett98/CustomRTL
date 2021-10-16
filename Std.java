import java.util.ArrayList;

public class Std {
	
	public static Data ADD(Data a, Data b) {
		// input validation
		Util.ASSERT(a.getLen() == b.getLen() || a.isEmpty() || b.isEmpty(), "ADD operands must have equal lengths");
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node add = new Node("ADD", false, -1, false, false, new VerilogOperation("+"));
		Graph.addNode(add);
		// data source/sink
		out.setSource(add);
		a.addSink(add);
		b.addSink(add);
		// node source/sink
		add.addSource(a);
		add.addSource(b);
		add.addSink(out);
		return out;
	}
	
	public static Data MULT(Data a, Data b) {
		// input validation
		Util.ASSERT(a.getLen() == b.getLen() || a.isEmpty() || b.isEmpty(), "MULT operands must have equal lengths");
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node mult = new Node("MULT", false, -1, false, false, new VerilogOperation("*"));
		Graph.addNode(mult);
		// data source/sink
		out.setSource(mult);
		a.addSink(mult);
		b.addSink(mult);
		// node source/sink
		mult.addSource(a);
		mult.addSource(b);
		mult.addSink(out);
		return out;
	}
	
	public static Data MUX(Data a, Data b, Data swtch) {
		// input validation
		Util.ASSERT(a.getLen() == b.getLen() || a.isEmpty() || b.isEmpty(), "MUX operands must have equal lengths");
		Util.ASSERT(swtch.getLen() == 1, "MUX switch must be 1 bit long");
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node mux = new Node("MUX", false, -1, false, false, new VerilogOperation("???"));
		Graph.addNode(mux);
		// data source/sink
		out.setSource(mux);
		a.addSink(mux);
		b.addSink(mux);
		swtch.addSink(mux);
		// node source/sink
		mux.addSource(a);
		mux.addSource(b);
		mux.addSource(swtch);
		mux.addSink(out);
		return out;
	}
	
	public static Data CONST(int len, int value) {
		// create data & node objects
		Data out = new Data(len);
		Graph.addData(out);
		Node root = new Node("ROOT", false, -1, false, false, new VerilogString("" + value));
		Graph.addNode(root);
		// data source/sink
		out.setSource(root);
		// node source/sink
		root.addSink(out);
		return out;
	}
	
	public static Data DELAY(Data in, Data clk) { // TODO: add clk!!!
		// create data & node objects
		Data out = new Data(in.getLen());
		Graph.addData(out);
		Node delay = new Node("DELAY", true, -1, false, false, new VerilogRelation("<="));
		Graph.addNode(delay);
		// data source/sink
		out.setSource(delay);
		in.addSink(delay);
		clk.addSink(delay);
		// node source/sink
		delay.addSink(out);
		delay.addSource(in);
		delay.addSource(clk);
		return out;
	}
	
	public static void MODULE_BUNDLE(Data[] inputs, Data[] outputs) {
		int moduleID = Graph.getID();
		for (Data d : inputs) {
			// create data & node objects
			Data newD = new Data(d.getLen());
			Graph.addData(newD);
			Node input = new Node("INPUT", false, moduleID, true, false, new VerilogConstant(""));
			Graph.addNode(input);
			// handle connections
			for (Node n : d.getSinks()) {
				// copy original data's sinks to the new data's sinks
				newD.addSink(n);
				// add new data to original data's sink nodes
				n.addSource(newD);
				// remove original data as a source for it's output node
				n.getSources().remove(d);
			}
			// remove all of original data's sinks
			d.getSinks().clear();
			// add original data as a source for this node
			input.addSource(d);
			// add new data as a sink for this node
			input.addSink(newD);
			// add this node as a sink for original data
			d.addSink(input);
			// add this node as a source for new data
			newD.setSource(input);
		}
		for (Data d : outputs) {
			// create data & node objects
			Data newD = new Data(d.getLen());
			Graph.addData(newD);
			Node output = new Node("OUTPUT", false, moduleID, false, true, new VerilogConstant(""));
			Graph.addNode(output);
			// handle connections
			for (Node n : d.getSinks()) {
				// connect all of original data's sinks to new data
				n.addSource(newD);
				newD.addSink(n);
				// disconnect all of the original data's sinks
				n.getSources().remove(d);
			}
			newD.getSinks().clear();
			
			// set output node as sink of original data
			newD.setSource(output);
			output.addSink(newD);
			// set new data as sink of output node
			d.addSink(output);
			output.addSource(d);
		}
	}
	
	public static Data PASS(Data in) {
		// create data & node objects
		Data out = new Data(in.getLen());
		Graph.addData(out);
		Node pass = new Node("PASS", false, -1, false, false, new VerilogRelation("="));
		Graph.addNode(pass);
		// data source/sink
		out.setSource(pass);
		in.addSink(pass);
		// node source/sink
		pass.addSink(out);
		pass.addSource(in);
		return out;
	}
	
}