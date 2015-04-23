package cg.tools;

import java.awt.BorderLayout;

import java.util.Observer;
import java.util.Observable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JSlider;

import cg.controls.MatrixEditor;
import cg.controls.CGSlider;
import cg.controls.CGSliderListener;

/**
 * Ein tool-Objekt, dass quadratische Matrizen verwalten, 
 * darstellen und manipulieren kann.
 *
 * @version 1.0 (A 02.05.2004)
 * @author Ralf Kunze
 */
public class MatrixManager extends JPanel implements Resetable, Observer {
  CGSlider ms;                                   // waehlt aktuelle Matrix
  MatrixEditor me;                               // Anzeige und Manipulation
  MatrixModel model;                             // verwaltet Matrizen
  JButton nb;                                    // erzeugt neue Matrizen
  JButton rb;                                    // loescht Matrizen
  int n;                                         // Groesse der Matrizen
  
  /**
   * Erzeugt einen MatrixManager, der nxn-Matrizen der Groesse n verwalten
   * kann.
   * @param n Groesse der Matrizen
   */
  public MatrixManager(int n) {
    this.n = n;                                  // Matrixgroesse merken
    //JPanel bp = new JPanel(new GridLayout(3,1,10,10));
    JPanel bp = new JPanel(new BorderLayout());
    //this.setLayout(new GridLayout(2,1,10,10));
    this.setLayout(new BorderLayout());
    model = new MatrixModel(NxNMatrix.unity(3)); // Model erzeugen
    model.addObserver(this);

    // Matrixauswahlschieber dazu
    ms = new CGSlider("Matrix", 0,0,0, new MatrixEditListener(model));
    // Matrixeditor dazu
    me = new MatrixEditor(model.getActiveMatrix());

    nb = new JButton("Neue Matrix");
    nb.addActionListener(new NewMatrixButtonListener(model, ms, n));

    rb = new JButton("Matrix loeschen");
    rb.addActionListener(new RemoveMatrixButtonListener(model, ms));

    bp.add(ms, BorderLayout.NORTH);
    bp.add(nb, BorderLayout.CENTER);
    bp.add(rb, BorderLayout.SOUTH);
    bp.setBorder(BorderFactory.createEtchedBorder());
    
    add(me, BorderLayout.NORTH);
    add(bp, BorderLayout.SOUTH);
    setBorder(BorderFactory.createEtchedBorder());
    reset();
  }

  /**
   * Liefert das dem MatrixManager zugrundeliegende Model.
   *
   * @return Liefert das zugeh&ouml;rige MatrixModel
   */
  public MatrixModel getModel() {
    return model;
  }

  /**
   * Versetzt den MatrixManager in seinen Ausgangszustand.
   * D.h. einzige Matrix ist diejenige, mit der der Manager instanziiert 
   * worden ist.
   */
  public void reset() {
    model.reset();
    ms.reset();
    me.setMatrix(model.getActiveMatrix());
  }


  /**
   * Liefert eine Produktmatrix, die dem Matrizenprodukt aller
   * Matrizen im MatrixModel entspricht.
   * @return die Produktmatrix
   */
  public Matrix getProduct() {
    return model.getProduct();
  }

  /**
   * Methode aus dem Interface Observer. Wird aufgerufen, wenn ein
   * Observable sich veraendert hat.
   */
  public void update(Observable o, Object arg) {
    me.setMatrix(model.getActiveMatrix());       // aktuelle Matrix in
                                                 //Editor uebertragen
  }


  /** 
   * Eine Instanz dieser Klasse macht diejenige Matrix des Models zur
   * aktuellen, die der Benutzer mit dem Schieber gewaehlt hat.
   *
   * @version 1.0 (A 02.05.2004)
   * @author Ralf Kunze
   */
  protected static class MatrixEditListener extends CGSliderListener {
    private MatrixModel model;

    /** 
     * Erzeugt einen MatrixEditListener mit dem uebergebenen MatrixModel.
     * @param model das MatrixModel
     */
    public MatrixEditListener(MatrixModel model) {
      this.model = model;
    }

    /**
     * Wird aufgerufen, wenn der Benutzer den Wert des Schiebers
     * veraendert hat.
     * @param e der vom Schieber ausgeloeste Event
     */
    public void stateChanged(ChangeEvent e) {
      //System.out.println("MatrixEditListener.valChange: e="+e);
      //model.setActiveMatrix(e.getValue());       // Model die Wahl mitteilen
	  model.setActiveMatrix(((JSlider)e.getSource()).getValue());       // Model die Wahl mitteilen
    }
  }

  /**
   * Ein ButtonListener, der eine neue Matrix erzeugt, wenn der zugehoerige
   * Button gedrueckt wurde.
   *
   * @version 1.0 (A 02.05.2004)
   * @author Ralf Kunze
   */
  protected static class NewMatrixButtonListener implements ActionListener {
    private MatrixModel model;
    private int n;
    private CGSlider s;
    
   
    /**
     * Erzeugt einen NewMatrixButtonListener.
     * @param model dem Model werden die neuen Matrizen uebergeben
     * @param s dieser Schieber wird ggf. nach rechts erweitert.
     * @param n Groesse der zu erzeugenden Matrix
     */
    public NewMatrixButtonListener(MatrixModel model, CGSlider s, int n) {
      this.model = model;
      this.n = n;
      this.s = s;
    }
   
    /**
     * Methode aus dem Interface ActionListener.
     * Wird aufgerufen, wenn jemand den zugehoerigen Button gedrueckt hat.
     */
    public void actionPerformed(ActionEvent e) {
      model.insertMatrix(NxNMatrix.unity(n));    // neue Matrix dazu
      s.setMaximum(s.getMaximum()+1);            // neue rechte Grenze
      s.setValue(s.getValue()+1);                // neuen Wert setzen
    }
  }

  /**
   * Ein ButtonListener, der eine Matrix loescht, wenn der zugehoerige
   * Button gedrueckt wurde.
   *
   * @version 1.0 (A 02.05.2004)
   * @author Ralf Kunze
   */
  protected static class RemoveMatrixButtonListener implements ActionListener {
    private MatrixModel model;
    private CGSlider s;
   
    /**
     * Erzeugt einen RemoveMatrixButtonListener.
     * @param model dem Model werden die neuen Matrizen uebergeben
     * @param s dieser Schieber wird ggf. nach rechts erweitert.
     */
    public RemoveMatrixButtonListener(MatrixModel model, CGSlider s) {
      this.model = model;
      this.s = s;
    }
   
    /**
     * Methode aus dem Interface ActionListener.
     * Wird aufgerufen, wenn jemand den zugehoerigen Button gedrueckt hat.
     */
    public void actionPerformed(ActionEvent e) {
      if(model.removeActiveMatrix()) {           // Matrix aus Model loeschen
        s.setMaximum(s.getMaximum()-1);          // Falls erfolgreich: rechte 
                                                 // Grenze um 1 erniedrigen
      }
    }
  }
}
