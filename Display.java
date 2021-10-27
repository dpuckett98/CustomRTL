import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;

public class Display extends JPanel {
	
	private ArrayList<Node> nodeList;
	private String moduleName;
	private JFrame frame;
	private int xScale = 300;
	private int yScale = 50;
	private int boxWidth = 240;
	
	private int lastStarted = 0;
	
	public Display(ArrayList<Node> nodeList, String moduleName) {
		this.nodeList = nodeList;
		this.moduleName = moduleName;
		init();
	}
	
	public void init() {
		frame = new JFrame(moduleName);
		frame.setSize(700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// prep graph
		nodeList.get(0).placeNodes(lastStarted);
		//lastStarted++;
		if (lastStarted > nodeList.size()) {
			lastStarted = 0;
		}
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
		
		// storage for data lines
		ArrayList<String> dataNames = new ArrayList<String>();
		ArrayList<ArrayList<Integer>> dataPosSource = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> dataPosSink = new ArrayList<ArrayList<Integer>>();
		
		// draw nodes & setup data lines
		int currPosX = 20;
		for (int i = minNum; i <= maxNum; i++) {
			int currPosY = 20 + 10*(i-minNum);
			for (Node n : nodeList) {
				if (n.getPosition() == i) {
					int height = n.getSources().size() * 15 + 10;
					if (n.getSinks().size() > n.getSources().size()) {
						height = n.getSinks().size() * 15 + 10;
					}
					// draw box
					g.drawRect(currPosX, currPosY, boxWidth, height);
					// draw node name
					g.drawString(n.getFullName(), currPosX+80, currPosY+15);
					// draw wire names
					int amt = 15;
					for (Data d : n.getSources()) {
						g.drawString(d.getVerilog(), currPosX+5, currPosY+amt);
						// TODO: make the following not trash
						int index = -1;
						for (int j = 0; j < dataNames.size(); j++) {
							if (dataNames.get(j).equals(d.getVerilog())) {
								index = j;
								break;
							}
						}
						if (index == -1) {
							dataNames.add(d.getVerilog());
							ArrayList<Integer> temp = new ArrayList<Integer>();
							temp.add(currPosX);
							temp.add(currPosY+amt);
							dataPosSink.add(temp);
							dataPosSource.add(new ArrayList<Integer>());
						} else {
							dataPosSink.get(index).add(currPosX);
							dataPosSink.get(index).add(currPosY+amt);
						}
						amt += 15;
					}
					amt = 15;
					for (Data d : n.getSinks()) {
						g.drawString(d.getVerilog(), currPosX+boxWidth-d.getVerilog().length()*6-10, currPosY+amt);
						// TODO: make the following not trash
						int index = -1;
						for (int j = 0; j < dataNames.size(); j++) {
							if (dataNames.get(j).equals(d.getVerilog())) {
								index = j;
								break;
							}
						}
						if (index == -1) {
							dataNames.add(d.getVerilog());
							ArrayList<Integer> temp = new ArrayList<Integer>();
							temp.add(currPosX+boxWidth);
							temp.add(currPosY+amt);
							dataPosSource.add(temp);
							dataPosSink.add(new ArrayList<Integer>());
						} else {
							dataPosSource.get(index).add(currPosX+boxWidth);
							dataPosSource.get(index).add(currPosY+amt);
						}
						amt += 15;
					}
					currPosY += yScale + height;
				}
			}
			currPosX += xScale;
		}
		
		// draw data lines
		for (int i = 0; i < dataNames.size(); i++) {
			for (int j = 0; j < dataPosSource.get(i).size(); j += 2) {
				for (int k = 0; k < dataPosSink.get(i).size(); k += 2) {
					g.drawLine((int)dataPosSource.get(i).get(j), (int)dataPosSource.get(i).get(j+1), (int)dataPosSink.get(i).get(k), (int)dataPosSink.get(i).get(k+1));
				}
			}
		}
	}
	
}