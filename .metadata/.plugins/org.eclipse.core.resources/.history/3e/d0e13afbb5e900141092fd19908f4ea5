package algorithms;

import objects.Matrix;

public class BresenhamLine {
	
	public void drawLine(int startX, int startY, int endX, int endY) {
		int x = 0, y = 0;
		int width = endX - startX;
		int height = endY - startY;
		int dStartX = 0, dEndX = 0, dStartY = 0, dEndY = 0;
		if(width<0) {
			dStartX = -1;
			dEndX = -1;
		} else if (width>0) {
			dStartX = 1;
			dEndX = 1;
		}
	    if (height<0) {
	    	dStartY = -1;
	    } else if (height>0) {
	    	dStartY = 1 ;
	    }
	    int longest = Math.abs(width) ;
	    int shortest = Math.abs(height) ;
	    if (!(longest>shortest)) {
	        longest = Math.abs(height) ;
	        shortest = Math.abs(width) ;
	        if (height<0) {
	        	dEndY = -1 ; 
	        } else if (height>0) {
	        	dEndY = 1 ;
	        }
	        dEndX = 0 ;            
	    }
	    int numerator = longest >> 1 ;
	    for (int i=0;i<=longest;i++) {
	        Matrix.setPoint(x+Matrix.getHeight()/2, y+Matrix.getHeight()/2);
	        numerator += shortest ;
	        if (!(numerator<longest)) {
	            numerator -= longest ;
	            x += dStartX ;
	            y += dStartY ;
	        } else {
	            x += dEndX ;
	            y += dEndY ;
	        }
	    }
	}
	
}