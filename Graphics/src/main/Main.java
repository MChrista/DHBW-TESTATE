package main;

import algorithms.BresenhamCircle;
//import algorithms.BresenhamLine;
import objects.Matrix;
import renderer.Frame;
import renderer.Panel;

public class Main {

	public static void main(String[] args) {
		final int SIZE = 400;
		new Matrix(SIZE, SIZE);
		//BresenhamLine bl = new BresenhamLine();
		//bl.drawLine(25, 30, 200, 200);
		BresenhamCircle bc = new BresenhamCircle();
		bc.drawCircle(50);
		new Frame(new Panel(Matrix.getMatrix()));
	}

}