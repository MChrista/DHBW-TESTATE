import java.awt.*;
import javax.swing.*;

public class PaintingArea extends JPanel{
	
	
	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setColor(Color.red);
	    
	    
	  }
	  
	

}
