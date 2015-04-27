package algorithms;

import objects.Matrix;

public class BresenhamCircle {
	
	private Matrix matrix;

	public BresenhamCircle(Matrix matrix) {
		// TODO Auto-generated constructor stub
		this.matrix = matrix;
	}

	public void drawCircle(int radius) {
		int distance = 3 - (2 * radius);
		int x = 0;
		int y = radius;
		setPoint(x, y);

		while (x < y) {
			if (distance < 0) {
				distance = distance + 4 * x + 6;
				if (distance > 0) {
					y--;
				}
			} else {
				distance = distance + 4 * (x - y) + 10;
				if (distance > 0) {
					y--;
				}
			}
			x++;
			setPoint(x, y);

		}
	}

	private void setPoint(int x, int y) {
		int xOffset = (matrix.getWidth() / 2);
		int yOffset = (matrix.getHeight() / 2);
		System.out.println("X Value is: " + (x + xOffset));
		System.out.println("Y Value is: " + (y + yOffset));
		if ((x + xOffset) <= matrix.getWidth() - 1 && (yOffset + y) <= matrix.getHeight() - 1 && (x + xOffset) >= 0
				&& (yOffset + y) >= 0) {
			matrix.setPoint(x + xOffset, yOffset + y);
			matrix.setPoint(yOffset + y, x + xOffset);
		}
		if (-x + xOffset <= matrix.getWidth() - 1 && yOffset + y <= matrix.getHeight() - 1 && -x + xOffset >= 0
				&& yOffset + y >= 0) {
			matrix.setPoint(-x + xOffset, yOffset + y);
			matrix.setPoint(yOffset + y, -x + xOffset);
		}
		if (x + xOffset <= matrix.getWidth() - 1 && yOffset - y <= matrix.getHeight() - 1 && x + xOffset >= 0
				&& yOffset - y >= 0) {
			matrix.setPoint(x + xOffset, yOffset - y);
			matrix.setPoint(yOffset - y, x + xOffset);
		}
		if (-x + xOffset <= matrix.getWidth() - 1 && yOffset - y <= matrix.getHeight() - 1 && -x + xOffset >= 0
				&& yOffset - y >= 0) {
			matrix.setPoint(-x + xOffset, yOffset - y);
			matrix.setPoint(yOffset - y, -x + xOffset);
		}

	}

}