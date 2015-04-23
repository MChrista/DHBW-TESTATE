package cg.controls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.event.MouseInputAdapter;

import cg.draw2D.Model;
import cg.grOb.Point;
import cg.grOb.Rectangle;

/**
 * Der RectangleListener achtet auuf die Aktionen des Benutzers der
 * Applikation und erzeugt gegebenenfalls ein neues Rechteck.
 *
 * @version 1.0 (ML 19.04.2004)
 * @author Ralf Kunze
 */
public class RectangleListener extends MouseInputAdapter {
  private Point startpunkt;                // true, wenn als naechstes
                                           // der Endpunkt einer Linie
                                           // gezeichnet werden wird.
  private Point startDashedPoint;
  private Graphics g;
  private boolean dashedMode = false;
  private Rectangle dr = null;

  private Model model;

  private String description;
  
  /**
   * Der Konstrukter verbindet den Listener mit dem Model
   *
   * @param model das Model, an das die Events geschickt werden.
   */
  public RectangleListener(Model model) {
    setModel(model);
    startpunkt = null;                     // Wir beginnen mit Startpunkt
    description="Rechteck";
  }

  /**
   * Der Konstrukter verbindet den Listener mit dem Model
   *
   * @param model das Model, an das die Events geschickt werden.
   */
  public RectangleListener(Model model, String description) {
    setModel(model);
    startpunkt = null;                     // Wir beginnen mit Startpunkt
    this.description=description;
  }
  
  /**
   * Setzen des Models
   */
  public void setModel(Model model) {
    this.model = model;
  }

  /**
   * Liefert das Model des Listeners zur&uuml;ck
   */
  public Model getModel() {
    return model;
  }

  /**
   * Merkt sich beim ersten mal einen Startpunkt (Punkt in der rechten
   * oberen ecke des Rechtecks) und beim zweiten mal wird das Rechteck
   * erstellt und dem Model &uuml;bergeben.
   *
   * @param e mousePressed-Event
   */
  public void mousePressed(MouseEvent e) {
    // Koordinaten des Mauszeigers sind relativ zum sichtbaren Bereich der
    // Zeichenflaeche. Also linke obere Ecke des sichtb. Bereichs holen
    // und beruecksichtigen.
    java.awt.Rectangle r = ((JScrollPane)e.getSource()).getViewport().getViewRect();
    int f = getModel().getFactor();        // Zoomfaktor beachten
    int x = r.x + e.getX();
    x = x / f;
    int y = r.y + e.getY();
    y = y / f;
    if(startpunkt==null) {                 // Falls Startpunkt
      startpunkt = new Point(x, y, f);     // Punkt erzeugen
      startDashedPoint = new Point(e.getX()/f,e.getY()/f,f);
    } 
    else {                                 // Falls Endpunkt
                                           // Rechteck vom Startpunkt bis
                                           // Endpunkt erzeugen
     cg.grOb.Rectangle re = new cg.grOb.Rectangle(startpunkt,new Point(x,y,f), f);
     re.setDitherMatrix(model.getDitherMode());
     re.setGreyValue(model.getGreyValue());
     model.append(re);                     // Rechteck vermerken
     startpunkt = null;                    // neues Rechteck kann beginnen
     dashedMode=false;
     startDashedPoint=null;
    }
  }

  /**
   * Wenn die Maus die Zeichenfl&auml;che verl&auml;&szlig;t soll das
   * Rechteck zu Ende gezeichnet werden.
   *
   * @param e MouseExited Event
   */
  public void mouseExited(MouseEvent e) {        
  // Wenn die Mouse die Zeichen flaeche verlaesst, wird die Linie abgeschlossen
    java.awt.Rectangle r = ((JScrollPane)e.getSource()).getViewport().getViewRect();
    int f = getModel().getFactor();
    int x = r.x + e.getX();
    x = x / f;
    int y = r.y + e.getY();
    y = y / f;
    if(startpunkt!=null) {
       cg.grOb.Rectangle re = new cg.grOb.Rectangle(startpunkt, new Point(x,y,f), f);
       model.append(re);
       startpunkt = null;
       dashedMode=false;
       startDashedPoint=null;
    }
  }

  /**
   * Beim Event mouseMoved soll ein gestricheltes Rechteck zur besseren
   * Benutzerf&uuml;hrung zu sehen sein, insofern der Startpunkt festgelegt
   * wurde
   *
   * @param e mouseMoved-Event
   */
  public void mouseMoved(MouseEvent e) {
    if (startpunkt!=null) {
      Graphics g = ((JScrollPane)e.getSource()).getViewport().getGraphics();
      g.setXORMode(Color.cyan);
      if (dashedMode) {
         dr.paint(g);
      }
      int f = getModel().getFactor();      // Zoomfaktor beachten
      dr = new Rectangle(startDashedPoint, new Point(e.getX()/f,e.getY()/f,f),f,Rectangle.DASHED);
      dashedMode = true;
      dr.paint(g);                         // rechteck auf den
                                           // Viewport zeichnen
    }
  }
  /**
   * Liefert einen String zurueck
   * @return "Rechteck"
   */
  public String toString(){
	  return description;
  }

}
