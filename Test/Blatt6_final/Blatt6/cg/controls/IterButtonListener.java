package cg.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cg.draw2D.Model;
/**
 * Der IterButtonListener iteriert ein Fraktal im Model.
 * 
 * @author rkunze
 * @version 1.0 (A 17.05.2004)
 */
public class IterButtonListener implements ActionListener {

  private Model m;

  /**
   * Erzeugt einen Listener, der das Model informiert, wenn es die Fraktale iterieren soll.
   * @param m Model der Applikation
   */
  public IterButtonListener(Model m) {
     this.m=m;
  }

  /**
   * Wird aufgerufen, wenn der User auf den IterierButton gedrueckt hat.
   * @param e der vom Button ausgeloeste Event
   */
  public void actionPerformed(ActionEvent e) {
     m.iterateFractal();
  }
}

