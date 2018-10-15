package main;

import java.awt.Choice;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DrawGraphics implements ActionListener{
	 JButton init,addweight,gofind,reset,saveResult,loadFile;
	 JPanel matrix ;
	 JTextField txtVertex, txtEdge, txtweight;
	 JFrame frame;
	 Choice choicefirst, choicelast,from,to,choiceAlgorithm;
	 JLabel txtResult;
	 int bg[][],S,F,V,E;
	 static int INFINITY = 99999; 
	 ArrayList<Edge> edges = new ArrayList<>();
	 
	 
public DrawGraphics() {
	frame = new JFrame();
	frame.setLayout(null);
	frame.setSize(800, 500);
	frame.setLocation(200, 100);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setResizable(false);
	//end jframe
	JPanel right = new JPanel(null);
	right.setBounds(500, 0, 300, 500);
	right.setBackground(Color.CYAN);
	
	JLabel lVertex = new JLabel("Nhập số đỉnh");
	lVertex.setBounds(10, -30, 100, 100);
	right.add(lVertex);
    txtVertex = new JTextField(5);
    txtVertex.setText("");
	txtVertex.setBounds(100, 10, 30, 20);
	right.add(txtVertex);
	
	JLabel lEdge = new JLabel("Nhập số cạnh");
	lEdge.setBounds(10, 10, 100, 100);
	right.add(lEdge);
	txtEdge = new JTextField(5);
	txtEdge.setText("");
	txtEdge.setBounds(100, 50, 30, 20);
	right.add(txtEdge);	
	
	init = new JButton("Khởi tạo");
	init.setBounds(150, 50, 120, 20);
	right.add(init);
	init.addActionListener(this);
    loadFile = new JButton("Chọn từ File");
	loadFile.setBounds(150, 10, 120, 20);
	loadFile.addActionListener(this);
	right.add(loadFile);
	
	JLabel firstPoint = new JLabel("Đỉnh đầu");
	firstPoint.setBounds(10, 80, 100, 20);
	right.add(firstPoint);
	
	JLabel lastPoint = new JLabel("Đỉnh cuối");
	lastPoint.setBounds(90, 80, 100, 20);
	right.add(lastPoint);
	
    choicefirst = new Choice();
	choicefirst.setBounds(10, 110, 50, 0);
	right.add(choicefirst);
	
    choicelast = new Choice();
	choicelast.setBounds(90, 110, 50, 0);
	right.add(choicelast);
	
	JLabel weight = new JLabel("Trọng số");
	weight.setBounds(180, 80, 100, 20);
	right.add(weight);
    txtweight = new JTextField(5);
	txtweight.setBounds(180, 110, 50, 22);
	right.add(txtweight);
	
	addweight = new JButton("Cập nhật trọng số");
	addweight.setBounds(70, 150, 150, 20);
	right.add(addweight);
	addweight.addActionListener(this);
	
	JLabel txtfindRoad = new JLabel("Tìm đường đi:  Từ");
	txtfindRoad.setBounds(10, 185, 115, 20);
	right.add(txtfindRoad);
	
	from = new Choice();
	from.setBounds(125, 184, 40, 22);
	right.add(from);
	
	JLabel txtfrom = new JLabel("đến");
	txtfrom.setBounds(170, 184, 30, 22);
	right.add(txtfrom);
	
    to = new Choice();
	to.setBounds(200, 184, 40, 22);
	right.add(to);
	
	JLabel labelAlgorithm = new JLabel("Chọn thuật toán");
	labelAlgorithm.setBounds(10, 240, 100, 22);
	right.add(labelAlgorithm);
	
    choiceAlgorithm = new Choice();
	choiceAlgorithm.setBounds(120, 240, 130, 22);
	choiceAlgorithm.addItem("Bellman-Ford");
	choiceAlgorithm.addItem("Dijkstra");
	right.add(choiceAlgorithm);
	
	gofind = new JButton("Tìm đường");
	gofind.setBounds(80, 290, 130, 22);
	right.add(gofind);
	gofind.addActionListener(this);
    
    JLabel labelResult = new JLabel("Đường đi ngắn nhất là: ");
    labelResult.setBounds(10, 320, 260, 50);
	right.add(labelResult);
	
	txtResult = new JLabel("");
    txtResult.setBounds(10, 340, 260, 50);
    right.add(txtResult);
    
    reset = new JButton("Reset");
    reset.setBounds(10, 390, 100, 20);
    reset.addActionListener(this);
    right.add(reset);
    
    saveResult = new JButton("Lưu kết quả(.txt)");
    saveResult.setBounds(130, 390, 140, 20);
    right.add(saveResult);
    setView(false);
	frame.add(right);
	frame.setVisible(true);
}

@Override
public void actionPerformed(ActionEvent e) {
if(e.getSource()==init) {
	String V = txtVertex.getText();
	String E = txtEdge.getText();
	if(V.length()==0||E.length()==0) {
		JOptionPane.showMessageDialog(frame, "Vui lòng nhập đủ thông tin", "Lỗi", JOptionPane.ERROR_MESSAGE);
	}else {
		setView(true);
		int v = Integer.parseInt(V);
		int edge = Integer.parseInt(E);
		this.V =v;
		this.E=edge;
		bg = new int [v][v];

		for(int i=0;i<bg.length;i++) {
			for(int j=0;j<bg.length;j++) {
				if(i==j) bg[i][j]=0;
				else bg[i][j]=Main.INFINITY;
			}
		}
		
		matrix = new JPanel(new GridLayout(v,v));
		matrix.setBounds(120, 20, 250, 250);
		drawMatrix(bg);
		frame.add(matrix);
		addItemSelect();
		
		frame.setVisible(true);
	}
	
}else if(e.getSource()==addweight) {
	String start =choicefirst.getSelectedItem();
	String end =choicelast.getSelectedItem();
	String weight = txtweight.getText();
	
	int we = Integer.parseInt(weight);
	int en = Integer.parseInt(end)-1;
	int st = Integer.parseInt(start)-1;
	//khởi tạo cạnh
	if(en!=st) {
		bg[st][en]=we;
		drawMatrix(bg);
		Edge temp = new Edge(st, en, we);
		//kiểm tra cạnh đã tồn tại
		int check =-1;
		for(int i=0;i<edges.size();i++) {
			if(temp.getFirstPoint()==edges.get(i).getFirstPoint()&&temp.getLastPoint()==edges.get(i).getLastPoint()) {
				check=i;
				break;
			}
		}
		if(check==-1) {
			edges.add(temp);
			this.E++;
		}else {
			edges.set(check,temp);
		}	
	}
	frame.setVisible(true);
}else if(e.getSource()==gofind) {
	String st=from.getSelectedItem();
	String en =to.getSelectedItem();
	int en1 = Integer.parseInt(en)-1;
	int st1 = Integer.parseInt(st)-1;
	S =st1;
	F =en1;
	Graph graph = new Graph(V,E,edges);
	Main.bellmanford(graph,S,F);
	txtResult.setText(Main.result);
}else if(e.getSource()==reset) {
	setView(false);
	bg=null;
	frame.remove(matrix);
	frame.repaint();
	
}else if(e.getSource()==loadFile) {
	JFileChooser chooser = new JFileChooser();
    Scanner scanner =null;
  
    if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
    	 try {
    	    File selectFile = chooser.getSelectedFile();
			scanner = new Scanner(selectFile);
			int lineNumber = 0;
			final int MAX_LINES = 10;
			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineNumber++;
				if(lineNumber==1) {
					String oneLine[] =line.split("\\s");
					this.V = Integer.parseInt(oneLine[0]);
					this.E = Integer.parseInt(oneLine[1]);
					bg = new int[this.V][this.V];
				}else {
					if(line.length()>0) {
						String oneLine[] =line.split("\\s");
						for(int i=0;i<oneLine.length;i++) {
							int tmp = Integer.parseInt(oneLine[i]);
							bg[lineNumber-2][i]=tmp;
						}	
					}

				}
			}
//			gán dữ liệu cho cạnh
			for(int i=0;i<bg.length;i++) {
				for(int j=0;j<bg.length;j++) {
					if(bg[i][j]!=99999 &&i!=j) {
						Edge temp = new Edge(i, j, bg[i][j]);
						edges.add(temp);
					}
				}
			}
			
			matrix = new JPanel(new GridLayout(this.V,this.V));
			matrix.setBounds(120, 20, 250, 250);
			drawMatrix(bg);
			frame.add(matrix);
			addItemSelect();
			setView(true);
			frame.setVisible(true);
				
			if(lineNumber==0) {
				JOptionPane.showMessageDialog(frame, "File không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
    	 
    }
   
}
	
}

private void checkEdges() {
	for(int i=0;i<edges.size();i++) {
		System.out.println("s: "+edges.get(i).getFirstPoint()+" e: "+edges.get(i).getLastPoint()+"w: "+edges.get(i).getWeight());
	}
	
}

private void addItemSelect() {
	choicefirst.removeAll();
	choicelast.removeAll();
	from.removeAll();
	to.removeAll();
	for(int i =0;i<this.V;i++) {
		choicefirst.addItem(""+(i+1));
		choicelast.addItem(""+(i+1));
		from.addItem(""+(i+1));
		to.addItem(""+(i+1));
	}
	
}

private void drawMatrix(int[][] bg) {
	matrix.removeAll();
	for(int i=0;i<bg.length;i++) {
		for(int j=0;j<bg.length;j++) {
			int temp = bg[i][j];
			if(temp==Main.INFINITY) {
				JButton tmp = new JButton(String.valueOf("∞"));
				tmp.setMargin(new Insets(0, 0, 0, 0));
				matrix.add(tmp);
			}else {
				JButton tmp = new JButton(String.valueOf(temp));
				tmp.setMargin(new Insets(0, 0, 0, 0));
				matrix.add(tmp);
			}
			
		}
	}
	
}
private void setView(boolean status) {
	addweight.setEnabled(status);
	gofind.setEnabled(status);
	reset.setEnabled(status);
	saveResult.setEnabled(status);
	txtweight.setEnabled(status);
	choicefirst.setEnabled(status);
	choicelast.setEnabled(status);
	from.setEnabled(status);
	to.setEnabled(status);
	choiceAlgorithm.setEnabled(status);
	txtEdge.setEnabled(!status);
	txtVertex.setEnabled(!status);
	loadFile.setEnabled(!status);
	init.setEnabled(!status);
}

}
