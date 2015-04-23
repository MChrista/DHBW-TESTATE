package cg.controls;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.event.MouseInputAdapter;

import cg.draw2D.Model;
import cg.grOb.Point;

/**
 * Mit dem PointListener werden Punkte auf die Zeichenfl&auml;che gesetzt.
 * Es werden die Interfaces MouseListener und MouseMotionListener
 * teilweise implementiert
 *
 * @version 1.0 (A 19.04.2004)
 * @author Ralf Kunze
 */
public class PointListener extends  MouseInputAdapter {
  private Model model;

  private String description;
  
  /**
   * Konstruktor des PointListeners. Das Model wird uebergeben, damit
   * dem Listener bekannt ist, wo die Punkte eingetragen werden muessen
   *
   * @param model Der Listener erh&auml;lt das Model, um es &uuml;ber
   * &Auml;derungen zu informieren.
   */
  public PointListener(Model model) {
    setModel(model);
    description="Punkte";
  }

  /**
   * Konstruktor des PointListeners. Das Model wird uebergeben, damit
   * dem Listener bekannt ist, wo die Punkte eingetragen werden muessen
   *
   * @param model Der Listener erh&auml;lt das Model, um es &uuml;ber
   * &Auml;derungen zu informieren.
   */
  public PointListener(Model model, String description) {
    setModel(model);
    this.description=description;
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
   * Liefert das dem PointListener zugh&ouml;rige Model
   *
   * @return das zugeh&ouml;rige Model
   */
  public Model getModel() {
    return model;
  }

  /**
   *  Diese Methode errechnet die entsprechenden Koordinaten und setzt dort
   *  einen Punkt
   *
   * @param e der erzeugte Event
   */
  public void mouseClicked(MouseEvent e) {
    // Koordinaten des Mauszeigers sind relativ zum sichtbaren Bereich der
    // Zeichenflaeche. Also linke obere Ecke des sichtb. Bereichs holen
    // und beruecksichtigen.
    Rectangle r = ((JScrollPane)e.getSource()).getViewport().getViewRect();
    int f = getModel().getFactor();        // Zoomfaktor beachten
    int x = r.x + e.getX();                // Koords berechnen
    x = x / f;
    int y = r.y + e.getY();
    y = y / f;
    Point p = new Point(x, y, f);          // neuen Punkt erzeugen
    model.append(p);                       // und vermerken
  }
  /**
   * Liefert einen String zurueck
   * @return "Punkt"
   */
  public String toString(){
	  return description;
  }
  
}
