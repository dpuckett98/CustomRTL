import java.util.ArrayList;

class GraphData {
	
	private Data link;
	private Node source;
	private ArrayList<Node> sinks;
	
	public GraphData(Data data) {
		link = data;
		source = null;
		sinks = new ArrayList<Node>();
	}
	
	public boolean hasSource() {
		return source != null;
	}
	
	public void setSource(Node source) {
		this.source = source;
	}
	
	public void addSink(Node sink) {
		sinks.add(sink);
	}
	
	public ArrayList<Node> getSinks() {
		return sinks;
	}
	
}