
public class BresenhamLogik {
	public final int SIZE = 25;
	int [][] feld = new int[SIZE][SIZE];
	
	public void doing(int radius){
		int distance = 3-(2*radius);
		int x = 0;
		int y = radius;
		zeichne(x, y);
		zeichne(-x,y);
		zeichne(x,-y);
		zeichne(-x,-y);
		zeichne(y,x);
		zeichne(y,-x);
		zeichne(-y,x);
		zeichne(-y,-x);
		
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
			zeichne(x, y);
			zeichne(-x,y);
			zeichne(x,-y);
			zeichne(-x,-y);
			zeichne(y,x);
			zeichne(y,-x);
			zeichne(-y,x);
			zeichne(-y,-x);
			
		}
		
		
		/*
	

		while (x!=y){
			x++;
			if(x==y){
				return;
			}
			double kreispunkt = Math.sqrt(radius * radius - x * x);
			if((y-kreispunkt) > 0.5){
				y--;
			}
			zeichne(x, y);
			zeichne(-x,y);
			zeichne(x,-y);
			zeichne(-x,-y);
			zeichne(y,x);
			zeichne(y,-x);
			zeichne(-y,x);
			zeichne(-y,-x);
			System.out.println("x = " + x + "\ny= "+ y);
		}			
		*/
		
	}
	
	public void zeichne(int x, int y){
		y = 12 - y;
		feld[y][x+12] = 1;
		return;
	}
	
	
	
	public void print(){
		String buffer = "";
		for(int i = 0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				System.out.print(feld[i][j] + " ");
			}
			System.out.print("\n");
		}
	}

}
