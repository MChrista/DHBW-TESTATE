import java.awt.*;
import javax.swing.*;

public class PaintingArea extends JPanel{
	  public void paintComponent(Graphics g) {
		int radius = 12;
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.red);
	    int distance = 3-(2*radius);
		int x = 0;
		int y = radius;
		g2d.drawLine(x,x, y,y);
		g2d.drawLine(-x,-x,y,y);
		g2d.drawLine(x,x,-y,-y);
		g2d.drawLine(-x,-x,-y,-y);
		g2d.drawLine(y,y,x,x);
		g2d.drawLine(y,y,-x,-x);
		g2d.drawLine(-y,-y,x,x);
		g2d.drawLine(-y,-y,-x,-x);
		
		while(x<y){
			if(distance<0){
				distance = distance + 4*x +6;
				if(distance>0){
					y--;
				}
			}else{
				distance = distance + 4*(x-y)+10;
				if(distance>0){
					y--;
				}
			}
			x++;
			g2d.drawLine(x,x, y,y);
			g2d.drawLine(-x,-x,y,y);
			g2d.drawLine(x,x,-y,-y);
			g2d.drawLine(-x,-x,-y,-y);
			g2d.drawLine(y,y,x,x);
			g2d.drawLine(y,y,-x,-x);
			g2d.drawLine(-y,-y,x,x);
			g2d.drawLine(-y,-y,-x,-x);
			
			
		}
	  }
	

}
