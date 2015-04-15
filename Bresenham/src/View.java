import java.awt.*;

import javax.swing.*;

public class View extends JFrame{
	public View(){
		JPanel content = new PaintingArea();
		this.setSize(400, 400);
		this.setContentPane(content);
		this.setVisible(true);
	}
	
	public void fill(){
		Container content = new JButton("Test");
		this.setContentPane(content);
	}
	
}
