package View;

import java.awt.Container;


import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame{
	public MainWindow() {
		JPanel content = new Circle(100,100,50);
		this.setSize(400, 400);
		this.setContentPane(content);
		this.setVisible(true);
		// TODO Auto-generated constructor stub
	}
}
