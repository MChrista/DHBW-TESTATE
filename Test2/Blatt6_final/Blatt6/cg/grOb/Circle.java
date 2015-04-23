package cg.grOb;

import java.awt.Graphics;
import cg.tools.Matrix;

/**
 * Circle Klasse, die einen Kreis mit dem bresenham-Algorithmus zeichen
 * kann. Ein Kreis besteht aus einem Mittelpunkt und einem Radius.
 *
 * @version 1.2 (A 19.05.2004)
 * @author Ralf Kunze
 */
public class Circle extends GraphicObject {
    protected Point m;                     // Mittelpunkt m
    protected int r;                       // Radius r
    private boolean dashed = false;
    public static final boolean DASHED = true;
    public static final boolean SOLID = false;
    public static final boolean FILLED = true;
    public static final boolean EMPTY = false;
    private boolean filled = false;

    /**
     * Defaultkonstruktor, der einen Kreis im Ursprung mit Radius=1 und
     * Faktor=1 erzeugt.
     */
    public Circle() {
        this(0, 0, 1, 1);
    }

    /**
     * Konstruktor, der einen Kreis aus dem Koordinatenpaar des
     * Mittelpunktes, dem Radius und dem Zoomfaktor erstellt.
     *
     * @param x X-Koordinate des Mittelpunktes
     * @param y Y-Koordinate des Mittelpunktes
     * @param r Radius des Kreises
     * @param faktor Zoomfaktor des Kreises
     */
    public Circle(int x, int y, int r, int faktor) {
        this(new Point(x, y), r, faktor);
    }

    /**
     * Konstruktor, der einen Kreis aus dem Koordinatenpaar des
     * Mittelpunktes, dem Radius und dem Zoomfaktor erstellt.
     *
     * @param x X-Koordinate des Mittelpunktes
     * @param y Y-Koordinate des Mittelpunktes
     * @param r Radius des Kreises
     * @param faktor Zoomfaktor des Kreises
     * @param dashed true: gestrichelter Kreis, false: durchgezogener Kreis
     */
    public Circle(int x, int y, int r, int faktor, boolean dashed) {
        this(new Point(x, y), r, faktor, dashed);
    }

    /**
     * Konstruktor, der aus einem Objekt Point, dem Radius und dem Zoomfaktor
     * einen Kreis erzeugt.
     *
     * @param m Mittelpunkt des Kreises
     * @param r Radius des Kreises
     * @param faktor Zoomfaktor des Kreises
     */
    public Circle(Point m, int r, int faktor) {
        super(faktor);
        this.m = m;
        this.r = r;
    }

    /**
     * Konstruktor, der aus einem Objekt Point, dem Radius und dem Zoomfaktor
     * einen Kreis erzeugt.
     *
     * @param m Mittelpunkt des Kreises
     * @param r Radius des Kreises
     * @param faktor Zoomfaktor des Kreises
     */
    public Circle(Point m, int r, int faktor, boolean dashed) {
        this(m, r, faktor);
        this.dashed = dashed;
    }

    /**
     * Dieser Konstruktor erzeugt einen Kreis um einen Mittelpunkt mit dem
     * Radius r und dem Default Zoomfaktor 1
     *
     * @param m Mittelpunkt des Kreises
     * @param r Radius des Kreises
     */
    public Circle(Point m, int r) {
        this(m, r, 1);
    }

    /**
     * Dieser Konstruktor erzeugt einen Kreis um einen Mittelpunkt mit dem
     * Radius r und dem Default Zoomfaktor 1
     *
     * @param m Mittelpunkt des Kreises
     * @param r Radius des Kreises
     */
    public Circle(Point m, int r, boolean dashed) {
        this(m, r, 1, dashed);
    }

    public void setFillStyle(boolean filled) {
        this.filled = filled;
    }
    /**
     * Setzt den Zoomfaktor des Kreises und des zugehoerigen Mittelpunktes.
     *
     * @param f Zu setzender Zoomfaktor
     */
    public void setFactor(int f) {
        faktor = f;
        m.setFactor(f);
    }

    /**
     * Statische Methode zur Berechnung des Radius unter zuhilfenahme des
     * Mittelpunktes und einem Randpunkt.
     *
     * @param m Mittelpunkt des Kreises
     * @param e Punkt auf dem Kreisrand
     */
    public static int getRadius(Point m, Point e) {
        double xdiff = Math.abs(m.getXCoord() - e.getXCoord());
        double ydiff = Math.abs(m.getYCoord() - e.getYCoord());
        // Da casting nach int alle Nachkommastellen abschneidet, wird vorher
        // soviel addiert, dass das Ergbnis so ist, als waere gerundet worden
        return (int) (0.5 + Math.sqrt(xdiff * xdiff + ydiff * ydiff));

    }

    /**
     * Liefert den Mittelpunkt des Kreises
     *
     * @return Mittelpunkt des Kreises
     */
    public Point getMiddle() {
        return m;
    }

    /**
     * setzen der X-Koordinate des Mittelpunktes
     *
     * @param x X-Koordinate des Mittelpunktes
     */
    public void setX(int x) {
        m.x = x;
    }

    /**
     * liefert die X-Koordinate des Mittelpunktes
     *
     * @return X-Koordinate des Mittelpunktes
     */
    public int getXCoord() {
        return m.getXCoord();
    }

    /**
     * setzen der Y-Koordinate des Mittelpunktes
     *
     * @param y Y-Koordinate des Mittelpunktes
     */
    public void setY(int y) {
        m.y = y;
    }

    /**
     * liefert die Y-Koordinate des Mittelpunktes
     *
     * @return Y-Koordinate des Mittelpunktes
     */
    public int getYCoord() {
        return m.getYCoord();
    }

    /**
     * setzen des Radius des Mittelpunktes
     *
     * @param r Radius des Mittelpunktes
     */
    public void setRadius(int r) {
        this.r = r;
    }

    /**
     * liefert den Radius des Mittelpunktes
     *
     * @return Radius des Mittelpunktes
     */
    public int getRadius() {
        return r;
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
	  double dx = m.x - p.x;
	  double dy = m.y - p.y;
	  return (dx*dx + dy*dy <= r*r);
	}
   
   /**
    * Setzt die Transformations Matrix eines Objektes
    * @param transform Transformationsmatrix
    */
   public void setTransform(Matrix transform) {
         if (transform != null) {
            m.setTransform(transform);
         }
   }
   
    /**
     * Liefert eine Stringrepraesentation eines Circle-Objektes
     *
     * @return Circle-String
     */
    public String toString() {
        return (
            "Circle at "
                + m.toString()
                + " with radius "
                + Integer.toString(r));
    }

    /**
     * Den Kreis in einen grafischen Kontext zeichnen
     *
     * @param g der grafische Kontext in den der Kreis gezeichnet werden soll
     */
    public void paint(Graphics g) {
        if (!dashed)
            if (filled)
                Bresenham.filledCircle(m, r, g, faktor);
            else
                Bresenham.circle(m, r, g, faktor);
        else
            Bresenham.dashedCircle(this, g);
    }
    
    /**
     * toSVG liefert eine SVG Darstellung des Kreises.
     * Der Kreis wird Gruen gefuellt.
     * @return Kreis als SVG-Tag
     */
    public String toSVG() {
    	//feststellen, ob der Kreis gefüllt ist
    	String f_color;
    	if (filled) f_color = "lightgreen"; else f_color="white";
    	
		StringBuffer s = new StringBuffer();
		//Tag einleiten
		s.append("<circle cx=\""+ m.x + "\" cy=\""+ m.y + "\" r=\"" + r+"\"\n");
		s.append("   fill=\""+f_color+"\" stroke=\"black\" stroke-width=\"1px\" />\n");
		//String zurückgeben
		return s.toString();
    }
}
