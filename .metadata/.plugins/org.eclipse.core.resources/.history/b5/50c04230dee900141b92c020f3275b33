package algorithms;

import objects.Matrix;

public class BresenhamCircle {
	
	public void drawCircle(int radius){
		int distance = 3-(2*radius);
		int x = 0;
		int y = radius;
		setPoint(x, y);
		
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
			setPoint(x, y);
			
		}
	}
	
	private void setPoint(int x, int y) {
		Matrix.setPoint(x+Matrix.getWidth(), y+Matrix.getHeight());
		Matrix.setPoint(-x+Matrix.getWidth(), y+Matrix.getHeight());
		Matrix.setPoint(x+Matrix.getWidth(), -y+Matrix.getHeight());
		Matrix.setPoint(-x+Matrix.getWidth(), -y+Matrix.getHeight());
		Matrix.setPoint(y+Matrix.getHeight(), x+Matrix.getWidth());
		Matrix.setPoint(-y+Matrix.getHeight(), x+Matrix.getWidth());
		Matrix.setPoint(y+Matrix.getHeight(), -x+Matrix.getWidth());
		Matrix.setPoint(-y+Matrix.getHeight(), -x+Matrix.getWidth());
	}
	
}