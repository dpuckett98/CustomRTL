import java.util.ArrayList;

public class Std {
	
	public static Data AND(Data a, Data b) {
		// input validation
		Util.ASSERT(a.getLen() == b.getLen() || a.isEmpty() || b.isEmpty(), "AND operands must have equal lengths");
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node and = Node.createRegular("AND", new VerilogOperation("&"));
		Graph.addNode(and);
		// data source/sink
		out.setSource(and);
		a.addSink(and);
		b.addSink(and);
		// node source/sink
		and.addSource(a);
		and.addSource(b);
		and.addSink(out);
		return out;
	}
	
	public static Data OR(Data a, Data b) {
		// input validation
		Util.ASSERT(a.getLen() == b.getLen() || a.isEmpty() || b.isEmpty(), "OR operands must have equal lengths");
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node or = Node.createRegular("OR", new VerilogOperation("|"));
		Graph.addNode(or);
		// data source/sink
		out.setSource(or);
		a.addSink(or);
		b.addSink(or);
		// node source/sink
		or.addSource(a);
		or.addSource(b);
		or.addSink(out);
		return out;
	}
	
	public static Data NOT(Data a) {
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node not = Node.createRegular("NOT", new VerilogSingleOperation("~"));
		Graph.addNode(not);
		// data source/sink
		out.setSource(not);
		a.addSink(not);
		// node source/sink
		not.addSource(a);
		not.addSink(out);
		return out;
	}
	
	public static Data XOR(Data a, Data b) {
		// input validation
		Util.ASSERT(a.getLen() == b.getLen() || a.isEmpty() || b.isEmpty(), "XOR operands must have equal lengths");
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node xor = Node.createRegular("XOR", new VerilogOperation("^"));
		Graph.addNode(xor);
		// data source/sink
		out.setSource(xor);
		a.addSink(xor);
		b.addSink(xor);
		// node source/sink
		xor.addSource(a);
		xor.addSource(b);
		xor.addSink(out);
		return out;
	}
	
	public static Data ADD(Data a, Data b) {
		// input validation
		Util.ASSERT(a.getLen() == b.getLen() || a.isEmpty() || b.isEmpty(), "ADD operands must have equal lengths");
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node add = Node.createRegular("ADD", new VerilogOperation("+"));
		//Node add = new Node("ADD", false, -1, false, false, new VerilogOperation("+"));
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
	
	public static Data SUB(Data a, Data b) {
		// input validation
		Util.ASSERT(a.getLen() == b.getLen() || a.isEmpty() || b.isEmpty(), "SUB operands must have equal lengths");
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node sub = Node.createRegular("SUB", new VerilogOrderedOperation("-"));
		Graph.addNode(sub);
		// input tags
		a.addSpecialTag(sub.getFullName() + "_first");
		b.addSpecialTag(sub.getFullName() + "_second");
		// data source/sink
		out.setSource(sub);
		a.addSink(sub);
		b.addSink(sub);
		// node source/sink
		sub.addSource(a);
		sub.addSource(b);
		sub.addSink(out);
		return out;
	}
	
	public static Data MULT(Data a, Data b) {
		// input validation
		Util.ASSERT(a.getLen() == b.getLen() || a.isEmpty() || b.isEmpty(), "MULT operands must have equal lengths");
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node mult = Node.createRegular("MULT", new VerilogOperation("*"));
		//Node mult = new Node("MULT", false, -1, false, false, new VerilogOperation("*"));
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
	
	public static Data DIV(Data a, Data b) {
		// input validation
		Util.ASSERT(a.getLen() == b.getLen() || a.isEmpty() || b.isEmpty(), "DIV operands must have equal lengths");
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node div = Node.createRegular("DIV", new VerilogOrderedOperation("/"));
		Graph.addNode(div);
		// input tags
		a.addSpecialTag(div.getFullName() + "_first");
		b.addSpecialTag(div.getFullName() + "_second");
		// data source/sink
		out.setSource(div);
		a.addSink(div);
		b.addSink(div);
		// node source/sink
		div.addSource(a);
		div.addSource(b);
		div.addSink(out);
		return out;
	}
	
	public static Data MOD(Data a, Data b) {
		// input validation
		Util.ASSERT(a.getLen() == b.getLen() || a.isEmpty() || b.isEmpty(), "MOD operands must have equal lengths");
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node mod = Node.createRegular("MOD", new VerilogOrderedOperation("%"));
		Graph.addNode(mod);
		// input tags
		a.addSpecialTag(mod.getFullName() + "_first");
		b.addSpecialTag(mod.getFullName() + "_second");
		// data source/sink
		out.setSource(mod);
		a.addSink(mod);
		b.addSink(mod);
		// node source/sink
		mod.addSource(a);
		mod.addSource(b);
		mod.addSink(out);
		return out;
	}
	
	public static Data LSHIFT(Data a, Data b) {
		// no input validation... these don't have to be the same length
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node lshift = Node.createRegular("LSHIFT", new VerilogOrderedOperation("<<"));
		Graph.addNode(lshift);
		// input tags
		a.addSpecialTag(lshift.getFullName() + "_first");
		b.addSpecialTag(lshift.getFullName() + "_second");
		// data source/sink
		out.setSource(lshift);
		a.addSink(lshift);
		b.addSink(lshift);
		// node source/sink
		lshift.addSource(a);
		lshift.addSource(b);
		lshift.addSink(out);
		return out;
	}
	
	public static Data RSHIFT(Data a, Data b) {
		// no input validation... these don't have to be the same length
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node rshift = Node.createRegular("RSHIFT", new VerilogOrderedOperation(">>"));
		Graph.addNode(rshift);
		// input tags
		a.addSpecialTag(rshift.getFullName() + "_first");
		b.addSpecialTag(rshift.getFullName() + "_second");
		// data source/sink
		out.setSource(rshift);
		a.addSink(rshift);
		b.addSink(rshift);
		// node source/sink
		rshift.addSource(a);
		rshift.addSource(b);
		rshift.addSink(out);
		return out;
	}
	
	public static Data CONCAT(Data a, Data b) {
		// no input validation... these don't have to be the same length
		// create data & node objects
		Data out = new Data(a.getLen() + b.getLen());
		Graph.addData(out);
		Node concat = Node.createRegular("CONCAT", new VerilogConcatenate());
		Graph.addNode(concat);
		// input tags
		a.addSpecialTag(concat.getFullName() + "_first");
		b.addSpecialTag(concat.getFullName() + "_second");
		// data source/sink
		out.setSource(concat);
		a.addSink(concat);
		b.addSink(concat);
		// node source/sink
		concat.addSource(a);
		concat.addSource(b);
		concat.addSink(out);
		return out;
	}
	
	public static Data SPLIT(Data a, int lowBit, int highBit) {
		// input validation
		Util.ASSERT(lowBit >= 0, "ERROR: Split cannot access element " + lowBit);
		Util.ASSERT(highBit < a.getLen(), "ERROR: Split cannot access element " + highBit);
		// create data & node objects
		Data out = new Data(highBit - lowBit + 1);
		Graph.addData(out);
		Node split = Node.createRegular("SPLIT", new VerilogSplit(lowBit, highBit));
		Graph.addNode(split);
		// data source/sink
		out.setSource(split);
		a.addSink(split);
		// node source/sink
		split.addSource(a);
		split.addSink(out);
		return out;
	}
	
	public static Data MUX(Data a, Data b, Data swtch) {
		// input validation
		Util.ASSERT(a.getLen() == b.getLen() || a.isEmpty() || b.isEmpty(), "MUX operands must have equal lengths");
		Util.ASSERT(swtch.getLen() == 1, "MUX switch must be 1 bit long");
		// create data & node objects
		Data out = new Data(a.getLen());
		Graph.addData(out);
		Node mux = Node.createRegular("MUX", new VerilogMUX());
		// tags
		swtch.addSpecialTag(mux.getFullName() + "_switch");
		a.addSpecialTag(mux.getFullName() + "_first");
		b.addSpecialTag(mux.getFullName() + "_second");
		//Node mux = new Node("MUX", false, -1, false, false, new VerilogOperation("???"));
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
		Node root = Node.createRegular("CONST", new VerilogConst("" + value));
		//Node root = new Node("ROOT", false, -1, false, false, new VerilogString("" + value));
		Graph.addNode(root);
		// data source/sink
		out.setSource(root);
		// node source/sink
		root.addSink(out);
		return out;
	}
	
	public static Data DELAY(Data in, Clock clk) { // TODO: add clk!!!
		// create data & node objects
		Data out = new Data(in.getLen());
		Graph.addData(out);
		Node delay = Node.createDelay("DELAY", clk, new VerilogDelay());
		//Node delay = new Node("DELAY", true, -1, false, false, new VerilogRelation("<="));
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
	
	// in module mode (as opposed to execution mode???), then this actually splits off the old module from the new
	// it separates off the enclosed nodes into an entirely new graph/module and creates a new "module" node in its place in the original graph
	// it also labels all the nodes in the new module as being in the new module
	// in execution mode, this module is ignored (definitely better ways to do it, but that's okay for now)
	public static void MODULE_BUNDLE_2(Data[] inputs, Data[] outputs, String moduleName) { // TODO: add labels for each input???
		int moduleID = Graph.getID();
		// create new module/graph
		// module node
		Node module = Node.createRegular("MODULE", new VerilogModule(moduleID));
		Graph.addNode(module);
		Graph.addModule(moduleID, moduleName + "_" + moduleID);
		// flood node
		Node flood = null;
		// hook up inputs
		for (Data d : inputs) {
			// create data & node objects, do tags, add to graph, etc.
			Data newD;
			if (d.isClock()) {
				newD = new Clock(((Clock)d).getNumTimestepsPerFlip());
			} else {
				newD = new Data(d.getLen());
			}
			newD.setTag(d.getTag());
			newD.copySpecialTags(d);
			Graph.addData(newD);
			Node input = Node.createInput("INPUT", moduleID, new VerilogIO(true));
			Graph.addNode(input);
			if (flood == null) {
				flood = input;
			}
			// new data source
			newD.setSource(input);
			input.addSink(newD);
			// old data source STAYS THE SAME
			// new data sink
			for (Node n : d.getSinks()) {
				newD.addSink(n);
				n.addSource(newD);
				n.getSources().remove(d);
			}
			// old data sink
			d.getSinks().clear();
			module.addSource(d);
			d.addSink(module);
		}
		// hook up outputs
		for (Data d : outputs) {
			// create data & node objects, do tags, add to graph, etc.
			Data newD;
			if (d.isClock()) {
				newD = new Clock(((Clock)d).getNumTimestepsPerFlip());
			} else {
				newD = new Data(d.getLen());
			}
			newD.setTag(d.getTag());
			newD.copySpecialTags(d);
			Graph.addData(newD);
			Node output = Node.createOutput("OUTPUT", moduleID, new VerilogIO(false));
			Graph.addNode(output);
			if (flood == null) {
				flood = output;
			}
			// new data source
			newD.setSource(d.getSource());
			if (newD.getSource() != null) { // TODO: how do I want to handle this?
				newD.getSource().addSink(newD);
				newD.getSource().getSinks().remove(d);
			}
			// old data source
			d.setSource(module);
			module.addSink(d);
			// new data sink
			newD.addSink(output);
			output.addSource(newD);
			// old data sink STAYS THE SAME
		}
		
		// propogate module ID through new module
		flood.propogateModuleID(moduleID);
	}
	
	public static void MODULE_BUNDLE(Data[] inputs, Data[] outputs) {
		int moduleID = Graph.getID();
		for (Data d : inputs) {
			// create data & node objects
			Data newD;
			if (d.isClock()) {
				newD = new Clock(((Clock)d).getNumTimestepsPerFlip());
			} else {
				newD = new Data(d.getLen());
			}
			newD.setTag(d.getTag());
			newD.copySpecialTags(d);
			Graph.addData(newD);
			Node input = Node.createInput("INPUT", moduleID, new VerilogIO(true));
			//Node input = new Node("INPUT", false, moduleID, true, false, new VerilogConstant(""));
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
			if (d.isClock()) {
				newD = new Clock(((Clock)d).getNumTimestepsPerFlip());
			} else {
				newD = new Data(d.getLen());
			}
			newD.setTag(d.getTag());
			newD.copySpecialTags(d);
			Graph.addData(newD);
			Node output = Node.createOutput("OUTPUT", moduleID, new VerilogIO(false));
			//Node output = new Node("OUTPUT", false, moduleID, false, true, new VerilogConstant(""));
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
		Data out;
		if (in.isClock()) {
			out = new Clock(((Clock)in).getNumTimestepsPerFlip());
		} else {
			out = new Data(in.getLen());
		}
		out.setTag(in.getTag());
		out.copySpecialTags(in);
		Graph.addData(out);
		Node pass = Node.createRegular("PASS", new VerilogPass());
		//Node pass = new Node("PASS", false, -1, false, false, new VerilogRelation("="));
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