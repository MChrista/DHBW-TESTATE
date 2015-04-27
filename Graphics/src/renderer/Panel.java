package renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.swing.JPanel;

import objects.Matrix;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	private Matrix matrix;
	
	public Panel(Matrix matrix) {
		this.matrix = matrix;
		this.setPreferredSize(new Dimension(matrix.getWidth(), matrix.getHeight()));
		//this.setSize(Matrix.getWidth(), Matrix.getHeight());
	}
	
	 @Override
	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 render(g);
	 }

	private void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//g2d.drawline(x, y, x, y);
		for(int i=0; i<matrix.getWidth(); i++) {
			for(int j=0; j<matrix.getHeight(); j++) {
				if(matrix.getPoint(j, i) == 1) {
					g2d.setColor(Color.BLACK);
					g2d.drawLine(i, j, i, j);
				}
			}
		}
	}
	
}