package cg.grOb;

import java.awt.Graphics;

import javax.swing.JComponent;

import cg.tools.DitherMatrix;
import cg.tools.Matrix;


/**
 * Basisklasse f&uuml;r alle Grafikobjekte der draw2D Applikation
 *
 * @version 1.1 (A 26.04.2004)
 * @author Ralf Kunze
 */
public abstract class GraphicObject extends JComponent {
    
    /**
     * Zoomfaktor eines GraphicObjects
     */
    protected int faktor;

    /**
     * Klassenkonstante fuer Zentrum
     */
    protected static final byte EMPTY = 0;

    /**
     * Klassenkonstante fuer Links
     */
    protected static final byte LEFT = 1;

    /**
     * Klassenkonstante fuer Rechte
     */
    protected static final byte RIGHT = 2;

    /**
     * Klassenkonstante fuer Unten
     */
    protected static final byte BOTTOM = 4;

    /**
     * Klassenkonstante fuer Oben
     */
    protected static final byte TOP = 8;

    /**
     * Dither-Matrix des Objectes
     */
    protected DitherMatrix dm;

    /**
     * Grauwert, mit dem dieses Objekt gefuellt wird, wenn es sich zeichnet
     */
    protected int grey;

    /**
     * Defaultkonstruktor
     * Der Zoomfaktor wird auf den Wert 1 gesetzt
     */
    public GraphicObject() {
        this(1);
    }

    /**
     * Konstruktor, der es erm&ouml;glicht den Zoomfaktor zu setzen
     *
     * @param faktor zu setzender Zoomfaktor
     */
    public GraphicObject(int faktor) {
        this.faktor = faktor;
    }

    /**
     * Methode zum setzen des Zoomfaktors
     *
     * @param f der Zoomfaktor
     */
    public void setFactor(int f) {
        faktor = (f >= 1) ? f : 1;
    }

    /**
     * Methode zum setzen des Grauwertes
     *
     * @param g Der Grauwert
     */
    public void setGreyValue(int g) {
        grey = (g >= 0) ? g : 0;
    }

    /**
     * Liefert den aktuellen Grauwert zurueck
     *
     * @return Der Aktuelle Grauwert des Objektes
     */
    public int getGreyValue() {
        return grey;
    }

    /**
     * Setzt die DitherMatrix des grafischen Objektes.
     * @param dm  die zu setzende DitherMatrix
     */
    public void setDitherMatrix(DitherMatrix dm) {
        this.dm = dm;
    }

    /**
     * Liefert die DitherMatrix des grafischen Objektes.
     * @return die DitherMatrix
     */
    public DitherMatrix getDitherMatrix() {
        return dm;
    }

    /**
     * Liefert den Zoomfaktor des Objektes
     *
     * @return der Zoomfaktor
     */
    public int getFactor() {
        return faktor;
    }

    /**
     * Clippt das Objekt an dem uebergebenen Polygon. Liefert false, falls das
     * Objekt anschliessend nicht mehr zu sehen ist; true sonst.
     * Diese Methode liefert zur Zeit immer true. Sie muss von den Subklassen
     * ueberschrieben werden.
     * @param re das Clipping-Rechteck
     * @return true: wenn das Objekt noch zu sehen ist; false: wenn alles weggeclippt wurde
     */
    public boolean clip(Rectangle re) {
        return true;
    };
    
	/**
	 * Liefert true, falls der uebergebene Punkt innerhalb dieses grafischen
	 * Objekts liegt; false sonst.
	 * Der Rand des Objekts gehoert NICHT zum Inneren!.
	 * Muss von denjenigen Klassen ueberschrieben werden, die Punkte enthalten
	 * koennen; also ein echtes Inneres haben.
	 * Da diese Superklasse kein Inneres hat, liefert die Methode immer false.
	 * @param p der zu testende Pixel
	 */
	public boolean contains(Point p) {
	  return false;                                // In dieser Klasse immer!
	}

    /**
     * Setzt die Transformations Matrix eines Objektes
     * @param m Transformationsmatrix
     */
	public abstract void setTransform(Matrix m);

    /**
     * toString Methode eines Grafik Objektes
     *
     * @return der aktuell gesetzte Zoomfaktor
     */
    public String toString() {
        return ("GaphicObject [factor=" + getFactor() + "]");
    }

    /**
     * Jedes grafische Objekt muss sich zeichnen koennen.
     *
     * @param g der grafische Kontext in dem gezeichnet wird.
     */
    public abstract void paint(Graphics g);
    
    /**
     * Jedes Objekt soll sich darum kuemmern, dass es sich als SVG ausgeben kann. 
     * @return String mit einer SVG Repraesentation des betreffenden Objektes.
     */
    public abstract String toSVG();
}