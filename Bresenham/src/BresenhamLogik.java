
public class BresenhamLogik {
	int [][] feld = new int[24][24];
	
	public void doing(int radius){
		if(radius <=0 && radius > 24){
			
		}else{
			System.out.println("Ungültiger Radius");
		}
		
	}
	
	
	
	public void print(){
		String buffer = "";
		for(int i = 0;i<24;i++){
			for(int j=0;j<24;j++){
				System.out.print(feld[i][j]);
			}
			System.out.print("\n");
		}
	}

}
