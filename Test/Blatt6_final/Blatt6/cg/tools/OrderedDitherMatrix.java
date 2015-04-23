package cg.tools;

import cg.grOb.Point;

/** 
 * Eine OrderedDitherMatrix.
 *
 * @version 1.0 (ML 26.04.2004)
 * @author Ralf Kunze
 */
public class OrderedDitherMatrix extends NxNMatrix implements DitherMatrix {

  private int stufen;
  /**
   * Gibt eine DitherMatrix zurueck, die mit Ditherwerten gefuellt ist.
   * @param n die Matrix ist (2 hoch n)x(2 hoch n) Elemente gross und
   * enthaelt die (2 hoch 2n) Ditherwerte von 0 bis (2 hoch 2n) - 1.
   */
  public OrderedDitherMatrix(int n) {
  /*
   * Idee: Sei DM_n die Dithermatrix der Groesse (2 hoch n)x(2 hoch n)
   * dann gilt:
   *
   *         (                                               )
   *         (  4*DM_(n-1)               4*DM_(n-1) + 2*U_(n-1)    )
   *  DM_n = (                                               )
   *         (  4*DM_(n-1) + 3*U_(n-1)   4*DM_(n-1) + U_(n-1)  )
   *         (                                               )
   *
   *  Wobei U_n eine mit Einsen gefuellt Matrix der Groesse 
   *  (2 hoch n)x(2 hoch n) ist.
   */
    super((int)Math.pow(2,n), 0.0);        // Matrix mit Nullen fuellen

    if(n != 0) {                           // bei n==0 ist man fertig
      OrderedDitherMatrix dd = new OrderedDitherMatrix(n-1);   // DitherMatrix halber Groesse
                                           // besorgen und die
      fill(0, 0, 0, dd);                   // Quadranten fuellen: ul
      fill(1, 1, 1, dd);                   // lr
      fill(1, 0, 2, dd);                   // ur
      fill(0, 1, 3, dd);                   // ll
    }
    stufen = (int)Math.pow(2,2*n);
  }

  private void fill(int s, int z, int add, NxNMatrix dd) {
    int size = dd.size();                  // Ausmasse besorgen
    for(int i=0; i < size; i++) {          // passenden Quadranten 
      for(int j=0; j < size; j++) {        // fuellen
        val[size*z+i][size*s+j]=4*dd.val[i][j] + add;
      }
    }
  }

  /**
   * Liefert zurueck, ob die Abbildung des uebergebenen Punktes in die
   * Matrix auf ein Matrixelement trifft, das kleiner als die uebergebene 
   * Schwelle ist.
   * @param p Der zu testende Punkt
   * @param schwelle Der Schwellwert
   * @return true, falls Matrixelement bei Punkt p kleiner ist als schwelle; sonst false
   */
  public boolean kleinerSchwelle(Point p, int schwelle) {
    if (stufen==(schwelle+1)) return true;
    int x = p.getXCoord();
    int y = p.getYCoord();
    if((x < 0) || (y < 0)) {               // Wert(e) sinnvoll?
      return false;                        // Falls nicht: Abbruch
    }
    else {                                 // Sonst: Werte mit Modulo
      return (val[x%n][y%n] < schwelle);   // in Matrix abbilden und
    }                                      // Vergleichsergebnis zurueck
  }
}
