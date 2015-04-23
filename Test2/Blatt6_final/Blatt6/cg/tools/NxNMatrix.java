package cg.tools;

/** 
 * Ein Objekt, das eine nxn-Matrix repraesentiert.
 *
 * @version 1.0 (A 26.04.2004)
 * @author Ralf Kunze
 */
public class NxNMatrix extends Matrix{

  /**
   * Erzeugt eine nxn-Matrix, deren Elemente alle gleich 0 sind.
   * @param n Zeilen- und Spaltenzahl der Matrix
   */
  public NxNMatrix(int n) {
    this(n, 0.0);
  }

  /**
   * Erzeugt eine nxn-Matrix, deren Elemente alle gleich dem
   * uebergebenen Wert sind.
   * @param n Zeilen- und Spaltenzahl der Matrix
   * @param value alle Elemente der Matrix sind gleich diesem Wert
   */
  public NxNMatrix(int n, double value) {
    super(n, n, value);
  }

  /**
   * Liefert eine nxn-Matrix, deren Diagonalelemente alle gleich dem 
   * uebergebenen Wert sind. Die anderen Elemente sind alle 0.
   * @param n Spalten- und Zeilenzahl der Matrix
   * @param diag Wert, der in die Diagonalelemente eingetragen wird.
   * @return Diagonalmatrix mit nxn Elementen und Wert diag auf der 
   * Hauptdiagonalen
   */
  public static NxNMatrix diag(int n, double diag) {
    NxNMatrix mat = new NxNMatrix(n, 0.0);
    for(int i=0; i < n; i++) {
      mat.val[i][i] = diag;
    }
    return mat;
  }

  /**
   * Liefert eine nxn-Matrix, deren Diagonalelemente alle gleich 1.0 sind. 
   * Die anderen Elemente sind alle 0.
   * @param n Spalten- und Zeilenzahl der Matrix
   * @return Diagonalmatrix mit nxn Elementen und Wert 1.0 auf der 
   * Hauptdiagonalen
   */
  public static NxNMatrix unity(int n) {
    return diag(n, 1.0);
  }

  /**
   * Liefert die Spalten- bzw. Zeilenzahl der nxn-Matrix.
   * @return Spalten- bzw. Zeilenzahl der Matrix
   */
  public int size() {
    return n;
  }
  
  

  /**
   * Liefert eine nxn-Matrix, die eine Kopie der uebergebenen Matrix ist.
   * Wenn die uebergebene Matrix nicht quadratisch ist, wird null
   * geliefert.
   * @param m eine quadratische Matrix, von der eine Kopie erzeugt werden
   * soll
   */
  public static NxNMatrix copy(Matrix m) {
	if(m.n == m.m) {                             // Falls quadratisch
	  NxNMatrix hilf = new NxNMatrix(m.n);       // Platz schaffen
	  hilf.setTitle(m.getTitle());
	  for(int i =0; i < m.n; i ++) {
		for(int j = 0; j < m.n; j ++) {
		  hilf.val[i][j] = m.val[i][j];          // Alle Elemente kopieren
		}
	  }
	  return hilf;
	}
	else {                                       // Falls nicht quadratisch
	 return null;                                // Misserfolg melden
	}
  }

}
