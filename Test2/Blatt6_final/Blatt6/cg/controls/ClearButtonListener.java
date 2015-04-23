package cg.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import cg.tools.Resetable;

/**
 * Der Clearbutton Listener erhaelt ein Array mit Resetables, bei welchen
 * bei Klick die methode reset() aufgerufen wird.
 * 
 * @author rkunze
 * @version 1.0 (ML 17.04.2004)
 */
public class ClearButtonListener implements ActionListener {

  private Vector rv = new Vector(3,1);     // Vektor mit zu
                                           // resettenden Objekten

  /**
   * Erzeugt einen Listener, der Resetable Objekte in den Ursprungszustand
   * versetzt.
   */
  public ClearButtonListener() {
  }

  /**
   * Fuegt ein Resetable Objekt zum Listener hinzu.
   * @param r Objekt, welches bei einem actionPerformed 
   * Event resetted werden soll.
   */
  public void append(Resetable r){
	rv.addElement(r);
  }
  /**
   * Wird aufgerufen, wenn der User auf den ClearButton gedrueckt hat.
   * @param e der vom Button ausgeloeste Event
   */
  public void actionPerformed(ActionEvent e) {
	  for(int i=0; i < rv.size(); i++) {    // Fuer alle Objekte im Vektor
		((Resetable)rv.elementAt(i)).reset();                      // reset-Methode ausfuehren
	  }
  }
}

