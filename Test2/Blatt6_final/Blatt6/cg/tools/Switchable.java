package cg.tools;

/**
 * Ein Interface, dass all diejenigen Objekte implementieren muessen, bei
 * denen einige (oder alle) Komponenten der grafischen Oberflaeche enabled
 * oder disabled werden koennen.
 * @author Ralf Kunze
 * @version 1.0 (A 02.05.2004)
 */
public interface Switchable {
   
  /**
   * Wird aufgerufen, wenn das Objekt seine Componenten ein- oder
   * ausschalten soll.
   * @param mode true: die Componenten werden enabled, bei false
   * disabled
   */
  public void enableComponents(boolean mode);

  /**
   * Liefert true, wenn die Komponenten dieses Objektes aktiviert sind;
   * false sonst.
   */
  public boolean areComponentsEnabled();

}