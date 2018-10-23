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
import java.io.FileWriter;
import java.io.IOException;
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
	 JTextField txtVertex, txtweight;
	 JFrame frame;
	 Choice choicefirst, choicelast,from,to,choiceAlgorithm;
	 JLabel txtResult,txtCost;
	 int bg[][],S,F,V,E;
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
	
	init = new JButton("Khởi tạo");
	init.setBounds(80, 50, 120, 20);
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
    
    txtCost = new JLabel();
    txtCost.setBounds(10, 360, 260, 50);
    right.add(txtCost);
    
    reset = new JButton("Reset");
    reset.setBounds(10, 410, 100, 20);
    reset.addActionListener(this);
    right.add(reset);
    
    saveResult = new JButton("Lưu kết quả(.txt)");
    saveResult.setBounds(130, 410, 140, 20);
    right.add(saveResult);
    saveResult.addActionListener(this);
    setView(false);
	frame.add(right);
	frame.setVisible(true);
}

@Override
public void actionPerformed(ActionEvent e) {
if(e.getSource()==init) {
	String V = txtVertex.getText();
	if(V.length()==0) {
		JOptionPane.showMessageDialog(frame, "Vui lòng nhập số đỉnh", "Lỗi", JOptionPane.ERROR_MESSAGE);
	}else {
		setView(true);
		int v = Integer.parseInt(V);
		this.V =v;
		this.E=0;
		bg = new int [v][v];
		matrix = new JPanel(new GridLayout(v,v));
		matrix.setBounds(120, 20, 250, 250);
		drawMatrix(bg);
		frame.add(matrix);
		addItemSelect();
		
		frame.setVisible(true);
	}
	
}else if(e.getSource()==addweight) {
	int st =choicefirst.getSelectedIndex();
	int en =choicelast.getSelectedIndex();
	String weight = txtweight.getText();
	int we = Integer.parseInt(weight);
	
	//khởi tạo cạnh
	if(en!=st && we !=0) {
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
	Main.cost="";
	int start=from.getSelectedIndex() ;
	int finish =to.getSelectedIndex() ;
	S =start;
	F =finish;
	Graph graph = new Graph(V,E,edges);
	int Algorithm =choiceAlgorithm.getSelectedIndex() ;
	Main main = new Main();
	if(Algorithm==0) {
		main.bellmanford(graph,S,F);
	}else if(Algorithm==1) {
		main.dijkstra(bg,S,F);
	}
	txtResult.setText(Main.result);
	txtCost.setText(Main.cost);
}else if(e.getSource()==reset) {
	setView(false);
	bg=null;
	txtVertex.setText("");
	choicefirst.removeAll();
	choicelast.removeAll();
	txtweight.setText("");
	from.removeAll();
	to.removeAll();
	txtResult.setText("");
	txtCost.setText("");
	Main.cost="";
	this.E=0;
	edges.removeAll(edges);
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
			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineNumber++;
				if(lineNumber==1) {
					String oneLine[] =line.split("\\s");
					this.V = Integer.parseInt(oneLine[0]);
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
					if(bg[i][j]!=0 && i!=j) {
						Edge temp = new Edge(i, j, bg[i][j]);
						edges.add(temp);
						this.E++;
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
   
}else if(e.getSource()==saveResult) {
	JFileChooser fileChooser = new JFileChooser();	
	fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
	int userSelection = fileChooser.showSaveDialog(frame);
	
	if (userSelection == JFileChooser.APPROVE_OPTION) {
	    File fileToSave = fileChooser.getSelectedFile();
	    try {
	        File f = new File(fileToSave.getAbsolutePath());
	        FileWriter fw = new FileWriter(f);
	        fw.write("Ma trận trọng số "+"\r\n");
	        for(int i =0;i<bg.length;i++) {
	        	for(int j=0;j<bg.length;j++) {
	        		fw.write(bg[i][j]+" ");	
	        	}
	        	fw.write("\r\n");
	        }
	        fw.write("Đường đi ngắn nhất từ "+(this.S+1)+" đến "+(this.F+1)+" là: ");
	        fw.write("\r\n");
	        fw.write(Main.result);
	        fw.close(); 
	      } catch (IOException ex) {
	        System.out.println("Loi ghi file: " + ex);
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
			if(temp==0 && i != j) {
				JButton tmp = new JButton(String.valueOf("∞"));
				tmp.setBackground(Color.PINK);
				tmp.setMargin(new Insets(0, 0, 0, 0));
				matrix.add(tmp);
			}else {
				JButton tmp = new JButton(String.valueOf(temp));
				tmp.setMargin(new Insets(0, 0, 0, 0));
				tmp.setBackground(Color.PINK);
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
	txtVertex.setEnabled(!status);
	loadFile.setEnabled(!status);
	init.setEnabled(!status);
}

}
