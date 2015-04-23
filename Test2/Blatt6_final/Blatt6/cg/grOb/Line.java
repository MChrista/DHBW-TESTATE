package cg.grOb;

import java.awt.Graphics;

import cg.tools.Matrix;

/**
 * Klasse, die eine Linie repr&auml;sentiert. Eine Linie besteht aus zwei Punkten.
 * Diese Punkte dienen als Sart- und Endpunkt fuer den Bresenham Algorithmus.
 *
 * @version 1.0 (A 13.04.2004)
 * @author Ralf Kunze
 */
public class Line extends GraphicObject {
   protected Point a; // Anfangspunkt a
   protected Point e; // Endpunkt e
   private boolean dashed = false;
   public static final boolean DASHED = true;
   public static final boolean SOLID = false;

   /**
    * Defaultkonstruktor
    * Erzeugt eine Linie vom Ursprung zum Ursprung
    */
   public Line() {
      this(0, 0, 0, 0);
   }

   /**
    * Konstruktor zum erzeugen eines Linienobjektes
    *
    * @param xa x-Koordinate des Startpunktes
    * @param ya y-Koordinate des Startpunktes
    * @param xe x-Koordinate des Endpunktes
    * @param ye y-Koordinate des Endpunktes
    */
   public Line(double xa, double ya, double xe, double ye) {
      this(new Point(xa, ya), new Point(xe, ye));
   }

   /**
    * Konstruktor zum erzeugen eines Linienobjektes mit Zoomfaktor 1
    *
    * @param xa x-Koordinate des Startpunktes
    * @param ya y-Koordinate des Startpunktes
    * @param xe x-Koordinate des Endpunktes
    * @param ye y-Koordinate des Endpunktes
    * @param dashed true: gestrichelte Linie, false: durchgezogene Linie
    */
   public Line(double xa, double ya, double xe, double ye, boolean dashed) {
      this(new Point(xa, ya), new Point(xe, ye), 1, dashed);
   }

   /**
    * Konstruktor zum erzeugen eines Linienobjektes
    *
    * @param xa x-Koordinate des Startpunktes
    * @param ya y-Koordinate des Startpunktes
    * @param xe x-Koordinate des Endpunktes
    * @param ye y-Koordinate des Endpunktes
    * @param faktor der zu setzende Zoomfaktor
    */
   public Line(double xa, double ya, double xe, double ye, int faktor) {
      this(new Point(xa, ya), new Point(xe, ye), faktor);
   }

   /**
    * Konstruktor zum erzeugen eines Linienobjektes
    *
    * @param xa x-Koordinate des Startpunktes
    * @param ya y-Koordinate des Startpunktes
    * @param xe x-Koordinate des Endpunktes
    * @param ye y-Koordinate des Endpunktes
    * @param faktor Der zu setzende Zoomfaktor
    * @param dashed true: gestrichelte Linie, fals: durchgezogene Linie
    */
   public Line(double xa, double ya, double xe, double ye, int faktor, boolean dashed) {
      this(new Point(xa, ya), new Point(xe, ye), faktor, dashed);
   }

   /**
    * Konstruktor zum erzeugen eines Linienobjektes
    *
    * @param a Startpunktes
    * @param e Endpunktes
    * @param faktor Der zu setzende Zoomfaktor
    * @param dashed true: gestrichelte Linie, false: gurchgezogene Linie
    */
   public Line(Point a, Point e, int faktor, boolean dashed) {
      super(faktor);
      this.a = a;
      this.e = e;
      this.dashed = dashed;
   }

   /**
    * Konstruktor zum erzeugen eines durchgezogenen Linienobjektes
    *
    * @param a Startpunktes
    * @param e Endpunktes
    * @param faktor der zu setzende Zoomfaktor
    */
   public Line(Point a, Point e, int faktor) {
      this(a, e, faktor, false);
   }

   /**
    * Setzt den Zoomfaktor einer Linie
    * 
    * @param f Der positive Zoomfaktor
    */
   public void setFactor(int f) {
      if (faktor > 0) {
         faktor = f;
         a.setFactor(f);
         e.setFactor(f);
      }
   }

   /**
    * Konstruktor zum Erzeugen eines Linienobjektes
    *
    * @param a Startpunktes
    * @param e Endpunktes
    */
   public Line(Point a, Point e) {
      this(a, e, 1);
   }

   /**
    * Setzen des Startpunktes
    *
    * @param a der Startpunkt der Linie
    */
   public void setA(Point a) {
      this.a = a;
   }

   /**
    * Den Startpunkt abfragen
    *
    * @return liefert den Startpunkt der Linie
    */
   public Point getA() {
      return a;
   }

   /**
    * Setzen des Endpunktes
    *
    * @param e der Endpunkt der Linie
    */
   public void setE(Point e) {
      this.e = e;
   }

   /**
    * Den Endpunkt abfragen
    *
    * @return liefert den Endpunkt der Linie
    */
   public Point getE() {
      return e;
   }

   /**
    * Liefert den Schnittpunkt, wenn diese Linie die spezifizierte Kante
    * eines Clipping-Rechtecks schneidet; null sonst.
    * @param value der Wert der interessanten Kante in Pixeln
    * @param edge die interessante Kante
    * @return der Schnittpunkt, falls er existiert; null sonst
    */
   public Point intersects(int value, int edge) {
      boolean avis; // ist Startpunkt drinnen?
      boolean evis; // ist Endpunkt drinnen?
      double slope; // Steigung dieser Linie
      Point p; // Hilfspunkt

      avis = a.onVisibleSide(value, edge); // Startpunkt testen
      evis = e.onVisibleSide(value, edge); // Endpunkt testen

      if ((avis && evis) || (!avis && !evis)) { // kein Schnittpunkt
         return null;
      } else {
         p = new Point(a); // Hilfspunkt vorbereiten
         slope = (double) (e.y - a.y) / (double) (e.x - a.x); // Steigung berechnen
         if ((edge == TOP) || (edge == BOTTOM)) { // waagerechte Kante
            p.x = (int) ((value - a.y) / slope) + a.x;
            p.y = value;
         } else { // senkrechte Kante
            p.x = value;
            p.y = (int) ((value - a.x) * slope) + a.y;
         }
      } // Schnittpunkt existiert

      return p; // Hilfspunkt zurueck
   }

   /**
    * Clippt die Linie nach der Idee von Cohen & Sutherland
    * an dem uebergebenen Rechteck. Die Koordinaten der Linie
    * sind anschliessend unwiederbringlich veraendert. Falls die Linie ganz
    * ausserhalb des Rechtecks ist, wird false zurueckgegeben, sonst true.
    *
    * @param re das Clipping-Rechteck
    * @return true, wenn die Linie mindestens teilweise im Clippingrechteck
    * lag.
    */
   public boolean clip(Rectangle re) {
      boolean finite_slope; // true wenn Linie nicht senkrecht
      double slope = 0.0; // Steigung
      byte C, C1, C2; // Variablen fuer RegionCodes
      Point Q = new Point(); // Hilfspunkt
      finite_slope = (a.x != e.x); // Laeuft Linie senkrecht?

      if (finite_slope) { // Falls endl. Steigung: berechnen
         slope = (double) (e.y - a.y) / (double) (e.x - a.x);
      }
      C1 = a.getRegionCode(re); // RegionCode Startpunkt
      C2 = e.getRegionCode(re); // RegionCode Endpunkt

      while ((C1 != EMPTY) || (C2 != EMPTY)) { // mind. 1 Endpunkt noch aussen
         if ((C1 & C2) != EMPTY) { // a und e auf gleicher Seite aussen
            return false; // Ja: Linie nicht mehr sichtbar
         } else { // Sonst: a oder e drinnen
            C = C1 == EMPTY ? C2 : C1; // C ist aussen; Schnittpunkt

            if ((C & LEFT) != EMPTY) { // linke Fensterkante
               Q.x = re.getXMin(); // x-Wert Schnittpunkt
               Q.y = (int) ((Q.x - a.x) * slope + a.y);
            } else if ((C & RIGHT) != EMPTY) { // rechte Fensterkante
               Q.x = re.getXMax(); // x-Wert Schnittpunkt
               Q.y = (int) ((Q.x - a.x) * slope + a.y);
            } else if ((C & BOTTOM) != EMPTY) { // untere Fensterkante
               Q.y = re.getYMax(); // y-Wert Schnittpunkt
               if (finite_slope) { // Falls endliche Steigung
                  Q.x = (int) ((Q.y - a.y) / slope + a.x); // X-Wert berechnen
               } else { // Sonst
                  Q.x = a.x; // x-Wert uebernehmen
               }
            } else if ((C & TOP) != EMPTY) { // obere Fensterkante
               Q.y = re.getYMin(); // y-Wert Schnittpunkt
               if (finite_slope) { // Falls endliche Steigung
                  Q.x = (int) ((Q.y - a.y) / slope + a.x); // X-Wert berechnen
               } else { // Sonst
                  Q.x = a.x; // x-Wert uebernehmen
               }
            }

            if (C == C1) { // Wer war draussen?
               a.x = Q.x;
               a.y = Q.y;
               C1 = a.getRegionCode(re);
            } else {
               e.x = Q.x;
               e.y = Q.y;
               C2 = e.getRegionCode(re);
            }
         } // nicht beide auf derselben Seite ausserhalb
      } // mind. 1 Endpunkt noch aussen
      return true;
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
      return (((a.x - r <= p.x) && (a.x + r >= p.x)) && ((a.y - r <= p.y) && (a.y + r >= p.y)))
         || (((e.x - r <= p.x) && (e.x + r >= p.x)) && ((e.y - r <= p.y) && (e.y + r >= p.y)));
   }

   /**
    * Setzt die Transformations Matrix eines Objektes
    * @param transform Transformationsmatrix
    */
   public void setTransform(Matrix transform) {
      if (transform != null) {
         a.setTransform(transform);
         e.setTransform(transform);
      }
   }

   /**
    * toString Methode der Linienklasse
    * 
    * @return Liefert eine Beschreibung des Linienobjektes
    */
   public String toString() {
      return ("Line from " + a.toString() + " to " + e.toString() + " Faktor: " + faktor);
   }

   /**
    * Paint-Methode der Linienklasse
    *
    * @param g der Graphikkontext, in den das Objekt gezeichnet werden soll
    */
   public void paint(Graphics g) {
      if (!dashed)
         Bresenham.line(a, e, g, faktor);
      else
         Bresenham.dashedLine(a, e, g, faktor);
   }
   
   /**
    * Liefert einen String, der die Linie als SVG Tag repraesentiert.
    */
   public String toSVG() {
   	 StringBuffer s = new StringBuffer();
   	 //Tags schreiben
	 s.append("<line x1=\""+ a.x + "\" y1=\""+ a.y + "\" x2=\"" + e.x + "\" y2=\"" + e.y + "\"\n");
	 s.append("   stroke=\"black\" stroke-width=\"1px\" />\n");
	
	 return s.toString();	
   }
}
