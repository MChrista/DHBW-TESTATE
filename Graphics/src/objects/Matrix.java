package objects;

public class Matrix {
	private static int[][] matrix;
	private static int width;
	private static int height;
	
	public Matrix(int width, int height) {
		Matrix.width = width;
		Matrix.height = height;
		Matrix.matrix = new int[width][height];
	}
	
	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
	
	public static int[][] getMatrix() {
		return matrix;
	}

	public static void setMatrix(int[][] matrix) {
		Matrix.matrix = matrix;
	}	
	
	public static void setPoint(int x, int y) {
		Matrix.matrix[y+Matrix.getHeight()/2][x+Matrix.getWidth()/2] = 1;
	}
	
	public static int getPoint(int x, int y) {
		return Matrix.matrix[y][x];
	}
	
	public static void clearMatrix() {
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				matrix[i][j] = 0;
			}
		}
	}
	
}