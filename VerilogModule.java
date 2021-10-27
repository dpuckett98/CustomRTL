// This module takes three inputs and makes one output
public class VerilogModule implements GenerateVerilog {

	private int number;

	public VerilogModule(int number) {
		this.number = number;
	}
	
	private Data getSwitch(Node n) {
		boolean hasSwitch = false;
		Data swtch = null;
		for (Data d : n.getSources()) {
			if (d.hasSpecialTag(n.getFullName() + "_switch")) {
				hasSwitch = true;
				swtch = d;
				break;
			}
		}
		Util.ASSERT(hasSwitch, "ERROR: One of MUX's inputs must be tagged as a switch: " + n.getFullName());
		return swtch;
	}
	
	private Data getFirst(Node n) {
		boolean hasSwitch = false;
		Data swtch = null;
		for (Data d : n.getSources()) {
			if (d.hasSpecialTag(n.getFullName() + "_first")) {
				hasSwitch = true;
				swtch = d;
				break;
			}
		}
		Util.ASSERT(hasSwitch, "ERROR: One of MUX's inputs must be tagged as first: " + n.getFullName());
		return swtch;
	}
	
	private Data getSecond(Node n) {
		boolean hasSwitch = false;
		Data swtch = null;
		for (Data d : n.getSources()) {
			if (d.hasSpecialTag(n.getFullName() + "_second")) {
				hasSwitch = true;
				swtch = d;
				break;
			}
		}
		Util.ASSERT(hasSwitch, "ERROR: One of MUX's inputs must be tagged as second: " + n.getFullName());
		return swtch;
	}
	
	private Data getOut(Node n) {
		Util.ASSERT(n.getSinks().size() == 1, "ERROR: MUX must have one output: " + n.getFullName());
		return n.getSinks().get(0);
	}

	public String getIOPorts(Node n) {
		return null;
	}
	
	public String getDataInit(Node n) {
		return null;
	}
	
	public String getAssignVals(Node n) {
		String res = Graph.getModuleName(number) + " " + Graph.getModuleName(number) + "_" + n.getFullName() + " ("; // TODO: set module name in lowercase
		String sources = "";
		for (Data d : n.getSources()) {
			if (sources != "") {
				sources += ", ";
			}
			sources += d.getVerilog();
		}
		for (Data d : n.getSinks()) {
			if (sources != "") {
				sources += ", ";
			}
			sources += d.getVerilog();
		}
		return res + sources + ");";
	}
	
	public String getClock(Node n) { // null/empty if none
		return null;
	}
	
	public String getDelayLoop(Node n) { // null/empty if none
		return null;
	}

}