import java.util.ArrayList;

public class Data {
	
	// typical data stuff
	private String tag;
	private ArrayList<String> specialTags;
	private Node source;
	private ArrayList<Node> sinks;
	private int length; // if length is -1, data is empty
	private int ID;
	protected boolean isClock;
	
	public static Data EMPTY() {
		return new Data(-1);
	}
	
	public Data(int length) {
		ID = Graph.getID();
		this.length = length;
		source = null;
		sinks = new ArrayList<Node>();
		this.moduleIn = -1; // TODO: remove
		this.moduleOut = -1;
		this.moduleInput = false;
		this.moduleOutput = false;
		isClock = false;
		tag = "";
		specialTags = new ArrayList<String>();
	}
	
	// adds tag
	public Data setTag(String tag) {
		this.tag = tag;
		return this;
	}
	
	public String getTag() {
		return tag;
	}
	
	// special tag
	public Data addSpecialTag(String specialTag) {
		specialTags.add(specialTag);
		return this;
	}
	
	public boolean hasSpecialTag(String val) {
		return specialTags.contains(val);
	}
	
	public void copySpecialTags(Data other) {
		specialTags.addAll(other.specialTags);
	}
	
	// fills empty 
	public void fill(Data d) {
		Util.ASSERT(isEmpty(), "WARNING: Should only fill object that is empty");
		Util.ASSERT(!(hasSource() && d.hasSource()), "ERROR: Cannot fill data with a source");
		// update length
		length = d.getLen();
		// combine sources
		if (!hasSource()) {
			if (d.hasSource()) {
				setSource(d.getSource());
				d.getSource().addSink(this);
				d.getSource().getSinks().remove(d);
			}
		}
		// combine sinks
		getSinks().addAll(d.getSinks());
		for (Node n : d.getSinks()) {
			n.addSource(this);
			n.getSources().remove(d);
		}
	}
	
	public boolean isClock() {
		return isClock;
	}
	
	public int getLen() {
		return length;
	}
	
	public boolean hasSource() {
		return source != null;
	}
	
	public void setSource(Node source) {
		this.source = source;
	}
	
	public Node getSource() {
		return source;
	}
	
	public void addSink(Node sink) {
		sinks.add(sink);
	}
	
	public ArrayList<Node> getSinks() {
		return sinks;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getVerilog() {
		if (tag == "") {
			return "Data_" + ID;
		} else {
			return tag + "_" + ID;
		}
	}
	
	public boolean isEmpty() {
		return length == -1;
	}
	
	// experimental verilog stuff
	
	private int moduleIn;
	private int moduleOut;
	private boolean moduleInput;
	private boolean moduleOutput;
	
	public int getModuleIn() {
		return moduleIn;
	}
	
	public int getModuleOut() {
		return moduleOut;
	}
	
	public boolean isModuleInput() {
		return moduleInput;
	}
	
	public boolean isModuleOutput() {
		return moduleOutput;
	}
	
	public void setModuleIn(int module) {
		moduleInput = true;
		moduleIn = module;
	}
	
	public void setModuleOut(int module) {
		moduleOutput = true;
		moduleOut = module;
	}
	
}