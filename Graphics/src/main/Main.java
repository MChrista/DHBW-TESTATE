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
		bl.drawLine(0, 0, 400, 400);
		bl.drawLine(400, 0, 0, 400);
		bl.drawLine(0, 250, 400, 250);
		BresenhamCircle bc = new BresenhamCircle();
		bc.drawCircle(50);
		bc.drawCircle(250);
		FillPolygon fp = new FillPolygon();
		fp.fill(210, 20);
		fp.fill(210, 210);
		new Frame(new Panel(Matrix.getMatrix()));
		
	}

}