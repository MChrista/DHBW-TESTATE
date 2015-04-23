package cg.controls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.event.MouseInputAdapter;

import cg.draw2D.Model;
import cg.grOb.Circle;
import cg.grOb.Point;

/**
 * Die Klasse CircleListener implementiert die Interfaces MouseListener und
 * MouseMotionListener.
 * Die Klasse dient dazu, &uuml;ber MausEvents
 * Mittelpunkt und Radius eines Kreises zu bestimmen und daraus ein
 * Circle-Objekt zu erzeugen.
 *
 * @version 2.0 (ML 19.04.2004)
 * @author Ralf Kunze
 */
public class CircleListener extends MouseInputAdapter {
   private Point startDashedCircle;
   private Point middle;
   private Model model;
   private Circle dc = null;
   private boolean dashedMode = false;
   private boolean fill = Circle.EMPTY;

   private String description;
   /** 
    * Konstruktor der Klasse, der das Model intern Ablegt, an welches die
    * Events geschickt werden.
    */
   public CircleListener(Model model) {
      setModel(model);
      middle = null; // Wir beginnen mit Mittelpunkt
      description = "Kreis";
   }

   public CircleListener(Model model, String description) {
      setModel(model);
      middle = null; // Wir beginnen mit Mittelpunkt
      this.description = description;
   }

   /** 
    * Konstruktor der Klasse, der das Model intern Ablegt, an welches die
    * Events geschickt werden.
    */
   public CircleListener(Model model, boolean fill) {
      setModel(model);
      middle = null; // Wir beginnen mit Mittelpunkt
      this.fill = fill;
   }

   /** 
    * Konstruktor der Klasse, der das Model intern Ablegt, an welches die
    * Events geschickt werden.
    */
   public CircleListener(Model model, String description, boolean fill) {
      setModel(model);
      middle = null; // Wir beginnen mit Mittelpunkt
      this.fill = fill;
      this.description = description;
   }

   /**
    * Setzen des Models der Applikation, in der die GraphicObjects abgelegt werden.
    */
   public void setModel(Model model) {
      this.model = model;
   }

   /**
    * Das Model der Applikation zurueckliefern.
    */
   public Model getModel() {
      return model;
   }

   /**
    * MousePressed Event f&uuml;r das erste oder zweite klicken bei 
    * Erstellung eines Kreises.
    * Es wird der Mittelpunkt (1.mal, dass der Event aufgerufen wird) oder der
    * Radius (2.mal, dass der Event aufgerufen wird) bestimmt.
    *
    * @param e der erzuegte Event
    */
   public void mousePressed(MouseEvent e) {
      // Koordinaten des Mauszeigers sind relativ zum sichtbaren Bereich der
      // Zeichenflaeche. Also linke obere Ecke des sichtb. Bereichs holen
      // und beruecksichtigen.
      Rectangle r = ((JScrollPane) e.getSource()).getViewport().getViewRect();
      int f = getModel().getFactor(); // Zoomfaktor beachten
      int x = r.x + e.getX();
      x = x / f;
      int y = r.y + e.getY();
      y = y / f;
      if (middle == null) { // Falls noch kein Mittelpunkt
         middle = new Point(x, y, f); // Mittelpunkt erzeugen
         startDashedCircle = new Point(e.getX() / f, e.getY() / f, f);
         // Mittelpunkt des
         // DashedCircle erzeugen
      } else { // Falls Radius
         // Kreis um Mittelpunkt mit
         // Radius erzeugen
         int rad = Circle.getRadius(middle, new Point(x, y, f));
         Circle k = new Circle(middle, rad, f);
         k.setFillStyle(fill);
         model.append(k); // Kreis vermerken
         middle = null; // neuer Kreis kann beginnen
         dashedMode = false; // Kreis gesetzt, kein
         // dash Mode mehr noetig
      }
   }

   /**
    * Den Kreis zeichnen, wenn die Zeichenflaeche verlassen wird.
    *
    * @param e der mouseExited-Event
    */
   public void mouseExited(MouseEvent e) {
      // Wenn die Mouse die Zeichen flaeche verlaesst, wird der Kreis abgeschlossen
      if (middle != null) {
         Rectangle r = ((JScrollPane) e.getSource()).getViewport().getViewRect();
         int f = getModel().getFactor();
         int x = r.x + e.getX();
         x = x / f;
         int y = r.y + e.getY();
         y = y / f;
         int rad = Circle.getRadius(middle, new Point(x, y, f));
         Circle k = new Circle(middle, rad, f);
         model.append(k);
         middle = null;
         dashedMode = false;
      }
   }

   /**
    * Wird die Maus bewegt, nachdem der Mittelpunkt festgelegt wurde soll
    * zur Unterst&uuml;tzung des Benutzers ein gestrichelter Kreis
    * gezeichnet werden.
    *
    * @param e mouseMoved Event
    */
   public void mouseMoved(MouseEvent e) {
      if (middle != null) {
         Graphics g = ((JScrollPane) e.getSource()).getViewport().getGraphics();
         g.setXORMode(Color.cyan);
         if (dashedMode)
            dc.paint(g);
         int f = getModel().getFactor(); // Zoomfaktor beachten
         dc = new Circle(startDashedCircle, Circle.getRadius(startDashedCircle, new Point(e.getX() / f, e.getY() / f, f)), f, Circle.DASHED);
         dashedMode = true;
         dc.paint(g);
      }
   }

   /**
    * Liefert einen String zurueck
    * @return String "kreis"
    */
   public String toString() {
      return description;
   }
}