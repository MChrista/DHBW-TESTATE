package cg.controls;

import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.event.MouseInputAdapter;

import cg.draw2D.Model;
import cg.grOb.Point;
import cg.grOb.Rectangle;

/**
 * Ein Listener-Objekt, das MouseEvents verarbeitet und dabei ein
 * ClipRechteck erzeugt,an dem alle grafischen Objekte anschliessend
 * geclippt werden.
 *
 * @version 1.0 (ML 26.04.2004)
 * @author Ralf Kunze
 */
public class ClipListener extends  MouseInputAdapter {
  private Point startpunkt = null;
  private Model model;
  private Rectangle dr = null;
  private Point dashedPoint = null;
  private boolean dashedMode = false;
  private String description; 

  /**
   * Erzeugt einen ClipListener.
   * @param model dieses Objekt fragt der Listener nach grafischen
   * Objekten.
   */
  public ClipListener(Model model) {
	setModel(model);
   description="Clipping";
  }

  /**
   * Erzeugt einen ClipListener.
   * @param model dieses Objekt fragt der Listener nach grafischen
   * Objekten.
   * @param description Beschreibung, die von toString() zurueckgeliefert wird.
   */
  public ClipListener(Model model, String description) {
   setModel(model);
   this.description=description;
  }
  
  /**
   * Setzt das Model.
   * @param model dieses Objekt fragt der Listener nach grafischen
   * Objekten.
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
   * wird aufgerufen, wenn der User in der Zeichenflaeche geclickt hat.
   * @param e der vom user ausgeloeste Event
   */
  public void mousePressed(MouseEvent e) {
	// Koordinaten des Mauszeigers sind relativ zum sichtbaren Bereich der
	// Zeichenflaeche. Also linke obere Ecke des sichtb. Bereichs holen
	// und beruecksichtigen.
	java.awt.Rectangle r = ((JScrollPane)e.getSource()).getViewport().getViewRect();
	int f = model.getFactor();             // Zoomfaktor beachten
	int x = r.x + e.getX();
	x = x / f;
	int y = r.y + e.getY();
	y = y / f;

	if(startpunkt==null) {                 // Falls Startpunkt
	  startpunkt = new Point(x, y, f);     // Punkt erzeugen
	  dashedPoint = new Point(e.getX()/f,e.getY()/f,f);
	}
	else {                                 // Falls Endpunkt
                                           // Rechteck vom Startpunkt bis
                                           // Endpunkt erzeugen
	 cg.grOb.Rectangle re = new cg.grOb.Rectangle(startpunkt, new Point(x,y), f);
	 model.clip(re);
	 startpunkt = null;                    // neues Rechteck beginnen
	 dashedPoint=null;
	 dashedMode=false;
	}
  }
  /**
   * wird aufgerufen, wenn der User mit der Maus die Zeichenflaeche verlassen
   * hat.
   * @param e der vom user ausgeloeste Event
   */
  public void mouseExited(MouseEvent e) {
  // Wenn die Mouse die Zeichen flaeche verlaesst, wird das Rechteck
  // geloescht.
	if(startpunkt!=null) {
	 startpunkt = null;
	 dashedPoint=null;
	}
  }
  /**
   * wird aufgerufen, wenn der User die Maus in der Zeichenflaeche bewegt hat.
   * @param e der vom user ausgeloeste Event
   */
  public void mouseMoved(MouseEvent e) {
	if(startpunkt!=null) {
	  java.awt.Graphics g = ((JScrollPane)e.getSource()).getViewport().getGraphics();
	  g.setXORMode(java.awt.Color.cyan);
	  if (dashedMode)
		dr.paint(g);
	  int f = model.getFactor();           // Zoomfaktor beachten


	  dr = new Rectangle(dashedPoint, new Point(e.getX()/f, e.getY()/f,f), f,Rectangle.DASHED);
	  dr.paint(g);
	  dashedMode = true;
	}
  }
  
  /**
   * Liefert einen String zurueck
   * @return "Clipping"
   */
  public String toString(){
  	  return description;
  }
}
