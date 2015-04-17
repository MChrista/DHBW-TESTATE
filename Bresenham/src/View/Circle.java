package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Model.BresenhamCircle;

public class Circle extends JPanel{
	
	public Circle(){
		
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.red);
	    BresenhamCircle bc = new BresenhamCircle();
	    int [][] circleData = bc.create(20);
	    for(int i=0;i < circleData.length;i++){
	    	for(int j=0; j< circleData.length;j++){
	    		if(circleData[i][j] == 1){
	    			System.out.println("x is "+ i);
	    			System.out.println("y is "+ j);
	    			g2d.drawLine(i, j, i, j);
	    		}	
	    	}
	    }
	}

}
