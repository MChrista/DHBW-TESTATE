package cg.tools;

import java.util.Observable;
import java.util.Vector;

/**
 * Ein Tool-Objekt, das Matrizen verwalten kann.
 *
 * @version 1.0 (A 02.05.2004)
 * @author Ralf Kunze
 */
public class MatrixModel extends Observable implements Resetable{
  private Vector v;                              // alle Matrizen
  private int am;                                // aktuelle Matrix
  private Matrix init;                           // initiale Matrix

  /**
   * Erzeugt ein MatrixModel, mit der initialen Matrix m.
   *
   * @param m Initiale Matrix
   */
  public MatrixModel(Matrix m) {
    v = new Vector(1,1);                         // Platz schaffen
    init = new Matrix(m);                        // initiale Matrix kopieren
    v.addElement(m);                             // Original in Vector legen
    am = 0;                                      // index der akt. Matrix 
    //System.out.println("MatrixModel.Konstruktor: "+toString());
  }

  /**
   * Fuegt Matrix m hinter der aktuellen Matrix ein.
   * m wird zur aktuellen Matrix.
   *
   * @param m einzuf&uuml;gende Matrix
   */
  public void insertMatrix(Matrix m) {
    am++;                                        // ab jetzt: 1 weiter rechts
    insertAt(m, am);                             // hinter akt. Stelle dazu
  }

  /**
   * Fuegt Matrix an der uebergebenen Position ins Model ein, wenn
   * moeglich; sonst nicht.
   * @param m die einzufuegende Matrix
   * @param pos die Position, an der eingetragen werden soll
   */
  public void insertAt(Matrix m, int pos) {
    if((pos >=0) && (pos <= v.size())) {         // Vector kann bis = size()!
      v.insertElementAt(m, pos);                 // Falls Platz: einfuegen
      notifyChanged();                           // Observer informieren
    }
    else {                                       // Fehlschlag
      System.err.println("MatrixModel.insertAt: index out of range!");
      return;
    }
  }

  /**
   * Liefert aktuelle Matrix.
   *
   * @return die aktuelle Matrix
   */
  public Matrix getActiveMatrix() {
    return (Matrix)v.elementAt(am);
  }

  /**
   * Liefert die Matrix mit dem Index n, falls diese existiert; null sonst.
   *
   * @param n Matrixindex
   * @return Matrix an der Stelle n
   */
  public Matrix getMatrixAt(int n) {
    if((n >= 0) && (n < v.size())) {             // Falls vorhanden
      return (Matrix)v.elementAt(n);             // Matrix liefern
    }
    else {
      System.err.println("MatrixModel.getMatrixAt: index out of range!");
      return null;
    }
  }

  /**
   * Macht die Matrix am uebergebenen Index zur aktuellen Matrix.
   *
   * @param n index
   */
  public void setActiveMatrix(int n) {
    if( am == n) {                               // Falls keine Veraenderung
      return;                                    // nichts tun
    }

    if((n >= 0) && (n < v.size())) {             // Falls vorhanden
      am = n;                                    // Wert setzen
      notifyChanged();                           // Observer informaieren
    }
    else {                                       // Fehlschlag
      System.err.println("MatrixModel.setActiveMatrix: index out of range!");
    }
  }

  /**
   * Loescht die aktuelle Matrix aus dem Model. Der Index aller
   * Nachfolgenden Matrizen wird um 1 erniedrigt.
   *
   * @return true, wenn Matrix geloescht, sonst false
   */
  public boolean removeActiveMatrix() {
    if(v.size() > 1) {                           // letzte Matrix uebriglassen
      v.removeElementAt(am);                     // Element an Pos. 'am' weg
      if(am >= v.size()) {                       // war es das letzte?
        am--;                                    // ja: akt. Matrix 1 zurueck
      }
      notifyChanged();                           // in jedem Fall: Veraenderung
      return true;                               // Erfolg
    } 
    else {                                       // Falls nur 1 Matrix in Model
      return false;                              // Misserfolg
    }
  }

  /**
   * Liefert eine Produktmatrix, die dem Matrizenprodukt aller
   * Matrizen im MatrixModel entspricht.
   * @return die Produktmatrix
   */
  public Matrix getProduct() {
    Matrix hilf = new Matrix((Matrix)v.elementAt(0));

    for(int i = 1; i < v.size(); i++) {
      //hilf = hilf.mult((Matrix)v.elementAt(i));
      hilf = ((Matrix)v.elementAt(i)).mult(hilf);
    }

    return hilf;
  }


  /**
   * Versetzt das Model in seinen Anfangszustand zurueck. Die Matrizen 
   * gehen dabei verloren, sofern sie nicht noch an anderer Stelle
   * referenziert werden.
   */
  public void reset() {
    v.removeAllElements();
    v.addElement(new Matrix(init));
    am = 0;
  }
  
  /**
   * Benachrichtigt die Observer, dass sich der Zustand des Model veraendert 
   * hat.
   */
  void notifyChanged() {
    setChanged();
    notifyObservers();
    //System.out.println("MatrixModel.notifyChanged: "+toString());
  }
  
  /**
   * Liefert eine Stringrepraesentation des Models.
   * @return die textuelle Repraesentation dieses Objekts
   */
  public String toString() {
    return("MatrixModel mit folgenden Objekten: "+v);
  }
}

