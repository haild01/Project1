package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

import javax.management.ImmutableDescriptor;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DrawGraph extends JPanel {
	Font f2;
	private static int ARR_SIZE = 0;

	public DrawGraph() {
		setSize(700, 700);
		setVisible(true);
		add(new JLabel("Graph"));
		f2 = new Font("Calibri", Font.BOLD, 15);

	}
		//vẽ cây đồ thị
	@Override
	public void paint(Graphics g2d) {
		if (DisplayComponent.bg != null) {
			int V = DisplayComponent.bg.length; // số đỉnh
			int tmp[][] = DisplayComponent.bg; // ma trận trọng số
			ARR_SIZE =5;
			Graphics2D g = (Graphics2D) g2d.create();
			RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHints(hints);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 700, 550);
			Color c1 = new Color(102, 255, 51);
			Color c2 = new Color(0, 0, 102);
			Font font = new Font("Monaco", Font.BOLD, 15);
			g.setStroke((Stroke) new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			for (int i = 0; i < V; i++) { // vẽ đỉnh
				g.setColor(c1);
				g.fillOval(Main.vertexs.get(i).getX(), Main.vertexs.get(i).getY(), 25, 25);
				// tên đỉnh
				g.setFont(font);
				g.setColor(c2);
				g.drawString(Main.vertexs.get(i).getName(), Main.vertexs.get(i).getX() + 7,
						Main.vertexs.get(i).getY() + 17);
			}
			// vẽ cạnh
			for (int i = 0; i < V; i++) {
				for (int j = 0; j < V; j++) {
					if (i != j && tmp[i][j] != 0) {
						int x1 = Main.vertexs.get(i).getX();
						int y1 = Main.vertexs.get(i).getY();
						int x2 = Main.vertexs.get(j).getX();
						int y2 = Main.vertexs.get(j).getY();
						if (x1 < x2) {
							x1 += 24;
							x2 -= 2;
						} else {
							x1 -= 2;
							x2 += 26;
						}
						if (y1 > y2) {
							y1 += 10;
							y2 += 10;
						} else {
							y1 += 10;
							y2 += 10;
						}
						if(tmp[j][i] != 0) g.drawLine(x1, y1, x2, y2); // có đường đi ngược lại
						else drawArrow(g, x1, y1, x2, y2);
						g.drawString(tmp[i][j] + "", (x1 + x2) / 2, (y1 + y2) / 2);
					}
				}
			}
			paintResult(g);
			paintKPath(g);
			paintMinimumTree(g);
		}

	}
	// vẽ cây khung nhỏ nhất
	private void paintMinimumTree(Graphics2D g2d) {
		if(Main.parent !=null) {
			int tmp[] = Main.parent;
			Graphics2D g = (Graphics2D) g2d.create();
			RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHints(hints);
			//set font and color
			g.setStroke((Stroke) new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			Font font = new Font("Monaco", Font.BOLD, 15);
			Color  color = new Color(255,0,0);
			g.setFont(font);
			g.setColor(color);
		
			for(int i=1;i<tmp.length;i++) {
				if(tmp[i]!=-1) {
					int  first = tmp[i]; // nút bắt đầu
					int  last = i;  // nút kết thúc
				
					int x1 = Main.vertexs.get(first).getX();
					int y1 = Main.vertexs.get(first).getY();
					int x2 = Main.vertexs.get(last).getX();
					int y2 = Main.vertexs.get(last).getY();
					if (x1 < x2) {
						x1 += 24;
						x2 -= 2;
					} else {
						x1 -= 2;
						x2 += 26;
					}
					if (y1 > y2) {
						y1 += 10;
						y2 += 10;
					} else {
						y1 += 10;
						y2 += 10;
					}
					g.drawLine(x1, y1, x2, y2);
				}
		
			}
		}
			
		}
	// vẽ cạnh có hướng
	static void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
		Graphics2D g = (Graphics2D) g1.create();
		double dx = x2 - x1, dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);
		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g.transform(at);
		g.drawLine(0, 0, len, 0);
		g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len }, new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 },
				4);
	}

	// vẽ đường đi ngắn nhất
	public static void paintResult(Graphics g2d) {
		if (Main.path != null) {
			ARR_SIZE =7;
			int tmp[][] = DisplayComponent.bg; // ma trận trọng số
			ArrayList path = Main.path;
			Graphics2D g = (Graphics2D) g2d.create();
			RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHints(hints);
			//set font and color
			g.setStroke((Stroke) new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			Font font = new Font("Monaco", Font.BOLD, 15);
			Color  color = new Color(255,0,0);
			g.setFont(font);
			g.setColor(color);
			for (int k = 0; k < path.size() - 1; k++) {
				int first = (int) path.get(k);
				int last = (int) path.get(k+1);
				int x1 = Main.vertexs.get(first).getX();
				int y1 = Main.vertexs.get(first).getY();
				int x2 = Main.vertexs.get(last).getX();
				int y2 = Main.vertexs.get(last).getY();
				if (x1 < x2) {
					x1 += 24;
					x2 -= 2;
				} else {
					x1 -= 2;
					x2 += 26;
				}
				if (y1 > y2) {
					y1 += 10;
					y2 += 10;
				} else {
					y1 += 10;
					y2 += 10;
				}
				drawArrow(g, x1, y1, x2, y2);
				g.drawString(tmp[first][last] + "", (x1 + x2) / 2, (y1 + y2) / 2);

			}

		}

	}
	// vẽ k đường đi ngắn nhất
	public static void paintKPath(Graphics g2d) {
		if(Main.Kpath !=null) {
			ARR_SIZE =7;
			int tmp[][] = DisplayComponent.bg; // ma trận trọng số
			ArrayList path = Main.path;
			Graphics2D g = (Graphics2D) g2d.create();
			RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHints(hints);
			//set font and color
			g.setStroke((Stroke) new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			Font font = new Font("Monaco", Font.BOLD, 15);
			
			g.setFont(font);
			for(int i =0;i<Main.Kpath.size();i++) {
				ArrayList temp = (ArrayList) Main.Kpath.get(i);
				Random random = new Random();
				
				Color  color = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
				g.setColor(color);
					for(int j =temp.size()-1;j>0;j--) {
						int first = (int) temp.get(j);
						int last = (int) temp.get(j-1);
						int x1 = Main.vertexs.get(first).getX();
						int y1 = Main.vertexs.get(first).getY();
						int x2 = Main.vertexs.get(last).getX();
						int y2 = Main.vertexs.get(last).getY();
						if (x1 < x2) {
							x1 += 24;
							x2 -= 2;
						} else {
							x1 -= 2;
							x2 += 26;
						}
						if (y1 > y2) {
							y1 += 10;
							y2 += 10;
						} else {
							y1 += 10;
							y2 += 10;
						}
						drawArrow(g, x1, y1, x2, y2);
						g.drawString(tmp[first][last] + "", (x1 + x2) / 2, (y1 + y2) / 2);
					}
				
			}
			
		}
	}
}
