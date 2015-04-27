package algorithms;

import java.util.Stack;

import objects.Matrix;

public class FillPolygon {
	
	private Matrix matrix;
	public FillPolygon(Matrix matrix) {
		// TODO Auto-generated constructor stub
		this.matrix = matrix;
	}

	public void fill(int x, int y){
		int data[][] = matrix.getMatrix();
		Stack<Integer> intstk = new Stack<Integer>();
		if(data[y][x]==1){
			System.out.println("Startpoint is on border");
		}else{
			data[y][x] = 1; //mark first pixel
			intstk.push(x);
			intstk.push(y);
			while(!intstk.empty()){
				int tempY = intstk.pop();
				int tempX = intstk.pop();
				System.out.println("Y is "+tempY +" and X is "+tempX);
				if(tempY-1 >= 0 && data[tempY-1][tempX] == 0){ //Pixel above. If no Border mark and Push to stack
					data[tempY-1][tempX] = 1;
					intstk.push(tempX);
					intstk.push(tempY-1);
				}
				if(tempX-1 >= 0 && data[tempY][tempX-1] == 0){ //Pixel left. If no Border mark and Push to stack
					data[tempY][tempX-1] = 1;
					intstk.push(tempX-1);
					intstk.push(tempY);
				}
				if(tempX+1 < 400 && data[tempY][tempX+1] == 0){ //Pixel right. If no Border mark and Push to stack
					data[tempY][tempX+1] = 1;
					intstk.push(tempX+1);
					intstk.push(tempY);
				}
				if(tempY+1 < 400 && data[tempY+1][tempX] == 0){ //Pixel bottom. If no Border mark and Push to stack
					data[tempY+1][tempX] = 1;
					intstk.push(tempX);
					intstk.push(tempY+1);
				}
			}
			
			
		}
	}
}