package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Model.BresenhamCircle;

public class Circle extends JPanel{
	private int xOffset;
	private int yOffset;
	private int radius;
	public Circle(int xOffset, int yOffset, int radius){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.radius = radius;
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.red);
	    BresenhamCircle bc = new BresenhamCircle();
	    int [][] circleData = bc.create(radius);
	    for(int i=0;i < circleData.length;i++){
	    	for(int j=0; j< circleData.length;j++){
	    		if(circleData[i][j] == 1){
	    			g2d.drawLine(i+xOffset, j+yOffset, i+xOffset, j+yOffset);
	    		}	
	    	}
	    }
	}

}
