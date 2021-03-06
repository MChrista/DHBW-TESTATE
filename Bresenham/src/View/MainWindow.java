package View;

import java.awt.Container;



import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.BresenhamCircle;

public class MainWindow extends JFrame{
	public MainWindow() {
		int xOffset = 100;
		int yOffset = 100;
		BresenhamCircle bc = new BresenhamCircle();
		int[][] data = new int[400][400];
		int [][] circleData = bc.create(50);
		for(int i=0;i<circleData.length;i++){
			for(int j=0;j<circleData.length;j++){
				data[i+yOffset][j+xOffset]=circleData[i][j];	
			}
		}
		
		data = this.fill(data,150,150);
		JPanel content = new DrawGraphic(data);
		this.setSize(400, 400);
		this.setContentPane(content);
		this.setVisible(true);
		// TODO Auto-generated constructor stub
	}
	
	public int[][] fill(int [][]data, int x, int y){
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
		
		return data;
	}
}
