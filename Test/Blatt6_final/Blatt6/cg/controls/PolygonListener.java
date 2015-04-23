package cg.controls;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.event.MouseInputAdapter;

import cg.draw2D.Model;
import cg.grOb.Line;
import cg.grOb.Point;
import cg.grOb.Polygon;


/**
 * Ein Listener-Objekt, das MouseEvents verarbeitet und dabei Polygone erzeugt,
 * die an den Stellen der Zeichenflaeche liegen, wo der Benutzer geklickt hat.
 *
 * @version 1.0 (ML 03.05.2004)
 * @author Ralf Kunze
 */
public class PolygonListener extends  MouseInputAdapter {
  private Model model;
  private Polygon poly;
  private int region;
  private Line dl;
  private boolean dashedMode = false;
  private Point startDashedLine;
  private Polygon dashedPoly;
  
  private String description;

  /**
   * Erzeugt einen PolygonListener.
   * @param model diesem Objekt schickt der Listener die erzeugten grafischen
   * Objekte zur Verwaltung
   * @param region Anzahl der Pixel um den Startpunkt, in dem geklickt werden kan, um das Polygon zu schliessen.
   */
  public PolygonListener(Model model, int region) {
    setModel(model);
    this.region = region;
    description="Polygon";
  }

  /**
   * Erzeugt einen PolygonListener.
   * @param model diesem Objekt schickt der Listener die erzeugten grafischen
   * Objekte zur Verwaltung
   * @param region Anzahl der Pixel um den Startpunkt, in dem geklickt werden kan, um das Polygon zu schliessen.
   */
  public PolygonListener(Model model, int region, String description) {
    setModel(model);
    this.region = region;
    this.description=description;
  }
  
  /**
   * Setzt das Model.
   * @param model diesem Objekt schickt der Listener die erzeugten grafischen
   * Objekte zur Verwaltung
   */
  public void setModel(Model model) {
    this.model = model;
  }

  /**
   * Liefert das Model.
   * @return das Model.
   */
  public Model getModel() {
    return model;
  }

  /**
   * Setzt die Groesse des Bereichs um den Startpunkt des Polygons, in den
   * der Benutzer clicken kann, um das Polygon zu beenden.
   * @param region Empfindlichkeit in Pixeln
   */
  public void setRegion(int region) {
    this.region = region;
  }

  /**
   * Liefert die Groesse des Bereichs um den Startpunkt des Polygons, in den
   * der Benutzer clicken kann, um das Polygon zu beenden.
   * @return die Empfindlichkeit in Pixeln
   */
  public int getRegion() {
    return region;
  }

  /**
   * wird aufgerufen, wenn der User in der Zeichenflaeche geclickt hat.
   * @param e der vom user ausgeloeste Event
   */
  public void mousePressed(MouseEvent e) {
    // Koordinaten des Mauszeigers sind relativ zum sichtbaren Bereich der
    // Zeichenflaeche. Also linke obere Ecke des sichtb. Bereichs holen
    // und beruecksichtigen.
    Rectangle r = ((JScrollPane)e.getSource()).getViewport().getViewRect();
    Graphics g = ((JScrollPane)e.getSource()).getViewport().getGraphics();
    g.setXORMode(Color.cyan);
    int f = getModel().getFactor();        // Zoomfaktor beachten
    int x = r.x + e.getX();
    x = x / f;
    int y = r.y + e.getY();
    y = y / f;

    Point p = new Point(x,y,f);            // Startpunkt erzeugen

   if(poly==null) {                        // Startpunkt?
      poly = new Polygon(p, region, f);    // Polygon erzeugen
      startDashedLine = new Point(e.getX()/f, e.getY()/f,f);
      dashedPoly = new Polygon(startDashedLine,region,f,Polygon.DASHED);
    } 
    else {                                 // Nicht Startpunkt
      if(poly.isClosingPoint(p)) {         // Will User Poly schliessen?
        poly.close();                      // Poly schliessen
        dashedMode=false;
        model.append(poly);
        poly=null;
        startDashedLine=null;
        dashedPoly=null;
      }   
      else
      {
		dashedMode=false;
      poly.append(p);
		startDashedLine = new Point(e.getX()/f, e.getY()/f,f);
		dashedPoly.append(startDashedLine);
      }
    }
  }

  /**
   * Wird aufgerufen, wenn der User mit der Maus die Zeichenflaeche verlassen
   * hat.
   * @param e der vom user ausgeloeste Event
   */
  public void mouseExited(MouseEvent e) {        
  // Wenn die Maus die Zeichenflaeche verlaesst und das aktuelle Polygon
  // noch nicht beendet war, wird das Polygon geloescht.
    if(poly!=null) {
		Graphics g = ((JScrollPane)e.getSource()).getViewport().getGraphics();
		g.setXORMode(Color.cyan);
		dashedPoly.paint(g);
    	dl.paint(g);
        dashedMode = false;
        poly=null;
        startDashedLine=null;
        dashedPoly=null;
    }
  }

  /**
   * wird aufgerufen, wenn der User die Maus in der Zeichenflaeche bewegt hat.
   * @param e der vom user ausgeloeste Event
   */
  public void mouseMoved(MouseEvent e) {
    if(poly!=null) {
      Graphics g = ((JScrollPane)e.getSource()).getViewport().getGraphics();
      g.setXORMode(Color.cyan);
      if (dashedMode) {
        dl.paint(g);
      }
      int f = getModel().getFactor();      // Zoomfaktor beachten
	   dl = new Line(startDashedLine, new Point(e.getX()/f, e.getY()/f,f), f,Line.DASHED);
      dl.paint(g);
      dashedMode=true;
    }
  }
  /**
   * Liefert einen String zurueck
   * @return "Polygon"
   */
  public String toString(){
	  return description;
  }

}
