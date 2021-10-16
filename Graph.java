import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

public class Graph {
	
	private static ArrayList<Node> nodeList;
	private static ArrayList<Data> dataList;
	private static int currID = 0;
	
	public static void init() {
		nodeList = new ArrayList<Node>();
		dataList = new ArrayList<Data>();
	}
	
	public static ArrayList<ArrayList<Node>> parseGraph2() {
		Stack<Integer> modules = new Stack<Integer>();
		Stack<Node> search = new Stack<Node>();
		
		// find node that's already assigned a module
		for (Node n : nodeList) {
			if (n.getModule() != -1) {
				search.add(n);
				break;
			}
		}
		
		// DFS through nodes, setting module of current node to top of modules stack
		// whenever reach an input going forward, push its module onto the stack
		// 
		
	}
	
	public static ArrayList<ArrayList<Node>> parseGraph() {
		ArrayList<ArrayList<Node>> result = new ArrayList<ArrayList<Node>>();
		
		int numModules = 0;
		ArrayList<Integer> moduleNames = new ArrayList<Integer>();
		ArrayList<Node> moduleNodes = new ArrayList<Node>();
		for (Node n : nodeList) {
			if (n.getModule() != -1 && !moduleNames.contains(n.getModule())) {
				moduleNames.add(n.getModule());
				moduleNodes.add(n);
			}
		}
		numModules = moduleNames.size();
		System.out.println(numModules);
		
		for (int i = 0; i < numModules; i++) {
			ArrayList<Node> res = findModule(moduleNodes.get(i));
		}
		
		return result;
	}
	
	public static ArrayList<Node> findModule(Node source) {
		int moduleID = source.getID();
		Queue<Node> search = new LinkedList<Node>();
		search.add(source);
		while (!search.isEmpty()) {
			Node curr = search.remove();
			//curr
			if (!curr.isModuleInput()) {
				
			}
		}
	}
	
	public static void visualizeGraph() {
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
		}
	}
	
	public static void printGraph() {
		for (Node i : nodeList) {
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
	
	public static String generateVerilog() {
		String ioPorts = "";
		String dataInits = "";
		String delayLoop = "";
		String assignVals = "";
		
		for (Data data : dataList) {
			if (data.hasSource() && data.getSource().isDelay()) {
				dataInits += "reg [" + (data.getLen()-1) + ":0] " + data.getVerilog() + ";\n";
			} else {
				dataInits += "wire [" + (data.getLen()-1) + ":0] " + data.getVerilog() + ";\n";
			}
		}
		
		for (Node node : nodeList) {
			if (node.isDelay()) {
				delayLoop += "   " + node.getVerilog() + ";\n";
			} else {
				assignVals += node.getVerilog() + ";\n";
			}
		}
		
		String res = "module test()\n\n";
		res += dataInits;
		res += "\n";
		res += assignVals;
		res += "\n";
		res += "always @ (posedge clk) begin\n";
		res += delayLoop;
		res += "end\nendmodule\n";
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