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

/**
 * Die Klasse LineListener dient zum setzen von Start- und Endpunkten einer
 * Linie und implementiert die Interfaces MouseListener und
 * MouseMotionListener
 *
 * @version 1.0 (13.04.2004)
 * @author Ralf Kunze
 */
public class LineListener extends  MouseInputAdapter {
  private Model model;

  private Point startpunkt;
  private Point startDashedPoint;
  private Graphics g;
  private boolean dashedMode = false;
  private Line dl = null;
  
  private String description;

  /**
   * Konstruktor
   *
   * @param model Der Listener erh&auml;lt das Model, damit &Auml;nderungen
   * dorthin weitergeleitet werden k&ouml;nnen.
   */
  public LineListener(Model model) {
    setModel(model);
    description = "Linie";
  }

  /**
   * Konstruktor
   *
   * @param model Der Listener erh&auml;lt das Model, damit &Auml;nderungen
   * dorthin weitergeleitet werden k&ouml;nnen.
   */
  public LineListener(Model model, String description ) {
    setModel(model);
    this.description = description ;
  }
  
  /**
   * Methode zum setzen des Models
   *
   * @param model Das Model an das der LineListener die entsprechenden
   * Informationen weitergibt.
   */
  public void setModel(Model model) {
    this.model = model;
  }

  /**
   * Liefert das dem LineListener zugehoerige Model.
   *
   * @return Das zum Linelistener geh&ouml;rige Model
   */
  public Model getModel() {
    return model;
  }

  /**
   * Diese Methode errechnet die Position des Mauszeigers und legt die
   * Endpunkte der Linie fest. Sind beide Endpunkte eingegeben, wird daraus
   * ein Linienobjekt erzeugt und dieses an das Model weitergereicht.
   *
   * @param e Der erzeugte Event
   */
  public void mouseClicked(MouseEvent e) {
    // Koordinaten des Mauszeigers sind relativ zum sichtbaren Bereich der
    // Zeichenflaeche. Also linke obere Ecke des sichtb. Bereichs holen
    // und beruecksichtigen.
    Rectangle r = ((JScrollPane)e.getSource()).getViewport().getViewRect();
    int f = getModel().getFactor();        // Zoomfaktor beachten
    int x = r.x + e.getX();
    x = x / f;
    int y = r.y + e.getY();
    y = y / f;
    if(startpunkt==null) {                 // Falls Startpunkt
      
      startpunkt = new Point(x, y, f);     // Punkt erzeugen
      startDashedPoint = new Point(e.getX()/f, e.getY()/f,f);
    } 
    else {                                 // Falls Endpunkt
                                           // Linie vom Startpunkt bis
                                           // Endpunkt erzeugen
     Line l = new Line(startpunkt, new Point(x,y,f), f);
     model.append(l);                      // Linie vermerken
     dashedMode=false;
     startpunkt=null;
     startDashedPoint=null;
    }
  }

  /**
   * Wird das Event MausExited erzeugt hat die Maus die Zeichenfl&auml;che
   * verlassen und die Linie wird abgeschlossen.
   *
   * @param e der entsprechend erzeugte Event
   */
  public void mouseExited(MouseEvent e) {        
    if(startpunkt!=null) {
      Rectangle r = ((JScrollPane)e.getSource()).getViewport().getViewRect();
      int f = getModel().getFactor();
      int x = r.x + e.getX();
      x = x / f;
      int y = r.y + e.getY();
      y = y / f;
      Line l = new Line(startpunkt, new Point(x,y,f), f);
      model.append(l);
      dashedMode=false;
      startpunkt=null;
      startDashedPoint=null;
    }
  }



  /**
   * Beim Event mouseMoved soll eine gestrichelte Linie zur besseren
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
         dl.paint(g);
      }
      int f = getModel().getFactor();      // Zoomfaktor beachten
      dl = new Line(startDashedPoint, new Point(e.getX()/f,e.getY()/f,f),f, Line.DASHED);
      dashedMode = true;
      dl.paint(g);                         // linie auf den
                                           // Viewport zeichnen
    }
  }
  /**
   * Liefert einen String zurueck
   * @return "Linie"
   */
  public String toString(){
	  return description ;
  }

}
