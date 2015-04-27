package algorithms;
import objects.Matrix;
import Jama.*;

public class MatrixOperations {
	
	private int[][]data;
	private int[][] operationArray = new int[3][3];
	
	public MatrixOperations(){
		this.resetToIdentityMatrix();
		this.data = new int[Matrix.getHeight()][Matrix.getWidth()];
	}
	
	public void slide(int x, int y){
		
	}
	
	public void rotate(int angle){
		
	}
	
	// Offsets between 1 and Matrix Width or Matrix Height
	public void setNewDrawingMatrix(int xOffset, int yOffset){
		
		//TODO: If Offsets are in range
		
		for(int i = 0; i < Matrix.getWidth(); i++){
			for(int j = 0; j < Matrix.getHeight(); j++){
				int x = i - xOffset;
				int y = yOffset - j;
				int z = 0;
				multiplyAndDraw(x, y, z);				
			}
		}
		Matrix.setMatrix(data);
	}
	private void multiplyAndDraw(int x, int y, int z){
		
//multiply matrix		
		if (x < Matrix.getWidth() && y < Matrix.getHeight() && y >= 0 && x >= 0) {
			data[y][x] = 1;
		}
		
	}
	
	private void resetToIdentityMatrix(){
		operationArray[0][0] = 1;
		operationArray[0][1] = 0;
		operationArray[0][2] = 0;
		operationArray[1][0] = 0;
		operationArray[1][1] = 1;
		operationArray[1][2] = 0;
		operationArray[2][0] = 0;
		operationArray[2][1] = 0;
		operationArray[2][2] = 1;
	}
	
	

}
