package cg.controls;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import javax.swing.JScrollPane;
import cg.grOb.Point;

import cg.draw2D.Model;

import java.awt.Rectangle;

/**
 * Klasse StuetzPunktlistener stellt einen MouseListener und einen
 * MouseMotionListener zur Verfuegung, welcher in ein Interpol-Objekt
 * weitere Stuetzpunkte einhaengen kann.
 * @version 03.05.2004
 * @author Ralf Kunze
 */
public class StuetzpunktListener extends MouseInputAdapter {
   /**
    * Enthaelt das Model in welchem das Interpol Objekt eingehaengt wurde/wird
    */
   private Model model;

   private String description;

   /**
    * Konstruktor, welcher sich das Model merkt
    * @param model Model, welches alle GraphikObjekte verwaltet
    */
   public StuetzpunktListener(Model model) {
      this.model = model;
      description = "Stuetzpunkte setzen";
   }

   /**
      * Konstruktor, welcher sich das Model merkt
      * @param model Model, welches alle GraphikObjekte verwaltet
      */
   public StuetzpunktListener(Model model, String description) {
      this.model = model;
      this.description = description;
   }

   /**
    * Methode wird aufgerufen, wenn eine Mautaste gedrueckt wird
    * @param e MausEvent welches ausgeloest wurde
    */
   public void mousePressed(MouseEvent e) {

      // Koordinaten des Mauszeigers sind relativ zum sichtbaren Bereich der
      // Zeichenflaeche. Also linke obere Ecke des sichtb. Bereichs holen
      // und beruecksichtigen.
      Rectangle r = ((JScrollPane) e.getSource()).getViewport().getViewRect();
      int f = model.getFactor(); // Zoomfaktor beachten
      int x = r.x + e.getX();
      x = x / f;
      int y = r.y + e.getY();
      y = y / f;
      model.appendInterpolPoint(new Point(x, y, f));

   }

   /**
    * Liefert eine Kurzbeschreibung des Objektes.
    */
   public String toString() {
      return description;
   }
}