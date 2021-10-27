import java.util.ArrayList;

public class Node {
	
	// standard Node interface
	
	private ArrayList<Data> sources;
	private ArrayList<Data> sinks;
	private String name;
	private int ID;
	
	public static Node createRegular(String name, GenerateVerilog verilog) {
		return new Node(name, false, null, -1, false, false, verilog);
	}
	
	public static Node createInput(String name, int module, GenerateVerilog verilog) {
		return new Node(name, false, null, module, true, false, verilog);
	}
	
	public static Node createOutput(String name, int module, GenerateVerilog verilog) {
		return new Node(name, false, null, module, false, true, verilog);
	}
	
	public static Node createDelay(String name, Clock clk, GenerateVerilog verilog) {
		return new Node(name, true, clk, -1, false, false, verilog);
	}
	
	private Node(String name, boolean delay, Clock clk, int module, boolean moduleInput, boolean moduleOutput, GenerateVerilog verilog) {
		ID = Graph.getID();
		sources = new ArrayList<Data>();
		sinks = new ArrayList<Data>();
		this.name = name;
		this.delay = delay;
		this.clk = clk;
		this.verilog = verilog;
		this.module = module;
		this.moduleInput = moduleInput;
		this.moduleOutput = moduleOutput;
	}
	
	public void addSource(Data source) {
		sources.add(source);
	}
	
	public void addSink(Data sink) {
		sinks.add(sink);
	}
	
	public ArrayList<Data> getSources() {
		return sources;
	}
	
	public ArrayList<Data> getSinks() {
		return sinks;
	}
	
	public boolean isRoot() {
		return (sources.size() == 0);
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFullName() {
		return getName() + "_" + getID();
	}
	
	// converting to RTL (experimental) -------------------------------------------------------------
	
	private GenerateVerilog verilog;
	private Clock clk;
	private boolean delay;
	private int module;
	private boolean moduleInput;
	private boolean moduleOutput;
	
	public Clock getClock() {
		return clk;
	}
	
	public GenerateVerilog getVerilog() {
		return verilog;
	}
	
	public boolean isDelay() {
		return delay;
	}
	
	public int getModule() {
		return module;
	}
	
	public boolean isModuleInput() {
		return moduleInput;
	}
	
	public boolean isModuleOutput() {
		return moduleOutput;
	}
	
	public void setModule(int module) {
		this.module = module;
	}
	
	// propogate module ID ------------------------------------------------------------------------
	
	// this fills the entire graph (that it's connected to) with its current ID
	public void propogateModuleID(int moduleID) {
		setModule(moduleID);
		// propogate backwards
		for (Data d : getSources()) {
			Node n = d.getSource();
			if (n != null && n.getModule() == -1) {
				n.propogateModuleID(moduleID);
			}
		}
		// propogate forwards
		for (Data d : getSinks()) {
			for (Node n : d.getSinks()) {
				if (n.getModule() == -1) {
					n.propogateModuleID(moduleID);
				}
			}
		}
	}
	
	// connections for visualization --------------------------------------------------------------
	
	private int position;
	private boolean placed = false;
	
	public int getPosition() {
		return position;
	}
	
	public boolean isPlaced() {
		return placed;
	}
	
	public void placeNodes(int pos) {
		position = pos;
		placed = true;
		for (Data d : sources) {
			Node considering = d.getSource();
			if (considering != null) {
				if (!considering.isPlaced()) {
					considering.placeNodes(position - 1);
				}
			}
		}
		for (Data d : sinks) {
			for (Node considering : d.getSinks()) {
				if (!considering.isPlaced()) {
					considering.placeNodes(position + 1);
				}
			}
		}
	}
	
}