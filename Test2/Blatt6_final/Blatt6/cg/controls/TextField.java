package cg.controls;

import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import cg.tools.Resetable;

/**
 * Ein JTextField, das Resetable ist und dem ein default String mitgegeben werden kann,
 * der stets nach einem reset-Aufruf dargestellt wird
 * @author Ralf Kunze
 * @version 1.0 (A 18.05.2004)
 */
public class TextField extends JTextField implements Resetable{

  String defaultText;  // Text mit dem das Textfeld stets dargestellt wird

  /**
   * Erzeugt ein leeres TextField
   */
  public TextField(){
    super();
  }

  /**
   * Erzeugt ein TextField mit entsprechendem String als Defaultstring
   * @param text Initialer und nach reset-Aufruf dar zu stellender Text
   */
  public TextField(String text){
    super(text);
    this.defaultText = text;
  }

  /**
   * Liefert das Document des TextFeldes, das als Model fungiert
   * @return Das Model des Ausgabetextes
   */
  public Document getModel(){
    return getDocument();
  }

  /**
   * Setzt den per default im TextField zu erscheinenden Text
   * @param text Der zu benutzende Defaulttext
   */
  public void setDefaultText(String text){
    this.defaultText = text;
    try {
         getDocument().insertString(0,defaultText,null); 
    }catch (BadLocationException ble){
       System.err.println("DefaultText einfuegen ist fehlgeschlagen!");
    }
  }

  /**
   * Loescht alle im Model des Textfeldes stehenden Texte
   * und setzt den Defaulttext hinein, sofern einer existiert
   */
  public void reset(){
    try {
       getDocument().remove(0,getDocument().getLength()); 
       if(defaultText != null){
         getDocument().insertString(0,defaultText,null); 
       }
    }catch (BadLocationException ble){
       System.err.println("Reset ist fehlgeschlagen!");
    }
  }

}