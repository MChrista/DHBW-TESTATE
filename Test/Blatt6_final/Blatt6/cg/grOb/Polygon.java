package cg.grOb;

import java.awt.Graphics;
import java.util.Vector;

import cg.tools.Matrix;

/**
 * Ein grafisches Objekt, dass ein Polygon repraesentiert.
 * Die einzelnen Polygonpunkte sind in einem Vektor abgelegt.
 * 
 * @author Ralf Kunze
 * @version 2.0 (A 18.05.2004)
 */
public class Polygon extends GraphicObject {

   protected Vector pv; // Vektor mit Eckpunkten
   private int r; // Empfindlichkeit beim 
   // Schliessen d. Polygonzuges
   private boolean closed; // true falls geschlossener false sonst
   private boolean dashed = false;

   /**
    * Klassenkonstante fuer ein durchgezogenes Polygon
    */
   public static final boolean SOLID = false;

   /**
    * Klassenkonstante fuer ein gestricheltes Polygon
    */
   public static final boolean DASHED = true;

   /**
    * erzeugt ein Polygon mit Empfindlichkeit 5, Zoomfaktor 1, durchgezogenen Linien
    * @param p Startpunkt des Polygonzuges.
    */
   public Polygon() {
      super(1);
      r = 5;
      closed = false; // zunaechst nicht geschlossen
      pv = new Vector(10, 1);
   }

   /**
    * erzeugt ein Polygon mit Empfindlichkeit 5, Zoomfaktor 1, durchgezogenen Linien
    * und einem Startpunkt.
    * @param p Startpunkt des Polygonzuges.
    */
   public Polygon(Point p) {
      this(p, 5, 1);
   }

   /**
    * erzeugt ein Polygon mit waehlbarer Empfindlichkeit, Zoomfaktor 1, durchgezogenen Linien
    * und einem Startpunkt.
    * @param region Der User kann in einem 2*region x 2*region Pixel grossen
    * Bereich um den Startpunkt des Polygons klicken, um den Polygonzug 
    * zu schliessen.
    * @param p Startpunkt des Polygonzuges.
    */
   public Polygon(int region, Point p) {
      this(p, region, 1); // Vector erzeugen
   }

   /**
    * erzeugt ein Polygon mit waehlbarer Empfindlichkeit, Zoomfaktor 1
    * und einem Startpunkt.
    * @param region Der User kann in einem 2*region x 2*region Pixel grossen
    * Bereich um den Startpunkt des Polygons klicken, um den Polygonzug 
    * zu schliessen.
    * @param p Startpunkt des Polygonzuges.
    * @param dashed true: gestricheltes Polygon, false: durchgezogenes Polygon
    */
   public Polygon(int region, Point p, boolean dashed) {
      this(p, region, 1, dashed); // Vector erzeugen
   }

   /**
    * erzeugt ein Polygon mit waehlbarer Empfindlichkeit, waehlbarem Zoomfaktor, durchgezogenen Linien
    * und einem Startpunkt.
    * @param region Der User kann in einem 2*region x 2*region Pixel grossen
    * Bereich um den Startpunkt des Polygons klicken, um den Polygonzug 
    * zu schliessen.
    * @param faktor der initiale Zoomfaktor
    * @param p Startpunkt des Polygonzuges.
    */
   public Polygon(Point p, int region, int faktor) {
      super(faktor);
      r = region;
      closed = false; // zunaechst nicht geschlossen
      pv = new Vector(10, 1);
      append(p); // Starpunkt hinten anhaengen
   }

   /**
    * erzeugt ein Polygon mit waehlbarer Empfindlichkeit, waehlbarem Zoomfaktor
    * und einem Startpunkt.
    * @param region Der User kann in einem 2*region x 2*region Pixel grossen
    * Bereich um den Startpunkt des Polygons klicken, um den Polygonzug 
    * zu schliessen.
    * @param faktor der initiale Zoomfaktor
    * @param p Startpunkt des Polygonzuges.
    * @param dashed true: gestricheltes Polygon, false: durchgezogenes Polygon
    */
   public Polygon(Point p, int region, int faktor, boolean dashed) {
      super(faktor);
      r = region;
      closed = false; // zunaechst nicht geschlossen
      pv = new Vector(10, 1);
      append(p); // Starpunkt hinten anhaengen
      this.dashed = dashed;
   }

   /**
    * Setzt den Stil eines Polygons, SOLID oder DASHED
    */
   public void setStyle(boolean dashed) {
      this.dashed = dashed;
   }
   /**
    * Setzt den Vergroesserungsfaktor fuer die Darstellung.
    * @param f der neue Vergroesserungsfaktor
    */
   public void setFactor(int f) {
      super.setFactor(f);
      for (int i = 0; i < pv.size(); i++) {
         ((Point) pv.elementAt(i)).setFactor(f);
      }
   }

   /**
    * Haengt den uebergebenen Punkt hinten an die Punktliste an, 
    * sofern das Polygon noch nicht geschlossen ist.
    * @param p der an die Punktliste anzuhaengende Punkt
    */
   public void append(Point p) {
      if (!closed)
         pv.addElement(new Point(p));
   }

   /** 
    * Liefert den letzten Punkt des Polygons
    * @return der z. Zt. letzte Punkt des Polygons oder null, 
    * falls kein Punkt enthalten
    */
   public Point getLastPoint() {
      if (pv.size() > 0)
         return (Point) pv.elementAt(pv.size() - 1);
      return null;
   }

   /** 
    * Liefert den Punkt an der bezeichneten Stelle oder null, falls das 
    * Polygon weniger Punkte enthaelt.
    * @param n der n-te Punkt aus dem Polygon
    */
   public Point getPointAt(int n) {
      if ((n >= 0) && (n < pv.size())) {
         return (Point) pv.elementAt(n);
      } else {
         return null;
      }
   }

   /**
    * Liefert die Anzahl der Punkte im Polygon.
    * Der Startpunkt ist zweimal enthalten. einmal als Instanz, 
    * einmal als Referenz.
    */
   public int getLength() {
      return pv.size();
   }

   /** 
    * Schliesst den Polygonzug, indem der letzte Punkt in der Punktliste mit
    * dem Startpunkt verbunden wird.
    */
   public void close() {
      pv.addElement(pv.elementAt(0)); // 1. Punkt holen
      // und nochmal eintragen
      closed = true;
   }

   /**
    * Liefert true, wenn das Polygon geschlossen ist; false sonst.
    */
   public boolean isClosed() {
      return closed;
   }

   /**
    * Liefert true, wenn der uebergebene Punkt im Empfindlichkeitsbereich um den
    * Startpunkt herum liegt.
    * @param p der zu pruefende Punkt
    * @return true, wenn p in Empfindlichkeitsbereich um den Startpunkt
    * liegt; sonst false
    */
   public boolean isClosingPoint(Point p) {
      if (pv.size() < 3)
         return false;
      double x = ((Point) pv.elementAt(0)).x;
      double y = ((Point) pv.elementAt(0)).y;
      // Wenn p im 2r*2r grossen Bereich um Startpunkt liegt, wird true
      // geliefert
      return (((x - r <= p.x) && (x + r >= p.x)) && ((y - r <= p.y) && (y + r >= p.y)));
   }

   /**
   	* Clippt das Polygon am uebergebenen Rechteck nach der Idee von Sutherland
   	* und Hodgeman. Liefert false, wenn das Polygon komplett
   	* ausserhalb des Clipping-Rechtecks lag; true sonst.
   	*
   	* @param re das Clipping-Rechteck
   	* @return true, wenn das Polygon zumindest teilweise im Clippingrechteck lag,
   	* sonst false
   	*/
   public boolean clip(Rectangle re) {
      boolean s; // zeigt Sichtbarkeit an
      Point philf; // Hilfspunkt
      sutherland_hodgeman(re.getA().getXCoord(), LEFT); // linke Seite
      sutherland_hodgeman(re.getE().getYCoord(), BOTTOM); // untere Seite
      sutherland_hodgeman(re.getE().getXCoord(), RIGHT); // rechte Seite
      // wenn nach dem Clipping an der letzten Kante noch was zu sehen ist,
      // war das Polygon z.T. innerhalb des Clipping-Rechtecks.
      return sutherland_hodgeman(re.getA().getYCoord(), TOP); // obere Seite
   }

   /**
   	* Clippt das Polygon an einer Kante des Clipping-Rechtecks.
   	* @param value der fuer Kante edge interessante Wert in Pixeln
   	* @param edge gibt an, ob es sich um eine linke, rechte, obere oder untere
   	* Kante handelt
   	*/
   private boolean sutherland_hodgeman(int value, int edge) {
      int old; // durchlaeuft die alten Punkte
      Vector hilf = new Vector(pv.size(), 1); // Hilfsvektor
      Point s, i;
      Line l;

      for (old = 0; old < pv.size() - 1; old++) {
         s = (Point) pv.elementAt(old);
         if (s.onVisibleSide(value, edge)) {
            hilf.addElement(s);
         }

         l = new Line(s, (Point) pv.elementAt(old + 1), faktor);
         i = l.intersects(value, edge);
         if (i != null) {
            hilf.addElement(i);
         }
      }
      if (hilf.size() > 0) { // Falls Punkte gemerkt
         hilf.addElement(hilf.elementAt(0)); // 1. nochmal ans Ende
      }
      pv = hilf; // neuen Vektor merken
      return hilf.size() != 0; // wenn draussen: Vektor leer
   }

   /**
   	* Liefert true, wenn der Punkt p im Inneren des Polygons liegt.
   	* Liefert false, wenn der Punkt p im &Auml;u&szlig;eren des Polygons
   	* liegt.
   	*
   	* Es wird die Kreuzungszahlmethode mit h&auml;ufiger
   	* Schnittpunktberechnung verwendet, um die Entscheidung zu treffen.
   	*
   	* @param p der Testpunkt
   	* @return Flagge, ob der Punkt in diesem Polygon enthalten ist.
   	*/
   public boolean contains(Point p) {
      boolean inside = false; // bisher: 0 Kreuzungen
      // also: draussen
      int x1 = ((Point) (pv.elementAt(0))).getXCoord();
      // ersten Polygonpunkt
      int y1 = ((Point) (pv.elementAt(0))).getYCoord(); // holen

      // Liegt der Startpunkt der akt. Kante ueber dem Strahl?
      // Da Schnitte von Strahl und Polygonpunkten vermieden werden muessen,
      // tut man so als ob der Punkt infinitesimal ueber seiner y-Koordinate
      // laege. Deshalb heisst "ueber" hier auch "gleich"
      boolean startUeber = y1 >= p.y ? true : false;

      for (int i = 1; i < pv.size(); i++) {
         int x2 = ((Point) (pv.elementAt(i))).getXCoord();
         // Endpunkt dieser Kante
         int y2 = ((Point) (pv.elementAt(i))).getYCoord(); // holen
         boolean endUeber = y2 >= p.y ? true : false;
         // Endpunkt ueber Strahl?

         // Wenn die Kante die y-Koordinate des Strahls ueberstreicht, kann es
         // einen Schnittpunkt geben
         if (startUeber != endUeber) {
            // Schnittpunktpixel von Kante und Strahl berechnen
            int sx = (int) ((double) (p.y * (x2 - x1) - y1 * x2 + y2 * x1) / (double) (y2 - y1));

            if ((sx > p.x) || // Falls Schnittpunkt rechts
             ((sx == p.x) && !inside)) { // oder Schnittpunkt bei p und bisher draussen
               inside = !inside; // Kreuzung vermerken
            }
         }

         startUeber = endUeber; // Werte retten und zur
         y1 = y2; // naechsten Kante
         x1 = x2; // uebergehen
      }

      return inside; // Ergebnis zurueck
   }

   /**
    * Setzt die Transformations Matrix eines Objektes
    * @param transform Transformationsmatrix
    */
   public void setTransform(Matrix transform) {
      for (int i = 0; i < pv.size() - 1; i++) {
         if (transform != null) {
            ((Point) pv.elementAt(i)).setTransform(transform);
         }
      }
   }

   /**
    * Liefert eine Stringrepraesentation des Polygons.
    * @return die textuelle Repraesentation dieses Objekts
    */
   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("Polygon with Points at:\n");
      for (int i = 0; i < pv.size(); i++) {
         buf.append("Point Nr. " + i + ": " + pv.elementAt(i).toString() + "\n");
      }
      return buf.toString();
   }

   /**
    * Zeichnet das Polygon in den uebergebenen grafischen Kontext und fuellt
    * es gegebenenfalls.
    * @param g der grafische Kontext, in den dieses Objekt sich zeichnen soll
    */
   public void paint(Graphics g) {
      Line l;
      for (int i = 0; i < pv.size() - 1; i++) {
         l = new Line((Point) pv.elementAt(i), (Point) pv.elementAt(i + 1), faktor, dashed);
         l.paint(g);
      }
   }

   /**
    * Liefert eine Stringrepraesentation des Objektes als SVG zurueck.
    */
   public String toSVG() {
   	String s_points;
	StringBuffer s = new StringBuffer();
	//Stringbuffer zum erzeugen des Strings mit den Polygonpunkten
	StringBuffer s_pointsBuffer = new StringBuffer();
	
	//String mit den Punkt-Koordinaten des Polygons erzeugen
	for (int i = 0; i < pv.size(); i++){
		int x = ((Point)pv.get(i)).getXCoord();
		int y = ((Point)pv.get(i)).getYCoord();
		s_pointsBuffer.append(x + "," + y + " ");
	}
	s_points = s_pointsBuffer.toString();
	
	//Tag aufbauen
	s.append("<polygon points=\"" + s_points + "\" \n");
	s.append("   fill=\"blue\" stroke=\"black\" stroke-width=\"1\" />");
	return s.toString();
		
   }

}