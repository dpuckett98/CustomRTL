public interface GenerateVerilog {
	
	public String getIOPorts(Node n);
	
	public String getDataInit(Node n);
	
	public String getAssignVals(Node n);
	
	public String getClock(Node n); // null/empty if none
	
	public String getDelayLoop(Node n); // null/empty if none
	
}