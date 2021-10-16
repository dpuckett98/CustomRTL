import java.util.ArrayList;

public class Node {
	
	// standard Node interface
	
	private ArrayList<Data> sources;
	private ArrayList<Data> sinks;
	private String name;
	private int ID;
	
	public Node(String name, boolean delay, int module, boolean moduleInput, boolean moduleOutput, GenerateVerilog verilog) {
		ID = Graph.getID();
		sources = new ArrayList<Data>();
		sinks = new ArrayList<Data>();
		this.name = name;
		this.delay = delay;
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
	private boolean delay;
	private int module;
	private boolean moduleInput;
	private boolean moduleOutput;
	
	public String getVerilog() {
		return verilog.getVerilog(this);
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