package cg.draw2D;

import java.awt.BorderLayout;

import javax.swing.JApplet;

/**
 * Diese Klasse startet die Applikation als Applet.
 *
 * @author Ralf Kunze
 * @version 19.04.2004
 */
public class Applet extends JApplet {

  /**
   * Wird beim Start des Applets aufgerufen.
   * Erzeugt ein Cotroller-Objekt und haegt dieses in die ContentPane 
   * des Applets ein.
   */
  public void init() {
    getContentPane().add(new Controller(), BorderLayout.CENTER);
  }
}
