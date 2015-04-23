package cg.draw2D;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import cg.controls.DrawingPane;

/**
 * Die zur Applikation gehoerige View
 *
 * @version 1.1 (A 27.04.2004)
 * @version Ralf Kunze
 */
public class View extends JScrollPane {
   public final static int WIDTH = 640;
   public final static int HEIGHT = 500;

   /**
    * Konstruktor der View. Erzeugt eine View der Groesse
    * 640 X 500.
    */
   public View() {
      // ScrollBars ausrichten
      super(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_ALWAYS);
      // ein ScrollBar ist 18
      // Pixel breit
      setPreferredSize(new Dimension(WIDTH + 18, HEIGHT + 18));
      // die sichtbare Flaeche
      // ist genau WIDTH*HEIGHT
      getViewport().setView(new DrawingPane(WIDTH, HEIGHT));
   }

   /**
    * Methode zur Darstellung einer JComponent
    *
    * @param comp darzustellende JComponent
    */
   public void display(JComponent comp) {
      Dimension d = comp.getPreferredSize();
      int width = d.width; // Breite und
      int height = d.height; // Hoehe der Componente
      Rectangle r = getViewport().getViewRect(); // Sichtfenster holen
      getViewport().setView(comp); // comp als View setzen

      // Evtl. wurde zuvor ein Bereich dargestellt, der jetzt ausserhalb von
      // comp liegt. Also sichtbaren Bereich neu setzen.
      if (r.width + r.x > width) { // Fenster breiter als Inhalt?
         r.setLocation(width - r.width, r.y);
      }
      if (r.height + r.y > height) { // Fenster hoeher als Inhalt?
         r.setLocation(r.x, height - r.height);
      }
      getViewport().scrollRectToVisible(r);
   }
}
