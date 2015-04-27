package main;

import algorithms.BresenhamCircle;
import algorithms.FillPolygon;
import algorithms.BresenhamLine;
import algorithms.NewtonInterpolation;
import objects.Matrix;
import renderer.Frame;
import renderer.Panel;

public class Main {

	public static void main(String[] args) {
		final int SIZE = 400;
		Matrix matrix = new Matrix(SIZE, SIZE);
		//public static Matrix m = new Matrix(SIZE, SIZE);
		BresenhamLine bl = new BresenhamLine(matrix);
		bl.drawLine(0, 0, 400, 400);
		bl.drawLine(400, 0, 0, 400);
		bl.drawLine(0, 250, 400, 250);
		BresenhamCircle bc = new BresenhamCircle(matrix);
		bc.drawCircle(50);
		bc.drawCircle(250);
		FillPolygon fp = new FillPolygon(matrix);
		fp.fill(210, 20);
		fp.fill(210, 210);
		/*
		NewtonInterpolation ni = new NewtonInterpolation();
		double[] f = {40.7, 50.9};
		double[] x = {90.3, 66.6};
		ni.newton(f, x);
		*/
		new Frame(new Panel(matrix));
	}

}