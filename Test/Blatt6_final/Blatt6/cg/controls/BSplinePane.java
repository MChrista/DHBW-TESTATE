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
 * Ein Modul mit Schaltflaechen zur BSpline-Manipulation
 *
 * @version (A 03.05.2004)
 * @author Ralf Kunze
 */
public class BSplinePane extends JPanel implements Resetable, Switchable {

   private CGButton onoff; // Ein-/Ausschalter
   private CGSlider grad; // Grad k einstellen
   private CGSlider ipp; // Anzahl InterPolationsPunkte
   private Model model;

   /**
    * Konstruktor der ein solches Modul erstellt
    * @param model Das Model der Applikation.
    */
   public BSplinePane(Model model) {
      super(new GridLayout(3, 1));

      onoff = new CGButton("B-Splines zeichnen", "BSpline-Interpolation ein- und ausschalten", "On", "Off", new InterpolSchalterListener(model, Interpol.BSPLINE, this));

      grad = new CGSlider("B-Spline Grad k", 1, 10, 3, new BSplineGradListener(model));

      ipp = new CGSlider("Anzahl Interpolationspunkte", 10, 100, 20, new BSplineInterpolListener(model));

      setBorder(BorderFactory.createRaisedBevelBorder());
      add(onoff);
      add(grad);
      add(ipp);

      this.model = model; // Model vermerken
      reset();
   }

   /**
    *  Setzt alle Komponenten zur&uuml;ck
    */
   public void reset() {
      onoff.reset();
      ipp.reset();
      ipp.setEnabled(false);
      grad.reset();
      grad.setEnabled(false);
   }

   /**
    * Enabled Komponenten.
    * @param mode true: Komponenten enablen; false sonst 
    */
   public void enableComponents(boolean mode) {
      ipp.setEnabled(mode);
      grad.setEnabled(mode);
   }

   /**
    * Liefert zurueck, ob Komponenten aktiviert sind.
    * @return true: falls Enabled; false sonst
    */
   public boolean areComponentsEnabled() {
      return (ipp.isEnabled() && grad.isEnabled());
   }

   /**
     * Eine Instanz dieser Klasse setzt den Grad fuer die B-Splineinterpolation
     * des Interpolationsobjektes.
     * Hier sind auch andere Auswahlarten denkbar.
     * @author Ralf Kunze
     * @version 03.05.2004
     */
   protected static class BSplineGradListener extends CGSliderListener {
      private Model model;

      /**
       * Erzeugt einen BSplineGradListener mit dem uebergebenen Model.
       * @param model das Model
       */
      public BSplineGradListener(Model model) {
         this.model = model;
      }

      /**
       * Wird aufgerufen, wenn der Benutzer den Wert des Schiebers
       * veraendert hat.
       * @param e der vom Schieber ausgeloeste Event
       */
      public void stateChanged(ChangeEvent e) {
         (model.getInterpol()).setBsplineGrad(((JSlider) e.getSource()).getValue());
      }
   }

   /**
    * Eine Instanz dieser Klasse setzt die Anzahl der Interpolationspunkte fuer die B-Splineinterpolation
    * des Interpolationsobjektes.
    * Hier sind auch andere Auswahlarten denkbar.
    * @author Ralf Kunze
    * @version 03.05.2004
    */
   protected static class BSplineInterpolListener extends CGSliderListener {
      private Model model;

      /**
       * Erzeugt einen BSplineInterpolListener mit dem uebergebenen Model.
       * @param model das Model
       */
      public BSplineInterpolListener(Model model) {
         this.model = model;
      }

      /**
       * Wird aufgerufen, wenn der Benutzer den Wert des Schiebers
       * veraendert hat.
       * @param e der vom Schieber ausgeloeste Event
       */
      public void stateChanged(ChangeEvent e) {
         (model.getInterpol()).setBsplineInterpolPunkte(((JSlider) e.getSource()).getValue());
      }
   }
}
