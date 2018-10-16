package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.text.Style;

public class TreeGraph extends JPanel {
	public TreeGraph() {

//		setBackground(Color.RED);
		setBounds(100, 100, 300, 300);
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval(20, 20, 40, 40);
		g.setColor(Color.black);
		g.setFont(new Font("MONACO", Font.BOLD, 15));
		g.drawString("A", 20, 20);
		
		g.setColor(Color.ORANGE);
		g.fillOval(250, 20, 40, 40);
		g.setColor(Color.black);
		g.setFont(new Font("MONACO", Font.BOLD, 15));
		g.drawString("B", 250, 20);
		
		g.drawLine(30, 20, 240, 20);
	}

}
