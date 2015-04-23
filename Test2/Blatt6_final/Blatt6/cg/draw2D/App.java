package cg.draw2D;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 *  App startet die Anwendung als eigenstaendige Applikation.
 *
 *  @author Ralf Kunze
 *  @version 19.04.2004
 */
public class App {

   /**
    * Main methode. Startet die draw2D Applikation.
    *
    * @param args Komandozeilanparameter
    */
   public static void main(String args[]) {
      // Container fuer die Anwendung
      JFrame frame = new JFrame("Draw2D"); // mit entspr. Ueberschrift
      // Inhalt der Anwendung erstellen 
      // und einhaengen
      frame.getContentPane().add(new Controller(), BorderLayout.CENTER);

      // Close-Operation  festlegen
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack(); // Groesse der Komponenten bestimmen
      frame.setVisible(true); // Sichtbar machen
   }
}
