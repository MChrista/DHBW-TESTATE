package renderer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends JPanel {
	private int[][] matrix;
	
	public Panel(int[][] matrix) {
		this.matrix = matrix;
	}
	
	 @Override
	 public void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 render(g);
	 }

	private void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//g2d.drawline(x, y, x, y);
		for(int i=0; i<matrix.length; i++) {
			for(int j=0; j<matrix.length; j++) {
				if(matrix[i][j] == 1) {
					g2d.setColor(Color.CYAN);
					g2d.drawLine(i, j, i, j);
				}
			}
		}
	}
	
}