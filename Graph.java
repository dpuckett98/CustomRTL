import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

public class Graph {
	
	private static ArrayList<Node> nodeList;
	private static ArrayList<Data> dataList;
	private static ArrayList<Integer> moduleNumbers;
	private static ArrayList<String> moduleNames;
	private static int currID = 0;
	
	public static void init() {
		nodeList = new ArrayList<Node>();
		dataList = new ArrayList<Data>();
		moduleNumbers = new ArrayList<Integer>();
		moduleNames = new ArrayList<String>();
		addModule(-1, "Testbench");
	}
	
	public static void addModule(int num, String name) {
		moduleNumbers.add(num);
		moduleNames.add(name);
	}
	
	public static String getModuleName(int num) {
		int index = -1;
		for (int i = 0; i < moduleNumbers.size(); i++) {
			if (moduleNumbers.get(i) == num) {
				index = i;
				break;
			}
		}
		return moduleNames.get(index);
	}
	
	// TODO: Make this not trash
	public static void parseModules(boolean printGraph, boolean saveVerilog, boolean visualizeGraph) {
		int numModules = 0;
		ArrayList<Integer> moduleNames = new ArrayList<Integer>();
		ArrayList<ArrayList<Node>> nodesByModule = new ArrayList<ArrayList<Node>>();
		for (Node n : nodeList) {
			if (!moduleNames.contains(n.getModule())) {
				moduleNames.add(n.getModule());
				nodesByModule.add(new ArrayList<Node>());
			}
			int index = -1;
			for (int i = 0; i < moduleNames.size(); i++) {
				if (moduleNames.get(i) == n.getModule()) {
					index = i;
					break;
				}
			}
			nodesByModule.get(index).add(n);
		}
		numModules = moduleNames.size();
		System.out.println("Number of modules: " + numModules + "\n");
		
		if (printGraph) {
			for (int i = 0; i < numModules; i++) {
				System.out.println("Module " + getModuleName(moduleNames.get(i)));
				printGraph(nodesByModule.get(i));
				System.out.println("\n");
			}
		}
		
		if (saveVerilog) {
			System.out.println("\nWriting files...");
			for (int i = 0; i < numModules; i++) {
				Util.writeToFile("verilog/" + getModuleName(moduleNames.get(i)) + ".v", generateVerilog(nodesByModule.get(i), moduleNames.get(i)));
				//System.out.println(generateVerilog(nodesByModule.get(i), moduleNames.get(i)));
				//System.out.println("\n");
			}
			System.out.println("Files written");
		}
		
		if (visualizeGraph) {
			for (int i = 0; i < moduleNames.size(); i++) {
				visualizeGraph(nodesByModule.get(i), getModuleName(moduleNames.get(i)));
			}
		}
		
	}
	
	public static void visualizeGraph(ArrayList<Node> nodeList, String name) {
		new Display(nodeList, name);
		//Display.displayModule(nodeList, name);
		/*System.out.println("\nModule " + getModuleName(moduleNames.get(i)));
		nodeList.get(0).placeNodes(0);
		int minNum = 0;
		int maxNum = 0;
		for (Node n : nodeList) {
			if (n.getPosition() < minNum) {
				minNum = n.getPosition();
			}
			if (n.getPosition() > maxNum) {
				maxNum = n.getPosition();
			}
		}
		for (int i = minNum; i <= maxNum; i++) {
			System.out.println("====================== " + i + " ========================");
			for (Node n : nodeList) {
				if (n.getPosition() == i) {
					printNode(n);
				}
			}
		}*/
	}
	
	public static void printGraph(ArrayList<Node> nodeList) {
		for (Node i : nodeList) {
			printNode(i);
		}
	}
	
	public static void printNode(Node i) {
		System.out.print(i.getName() + "_" + i.getID() + ": ");
		for (Data d : i.getSources()) {
			System.out.print(d.getID() + " ");
		}
		System.out.print("-> ");
		for (Data d : i.getSinks()) {
			System.out.print(d.getID() + " ");
		}
		System.out.println();
	}
	
	public static String generateVerilog(ArrayList<Node> nodeList, int moduleID) {
		String ioPorts = "";
		String dataInits = "";
		String assignVals = "";
		ArrayList<String> clocks = new ArrayList<String>();
		ArrayList<String> delayLoops = new ArrayList<String>();
		
		for (Node node : nodeList) {
			String iop = node.getVerilog().getIOPorts(node);
			String di = node.getVerilog().getDataInit(node);
			String av = node.getVerilog().getAssignVals(node);
			String clk = node.getVerilog().getClock(node);
			String dl = node.getVerilog().getDelayLoop(node);
			
			if (iop != null) {
				if (ioPorts != "") {
					ioPorts += ",\n";
				}
				ioPorts += "\t" + iop;
			}
			if (di != null) {
				dataInits += di + "\n";
			}
			if (av != null) {
				assignVals += av + "\n";
			}
			if (clk != null) {
				int index = -1;
				for (int i = 0; i < clocks.size(); i++) {
					if (clocks.get(i) == clk) {
						index = i;
						break;
					}
				}
				if (index == -1) {
					clocks.add(clk);
					delayLoops.add("");
					index = clocks.size() - 1;
				}
				delayLoops.set(index, delayLoops.get(index) + "\t" + dl + "\n");
			}
		}
		
		// module header
		String res = "module " + getModuleName(moduleID) + " (\n";
		res += ioPorts;
		res += "\n\t);\n\n";
		
		// data inits
		res += dataInits;
		res += "\n";
		
		// assign vals
		res += assignVals;
		res += "\n";
		
		// delay loops
		for (int i = 0; i < clocks.size(); i++) {
			res += "always @ (posedge " + clocks.get(i) + ") begin\n";
			res += delayLoops.get(i);
			res += "end\n\n";
		}
		
		// end module
		res += "endmodule\n";
		return res;
	}
	
	public static int getID() {
		currID++;
		return currID - 1;
	}
	
	public static void addNode(Node n) {
		nodeList.add(n);
	}
	
	public static void addData(Data d) {
		dataList.add(d);
	}
	
}