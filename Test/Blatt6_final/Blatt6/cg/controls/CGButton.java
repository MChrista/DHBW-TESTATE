package cg.controls;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BorderFactory;

import cg.tools.Resetable;

/**
 * Ein Control-Objekt, das einen Button mit Namen und textueller
 * Anzeige des eingestellten Wertes repraesentiert.
 * @version 03.05.2004
 * @author Ralf Kunze
 */
public class CGButton extends JPanel implements ActionListener, Resetable  {
    /**
     * Der eigentliche Button.
     */
    protected JButton jb;                    // eigentlicher Button
    /**
     * Der Name des Buttons.
     */
    protected JLabel text;                       // Name des Buttons
    /**
     * Der initiale Wert des Buttons.
     */
    protected String onString;                // initialer Wert von value
    /**
     * Der Wert des Buttons falls Aktiv.
     */
    protected String offString;
    /**
     * Status des Buttons/Schalters
     */
    protected boolean pushed = false;

  /**
   * Erzeugt einen Schalter mit Namen, aktivem Wert,inaktivem Wert und einem
   * Listener, der auf das Druecken des Buttons achtet.
   * @param text der Name des Schiebers
   * @param onString Label wenn Schalter auf aktivem Zustand steht
   * @param offString Label wenn Schalter auf inaktivem Zustand steht
   * @param sl Listener, der die vom Button erzeugten Events verarbeitet
   */

  public CGButton(String text, String onString, String offString, ActionListener sl) {
    this(text,"",onString,offString,sl);
  }

  /**
   * Erzeugt einen Schalter mit Namen, toolTipText, aktivem Wert,inaktivem Wert und einem
   * Listener, der auf das Druecken des Buttons achtet.
   * @param text der Name des Schiebers
   * @param toolTipText Erlaeuterung zu der Schalterkomponente
   * @param onString Label wenn Schalter auf aktivem Zustand steht
   * @param offString Label wenn Schalter auf inaktivem Zustand steht
   * @param sl Listener, der die vom Button erzeugten Events verarbeitet
   */

  public CGButton(String text, String toolTipText, String onString, String offString, ActionListener sl) {
    this.text = new JLabel(text);
    add(this.text);                              //Text dazu
    this.onString = onString;                    //Werte retten
    this.offString = offString;

    jb = new JButton(offString);                 //Knopf mit initialem Text
    jb.setPreferredSize(new Dimension(100, 18));
    jb.setToolTipText(toolTipText);              //ToolTipTesxt setzen

    jb.addActionListener(sl);                    //sl achtet auf Schieber
    jb.addActionListener(this);                  //Auf sich selbst achten
    add(jb);

    this.setBorder(BorderFactory.createEtchedBorder());
  }


  /**
   * Wird aufgerufen, wenn der Schalter sich in seinen Ausgangszustand
   * zurueckversetzen soll. Methode aus dem Interface Resetable.
   */
  public void reset() {
    jb.setText(offString);                       //wieder auf off setzen
    pushed=false;
  }

  /**
   * Wird aufgerufen, wenn der Schalter veraendert wurde. Methode aus dem
   * Interface ActionListener.
   * @param e der Event, der vom Schalter/Button ausgeloest wurde.
   */
  public void actionPerformed(java.awt.event.ActionEvent e) {
    if (!pushed) {                               //Status aendern
      jb.setText(onString);                      //Textlabel aendern
      pushed=true;
    }
    else {
      jb.setText(offString);
      pushed=false;
    }
  }

  /**
   * Liefert den aktuellen Wert des Schiebers
   * @return der aktuelle Wert des Schiebers
   */
  public boolean getValue() {
    return pushed;                               //Status liefern

  }
}