package objects;

public class Matrix {
	private int[][] matrix;
	private int width;
	private int height;

	public Matrix(int width, int height) {
		this.width = width;
		this.height = height;
		this.matrix = new int[width][height];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public void setPoint(int x, int y) {
		if (x < getWidth() && y < getHeight() && y >= 0 && x >= 0) {
			this.matrix[y][x] = 1;
		}
	}

	public int getPoint(int x, int y) {
		return this.matrix[y][x];
	}

	public void clearMatrix() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				matrix[i][j] = 0;
			}
		}
	}

}