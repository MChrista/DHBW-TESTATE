package renderer;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	public Frame(JPanel content) {
		this.setSize(content.getWidth()+200, content.getHeight()+200);
		this.setContentPane(content);
		this.pack();
		this.setVisible(true);
	}
}