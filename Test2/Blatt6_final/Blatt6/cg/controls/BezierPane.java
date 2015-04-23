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
 * Ein Modul mit Schaltflaechen zur Bezier-Manipulation
 *
 * @version (A 03.05.2004)
 * @author Ralf Kunze
 */
public class BezierPane extends JPanel implements Resetable, Switchable {

   private CGButton onoff; // Ein-/Ausschalter
   private CGSlider itiefe; // Interpolationstiefe
   private Model model;

   public BezierPane(Model model) {
      super(new GridLayout(2, 1));

      onoff =
         new CGButton(
            "Beziers zeichnen",
            "Bezierinterpolation ein-/ausschalten.Interpolation erst ab 4 Punkten Moeglich",
            "On",
            "Off",
            new InterpolSchalterListener(model, Interpol.BEZIER, this));

      itiefe =
         new CGSlider(
            "Bezier Iterationen",
            1,
            10,
            3,
            new BezierIterListener(model));

      setBorder(BorderFactory.createRaisedBevelBorder());
      add(onoff);
      add(itiefe);

      this.model = model; // Model vermerken
      reset();
   }

   /**
    *  Setzt alle Komponenten zur&uuml;ck
    */
   public void reset() {
      onoff.reset();
      itiefe.reset();
      itiefe.setEnabled(false);
   }

   /**
    * Enabled Komponenten.
    * @param mode true: Komponenten enablen; false sonst 
    */
   public void enableComponents(boolean mode) {
      itiefe.setEnabled(mode);
   }

   /**
    * Liefert zurueck, ob Komponenten aktiviert sind.
    * @return true: falls Enabled; false sonst
    */
   public boolean areComponentsEnabled() {
      return itiefe.isEnabled();
   }

   /**
    * Eine Instanz dieser Klasse setzt den IterationsFaktor fuer die
    * Bezierinterpolation des Interpolationsobjektes.
    * Hier sind auch andere Auswahlarten denkbar.
    * @author Ralf Kunze
    * @version 03.05.2004
    */
   protected static class BezierIterListener extends CGSliderListener {
      private Model model;

      /**
       * Erzeugt einen BezierIterListener mit dem uebergebenen Model.
       * @param model das Model
       */
      public BezierIterListener(Model model) {
         this.model = model;
      }

      /**
       * Wird aufgerufen, wenn der Benutzer den Wert des Schiebers
       * veraendert hat.
       * @param e der vom Schieber ausgeloeste Event
       */
      public void stateChanged(ChangeEvent e) {

         (model.getInterpol()).setBezierIterationen(((JSlider) e.getSource()).getValue());
      }
   }
}