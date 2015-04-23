package cg.grOb;

/**
 * Ein Interface, das von allen Klassen implementiert werden muﬂ,
 * deren Objekte iteriert werden koennen.
 * @author Ralf Kunze
 * @version 1.0 (A 18.05.2004)
 */
public interface Fractal {
  /**
   * Wird aufgerufen, wenn das Objekt eine Iteration machen soll.
   * @exception Exception mit Informationen ueber die Iteration
   */
  public void iterate();
}
