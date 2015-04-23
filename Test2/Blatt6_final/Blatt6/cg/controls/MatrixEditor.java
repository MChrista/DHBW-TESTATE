package cg.controls;

import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cg.tools.Matrix;

/**
 * Ein Controls-Objekt, dass eine Matrix darstellen und veraendern kann.
 *
 * @version 1.0 (A 03.05.2004)
 * @author Ralf Kunze
 */
public class MatrixEditor extends JPanel {

  private Matrix m;                              // hierauf wird gearbeitet
  private JTextField tf;                         // stellt Titel der Matrix dar
  private JPanel maxel;                          // stellt Matrixelemente dar
  
  /**
   * Erzeugt einen MatrixEditor, der die uebergebene Matrix anzeigt und 
   * manipuliert.
   * @param m die anzuzeigende Matrix
   */
  public MatrixEditor(Matrix m) {
    JPanel panel = new JPanel();                 // Platz fuer Komponenten

    JLabel jl = new JLabel("Titel:");       
    jl.setMaximumSize(new Dimension(100,30));
    panel.add(jl);

    tf = new JTextField(m.getTitle());           // Namen der Matrix dazu
    tf.getDocument().addDocumentListener(new MatrixTitleListener(m));
    tf.setPreferredSize(new Dimension(100,30));
    panel.add(tf);

    maxel = setMatrixElements(m);                // Panel mit Elementen dazu
    panel.add(maxel);

    add(panel);                          
    setBorder(BorderFactory.createEtchedBorder());
    this.m = m;                                  // Matrix merken
  }

  /**
   * Setzt die Werte der GUI Komponente mit den Werten der Matrix m 
   * @param m Matrix mit den zu Setzenden Werten.
   * @return Liefert eine gefuellte GUI Komponente zurueck.
   */
  private JPanel setMatrixElements(Matrix m) {
    JPanel pane;
    JTextField t;                                // Hilfstextfeld
    pane = new JPanel(new GridLayout(m.n,m.m,20,20));// Platz fuer Textfelder
    for(int i=0; i < m.n; i++) {                 // fuer alle Zeilen
      for(int j=0; j < m.m; j++) {               // fuer alle Spalten
        t = new JTextField(""+m.val[i][j]);      // Textfeld + Listener
        t.getDocument().addDocumentListener(new MatrixElementListener(m,i,j));
        pane.add(t);
      }
    }
    pane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    pane.setPreferredSize(new Dimension(200,150));
 
    return pane;
  }

  /**
   * Setzt die Matrix, die diesem MatrixEditor zugrunde liegt.
   * @param m die Matrix, die mit diesem Editor angezeigt und manipuliert 
   * werden soll.
   */
  public void setMatrix(Matrix m) {

    this.m = m;                                  // Matrix merken
    //System.out.println("MatrixEditor.setMatrix: m="+m.toString());

    JPanel panel = new JPanel();

    JLabel jl = new JLabel("Titel:");
    jl.setMaximumSize(new Dimension(100,30));
    panel.add(jl);

    tf = new JTextField(m.getTitle());
    tf.getDocument().addDocumentListener(new MatrixTitleListener(m));
    tf.setPreferredSize(new Dimension(100,30));
    panel.add(tf);                               // Name dazu

    maxel = setMatrixElements(m);                // Elemente dazu
    panel.add(maxel);

    removeAll();                                 // altes Panel loeschen
    add(panel);                                  // neues dazu
    if(getTopLevelAncestor() != null) {          // existiert Fenster schon?
      getTopLevelAncestor().setVisible(true);    // Wegen Bug in Swing 
    }
  }

  /**
   * Eine Instanz dieser Klasse achtet auf Aenderungen in einem bestimmten
   * Matrixlement und vermerkt diese sofort in der zugehoerigen Matrix
   */
  protected static class MatrixElementListener implements DocumentListener {
    private Matrix m;                            // hierin ist das Element
    private int s,z;                             // Spalte/Zeile des Elements
    

    /**
     * Erzeugt einen MatrixElementListener, der auf das spezifizierte
     * Element der uebergebenen Matrix achtet.
     * @param m die Matrix, die das Element enthaelt
     * @param z Zeilenindex des Elements
     * @param s Spaltenindex des Elements
     */
    public MatrixElementListener(Matrix m, int z, int s) {
      this.m = m;
      this.s = s;
      this.z = z;
    }

    /**
     * Methode aus dem Interface DocumentListener.
     * Wird aufgerufen, wenn im verknuepften Document etwas geloescht
     * wurde.
     * @param e der ausgeloeste Event
     */
    public void removeUpdate(DocumentEvent e) {
      Document doc= e.getDocument();             // Document holen
      int len = doc.getLength();                 // Textlaenge besorgen
      String text= "";
      try {
        text = doc.getText(0,len);               // Text aus Document holen
      } catch(BadLocationException ex) {         // Fehlschlag
        System.err.println("MatrixElementListener.removeUpdate: index out of range!");
      }

      m.val[z][s] = toDouble(text);              // double erzeugen
    }

    /**
     * Methode aus dem Interface DocumentListener.
     * Wird aufgerufen, wenn im verknuepften Document etwas hinzugefuegt
     * wurde.
     * @param e der ausgeloeste Event
     */
    public void insertUpdate(DocumentEvent e) {
      Document doc= e.getDocument();             // Document holen
      int len = doc.getLength();                 // Textlaenge besorgen
      String text= "";
      try {
        text = doc.getText(0,len);               // Text aus Document holen
      } catch(BadLocationException ex) {         // Fehlschlag
        System.err.println("MatrixElementListener.removeUpdate: index out of range!");
      }
      m.val[z][s] = toDouble(text);              // double erzeugen
    }

    /**
     * Methode aus dem Interface DocumentListener.
     * Wird aufgerufen, wenn beim verknuepften Document ein Attribut
     * veraendert wurde.
     * @param e der ausgeloeste Event
     */
    public void changedUpdate(DocumentEvent e) {
      Document doc= e.getDocument();             // Document holen
      int len = doc.getLength();                 // Textlaenge besorgen
      String text= "";
      try {
        text = doc.getText(0,len);               // Text aus Document holen
      } catch(BadLocationException ex) {         // Fehlschlag
        System.err.println("MatrixElementListener.removeUpdate: index out of range!");
      }
      m.val[z][s] = toDouble(text);              // double erzeugen
    }

    /**
     * Bestimmt aus einem String eine double-Zahl.
     * @param s String, der die Zahl enthaelt.
     * @return Die ermittelte double-Zahl, oder 0.0, falls
     * keine Zahl ausgelesen werden konnnte.
     */
    private double toDouble(String s) {          // Liefert den double-Wert,
      Double val;                                // der s entspricht
      try {
        val = new Double(s);                     // sinnvoller Inhalt
      } catch(NumberFormatException ex) {
        val = new Double(0.0);                   // kein sinnvoller Inhalt
      }

      return val.doubleValue();                  // fertig
    }

    /**
     * Liefert eine Stringrepraesentation des Objektes.
     */
    public String toString() {
      return new String("MatrixElementListener listening to Element["+z+","+s+"] of "+m);
    }
  }



  /**
   * Eine Instanz dieser Klasse achtet auf Aenderungen in einem bestimmten
   * Textfeld und vermerkt diese sofort im Titel der zugehoerigen Matrix.
   */
  protected static class MatrixTitleListener implements DocumentListener {
    private Matrix m;                            // hierin ist der Title
    

    /**
     * Erzeugt einen MatrixTitleListener, der auf den Titel
     * der uebergebenen Matrix achtet.
     * @param m die Matrix, die das Element enthaelt
     */
    public MatrixTitleListener(Matrix m) {
      this.m = m;
    }

    /**
     * Methode aus dem Interface DocumentListener.
     * Wird aufgerufen, wenn im verknuepften Document etwas geloescht
     * wurde.
     * @param e der ausgeloeste Event
     */
    public void removeUpdate(DocumentEvent e) {
      Document doc= e.getDocument();             
      int len = doc.getLength();
      String text= "";
      try {                                      // versuche den Text als
        text = doc.getText(0,len);               // String zu holen
      } catch(BadLocationException ex) {         // Fehlschlag
        System.err.println("MatrixTitleListener.removeUpdate: index out of range!");
      }
      m.setTitle(text);                          // Text im Titel merken
    }

    /**
     * Methode aus dem Interface DocumentListener.
     * Wird aufgerufen, wenn im verknuepften Document etwas hinzugefuegt
     * wurde.
     * @param e der ausgeloeste Event
     */
    public void insertUpdate(DocumentEvent e) {
      Document doc= e.getDocument();
      int len = doc.getLength();
      String text= "";
      try {                                      // versuche den Text als
        text = doc.getText(0,len);               // String zu holen
      } catch(BadLocationException ex) {         // Fehlschlag
        System.err.println("MatrixTitleListener.removeUpdate: index out of range!");
      }
      m.setTitle(text);                          // Text im Titel merken
    }

    public void changedUpdate(DocumentEvent e) {
      Document doc= e.getDocument();
      int len = doc.getLength();
      String text= "";
      try {                                      // versuche den Text als
        text = doc.getText(0,len);               // String zu holen
      } catch(BadLocationException ex) {         // Fehlschlag
        System.err.println("MatrixTitleListener.removeUpdate: index out of range!");
      }
      m.setTitle(text);                          // Text im Titel merken
    }

    public String toString() {
      return new String("MatrixTitleListener");
    }
  }
}