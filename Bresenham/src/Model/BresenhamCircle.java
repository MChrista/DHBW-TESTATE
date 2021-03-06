package Model;

import java.util.Stack;

public class BresenhamCircle {
	private int size = 0;

	
	public int [][] create(int radius){
		size = (radius * 2) + 1;
		int [][] matrix = new int[size][size];
		int distance = 3-(2*radius);
		int x = 0;
		int y = radius;
		setPoint(matrix,x, y);
		
		while(x<y){
			if(distance<0){
				distance = distance + 4*x +6;
				if(distance>0){
					y--;
				}
			}else{
				distance = distance + 4*(x-y)+10;
				if(distance>0){
					y--;
				}
			}
			x++;
			setPoint(matrix, x, y);
			
		}
		return matrix;
	}
	
	public void setPoint(int[][] matrix,int x, int y){
		int offset = (size / 2);
		matrix[offset - y][x + offset] = 1;
		matrix[offset - y][-x + offset] = 1;
		matrix[offset+y][x+ offset] = 1;
		matrix[offset+y][-x+ offset] = 1;
		matrix[x+ offset][offset - y] = 1;
		matrix[-x+ offset][offset - y] = 1;
		matrix[x+ offset][offset+y] = 1;
		matrix[-x+ offset][offset+y] = 1;
		return;
	}
	
	 // Testing Function	
	public void print(){
		int[][]matrix = create(12);
		for(int i = 0;i<size;i++){
			for(int j=0;j<size;j++){
				System.out.print(matrix[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
}
