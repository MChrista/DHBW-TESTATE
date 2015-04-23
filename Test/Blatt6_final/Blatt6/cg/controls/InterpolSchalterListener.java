package cg.controls;

import java.awt.event.ActionListener;

import cg.draw2D.Model;

import cg.tools.Switchable;

/**
 * Ein Control-Objekt, das einen Schalter-Listener repraesentiert.
 * @version 03.05.2004
 * @author Ralf Kunze
 */

public class InterpolSchalterListener implements ActionListener {

   /**
    * Der darzustellende Modus (siehe GrOb.Interpol) BEZIER, SPLINE, BSPLINE
    */
   private int modus;

   /**
    * Das zugehoerige Model
    */
   private Model model;

   /**
    * Das zugehoerige Switchable-Objekt. Es handelt sich hierbei fuer
    * gewoehnlich um die grafische Komponente, zu der auch der Schalter
    * gehoert, an dem dieser Listener haengt.
    */
   private Switchable onoff;

   /**
    * Erzeugt einen Listener fuer ein Interpolobjekt
    * @param model Das zugehoerige Model
    */
   public InterpolSchalterListener(Model model) {
      this(model, 0);
   }

   /**
    * Erzeugt einen Listener fuer ein Interpolobjekt
    * @param model Das zugehoerige Model
    * @param modus Gibt an, welche Interpolationsart ein
    * oder ausgeschaltet wird (siehe Klasse GrOb.Interpol)
    */
   public InterpolSchalterListener(Model model, int modus) {
      this(model, modus, null);
   }

   /**
    * Erzeugt einen Listener fuer ein Interpolobjekt
    * @param model Das zugehoerige Model
    * @param modus Gibt an, welche Interpolationsart ein
    * @param onoff das zugehoerige Switchable Objekt
    * oder ausgeschaltet wird (siehe Klasse GrOb.Interpol)
    */
   public InterpolSchalterListener(Model model, int modus, Switchable onoff) {
      this.model = model;
      this.modus = modus;
      this.onoff = onoff;
   }
   /**
    * Wird aufgerufen, wenn der Schalter veraendert wurde. Methode aus dem
    * Interface ActionListener.
    * @param e der Event, der vom Schalter/Button ausgeloest wurde.
    */
   public void actionPerformed(java.awt.event.ActionEvent e) {
      (model.getInterpol()).changeInterpolatingMethod(modus);

      if (onoff != null) {
         onoff.enableComponents(!onoff.areComponentsEnabled()); // hin und her
      } else {
         System.out.println(
            "InterpolSchalterListener.actionPerformed: Kein Switchable-Objekt!");
      }
   }
}
