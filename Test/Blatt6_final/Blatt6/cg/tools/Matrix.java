package cg.tools;

/** 
 * Ein Objekt, das eine nxm-Matrix repraesentiert.
 *
 * @version 1.0 (26.04.2004)
 * @author Ralf Kunze
 */
public class Matrix {

  /**
   * Elemente der Matrix.
   */
  public double[][] val;
  
  /**
   * Zeilenzahl der Matrix
   */
  public int n;
 
  /**
   * Spaltenzahl der Matrix
   */
  public int m;
 
  /**
   * Titel der Matrix
   */
  public String title;

  /**
   * Gibt eine nxm-Matrix zurueck, deren Elemente alle gleich 0 sind.
   * @param n Zeilenzahl der Matrix
   * @param m Spaltenzahl der Matrix
   */
  public Matrix(int n, int m) {
    this(n,m, 0.0);
  }

  /**
   * Gibt eine nxm-Matrix zurueck, deren Elemente alle gleich dem
   * uebergebenen Wert sind.
   * @param n Zeilenzahl der Matrix
   * @param m Spaltenzahl der Matrix
   * @param value alle Elemente der Matrix sind gleich diesem Wert
   */
  public Matrix(int n, int m, double value) {
    this.n = n;
    this.m = m;
    this.title = "unbenannt";
    val = new double[n][m];

    for (int i=0; i < n; i++) {
      for (int j=0; j < m; j++) {
        val[i][j] = value;
      }
    }
  }

  /** 
   * Liefert die Zeilenzahl der Matrix
   * @return die Anzahl der Zeilen in der Matrix
   */
  public int getRows() {
    return n;
  }

  /** 
   * Liefert die Spaltenzahl der Matrix
   * @return die Anzahl der Spalten in der Matrix
   */
  public int getColumns() {
    return m;
  }

  /**
   * Setzt den Titel der Matrix
   * @param title der neue Titel
   */
  public void setTitle(String title) {
    this.title = new String(title);
  }
 
  /**
   * Liefert den Titel der Matrix.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gibt eine Kopie der uebergebenen nxm-Matrix zurueck.
   * @param M nxm-Matrix, die kopiert werden soll.
   */
  public Matrix(Matrix M) {
    val = new double[M.getRows()][M.getColumns()];
    n = M.getRows();
    m = M.getColumns();
    title = new String(M.title);

    for (int i=0; i < n; i++) {
      for (int j=0; j < m; j++) {
        this.val[i][j] = M.val[i][j];
      }
    }
  }


  /**
   * Liefert ein neu allokiertes Objekt der Klasse Matrix mit dem
   * Ergebnis
   * der Matrixmultiplikation this*M zurueck, wenn die Spaltenzahl von
   * this
   * und die Zeilenzahl von M identisch sind; sonst null.
   * @param M die Matrix, mit der sich diese Matrix
   * multiplizieren soll
   * @return das Produkt dieser Matrix mit M
   */
  public Matrix mult(Matrix M) {
    Matrix hilf;
    int l = 0;                                   // Zeilenzahl this
    int n = 0;                                   // Zeilen(M) == Spalten(this)
    int m = 0;                                   // Spaltenzahl M 

    int i = 0;                                   // Zeilenindex this =0...l-1
    int j = 0;                                   // Spaltenindex M =0...m-1
    int k = 0;                                   // Zeilenindex M = 0...n-1

    double sum = 0.0;                            // Produktmatrixelement c_ij
    
    if(getColumns() != M.getRows()) {            // Zeilen(this) != Spalten(m)
      return null;                               // Multiplikation unmoeglich
    }
    else {
      l = getRows();                             // Zeilenzahl this holen
      m = M.getColumns();                        // Spaltenanzahl M
      n = M.getRows();                           // Zeilenzahl M holen

      hilf = new Matrix(l, m);                   // Platz fuer Produktmatrix 

      sum = 0.0;                                 // c_ij = 0

      for(i = 0; i < l; i++) {                   // Fuer alle Zeilen von this
        for(j = 0; j < m; j++) {                 // Alle Spalten von M
          for(k = 0; k < n; k++) {               // Zeile i(this)*Spalte k(M)
            sum = sum + val[i][k]*M.val[k][j];   // aufsummieren
          }
          hilf.val[i][j] = sum;                  // c_ij retten
          sum =0.0;                              // naechstes Element
        } // alle Spalten
      } // alle Zeilen
    } // Matrizenmultiplikation moeglich?


    return hilf;
  }

  /**
   * Gibt einen n-zeiligen String zurueck,
   * der zeilenweise die m Elemente der Matrixzeile enthaelt.
   */
  public String toString() {
    String out = ""+n+"x"+m+"-Matrix "+title+":\n";

    for (int i=0; i < n; i++) {
      for (int j=0; j < m; j++) {
        out += "[" + val[i][j] + "]";
      }
      out += "\n";
    }
    return out;
  }
}
