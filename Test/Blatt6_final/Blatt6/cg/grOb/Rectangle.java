package cg.grOb;

import java.awt.Graphics;

import cg.tools.DitherMatrix;
import cg.tools.Matrix;

import java.awt.Color;

/**
 * Diese Klasse repr&auml;sentiert ein Rechteck.
 * Ein Rechteck wird durch zwei Punkte definiert, zum Einen die
 * linke obere Ecke und zum Anderen die rechte untere Ecke.
 *
 * @version 1.2 (A 19.5.2004)
 * @author Ralf Kunze
 */
public class Rectangle extends GraphicObject {

   private boolean dashed = false;
   private double xmin, xmax, ymin, ymax;

   protected Point a; // Anfangspunkt a
   protected Point e; // Endpunkt e

   public static final boolean DASHED = true;
   public static final boolean SOLID = false;

   /**
    * Default Konstruktor, erstellt ein Rechteck im Ursprung.
    * Breite = 0; Hoehe = 0
    */
   public Rectangle() {
      this(0, 0, 0, 0);
   }

   /**
    * Konstrukter zur Erzeugung eines Rechteck-Objektes.
    *
    * @param xa X-Koordinate der linken oberen Ecke
    * @param ya Y-Koordinate der linken oberen Ecke
    * @param xe X-Koordinate der rechten unteren Ecke
    * @param ye Y-Koordinate der rechten unteren Ecke
    */
   public Rectangle(int xa, int ya, int xe, int ye) {
      this(new Point(xa, ya), new Point(xe, ye));
   }

   /**
    * Konstrukter zur Erzeugung eines Rechteck-Objektes.
    *
    * @param xa X-Koordinate der linken oberen Ecke
    * @param ya Y-Koordinate der linken oberen Ecke
    * @param xe X-Koordinate der rechten unteren Ecke
    * @param ye Y-Koordinate der rechten unteren Ecke
    * @param dashed true: gestricheltes Rechteck, false: durchgezogenes Rechteck
    */
   public Rectangle(int xa, int ya, int xe, int ye, boolean dashed) {
      this(new Point(xa, ya), new Point(xe, ye), dashed);
   }

   /**
    * Konstrukter zur Erzeugung eines Rechteck-Objektes.
    *
    * @param xa X-Koordinate der linken oberen Ecke
    * @param ya Y-Koordinate der linken oberen Ecke
    * @param xe X-Koordinate der rechten unteren Ecke
    * @param ye Y-Koordinate der rechten unteren Ecke
    * @param faktor Zoomfaktor des Rechtecks
    */
   public Rectangle(int xa, int ya, int xe, int ye, int faktor) {
      this(new Point(xa, ya), new Point(xe, ye), faktor);
   }

   /**
    * Konstrukter zur Erzeugung eines Rechteck-Objektes.
    *
    * @param xa X-Koordinate der linken oberen Ecke
    * @param ya Y-Koordinate der linken oberen Ecke
    * @param xe X-Koordinate der rechten unteren Ecke
    * @param ye Y-Koordinate der rechten unteren Ecke
    * @param faktor Zoomfaktor des Rechtecks
    * @param dashed true: gestricheltes Rechteck, false: durchgezogenes Rechteck
    */
   public Rectangle(int xa, int ya, int xe, int ye, int faktor, boolean dashed) {
      this(new Point(xa, ya), new Point(xe, ye), faktor, dashed);
   }

   /**
    * Konstrukter zur Erzeugung eines Rechteck-Objektes.
    *
    * @param xa X-Koordinate der linken oberen Ecke
    * @param ya Y-Koordinate der linken oberen Ecke
    * @param xe X-Koordinate der rechten unteren Ecke
    * @param ye Y-Koordinate der rechten unteren Ecke
    * @param faktor Zoomfaktor des Rechtecks
    * @param dashed true: gestricheltes Rechteck, false: durchgezogenes Rechteck
    * @param dm DitherMatrix, die das Rechteck mit einem Grauwert versieht
    * @param grey Der zur DitherMatrix gehoerige Grauwert.
    */
   public Rectangle(int xa, int ya, int xe, int ye, int faktor, boolean dashed, DitherMatrix dm, int grey) {
      this(new Point(xa, ya), new Point(xe, ye), faktor, dashed, dm, grey);
   }

   /**
    * Konstrukter zur Erzeugung eines Rechteck-Objektes.
    *
    * @param a Punkt in der linken oberen Ecke
    * @param e Punkt in der rechten unteren Ecke
    * @param faktor Zoomfaktor des Rechtecks
    * @param dm DitherMatrix, die das Rechteck mit einem Grauwert versieht
    * @param grey Der zur DitherMatrix gehoerige Grauwert.
    */
   public Rectangle(Point a, Point e, int faktor, DitherMatrix dm, int grey) {
      super(faktor);
      this.a = a;
      this.e = e;
      this.dm = dm;
      this.grey = grey;
      sortCorners();
   }

   /**
    * Konstrukter zur Erzeugung eines Rechteck-Objektes.
    *
    * @param a Punkt in der linken oberen Ecke
    * @param e Punkt in der rechten unteren Ecke
    * @param faktor Zoomfaktor des Rechtecks
    */
   public Rectangle(Point a, Point e, int faktor) {
      this(a, e, faktor, null, 0);
   }

   /**
    * Konstrukter zur Erzeugung eines Rechteck-Objektes.
    *
    * @param a Punkt in der linken oberen Ecke
    * @param e Punkt in der rechten unteren Ecke
    * @param faktor Zoomfaktor des Rechtecks
    */
   public Rectangle(Point a, Point e, int faktor, boolean dashed) {
      this(a, e, faktor, dashed, null, 0);
   }

   /**
   	 * Konstrukter zur Erzeugung eines Rechteck-Objektes.
   	 *
   	 * @param a Punkt in der linken oberen Ecke
   	 * @param e Punkt in der rechten unteren Ecke
   	 * @param faktor Zoomfaktor des Rechtecks
    * @param dashed Rand des Rechtecks durchgezogen oder nicht. 
   	 * @param dm DitherMatrix, die das Rechteck mit einem Grauwert versieht
    * @param grey Der zur DitherMatrix gehoerige Grauwert.
   	 */
   public Rectangle(Point a, Point e, int faktor, boolean dashed, DitherMatrix dm, int grey) {
      this.a = a;
      this.e = e;
      this.faktor = faktor;
      this.dashed = dashed;
      this.dm = dm;
      this.grey = grey;
      if (!dashed)
         sortCorners();
   }

   /**
    * Konstrukter zur Erzeugung eines Rechteck-Objektes.
    *
    * @param a Punkt in der linken oberen Ecke
    * @param e Punkt in der rechten unteren Ecke
    * @param dm DitherMatrix, die das Rechteck mit einem Grauwert versieht
    * @param grey Der zur DitherMatrix gehoerige Grauwert.
    */
   public Rectangle(Point a, Point e, DitherMatrix dm, int grey) {
      this(a, e, 1, SOLID, dm, grey);
   }

   /**
    * Konstrukter zur Erzeugung eines Rechteck-Objektes.
    *
    * @param a Punkt in der linken oberen Ecke
    * @param e Punkt in der rechten unteren Ecke
    */
   public Rectangle(Point a, Point e) {
      this(a, e, 1);
   }

   /**
    * Konstrukter zur Erzeugung eines Rechteck-Objektes.
    *
    * @param a Punkt in der linken oberen Ecke
    * @param e Punkt in der rechten unteren Ecke
    * @param dashed true:gestrichelte Linie, false: durchgezogene Linie
    * @param dm DitherMatrix, die das Rechteck mit einem Grauwert versieht
    * @param grey Der zur DitherMatrix gehoerige Grauwert.
    */
   public Rectangle(Point a, Point e, boolean dashed, DitherMatrix dm, int grey) {
      this(a, e, 1, dashed, dm, grey);
   }

   /**
    * Konstrukter zur Erzeugung eines Rechteck-Objektes.
    *
    * @param a Punkt in der linken oberen Ecke
    * @param e Punkt in der rechten unteren Ecke
    * @param dashed true:gestrichelte Linie, false: durchgezogene Linie
    */
   public Rectangle(Point a, Point e, boolean dashed) {
      this(a, e, 1, dashed);
   }

   /**
    * Setzt den Faktor des Rechteckes.
    *
    * @param f Zu setzender Zoomfaktor
    */
   public void setFactor(int f) {
      faktor = f;
      a.setFactor(f);
      e.setFactor(f);
   }

   /**
    * Setzen des Punktes in der linken oberen Ecke.
    *
    * @param a linker oberer Punkt des Rechtecks
    */
   public void setA(Point a) {
      this.a = a;
      sortCorners();
   }

   /**
    * liefert Eckpunkt 
    *
    * @return liefert den linken oberen Eckpunkt
    */
   public Point getA() {
      return a;
   }

   /**
    * setzen der rechten unteren Ecke
    *
    * @param e rechter unterer Punkt des Rechtecks
    */
   public void setE(Point e) {
      this.e = e;
      sortCorners();
   }

   /**
    * liefert Eckpunkt 
    *
    * @return liefert den rechten unteren Eckpunkt
    */
   public Point getE() {
      return e;
   }

   /**
   	* Liefert die kleinste x-Koordinate dieses Rechtecks
   	* @return die x-Koordinate in Pixeln
   	*/
   public int getXMin() {
      return (int) (0.5 + xmin);
   }

   /**
   	* Liefert die groesste x-Koordinate dieses Rechtecks
   	* @return die x-Koordinate in Pixeln
   	*/
   public int getXMax() {
      return (int) (0.5 + xmax);
   }

   /**
   	* Liefert die kleinste y-Koordinate dieses Rechtecks
   	* @return die y-Koordinate in Pixeln
   	*/
   public int getYMin() {
      return (int) (0.5 + ymin);
   }

   /**
   	* Liefert die groesste y-Koordinate dieses Rechtecks
   	* @return die y-Koordinate in Pixeln
   	*/
   public int getYMax() {
      return (int) (0.5 + ymax);
   }

   /**
    * toString
    *
    * @return liefert eine Stringrepr&auml;sentation eines Rechteck Objektes
    */
   public String toString() {
      return ("Rectangle from " + a.toString() + " to " + e.toString());
   }

   /**
    * Zeichnet das Rechteck in einen grafischen Kontext
    *
    * @param g der grafische Kontext in den das Rechteck gezeichnet wird
    */
   public void paint(Graphics g) {
      if (dashed)
         Bresenham.dashedRectangle(this, g);
      else
         Bresenham.rectangle(this, g);

      if (dm != null && grey != 0) { // ist Rechteck zu fuellen?
         Point p;
         sortCorners();
         for (int i = getXMin(); i <= getXMax(); i++) { // Fuer alle Spalten
            for (int j = getYMin(); j <= getYMax(); j++) { // Fuer alle Zeilen
               if (dm.kleinerSchwelle(p = new Point(i, j, faktor), grey)) {
                  // Muss Punkt gesetzt werden?
                  p.paint(g);
               }
            } // Zeilen
         } // Spalten
      } // Rechteck fuellen?

   }

   /** Sortiert die extremen x- und y-Werte dieses Rechtecks */
   private void sortCorners() {

      if (a.x < e.x) { // x-Werte sortieren
         xmin = a.x;
         xmax = e.x;
      } else {
         xmin = e.x;
         xmax = a.x;
      }

      if (a.y < e.y) { // y-Werte sortieren
         ymin = a.y;
         ymax = e.y;
      } else {
         ymin = e.y;
         ymax = a.y;
      }

      a.x = xmin; // Jetzt Ecken sichern
      a.y = ymin;
      e.x = xmax;
      e.y = ymax;
   }

   /**
    * Clippt das Rechteck am uebergebenen Rechteck. Liefert true, wenn dieses
    * Objekt wenigstens z.T. innerhalb des Clipping-Rechtecks lag; sonst false.
    *
    * @param re das Clipping-Rechteck
    * @return true, wenn das Rechteck zumindest teilweise im
    * Clippingrechteck lag, sonst false
    */
   public boolean clip(Rectangle re) {
      // Idee: Polygon mit vier Punkten erzeugen; clippen und Punkte 0 und 2
      // wieder als a und e verwenden.
      sortCorners();
      Polygon poly = new Polygon(new Point(xmin, ymin, faktor));
      poly.append(new Point(xmax, ymin, faktor));
      poly.append(new Point(xmax, ymax, faktor));
      poly.append(new Point(xmin, ymax, faktor));
      poly.close();

      if (poly.clip(re)) { // Polygon soll sich clippen
         a = new Point(poly.getPointAt(0)); // falls weiterhin sichtbar
         e = new Point(poly.getPointAt(2)); // Eckpunkte holen
         sortCorners(); // sortieren
         return true;
      } else { // jetzt nicht mehr sichtbar
         return false;
      }
   }

   /**
   	* Liefert true, wenn der Punkt p im Inneren oder auf dem oberen Rand
   	* oder auf dem linken Rand dieses Rechtecks liegt.
   	* Liefert false, wenn der Punkt p auf dem unteren oder dem rechten Rand
   	* dieses Rechtecks liegt.
   	*
   	* @param p der Testpunkt
   	* @return Flagge, ob der Punkt in diesem Rechteck enthalten ist.
   	*/
   public boolean contains(Point p) {
      return (((p.x >= xmin) && (p.x < xmax)) && ((p.y >= ymin) && (p.y < ymax)));
   }

   /**
    * Setzt die Transformations Matrix eines Objektes
    * @param transform Transformationsmatrix
    */
   public void setTransform(Matrix transform) {
      if (transform != null) {
         a.setTransform(transform);
         e.setTransform(transform);
         sortCorners();
      }
   }

   /**
    * Liefert einen String, der das Rechteck als SVG Tag repraesentiert.
    */
   public String toSVG() {
   	//Breite und Höhe des Rechtecks berechnen
   	int w = getXMax() - getXMin();
   	int h = getYMax() - getYMin();
	
	//Rechteck standardmäßig mit weiß füllen
	String f_color = "ffffff";
	//Evtl. Grauwert berechnen
	if (grey > 0){
		Color c = new Color(255 - grey, 255 - grey, 255 - grey);
		f_color = Integer.toHexString(c.getRGB());
		f_color = f_color.substring(2);
    }
    //Stringbuffer für die Tags	
	StringBuffer s = new StringBuffer();
	//Tag einleiten
	s.append("<rect x=\""+ getXMin() + "\" y=\""+ getYMin() + "\" width=\"" + w + "\" height=\"" + h + "\"\n");
	s.append("   fill=\"#"+ f_color +"\" stroke=\"black\" stroke-width=\"1px\" />\n");
	//String zurückgeben
	return s.toString();
   }
   
}
