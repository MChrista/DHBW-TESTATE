import java.util.Stack;

public class BresenhamLogik {
	public final int SIZE = 25;
	int [][] feld = new int[SIZE][SIZE];
	Stack<Integer> xCoordinates = new Stack<Integer>();
	Stack<Integer> yCoordinates = new Stack<Integer>();
	
	public void doing(int radius) {
		int distance = 3-(2*radius);
		int x = 0;
		int y = radius;
		addShit(x, y);
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
			addShit(x, y);
		}
		fill(12, 12);
	}
	
	private void addShit(int x, int y) {
		put(addXOffset(x), addYOffset(y));
		put(addXOffset(-x), addYOffset(y));
		put(addXOffset(x), addYOffset(-y));
		put(addXOffset(-x), addYOffset(-y));
		put(addXOffset(y), addYOffset(x));
		put(addXOffset(-y), addYOffset(x));
		put(addXOffset(y), addYOffset(-x));
		put(addXOffset(-y), addYOffset(-x));
	}
	
	public void print(){
		for(int i = 0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				System.out.print(feld[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
	
	private void put(int x, int y) {
		feld[y][x]=1;
	}
	
	private int addXOffset(int x) {
		return x+12;
	}
	
	private int addYOffset(int y) {
		return 12-y;
	}
	
	public void fill(int x, int y) {
		xCoordinates.push(x);
		yCoordinates.push(y);
		while(!xCoordinates.empty()) {
			x = xCoordinates.pop();
			y = yCoordinates.pop();
			if(feld[x][y] == 0) {

				System.out.println(x + " " + y + " " + feld[x][y]);
				
				put(addXOffset(x), addYOffset(y));
				
				if(feld[x][y+1] == 0) {
					xCoordinates.push(x);
					yCoordinates.push(y+1);
				}
				

				if(feld[x][y-1] == 0) {
					xCoordinates.push(x);
					yCoordinates.push(y-1);
				}

				if(feld[x+1][y] == 0) {
					xCoordinates.push(x+1);
					yCoordinates.push(y);
				}

				if(feld[x-1][y] == 0) {
					xCoordinates.push(x-1);
					yCoordinates.push(y);
				}
			}
		}
		return;
	}

	private boolean isBorder(int x, int y) {
		if(!isBlank(x, y)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isBlank(int x, int y) {
		if(feld[x][y] == 0) {
			return true;
		} else {
			return false;
		}
	}
	
}