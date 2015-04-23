package cg.controls;

import cg.grOb.Interpol;

import cg.tools.Resetable;
import cg.tools.Switchable;

import cg.draw2D.Model;

import java.awt.GridLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Ein Modul mit Schaltflaechen zur Spline-Manipulation
 *
 * @version (A 03.05.2004)
 * @author Ralf Kunze
 */
public class SplinePane extends JPanel implements Resetable, Switchable {

  private CGButton onoff;                        // Ein-/Ausschalter
  private CGSlider ipp;                          // Anzahl InterPolationsPunkte
  private Model model;

  /**
   * Konstruktor der ein solches Modul erstellt
   * @param model Das Model der Applikation.
   */
  public SplinePane(Model model) {
    super(new GridLayout(2,1));

    onoff = new CGButton("Splines zeichnen","Splineinterpolation an- und abschalten. Interpolation erst ab 4 Punkten moeglich!","On","Off", new InterpolSchalterListener(model,Interpol.SPLINE, this));

    //ipp = new CGSlider("Spline Inter.-Punkte","Hier die Anzahl der Interpolationspunkte einstellen", 10,100,20, new SplineInterpolListener(model));
    ipp = new CGSlider("Spline Inter.-Punkte", 10,100,20, new SplineInterpolListener(model));

    setBorder(BorderFactory.createRaisedBevelBorder());
    add(onoff);
    add(ipp);

    this.model = model;                          // Model vermerken
    reset();
  }

  /**
   *  Setzt alle Komponenten zur&uuml;ck
   */
  public void reset() {
    onoff.reset();
    ipp.reset();
    ipp.setEnabled(false);
  }

  /**
   * Enabled Komponenten.
   * @param mode true: Komponenten enablen; false sonst 
   */
  public void enableComponents(boolean mode) {
    ipp.setEnabled(mode);
  }
  
  /**
   * Liefert zurueck, ob Komponenten aktiviert sind.
   * @return true: falls Enabled; false sonst
   */
  public boolean areComponentsEnabled() {
    return ipp.isEnabled();
  }


  /**
   * Eine Instanz dieser Klasse setzt die Anzahl der Interpolations-Punkte
   * fuer die Splineinterpolation des Interpolationsobjektes.
   * Hier sind auch andere Auswahlarten denkbar.
   * @author Ralf Kunze
   * @version 03.05.2004
   */
  protected static class SplineInterpolListener extends CGSliderListener {
    private Model model;

    /**
     * Erzeugt einen SplineInterpolListener mit dem uebergebenen Model.
     * @param model das Model
     */
    public SplineInterpolListener(Model model) {
      this.model = model;
    }

    /**
     * Wird aufgerufen, wenn der Benutzer den Wert des Schiebers
     * veraendert hat.
     * @param e der vom Schieber ausgeloeste Event

     */
    public void stateChanged(ChangeEvent e) {
        (model.getInterpol()).setSplineInterpolPunkte(((JSlider)e.getSource()).getValue());
    }
  }
}