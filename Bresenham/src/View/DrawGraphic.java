package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Model.BresenhamCircle;

public class DrawGraphic extends JPanel{
	private int[][] matrix;
	
	public DrawGraphic(int [][]matrix){
		this.matrix=matrix;
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.red);
	    for(int i=0;i < matrix.length;i++){
	    	for(int j=0; j< matrix.length;j++){
	    		if(matrix[i][j] == 1){
	    			g2d.drawLine(i, j, i, j);
	    		}	
	    	}
	    }
	}

}
