package cg.grOb;

import java.awt.Graphics;
import cg.tools.Matrix;
//import cg.draw2D.View;

/**
 * Diese Klasse stellt ein Punkt-Objekt zur Verfügung.
 * Ein Punkt ist durch ein XY-Koordinatenpaar bestimmt
 * und besitzt einen Zoomfaktor.
 * Die Punkte werden intern als double vermerkt. Die Methoden 
 * liefern gerundete Werte fuer die Bildschirmdarstellung.
 * Dadurch kann mit hoeherer Genauigkeit gerechnet 
 * werden und die Ausgabe (z.B. SVG) kann mit groeserer Aufloesung erfolgen.
 *
 * @version 1.5 (A 18.05.2004)
 * @author Ralf Kunze
 */
public class Point extends GraphicObject {
   protected double x;
   protected double y;

   /**
    * Defaultkonstruktor erzeugt einen Punkt im Ursprung
    */
   public Point() {
      this(0, 0);
   }

   /**
    * Konstruktor zum kopieren eines Punktes
    *
    * @param p zu kopierender Punkt
    */
   public Point(Point p) {
      this(p.x, p.y, p.getFactor());
   }

   /**
    * Konstruktor, der ein Punktobjekt aus einem Koordinatenpaar errechnet
    *
    * @param x x-Koordinate des Punktes
    * @param y y-Koordinate des Punktes
    */
   public Point(double x, double y) {
      this(x, y, 1);
   }

   /**
    * Konstruktor, der ein Punktobjekt aus einem Koordinatenpaar errechnet
    * mit zugeh&ouml;riger Zoomstufe
    *
    * @param x x-Koordinate des Punktes
    * @param y y-Koordinate des Punktes
    * @param faktor der zu setzende Zoomfaktor
    */
   public Point(double x, double y, int faktor) {
      super(faktor);
      this.x = x;
      this.y = y;
   }

   /**
    * Methode zum setzen der x-Koordinate
    *
    * @param x x-Koordinate
    */
   public void setX(double x) {
      this.x = x;
   }

   /**
    * Abfragen der x-Koordniate
    *
    * @return die x-Koordinate des Punktobjektes
    */
   public int getXCoord() {
      return (int) (x + 0.5);
   }

   /**
    * Methode zum setzen der y-Koordniate
    *
    * @param y y-Koordinate
    */
   public void setY(double y) {
      this.y = y;
   }

   /**
    * Abfragen der y-Koordniate
    *
    * @return die y-Koordinate des Punktobjektes
    */
   public int getYCoord() {
      return (int) (0.5 + y);
   }

   /**
    * Liefert true, wenn dieser Punkt bezueglich der Kante edge beim Wert value
    * auf der sichtbaren Seite (also zum Beispiel innerhalb eines Clipping-Rechtecks)
    * liegt.
    * @param value Wert der interessanten Kante
    * @param edge  interessante Kante
    */
   public boolean onVisibleSide(int value, int edge) {
      switch (edge) {
         case LEFT :
            return x >= value; // linke  Fenster-Kante
         case RIGHT :
            return x <= value; // rechte Fenster-Kante
         case TOP :
            return y >= value; // obere  Fenster-Kante
         case BOTTOM :
            return y <= value; // untere Fenster-Kante
      }
      return false; // sonst: draussen
   }

   /**
    * Liefert den Region Code fuer diesen Punkt bezueglich eines Rechtecks.
    * @param re das Rechteck, bezueglich dessen der Region-Code berechnet wird
    * @return der 4-Bit Region Code
    */
   public byte getRegionCode(Rectangle re) {
      byte c;
      c = EMPTY;

      if (x < re.getXMin()) {
         c = LEFT;
      } else if (x > re.getXMax()) {
         c = RIGHT;
      }
      if (y < re.getYMin()) {
         c |= TOP;
      } else if (y > re.getYMax()) {
         c |= BOTTOM;
      }

      return c;
   }

   /**
    * Clippt den Punkt am uebergebenen Rechteck. Liefert true, falls der Punkt
    * im Rechteck lag; false, falls er ausserhalb lag.
    *
    * @param re das Clipping-Rechteck
    * @return true, wenn der Punkt im Clippingrechteck lag, sonst false
    */
   public boolean clip(Rectangle re) {
      return getRegionCode(re) == EMPTY;
   }

   /**
    * Setzt die Transformations Matrix eines Objektes
    * @param transform Transformationsmatrix
    */
   public void setTransform(Matrix transform) {
      if (transform != null) {
         Matrix zv1 = new Matrix(3, 1, 1.0); // Matrix fuer Zeilenvektor
         zv1.val[0][0] = x; // x-Wert setzen
         zv1.val[1][0] = y; // y-Wert setzen
         zv1 = transform.mult(zv1); // Achtung Reihenfolge!

         x = (zv1.val[0][0]);
         y = (zv1.val[1][0]);
      }
   }

   /**
    * Liefert true, wenn der Punkt p im Inneren oder auf dem Rand
    * dieses Kreises liegt.
    * Liefert false, wenn der Punkt p im &Auml;u&szlig;eren dieses Kreises
    * liegt.
    *
    * @param p der Testpunkt
    * @return True: ob der Punkt in diesem Kreis enthalten ist; false sonst.
    */
   public boolean contains(Point p) {
      int r = 2;
      return (((x - r <= p.x) && (x + r >= p.x)) && ((y - r <= p.y) && (y + r >= p.y)));
   }

   /**
    * toString Methode der Klasse Punkt
    *
    * @return liefert eine Stringrepr&auml;sentation eines Punktobjektes
    */
   public String toString() {
      return ("Point[xCoord=" + x + ",yCoord=" + y + "] Factor=" + getFactor());
   }

   /**
    * Methode zum setzen eines Punktes auf der Zeichnfl&auml;che mit dem
    * zugeh&ouml;rigen Zoomfaktor
    *
    * @param g der Grafikkontext, in welches der Punkt gezeichnet werden
    * soll
    */
   public void paint(Graphics g) {
      // Je nach Faktor ein entsprechend grosses schwarzes Oval zeichnen
      g.fillOval(getXCoord() * faktor, getYCoord() * faktor, faktor, faktor);
   }

   /**
    * Bewegt den Punkt zu den Koordinaten (x,y).
    * @param x X-Koordinate
    * @param y Y-Koordinate
    */
   public void movePoint(double x, double y) {
      this.x = x;
      this.y = y;
   }

   /**
    * Liefert einen String, der den Punktals SVG Tag repraesentiert.
    */
   public String toSVG() {
   	
		StringBuffer s = new StringBuffer();
		//Tag einleiten
		s.append("<circle cx=\""+ x + "\" cy=\""+ y + "\" r=\"1\" />");
		
		return s.toString();
		
   }
}
