package cg.controls;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

import cg.grOb.Point;
import cg.draw2D.Model;

import java.awt.Rectangle;

import cg.tools.MatrixManager;

/**
 * Mit dem PointListener werden Punkte auf die Zeichenfl&auml;che gesetzt.
 * Es werden die Interfaces MouseListener und MouseMotionListener
 * teilweise implementiert
 *
 * @version 1.0 (ML 03.05.2004)
 * @author Ralf Kunze
 */
public class PickListener extends MouseInputAdapter {
   private Model model;
   private MatrixManager mm;
   private String description;

   /**
    * Konstruktor
    *
    * @param model Der Listener erh&auml;lt das Model, um es &uuml;ber
    * &Auml;derungen zu informieren.
    */
   public PickListener(Model model, MatrixManager mm) {
      setModel(model);
      setManager(mm);
      description = "Objekt auswaehlen";
   }

   /**
    * Konstruktor
    *
    * @param model Der Listener erh&auml;lt das Model, um es &uuml;ber
    * &Auml;derungen zu informieren.
    */
   public PickListener(Model model, MatrixManager mm, String description) {
      setModel(model);
      setManager(mm);
      this.description = description;
   }
   
   /**
    * Methode zum setzen des Models
    *
    * @param model Das Model an das der Listener die entsprechenden
    * Informationen weitergibt.
    */
   public void setModel(Model model) {
      this.model = model;
   }

   /**
    * Liefert das dem PointListener zugh&ouml;rige model
    *
    * @return das zugeh&ouml;rige Model
    */
   public Model getModel() {
      return model;
   }

   /**
    * Liefert den dem PointListener zugh&ouml;rigen MatrixManager
    *
    * @return der zugeh&ouml;rige MatrixManager
    */
   public MatrixManager getManager() {
      return mm;
   }

   /**
    * Methode zum setzen des MatrixManagers
    *
    * @param mm MatrixManager, der eine aktuelle Matrix liefert
    */
   public void setManager(MatrixManager mm) {
      this.mm = mm;
   }

   /**
    *  Diese Methode errechnet die entsprechenden Koordinaten und setzt dort
    *  einen Punkt
    *
    * @param e der erzeugte Event
    */
   public void mousePressed(MouseEvent e) {
      // Koordinaten des Mauszeigers sind relativ zum sichtbaren Bereich der
      // Zeichenflaeche. Also linke obere Ecke des sichtb. Bereichs holen
      // und beruecksichtigen.
      Rectangle r = ((JScrollPane) e.getSource()).getViewport().getViewRect();
      //int f = getModel().getFaktor();              // Zoomfaktor beachten
      int f = model.getFactor(); // Zoomfaktor beachten
      int x = r.x + e.getX();
      x = x / f;
      int y = r.y + e.getY();
      y = y / f;
      Point p = new Point(x, y, f); // neuen Punkt erzeugen

      model.setTransform(p, mm);
   }

   /**
    * Liefert eine Stringrepraesentation des Objektes.
    */
   public String toString() {
      return description;
   }
}