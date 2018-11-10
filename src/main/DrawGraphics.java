package main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DrawGraphics implements ActionListener{
	 JButton init,addweight,gofind,reset,saveResult,loadFile,btnConvert;
	 JPanel matrix, graph;
	 JTextField txtVertex, txtweight;
	 JFrame frame;
	 Choice choicefirst, choicelast,from,to,choiceAlgorithm,choiseMinimumTree;
	 JLabel txtResult,txtCost,txtNumberRoad,labelResult;
	 static int bg[][];
	 int S,F,V,E;
	 ArrayList<Edge> edges = new ArrayList<>();
	 GraphDraw graphDraw;
	 JPanel right;
	 ButtonGroup btnGroup;
	 JRadioButton radio1 ,radio2;
	 
public DrawGraphics() {
	//khởi tạo JFrame
	frame = new JFrame();
	frame.setLayout(null);
	frame.setSize(1100, 600);
	frame.setLocation(100, 30);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setResizable(false);
	
	// khởi tạo JPanel chọn thuật toán
	right = new JPanel(null);
	right.setBounds(700, 0, 400, 600);
	right.setBackground(Color.CYAN);
	
	// ma trận trọng số <=> đồ thị
	JPanel down = new JPanel();
	down.setBackground(Color.WHITE);
	down.setBounds(0, 550, 700, 20);
	ImageIcon img = new ImageIcon("Data\\button1.png");
	btnConvert = new JButton(img);
	btnConvert.setBounds(300, 550, 100, 20);
	frame.add(btnConvert);
	frame.add(down);
	btnConvert.addActionListener(this);
	//tạo số đỉnh
	JLabel lVertex = new JLabel("Nhập số đỉnh");
	lVertex.setBounds(10, -30, 100, 100);
	right.add(lVertex);
    txtVertex = new JTextField(5);
    txtVertex.setText("");
	txtVertex.setBounds(100, 10, 60, 20);
	right.add(txtVertex);
	init = new JButton("Khởi tạo");
	init.setBounds(140, 50, 120, 20);
	right.add(init);
	init.addActionListener(this);
	
	//chọn từ file
    loadFile = new JButton("Chọn từ File");
	loadFile.setBounds(200, 10, 120, 20);
	loadFile.addActionListener(this);
	right.add(loadFile);
	
	//thêm trọng số cho cạnh
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
	weight.setBounds(170, 80, 100, 20);
	right.add(weight);
    txtweight = new JTextField(5);
	txtweight.setBounds(170, 110, 50, 22);
	right.add(txtweight);
	
	addweight = new JButton("Thêm cạnh");
	addweight.setBounds(250, 110, 100, 22);
	right.add(addweight);
	addweight.addActionListener(this);
	
	//chọn bài toán
	btnGroup = new ButtonGroup();
    radio1 = new JRadioButton("Tìm đường đi ngắn nhất");
	radio2 = new JRadioButton("Cây khung nhỏ nhất");
	btnGroup.add(radio1);
	btnGroup.add(radio2);
	radio1.setBounds(5, 160, 180, 20);
	radio2.setBounds(230, 160, 150, 20);
	radio1.setBackground(null);
	radio2.setBackground(null);
	radio1.setSelected(true);
	right.add(radio1);
	right.add(radio2);
	radio1.addActionListener(this);
	radio2.addActionListener(this);
			
	// chọn thuật toán tìm đường
	choiceAlgorithm = new Choice();
	choiceAlgorithm.setBounds(30, 192, 110, 22);
	choiceAlgorithm.addItem("Bellman-Ford");
	choiceAlgorithm.addItem("Dijkstra");
	choiceAlgorithm.addItem("Floyd-Warshall");
	choiceAlgorithm.addItem("Tìm k đường đi ngắn nhất");
	right.add(choiceAlgorithm);
	
	// chọn thuật toán cây khung
	choiseMinimumTree = new Choice();
	choiseMinimumTree.setBounds(250, 192, 110, 22);
	choiseMinimumTree.addItem("Prim");
	choiseMinimumTree.addItem("Kruskal");
	right.add(choiseMinimumTree);
	//chọn đường đi
	JLabel txtfindRoad = new JLabel("Từ");
	txtfindRoad.setBounds(10, 230, 20, 20);
	right.add(txtfindRoad);
	
	from = new Choice();
	from.setBounds(35, 230, 50, 22);
	right.add(from);
	
	JLabel txtfrom = new JLabel("đến");
	txtfrom.setBounds(90, 230, 30, 22);
	right.add(txtfrom);
	
    to = new Choice();
	to.setBounds(120, 230, 50, 22);
	right.add(to);
	
    
	
	gofind = new JButton("Xem kết quả");
	gofind.setBounds(124, 275, 150, 22);
	right.add(gofind);
	gofind.addActionListener(this);
    
	txtCost = new JLabel();
    txtCost.setBounds(10, 340, 260, 50);
    right.add(txtCost);
	
    //view hiển thị  kq
    JLabel kq = new JLabel("KẾT QUẢ");
    kq.setBounds(170, 300, 260, 50);
    right.add(kq);
    
    txtNumberRoad = new JLabel("");
    txtNumberRoad.setBounds(10, 320, 260, 50);
    right.add(txtNumberRoad);
    
    labelResult = new JLabel("");
    labelResult.setBounds(10, 360, 260, 50);
	right.add(labelResult);
	
	txtResult = new JLabel("");
    txtResult.setBounds(10, 372, 300, 150);
    right.add(txtResult);
    
    // tạo lại đồ thị
    reset = new JButton("Reset");
    reset.setBounds(40, 530, 100, 20);
    reset.addActionListener(this);
    right.add(reset);
    
    //lưu kết quả vào file
    saveResult = new JButton("Lưu kết quả(.txt)");
    saveResult.setBounds(200, 530, 140, 20);
    right.add(saveResult);
    saveResult.addActionListener(this);
    setView(false);
	frame.add(right);
	frame.setVisible(true);
}

@Override
public void actionPerformed(ActionEvent e) {
if(e.getSource()==init) { //khởi tạo số đỉnh
	String V = txtVertex.getText();
	if(V.length()==0) {
		JOptionPane.showMessageDialog(frame, "Vui lòng nhập số đỉnh", "Lỗi", JOptionPane.ERROR_MESSAGE);
	}else {
		
		this.V =Integer.parseInt(V); // lấy số đỉnh của đồ thị
		if(this.V<=10) {
			setView(true);
			this.E=0; // số cạnh
			bg = new int [this.V][this.V];
			graphDraw = new GraphDraw();
			frame.add(graphDraw);
			addItemSelect(); 
			frame.setVisible(true);	
		}else {
			JOptionPane.showMessageDialog(frame, "Giới hạn đỉnh là 10", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}else if(e.getSource()==addweight) { // thêm trọng số cho cạnh
	int st =choicefirst.getSelectedIndex();
	int en =choicelast.getSelectedIndex();
	String weight = txtweight.getText();
	if(weight.length()==0) {
		JOptionPane.showMessageDialog(frame, "Vui lòng nhập trọng số", "Lỗi", JOptionPane.ERROR_MESSAGE);
	}else {
		//khởi tạo cạnh
		int we = Integer.parseInt(weight);
		if(en!=st && we !=0) {
			bg[st][en]=we;
			Edge temp = new Edge(st, en, we);
			if(checkEdges(temp,edges)==-1) {	//kiểm tra cạnh đã tồn tại
				edges.add(temp);
				this.E++;
			}else {
				edges.set(checkEdges(temp,edges),temp);
			}	
		}
		graphDraw.repaint();
		frame.setVisible(true);
	}

}else if(e.getSource()==gofind) { // chọn thuật toán 
	Main.cost="";
	Main.txtNumberRoad="";
	if(radio1.isSelected()) {
		labelResult.setText("Đường đi ngắn nhất là: ");
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
		}else if(Algorithm==2) {
			main.Floyd(bg, S, F);
		}else if(Algorithm==3) {
			txtResult.setBounds(10, 372, 300, 150);
			main.findAllShortestPath(graph,S,F);
		}
	}else {
		Main.prim(bg);
		labelResult.setText("Tập cạnh của cây khung nhỏ nhất là: ");
		txtResult.setBounds(10, 330, 300, 150);
		
	}
	txtNumberRoad.setText(Main.txtNumberRoad);
	txtCost.setText(Main.cost);
	txtResult.setText(Main.result);
	graphDraw.repaint();
	frame.setVisible(true);
	
	
}else if(e.getSource()==reset) { // reset đồ thị
	setView(false);
	Main.path=null;
	Main.Kpath=null;
	Main.parent =null;
	bg=null;
	txtNumberRoad.setText("");
	txtVertex.setText("");
	choicefirst.removeAll();
	choicelast.removeAll();
	txtweight.setText("");
	from.removeAll();
	to.removeAll();
	txtResult.setText("");
	txtCost.setText("");
	labelResult.setText("");
	Main.cost="";
	this.E=0;
	edges.removeAll(edges);
	if(matrix!=null) frame.remove(matrix);
	if(graphDraw!=null) frame.remove(graphDraw);
	frame.repaint();
	
}else if(e.getSource()==loadFile) { // đọc dữ liệu từ file
	JFileChooser chooser = new JFileChooser();
    Scanner scanner =null;
  
    if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) { // chọn file thành công
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
			
			graphDraw = new GraphDraw();
			frame.add(graphDraw);
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
   
}else if(e.getSource()==saveResult) { // lưu kết quả vào file
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
	
}else if(e.getSource()==btnConvert) {
	if(graphDraw ==null) {
		frame.remove(matrix);
		matrix=null;
		graphDraw = new GraphDraw();
		frame.add(graphDraw);	
	}else {
		frame.remove(graphDraw);
		graphDraw=null;
		matrix = new JPanel(new GridLayout(this.V,this.V));
		matrix.setBounds(190, 115, 300, 300);
		drawMatrix(bg); // vẽ ma trận trọng số
		frame.add(matrix); // add vào frame
	}
	frame.setVisible(true);
	frame.repaint();
}
}

private int checkEdges(Edge temp, ArrayList<Edge> edges2) {
	int check =-1;
	for(int i=0;i<edges.size();i++) {
		if(temp.getFirstPoint()==edges.get(i).getFirstPoint()&&temp.getLastPoint()==edges.get(i).getLastPoint()) {
			check=i;
			return check;
		}
	}
	return check;
}

// thêm item vào choice
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
// vẽ ma trận trọng số
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
// hiển thị button chọn
private void setView(boolean status) {
	choiseMinimumTree.setEnabled(status);
	addweight.setEnabled(status);
	btnConvert.setEnabled(status);
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
