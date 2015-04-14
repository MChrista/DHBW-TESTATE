
public class Backup {

}


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
	//fill(12, 12);
}
public void zeichne(int x, int y){
	y = 12 - y;
	feld[y][x+12] = 1;
	return;
}

public void setWithoutOffset(int x, int y) {
	feld[y][x] = 1;
}


public void print(){
	for(int i = 0;i<SIZE;i++){
		for(int j=0;j<SIZE;j++){
			System.out.print(feld[i][j] + " ");
		}
		System.out.print("\n");
	}
}

public void fill(int x, int y) {
	xCoordinates.push(x);
	yCoordinates.push(y);
	while(!xCoordinates.empty()) {
		x = xCoordinates.pop();
		y = yCoordinates.pop();
		if(feld[x][y] == 0) {

			//System.out.println(x + " " + y + " " + feld[x][y]);
			
			setWithoutOffset(x, y);
			
			if(!isBorder(x, y+1)) {
				xCoordinates.push(x);
				yCoordinates.push(y+1);
			}
			

			if(!isBorder(x, y-1)) {
				xCoordinates.push(x);
				yCoordinates.push(y-1);
			}

			if(!isBorder(x+1, y)) {
				xCoordinates.push(x+1);
				yCoordinates.push(y);
			}

			if(!isBorder(x-1, y)) {
				xCoordinates.push(x-1);
				yCoordinates.push(y);
			}
		}
	}
	return;
}

private boolean isBorder(int x, int y) {
	if(!isBlank(x, y) && x != 0 && y != 0) {
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