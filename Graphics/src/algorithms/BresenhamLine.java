package algorithms;

import objects.Matrix;

public class BresenhamLine {
	
	private Matrix matrix;

	public BresenhamLine(Matrix matrix) {
		// TODO Auto-generated constructor stub
		this.matrix = matrix;
	}

	// set point between 0 and 399
	public void drawLine(int x1, int y1, int x2, int y2) {
		// Matrix.setPoint(x1, y1);
		// Matrix.setPoint(x2, y2);
		int xIncrement = 1, yIncrement = 1, dy = 2 * (y2 - y1), dx = 2 * (x1 - x2), tmp;

		if (x1 > x2) { // Spiegeln an Y-Achse
			xIncrement = -1;
			dx = -dx;
		}

		if (y1 > y2) { // Spiegeln an X-Achse
			yIncrement = -1;
			dy = -dy;
		}

		int e = 2 * dy + dx;
		int x = x1; // Startpunkte setzen
		int y = y1;

		if (dy < -dx) // Steigung < 1
		{
			while (x != (x2 + 1)) {
				e += dy;
				if (e > 0) {
					e += dx;
					y += yIncrement;
				}
				if (x < matrix.getWidth() && y < matrix.getHeight() && x >= 0 && y >= 0) {

					System.out.println(x + " " + y);
					matrix.setPoint(x, y);
				}
				x += xIncrement;
			}
		} else // ( dy >= -dx ) Steigung >=1
		{
			// an der Winkelhalbierenden spiegeln
			tmp = -dx;
			dx = -dy;
			dy = tmp;

			e = 2 * dy + dx;

			while (y != (y2 + 1)) {
				e += dy;
				if (e > 0) {
					e += dx;
					x += xIncrement;
				}

				if (x < matrix.getWidth() && y < matrix.getHeight() && x >= 0 && y >= 0) {
					System.out.println(x + " " + y);
					matrix.setPoint(x, y);
				}
				y += yIncrement;
			}
		}
	}

	/*
	 * public void drawLine(int startX, int startY, int endX, int endY) { int x
	 * = 0, y = 0; int width = endX - startX; int height = endY - startY; int
	 * dStartX = 0, dEndX = 0, dStartY = 0, dEndY = 0; if(width<0) { dStartX =
	 * -1; dEndX = -1; } else if (width>0) { dStartX = 1; dEndX = 1; } if
	 * (height<0) { dStartY = -1; } else if (height>0) { dStartY = 1 ; } int
	 * longest = Math.abs(width) ; int shortest = Math.abs(height) ; if
	 * (!(longest>shortest)) { longest = Math.abs(height) ; shortest =
	 * Math.abs(width) ; if (height<0) { dEndY = -1 ; } else if (height>0) {
	 * dEndY = 1 ; } dEndX = 0 ; } int numerator = longest >> 1 ; for (int
	 * i=0;i<=longest;i++) { Matrix.setPoint(x, y); numerator += shortest ; if
	 * (!(numerator<longest)) { numerator -= longest ; x += dStartX ; y +=
	 * dStartY ; } else { x += dEndX ; y += dEndY ; } } }
	 */

}