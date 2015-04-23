package cg.controls;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;

/**
 * Ein Tool-Objekt, das eine Schaltflaeche repraesentiert, die die Applikation
 * in ihren Ausgangszustand zurueckversetzt, wenn sie gedrueckt wird.
 */
public class PanelButton extends JPanel {
  private JButton b;

  /**
   * Erzeugt einen Button in einem Panel, um die Groesse zu beeinflussen
   * Ausgangszustand versetzt werden sollen.
   * @param label Auf dem Button erscheinender Text
   */
  public PanelButton(String label) {
    JButton b = new JButton(label);            // Button erzeugen und die
    b.setPreferredSize(new Dimension(150,35));    // Groesse festlegen
    add(b);                                      // Button hinzufuegen
    this.b = b;
  }

  public void addActionListener(ActionListener listen){ 
    b.addActionListener(listen); // Listener einhaengen
  }

  public void setEnabled(boolean b){
    this.b.setEnabled(b);
  }

}
