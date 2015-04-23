package cg.controls;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.OverlayLayout;

/**
 * Klasse zur Erstellung einer Zeichenfl&auml;che
 *
 * @version 1.0 (A 13.04.2004)
 * @author Ralf Kunze
 */
public class DrawingPane extends JPanel {
  
  /**
   * Konstruktor, der eine Zeichenfl&auml;che in Standardgr&ouml;&szlig;
   * erstellt (100 mal 100 Pixel)
   */
  public DrawingPane() {
    this(100,100);
  }

  /**
   * Konstruktor, der eine Zeichenfl&auml;che in frei w&auml;hlbarer
   * gr&ouml;&szlig; erstellt 
   *
   * @param width Breite der Zeichenfl&auml;che
   * @param height H&ouml;he der Zeichnfl&auml;che
   */
  public DrawingPane(int width, int height) {
    super();
    setLayout(new OverlayLayout(this));    // Dies Layout ermoeglicht es,
                                           // alle Objekte uebereinander
                                           // zu zeichnen
    Dimension d = new Dimension(width, height);
    setMinimumSize(d);                     // Groesse sichern
    setMaximumSize(d);
    setPreferredSize(d);
    setForeground(new Color(  0,  0,  0)); // Malfarbe: schwarz
    setBackground(new Color(255,255,255)); // Hintergrund: weiss
  }
}
