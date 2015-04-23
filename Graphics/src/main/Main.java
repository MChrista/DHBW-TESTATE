package main;

import algorithms.BresenhamCircle;
import algorithms.FillPolygon;
import algorithms.BresenhamLine;
import objects.Matrix;
import renderer.Frame;
import renderer.Panel;

public class Main {

	public static void main(String[] args) {
		final int SIZE = 400;
		new Matrix(SIZE, SIZE);
		BresenhamLine bl = new BresenhamLine();
		bl.drawLine(0, 0, 399, 399);
		bl.drawLine(399, 0, 0, 399);
		bl.drawLine(0, 250, 399, 250);
		//BresenhamCircle bc = new BresenhamCircle();
		//bc.drawCircle(50);
		FillPolygon fp = new FillPolygon();
		fp.fill(210, 20);
		fp.fill(210, 210);
		new Frame(new Panel(Matrix.getMatrix()));
	}

}