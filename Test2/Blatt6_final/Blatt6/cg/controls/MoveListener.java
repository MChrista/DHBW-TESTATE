package cg.controls;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

import cg.grOb.Point;

import cg.draw2D.Model;

import java.awt.Rectangle;

/**
 * Klasse MoveListener stellt einen MouseListener und einen
 * MouseMotionListener zur Verfuegung, welcher ein grafische Objekt waehlen
 * und verschieben kann.
 * @version 03.05.2004
 * @author Ralf Kunze
 */
public class MoveListener extends MouseInputAdapter {

   /**
    * Enthaelt das Model in welchem das Interpol Objekt eingehaengt wurde/wird
    */
   private Model model;

   private Point moveP;

   private String description;

   /**
    * Constructor, welcher sich das model merkt
    * @param model Model in welchem das Interpol Objekt enthalten ist
    */
   public MoveListener(Model model) {
      this.model = model;
      moveP = null;
      description = "Stuetzpunkte verschieben";
   }

   /**
    * Constructor, welcher sich das model merkt
    * @param model Model in welchem das Interpol Objekt enthalten ist
    */
   public MoveListener(Model model, String description) {
      this.model = model;
      moveP = null;
      this.description = description;
   }

   /**
    * Methode wird aufgerufen, wenn eine Mautaste gedrueckt wird
    * @param e MausEvent welches ausgeloest wurde
    */
   public void mousePressed(MouseEvent e) {
      int i = 0;
      // Koordinaten des Mauszeigers sind relativ zum sichtbaren Bereich der
      // Zeichenflaeche. Also linke obere Ecke des sichtb. Bereichs holen
      // und beruecksichtigen.
      Rectangle r = ((JScrollPane) e.getSource()).getViewport().getViewRect();
      int f = model.getFactor(); // Zoomfaktor beachten
      int x = r.x + e.getX();
      x = x / f;
      int y = r.y + e.getY();
      y = y / f;

      moveP = model.getInterpolPoint(new Point(x, y, f));
      // Hilfspunkt erzeugen
   }

   /**
    * Wird aufgerufen, wenn der Mauscursor die Komponente verlaesst,
    * an der dieser Listener haengt.
    * @param e Der erzeugte Event.
    */
   public void mouseExited(MouseEvent e) {
      moveP = null; // sicherer
   }

   /**
    * Wird aufgerufen, wenn die Maustaste losgelassen wird.
    * @param e Der erzeugte Event.
    */
   public void mouseReleased(MouseEvent e) {
      moveP = null; // jetzt nicht mehr bewegen
   }

   /**
    * Methode wird aufgerufen, wenn eine Mautaste gedrueckt und dabei bewegt wird
    * @param e MausEvent welches ausgeloest wurde
    */
   public void mouseDragged(MouseEvent e) {
      if (moveP != null) {
         // Koordinaten des Mauszeigers sind relativ zum sichtbaren Bereich der
         // Zeichenflaeche. Also linke obere Ecke des sichtb. Bereichs holen
         // und beruecksichtigen.
         Rectangle r = ((JScrollPane) e.getSource()).getViewport().getViewRect();
         int f = model.getFactor(); // Zoomfaktor beachten
         int x = r.x + e.getX();
         x = x / f;
         int y = r.y + e.getY();
         y = y / f;

         moveP.movePoint(x, y);
         model.notifyChanged();
      }
   }

   /**
    * Liefert eine Stringrepraesentation des Objektes. 
    */
   public String toString() {
      return description;
   }
}
