package cg.draw2D;

import java.util.Observable;
import java.util.Vector;


import javax.swing.JComponent;

import cg.controls.DrawingPane;
import cg.grOb.GraphicObject;
import cg.grOb.Rectangle;
import cg.grOb.Polygon;
import cg.grOb.Point;
import cg.grOb.Line;
import cg.grOb.Interpol;
import cg.grOb.Fractal;

import cg.tools.DitherMatrix;
import cg.tools.Resetable;
import cg.tools.MatrixManager;

/**
 * Das Model der Draw2D Applikation.
 * Es verwaltet alle graphischen Objekte und informiert seine Observer
 * &uuml;ber &Auml;nderungen (ein neues Objekt, &Auml;nderung des
 * Zoomfaktors, ...)
 *
 * @version 1.5 (A 18.05.2004)
 * @author Ralf Kunze
 */
public class Model extends Observable implements Resetable {
   private Vector v;
   private int faktor;
   private DitherMatrix[] dm;
   private int dm_sel;
   private int grey = 0;

   private Fractal fractal = null;
   private Interpol curve = new Interpol(this);

   private Line xachse = new Line(new Point(0, View.HEIGHT / 2), new Point(View.WIDTH, View.HEIGHT / 2), 1);
   private Line yachse = new Line(new Point(View.WIDTH / 2, 0), new Point(View.WIDTH / 2, View.HEIGHT), 1);

   public final static int DITHERDIM = 4;

   /**
    * Klassenkonstante, die den groessten einstellbaren Grauwert angibt.
    * Der groesste einstellbare Grauwert (=schwarz) ist (2 hoch 2*DITHERDIM)
    * Der kleinste sinnvolle Grauwert ist 0 (=weiss).
    */
   final static int MAX_GRAUWERT = (int) Math.pow(2, 2 * DITHERDIM);

   /**
    * Konstruktor des Models.
    * Setzt den initialen Zoomfaktor auf 1.
    */
   public Model() {
      v = new Vector(10, 5); // Vector: Groesse 10, Inc 5
      setFactor(1);
   }

   /**
    * Dieser Konstruktor erzeugt ein neues Model und es
    * werden verschiedenen Dithermatrizen uebergeben.
    * @param dm Array mit Dithermatrizen
    */
   public Model(DitherMatrix[] dm) {
      this();
      this.dm = dm;
      if (dm.length > 0)
         dm_sel = 0;
      else
         dm = null;
   }

   /**
    *  Mache dem Model bekannt, welches Ditherverfahren gerade aktuell ist
    * @param sel Indes des Ditherverfahrens
    * @return true: Falls Verfahren gesetzt werden kann; false: sonst
    */
   public boolean setDitherMode(int sel) {
      if (sel >= 0 && sel < dm.length) {
         dm_sel = sel;
         return true;
      }
      return false;
   }

   /**
    * Liefert die aktuell gesetzte Dithermatrix
    * @return Die aktuelle Dithermatrix
    */
   public DitherMatrix getDitherMode() {
      if (dm != null) {
         return dm[dm_sel];
      }
      return null;
   }

   /**
    * diese Methode setzt den Grauwert fuer alle registrierten Objekte im
    * Model und benachrichtigt seine Observer
    *
    * @param g der zu setzende Grauwert
    */
   public void setGreyValue(int g) {
      grey = g; // ZoomFaktor setzen
      notifyChanged(); // Die Observer informieren
   }

   /**
    * diese Methode liefert den aktuell gesetzten Grauwert 
    *
    * @return Aktueller Grauwert des Models
    */
   public int getGreyValue() {
      return grey;
   }

   /**
    * diese Methode setzt den Zoomfaktor fuer alle registrierten Objekte im
    * Model und benachrichtigt seine Observer
    *
    * @param f der zu setzende Zoomfaktor
    */
   public void setFactor(int f) {
      faktor = f; // ZoomFaktor setzen
      int i; // Alle Elemente informieren
      for (i = 0; i < v.size(); i++) {
         ((GraphicObject) v.elementAt(i)).setFactor(f);
      }
      curve.setFactor(f);
      xachse.setFactor(f);
      yachse.setFactor(f);
      if (fractal != null)
          ((cg.grOb.Polygon) fractal).setFactor(f);
      notifyChanged(); // Die Observer informieren
   }

   /**
    *  liefert den aktuellen Zoomfaktor
    *
    * @return der aktuell gesetzte Zoomfaktor
    */
   public int getFactor() { // Liefert Zoomfaktor
      return faktor;
   }

   /**
    * F&uuml;gt ein neues graphisches Objekt ein
    *
    * @param o neues graphisches Objekt
    */
   public void append(Object o) { // haengt ein Objekt hinten an
      v.addElement(o); // neues Objekt hinten dran
      notifyChanged();
   }

   /**
    * Diese Methode wird aufgerufen, wenn sich der Zustand des Models
    * ge&auml;ndert hat. Durch diese Methode werden alle Observer
    * benachrichtigt.
    */
   public void notifyChanged() {
      setChanged();
      notifyObservers();
   }

   /**
    * toString Methode des models
    *
    * @return liefert einen String, der alle graphischen Objekte des
    * aktuellen Zustandes enth&auml;t
    */
   public String toString() {
      return (v.toString());
   }

   /** 
    * Liefert eine Komponente vom Typ JComponent, die alle grafischen 
    * Komponenten, wie Punkte Linien etc., des Models enthaelt
    *
    * @return die Komponente, die alle anderen enthaelt.
    */
   public JComponent getComponent() {
      // neue Zeichenflaeche machen
      DrawingPane dp = new DrawingPane(View.WIDTH * faktor, View.HEIGHT * faktor);
      int i;
      for (i = 0; i < v.size(); i++) { // Alle zu zeichnenden
         dp.add((JComponent) v.elementAt(i)); // Objekte in neue Flaeche
      }
      dp.add(curve);
      dp.add(xachse);
      dp.add(yachse);
      if (fractal != null)
         dp.add((cg.grOb.Polygon) fractal);
      return dp; // fertig
   }

   /**
    * Diese Methode liefert das zuletzt eingf&uuml;gte Objekt
    *
    * @return das als letztes eingef&uuml;gte Objekt
    */
   public GraphicObject getLast() { // liefert letztes Element
      return (GraphicObject) v.lastElement();
   }

   /**
    * Mit dieser Methode kann das zuletzt eingefuegte Element geloescht werden.
    * 
    * @return Das geloeschte Element wird zurueckgeliefert.
    */
   public GraphicObject removeLast() {
      GraphicObject last = getLast();
      v.removeElementAt(v.size() - 1);
      return last;
   }

   /**
    * Methode des Interface Resetable.
    * Innerhalb dieser Methode wird der Zoomfaktor auf 1 gesetzt und alle
    * Eelemente entfernt. 
    */
   public void reset() {
      faktor = 1;
      v.removeAllElements();
      curve = new Interpol(this);
      xachse.setFactor(1);
      yachse.setFactor(1);
      fractal = null;
      notifyChanged();

   }

   /**
    * Clippt die Objekte, die im Model abgelegt sind an dem uebergebenen Rechteck
    * @param re Das Clippingrechteck
    */
   public void clip(Rectangle re) {

      int i; // Alle Elemente informieren
      for (i = 0; i < v.size();) {
         if (!((GraphicObject) v.elementAt(i)).clip(re))
            v.remove(i); // Falls nichts mehr über loeschen
         else
            i++; // sonst naechstes Objekt
      }
      notifyChanged(); // nach Clipping neu zeichnen
   }

   /**
    * Diese Methode setzt bei den Objekten, die den Punkt p
    * enthalten eine neue Transformationmatrix 
    * @param p Punkt, der in dem Objekt enthalten sein muss.
    * @param mm MatrixManager, der die aktuelle Matrix liefert.
    */
   public void setTransform(Point p, MatrixManager mm) {
      for (int i = 0; i < v.size(); i++) {
         GraphicObject go = (GraphicObject) v.elementAt(i);
         if (go.contains(p)) {
            go.setTransform(mm.getProduct());
            System.out.println(mm.getProduct());
         }
         notifyChanged();
      }
   }

   /**
    * Fuegt dem Interpol Objekt einen weiteren Stuetzpunkt hinzu.
    * @param p Punkt, der hinzugefuegt werden soll.
    */
   public void appendInterpolPoint(Point p) {
      curve.append(p);
      notifyChanged();
   }

   /**
    * Prueft ob in der Naehe des Punktes p ein Stuetzpunkt vorhanden ist.
    * @param p Punkt an dessen Stelle getestet werden soll.
    * @return Der gefundene Stuetzpunkt, oder null, falls keiner gefunden.
    */
   public Point getInterpolPoint(Point p) {
      return curve.checkRegion(p); // und vermerken
   }

   /**
    * Liefert das im Model gespeicherte Interpol Objekt zurueck.
    * @return Das Interpol Objekt des Models.
    */
   public Interpol getInterpol() {
      return curve;
   }

   /**
    * Iteriert das im Model abgelegte Fraktal
    */
   public void iterateFractal() {
      if (fractal != null)
         fractal.iterate();
      notifyChanged();
   }

   /**
    * Entfernt das Fraktal aus dem Model.
    */
   public void removeFractal() {
      fractal = null;
      notifyChanged();
   }

   /**
    * Fuegt dem Model ein Fraktal hinzu.
    * @param f Das hinzuzufuegende Fraktal
    */
   public void setFractal(Fractal f) {
      fractal = f;
      //append( (Polygon) f);
      notifyChanged();
   }

   /**
    * Erstellt einen String, der alle enthaltenen Grafikobjekte als SVG repraesentiert.
    * @param width Breite des zu erstellenden SVG Files
    * @param height Hoehe des zu erstellenden SVG Files
    * @return String mit dem kompletten SVG File
    */
   public String toSVG(int width, int height) {
   	  StringBuffer s = new StringBuffer();
   	  //FileHeader schreiben
   	  s.append("<?xml version="+ "\"1.0\"" + " encoding=" + "\"iso-8859-1\""+ "?>"+"\n");
      s.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \n");
      s.append("       \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n\n");
      s.append("<svg width=\""+ width + "\" height=\""+ height + "\">\n");
   	  //Alle SVG-Tags der einzelnen Objekte hinzufügen
   	  for (int i = 0; i < v.size(); i++){
   	  	GraphicObject o = (GraphicObject) v.get(i);
   		s.append(o.toSVG()+ "\n");
   	  }
   	  s.append(curve.toSVG());
   	  if (fractal != null) s.append( ((Polygon)fractal).toSVG());  
   	  //SVG-Datei abschließen
   	  s.append("</svg>");
   	  //Testausgabe
   	  System.out.println(s.toString());
      //Rückgabe der Datei
      return s.toString();
   }
}