package cg.controls;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.BorderFactory;
import cg.tools.Resetable;

/**
 * Die Klasse CGSlider stellt ein JPanel zur Verfuegung,
 * welches einen JSlider und ein JLabel als &Uuml;berschrift
 * enth&auml;lt.
 *
 * @author Ralf Kunze
 * @version 1.1 (A 26.04.2004))
 */
public class CGSlider extends JPanel implements ChangeListener, Resetable {
   private JSlider slider;
   private JLabel text;
   private JLabel valueText;
   private int init;

   /**
    * Konstruktor, der eine neue Schieberkomponente erstellt)
    * 
    * @param text &Uuml;berschrift des Schiebereglers
    * @param min Mimimalwert des Schiebereglers
    * @param max Maximalwert des Schiebereglers
    * @param init Startwert des Schiebereglers
    * @param sl Der zugeh&ouml;rige Listener
    */
   public CGSlider(String text, int min, int max, int init, CGSliderListener sl) {
      // setLayout(new GridLayout(2,1,10,10));
      this.init = init;
      this.text = new JLabel(text);
      this.text.setPreferredSize(new Dimension(110, 18));
      add(this.text); // Text dazu

      valueText = new JLabel("" + init);
      valueText.setPreferredSize(new Dimension(30, 18));
      valueText.setHorizontalAlignment(JLabel.RIGHT);
      add(valueText);

      slider = new JSlider(min, max, init);
      slider.addChangeListener(sl);
      slider.addChangeListener(this);
      slider.setPreferredSize(new Dimension(100, 18));
      add(slider);
      this.setBorder(BorderFactory.createEtchedBorder());
   }

   /**
     * Setzt die obere Grenze des einstellbaren Intervalls neu.
     * @param n Neue obere Grenze des Sliders.
     */
   public void setMaximum(int n) {
      slider.setMaximum(n);
   }

   /**
    * Liefert die obere Grenze des einstellbaren Bereichs.
    * @return Obere Grenze des Sliders.
    */
   public int getMaximum() {
      return slider.getMaximum();
   }

   /**
     * Setzt die untere Grenze des einstellbaren Intervalls neu.
     * @param n Neue untere Grenze fuer den Slider.
     */
   public void setMinimum(int n) {
      slider.setMinimum(n);
   }

   /**
    * Liefert die obere Grenze des einstellbaren Bereichs.
    * @return Untere Grenze des Sliders
    */
   public int getMinimum() {
      return slider.getMinimum();
   }

   /**
    * Liefert den aktuellen Wert des Schiebers
    * @return der aktuelle Wert des Schiebers
    */
   public int getValue() {
      return slider.getValue();
   }

   /**
    * Setztden aktuellen Wert des Schiebers
    * @param n Der Wert, der gesetzt werden soll.
    */
   public void setValue(int n) {
      slider.setValue(n);
   }

   /**
    * Methode des Interfaces Resetable.
    * Diese Methode setzt den Slider auf seinen initialen Wert zurueck.
    */
   public void reset() {
      slider.setValue(init);
   }

   /**
    * Wenn der Slider veraendert wird, Soll das Textfeld aktualisiert werden.
    *
    * @param e Erzeugtes Event
    */
   public void stateChanged(ChangeEvent e) {
      valueText.setText("" + slider.getValue());
   }

   /**
    * Aktiviert/Deaktiviert den Slider.
    * @param mode true: falls aktiviert; false sonst
    */
   public void setEnabled(boolean mode) {
      slider.setEnabled(mode);
   }

   /**
    * Gibt an ob der Slider Aktiviert/Deaktiviert ist.
    * @return true: falls aktiviert; false sonst
    */
   public boolean isEnabled() {
      return slider.isEnabled();
   }
}
