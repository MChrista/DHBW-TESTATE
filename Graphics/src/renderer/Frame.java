package renderer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import objects.Matrix;

@SuppressWarnings("serial")
public class Frame extends JFrame {
	public Frame(JPanel content) {
		this.setSize(Matrix.getWidth()+200, Matrix.getHeight()+200);
		this.setContentPane(content);
		this.pack();
		this.setVisible(true);
	}
}