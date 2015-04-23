package cg.grOb;

/**
 * Ein Objekt dieser Klasse repraesentiert ein Lindenmayer-System.
 * @author Ralf Kunze
 * @version 1.0 (A 18.05.2004)
 */
public class LSystem extends Polygon implements Fractal {

   private int maxPoints; // Maximale Anzahl an Punkten der Kurve
   // die nicht ueberschritten wird bei Iterrationen
   private char word[]; // Wort mit Beschreibung der Kurve
   private double delta; // Abstand in X- bzw. Y-Richtung
   private char regel[]; // Regel des L-Systems
   private int regellaenge; // und Laenge

   /**
    * Leerer Konstruktor
    */
   private LSystem() {
      super();
      word = new char[0];
   }

   /**
    * Konstruktor zum Erzeugen eines L-Systems in Abhaengigkeit der Breite und Hoehe der Flaeche,
    * auf der es sich darstellen darf und der maximalen Anzahl an Polygonpunkten, wodurch die 
    * Zahl der Iterationen begrenzt wird. Vor der Berechnung muss mit {@link #setRegel(String)}
    * eine zu benutzende Regel gesetzt werden.  
    * @param width Breite der Flaeche, die dem L-System zur Verfuegung steht
    * @param height Hoehe der Flaeche, die dem L-System zur Verfuegung steht
    * @param maxPoints maximale Anzahl an Punkten, die das L-System nicht uebersteigen darf
    */
   public LSystem(int width, int height, int maxPoints) { // Beim Erzeugen des Objekts wird die Ausgangsfigur erstellt
      super();
      word = new char[maxPoints];
      this.maxPoints = maxPoints;

      for (int i = 0; i < maxPoints; i++)
         word[i] = '\0';

      word[0] = 'r'; // initiales Quadrat
      word[1] = 'u';
      word[2] = 'l';
      word[3] = 'd';

      int mx = width / 2; // bestimme Mitte der angegebenen Flaeche
      int my = height / 2;

      delta = width / 3;
      double halbd = delta / 2; // Haelfte des Abstandes

      append(new Point(mx - halbd, my + halbd));
      append(new Point(mx + halbd, my + halbd));
      append(new Point(mx + halbd, my - halbd));
      append(new Point(mx - halbd, my - halbd));
      append(new Point(mx - halbd, my + halbd));
      append(new Point(mx - halbd, my + halbd));

   }

   /**
    * Fuehrt eine Iteration des L-Systems durch
    * @exception AusgabeException 
    */
   public void iterate() {
      // noch Iteration moeglich? , d.h. maximale Anzahl an Polygonpunkten wird 
      if (getLength() * regellaenge >= maxPoints) { // nicht ueberschritten, fuehre Iteration durch
         System.err.println("Die maximale Punktzahl wuerde beim naechsten mal ueberschritten. Es sind aktuell" + getLength() + " Punkte");
         return;
      }
      delta = 2 * delta / regellaenge;
      lsystem_iteration(delta);
   }

   /**
    * Fuehrt eine Iteration des Lindenmayer-Systems durch. 
    * @param regellaenge Laenge der einzelnen Regeln
    * @param delta Abstand zwischen zwei Punkten in X- und Y-Richtung
    */
   public void lsystem_iteration(double delta) { //throws Exception {       
      int lauf;
      int lauf2;
      int index = 0;
      int regelnr = 0;
      char w_neu[] = new char[maxPoints];
      Point[] poly_neu = new Point[maxPoints];
      // Da sich der letzte Punkt doppelt gemerkt wird
      for (lauf = 0; lauf < (getLength() - 2); lauf++) {
         index = regellaenge * lauf;
         // Erster neuer Punkt identisch mit Altem
         poly_neu[index++] = new Point(getPointAt(lauf));

         switch (word[lauf]) {
            case 'r' :
               regelnr = 0;
               break;
            case 'u' :
               regelnr = 1;
               break;
            case 'l' :
               regelnr = 2;
               break;
            case 'd' :
               regelnr = 3;
               break;
         }
         // Laut regel die regellaenge Stueck neuen Punkte erstellen
         for (lauf2 = 0; lauf2 < regellaenge; lauf2++) {

            poly_neu[index] = new Point(poly_neu[index - 1]); // letzten Point kopieren

            w_neu[index - 1] = regel[regelnr * regellaenge + lauf2]; // neues Wort-Element erzeugen

            // aktuelles Element der Regel finden und Punkt an die neue Position verschieben
            switch (regel[regelnr * regellaenge + lauf2]) {
               case 'r' :
                  poly_neu[index].setX(poly_neu[index].x + delta);
                  break;
               case 'u' :
                  poly_neu[index].setY(poly_neu[index].y - delta);
                  break;
               case 'l' :
                  poly_neu[index].setX(poly_neu[index].x - delta);
                  break;
               case 'd' :
                  poly_neu[index].setY(poly_neu[index].y + delta);
                  break;
            }
            index++;
         } //for
      } //for
      pv = new java.util.Vector(1, 1);
      for (lauf = 0; lauf < index; lauf++) { //Neue Punkte in das wirkliche Polygon einhaengen
         append(poly_neu[lauf]);
         if (lauf != index)
            word[lauf] = w_neu[lauf];
      }
      append(new Point(poly_neu[0])); // ersten Punkt wieder einhaengen, um das Polygon zu schliessen             
   } //lsystem_iteration

   /**
    * Uebersetzt die uebergebene Regel und schreibt den Inhalt in regel
    * @param rule neue Regel
    */
   public void setRegel(String rule) {
      int lauf;
      int lauf2;
      String r = new String("r");
      String l = new String("l");
      String d = new String("d");
      String u = new String("u");

      regellaenge = rule.length(); // reale Laenge holen

      for (lauf = 0; lauf < regellaenge; lauf++)
         rule = new String(rule.concat(String.valueOf(rule.charAt(regellaenge - lauf - 1))));
      regellaenge *= 2;

      // Regel fuer 'u' konstruieren
      for (lauf = 0; lauf < regellaenge; lauf++) {
         if (rule.regionMatches(lauf, r, 0, 1)) {
            rule = new String(rule.concat(u));
         } else if (rule.regionMatches(lauf, u, 0, 1)) {
            rule = new String(rule.concat(l));
         } else if (rule.regionMatches(lauf, l, 0, 1)) {
            rule = new String(rule.concat(d));
         } else if (rule.regionMatches(lauf, d, 0, 1)) {
            rule = new String(rule.concat(r));
         }
      }

      // Regel fuer 'l' konstruieren
      for (lauf = 0; lauf < regellaenge; lauf++) {
         if (rule.regionMatches(lauf, r, 0, 1)) {
            rule = new String(rule.concat(l));
         } else if (rule.regionMatches(lauf, u, 0, 1)) {
            rule = new String(rule.concat(d));
         } else if (rule.regionMatches(lauf, l, 0, 1)) {
            rule = new String(rule.concat(r));
         } else if (rule.regionMatches(lauf, d, 0, 1)) {
            rule = new String(rule.concat(u));
         }
      }

      // Regel fuer 'd' konstruieren
      for (lauf = 0; lauf < regellaenge; lauf++) {
         if (rule.regionMatches(lauf, r, 0, 1)) {
            rule = new String(rule.concat(d));
         } else if (rule.regionMatches(lauf, u, 0, 1)) {
            rule = new String(rule.concat(r));
         } else if (rule.regionMatches(lauf, l, 0, 1)) {
            rule = new String(rule.concat(u));
         } else if (rule.regionMatches(lauf, d, 0, 1)) {
            rule = new String(rule.concat(l));
         }
      }
      regel = rule.toCharArray();

   }

   /**
    * Liefert das aktuelle Wort des LSystems als String
    * @return Wort als String
    */
   public String getRegel() {
      return new String(word);
   }

   /**
    * Liefert die Anzahl der Polygonpunkte des L-Systems und damit seine Beschreibung 
    * @return String, der das L-System beschreibt
    */
   public String toString() {
      return ("Anzahl der Polygonpunkte des L-Systems: " + getLength());
   }

}
