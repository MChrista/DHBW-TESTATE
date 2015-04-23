package cg.grOb;

import java.awt.Cursor;
import java.awt.Graphics;
import java.util.Vector;

import cg.draw2D.Model;
import cg.tools.Matrix;

/** 
 * Eine Klasse, welche verschiedene Interpolationsmethoden zur Verfuegng stellt
 * Basis fuer die Berechnungen ist ein Array von Punkten, welches die
 * Stuetzpunkte enthaelt.
 * Die Methoden sind im wesentlichen:<BR>
 *    &nbsp;&nbsp;&nbsp;private void drawBezier(void); &nbsp;&nbsp; 
 * Zeichne eine Bezierkurve vom Grad 3.<BR>
 *    &nbsp;&nbsp;&nbsp;private void drawSpline(void); &nbsp;&nbsp; 
 * Zeichne eine Splinekurve <BR>
 *    &nbsp;&nbsp;&nbsp;private void drawBSpline(void); &nbsp;&nbsp; 
 * Zeichne eine B-Splinekurve <BR>
 * Desweiteren sind Methoden zur Manipulation der Interpolationspolynome
 * vorhanden. Damit koennen weitere Stuetzpunkte angefuegt werden, oder 
 * aber auch Stuetzpunkte verschoben werden.
 * @author Ralf Kunze
 * @version 1.2 (A 18.05.2004)
 */
public class Interpol extends GraphicObject {

   /**
    * Enthaelt im weiteren alle Stuetzpunkte.
    * Da sie als kleine Kreise dargestellt werden sollen, werden
    * sie gleich als Objekte vom Typ Kreis abgelegt.
    */
   private Vector stuetzPunkte = new Vector(4, 1); // Vektor mit Stuetzpunkten

   /**
    * Konstante welche die Bezierinterpolation codiert
    */
   public static final int BEZIER = 1;

   /**
    * Konstante welche die Splineinterpolation codiert
    */
   public static final int SPLINE = 2;

   /**
    * Konstante welche die B-Splineinterpolation codiert
    */
   public static final int BSPLINE = 4;

   /** 
    * Maximale Anzahl der Interpolationspunkte
    */
   private static final int MAX_POINTS = 1000;

   /**
    * Darstellungsmodus, gibt an welche Interpolationsarten dargestellt werden
    */
   private int modus = 0;

   /**
    * Index eines zu bewegenden Punktes.
    * Wird ueber checkRegion(Punkt p) festgelegt
    */
   //private int movePoint=-1;

   /**
    * Das Model, in dem dieses Interpol-Objekt zur Verwaltung gespeichert ist.
    */
   private Model model;

   /** 
    * Privater Constructor, da das Interpolobjekt das Model benoetigt.
    */
   private Interpol() {
   }

   /**
    * Constructor fuer ein Interpolobjekt.
    * Zoomfaktor 1 wird gesetzt, Model wird eingehaengt und ein Kreis-Array 
    * der Laenge Null wird angelegt.
    * @param model Das Model in dem das Interpol-Objekt eingehaengt wird
    */
   public Interpol(Model model) {
      super(1);
      stuetzPunkte = new Vector(4, 1);
      this.model = model;
      this.modus = 0;
   }

   /**
    * Constructor fuer ein Interpolobjekt.
    * Zoomfaktor 1 wird gesetzt, Model wird eingehaengt und ein Kreis-Array 
    * der Laenge Null wird angelegt. 
    * Der Darstellungsmodus wird ebenfalls gleich gesetzt.
    * @param modus Der Darstellungsmodus (kann nachtraeglich geaendert werden)
    * @param model Das Model in dem das Interpol-Objekt eingehaengt wird
    */
   public Interpol(int modus, Model model) {
      super(1);
      stuetzPunkte = new Vector(4, 1);
      this.model = model;
      this.modus = modus;
   }

   /**
    * Constructor fuer ein Interpolobjekt.
    * Zoomfaktor 1 wird gesetzt, Model wird eingehaengt und ein Kreis-Array 
    * entsprechend der Anzahl der Stuetzstellen angelegt, sowie der 
    * Zeichenmodus festgelegt.
    * @param spv Vektor mit den bisher bekannten Stuetzpunkten
    * @param modus der Zeichenmodus zusammengesetzt aus den Klassenkonstanten 
    * SPLINE, BSPLINE und BEZIER
    * @param model Das Model in dem das Interpol-Objekt eingehaengt wird
    */
   public Interpol(Vector spv, int modus, Model model) {
      super(1);
      this.modus = modus;
      stuetzPunkte = new Vector(spv.size(), 1);
      for (int i = 0; i < spv.size(); i++) // Stuetzpunkte in den Vektor
         stuetzPunkte.addElement(spv.elementAt(i)); // uebernehmen
      this.model = model;
   }

   /**
    * Constructor fuer ein Interpolobjekt.
    * Zoomfaktor 1 wird gesetzt, Model wird eingehaengt und ein Kreis-Array 
    * entsprechend der Anzahl der Stuetzstellen wird angelegt.
    * @param stuetzPunkte array mit den bisher bekannten Stuetzpunkten
    * @param model Das Model in dem das Interpol-Objekt eingehaengt wird
    */
   public Interpol(Vector stuetzPunkte, Model model) {
      this(stuetzPunkte, 0, model);
   }

   /**
    * Aendert den Darstellungsmodus.
    * Der code wird aufaddiert, bzw wieder abgezogen (XOR)
    * also entsprechend gesetzt und geloescht.
    * Angenommen, die dem code entsprechende Kurve wird nicht gezeichnet.
    * Nach einmaligem Aufruf dieser Methode mit code wird sie gezeichnet und
    * nach nochmaligem Aufruf mit demselben code wird sie wieder nicht
    * gezeichnet.
    * @param code wird aus den Klassenkonstanten BEZIER, BSPLINE und 
    * SPLINE gewaehlt
    */
   public void changeInterpolatingMethod(int code) {
      if (code == BEZIER || code == SPLINE || code == BSPLINE) {
         //Testen ob code erlaubt
         modus = modus ^ code; //Code reversibel aendern
         model.notifyChanged(); //Gib dem Model bescheid
      }
   }

   /**
    * Stringrepraesentation eines Interpolobjektes
    */
   public String toString() {
      return ("Interpolierte Kurve mit Stuetzpunkten:" + stuetzPunkte);
   }

   /**
    * Haenge einen weiteren Stuetzpunkt ein
    * @param p ein neuer einzuhaengender Stuetzpunkt
    */
   public void append(Point p) {
      stuetzPunkte.addElement(new Circle(p, 2, faktor));
      //System.err.println("\nPunkte im Interpolobjekt:"+this);
      model.notifyChanged();
   }

   /**
    * Prueft, ob ein Stuetzpunkt in der Naehe des Punktes p liegt und merkt sich
    * gegebenenfalls den Index des Punktes. Die Fehlertoleranz betraegt fuenf 
    * Pixel. Falls mehrere Punkte in Frage kommen wird der erste ausgewaehlt.
    * @param p Punkt dessen Koordinaten zu ueberpruefen sind.
    */
   public Point checkRegion(Point p) {
      Circle k; // Hilfskreise
      for (int i = 0; i < stuetzPunkte.size(); i++) { // Alle Punkte ueberpruefen
         // Und Abstand bestimmen
         k = (Circle) stuetzPunkte.elementAt(i);
         double xdiff = Math.abs(k.getXCoord() - p.getXCoord());
         double ydiff = Math.abs(k.getYCoord() - p.getYCoord());
         if (Math.rint((0.5 + Math.sqrt(xdiff * xdiff + ydiff * ydiff))) < 5.0) {
            return k.getMiddle();
         }
      }
      return null;
   }

   /**
    * Aendert den Zoomfaktor
    * @param faktor neuer Zoomfaktor
    */
   public void setFactor(int faktor) {
      super.setFactor(faktor);
      for (int i = 0; i < stuetzPunkte.size(); i++) //Gib den Zoomfaktor an alle
          ((Circle) stuetzPunkte.elementAt(i)).setFactor(faktor); //Stuetzpunkte weiter
      model.notifyChanged();
   }

   /**
    * Setzt die Transformations Matrix eines Objektes
    * @param transform Transformationsmatrix
    */
   public void setTransform(Matrix transform) {
      for (int i = 0; i < stuetzPunkte.size(); i++) {
         if (transform != null) {
            ((Point) stuetzPunkte.elementAt(i)).setTransform(transform);
         }
      }
   }

   /**
    * Graphische Darstellung eines Interpolationsobjektes 
    * in den entsprechenden Graphischen Context
    * @param g Graphischer Kontext in dem sich das Objekt zeichen soll
    */
   public void paint(Graphics g) {
      getTopLevelAncestor().setCursor(new Cursor(Cursor.WAIT_CURSOR));
      for (int i = 0; i < stuetzPunkte.size(); i++) //Alle Stuetzpunkte zeichnen
          ((Circle) stuetzPunkte.elementAt(i)).paint(g);

      if ((modus & SPLINE) != 0)
         drawSpline(g); // Spline zeichnen?
      if ((modus & BEZIER) != 0)
         drawBezier(g); // Bezierkurve zeichnen?
      if ((modus & BSPLINE) != 0)
         drawBSpline(g); // BSpline zeichnen?

      getTopLevelAncestor().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
   }

   /***************************************************************************/
   /*                                                                         */
   /*         Bezierinterpolation fuer ein Array aus Stuetzpunkten            */
   /*                                                                         */
   /***************************************************************************/

   private int iter = 3; // Iterationstiefe fuer Bezierinterpolation

   /**
    * Setzt die Iterationstiefe fuer die Bezierinterpolation
    * @param k Wert der Iterationstiefe
    */
   public void setBezierIterationen(int k) {
      if (k >= 0) {
         iter = k;
         model.notifyChanged();
      }
   }

   /**
    * Zeichnet eine Bezierkurve mit den engegebenen Stuetzpunkten (mindestens 
    * 4 sind notwendig) in den entsprechenden graphischen Kontext
    * @param g graphischer Kontext in dem sich das Objekt zeichnen soll
    */

   private void drawBezier(Graphics g) {
      int count = 0;

      if (stuetzPunkte.size() < 4) { // Error!
         System.err.println("Keine Bezier-Interpolation mit weniger als vier Punkten moeglich!");
      } else {
         // Zeichne einzelne Bezier-3 Kurven mit jeweils vier Punkten
         while ((count + 3) < (stuetzPunkte.size())) {
            bezier(
               ((Circle) stuetzPunkte.elementAt(count + 0)).getXCoord(),
               ((Circle) stuetzPunkte.elementAt(count + 0)).getYCoord(),
               ((Circle) stuetzPunkte.elementAt(count + 1)).getXCoord(),
               ((Circle) stuetzPunkte.elementAt(count + 1)).getYCoord(),
               ((Circle) stuetzPunkte.elementAt(count + 2)).getXCoord(),
               ((Circle) stuetzPunkte.elementAt(count + 2)).getYCoord(),
               ((Circle) stuetzPunkte.elementAt(count + 3)).getXCoord(),
               ((Circle) stuetzPunkte.elementAt(count + 3)).getYCoord(),
               0,
               g);
            count += 3; // die naechsten 4 Punkte
         }
      }
   }

   /******************/
   /* Bezier - Kurve */
   /******************/

   /**
    * Berechnet eine Bezierkurve dritten Grades, anhand von einzelnen 
    * Teillinien, welche die interpolierte Kurve bilden
    * @param px0 x-Wert des ersten Punktes
    * @param py0 y-Wert des ersten Punktes
    * @param px1 x-Wert des zweiten Punktes
    * @param py1 y-Wert des zweiten Punktes
    * @param px2 x-Wert des dritten Punktes
    * @param py2 y-Wert des dritten Punktes
    * @param px3 x-Wert des vierten Punktes
    * @param py3 y-Wert des vierten Punktes
    * @param depth aktuelle Rekursionstiefe
    * @param g graphischer Kontext in dem sich die Bezierkurve zeichnen soll
    */
   private void bezier(// Zeichne Bezierk. 3. Grades
   double px0, double py0, // anhand des Stuetzpunktes p0
   double px1, double py1, // anhand des Stuetzpunktes p1
   double px2, double py2, // anhand des Stuetzpunktes p2
   double px3, double py3, // anhand des Stuetzpunktes p3
   int depth, // aktuelle Rekursionstiefe	
   Graphics g) {
      double qx01, qy01, qx12, qy12, qx23, qy23, qx012, qy012;
      double qx123, qy123, qx0123, qy0123; // Hilfspunkte
      if (depth > iter) // Iterationstiefe erreicht
         Bresenham.line(new Point((int) (px0 + 0.5), (int) (py0 + 0.5), model.getFactor()), new Point((int) (px3 + 0.5), (int) (py3 + 0.5), model.getFactor()), g, faktor);
      else { //Weiter iterieren
         depth++;
         //Mittels Casteljau weitere Punkte finden
         qx01 = (px0 + px1) / 2;
         qy01 = (py0 + py1) / 2;
         qx12 = (px1 + px2) / 2;
         qy12 = (py1 + py2) / 2;
         qx23 = (px2 + px3) / 2;
         qy23 = (py2 + py3) / 2;
         qx012 = (qx01 + qx12) / 2;
         qy012 = (qy01 + qy12) / 2;
         qx123 = (qx12 + qx23) / 2;
         qy123 = (qy12 + qy23) / 2;
         qx0123 = (qx012 + qx123) / 2;
         qy0123 = (qy012 + qy123) / 2;

         bezier(px0, py0, qx01, qy01, qx012, qy012, qx0123, qy0123, depth, g);
         bezier(qx0123, qy0123, qx123, qy123, qx23, qy23, px3, py3, depth, g);
      }
   }

   /************************/
   /* Spline Interpolation */
   /************************/
   /* HILFSARRAYS FUER SPLINE */

   private double[] calcfx = new double[MAX_POINTS];
   private double[] calcfy = new double[MAX_POINTS];
   private double[] x = new double[MAX_POINTS];
   private double[] y = new double[MAX_POINTS];
   private double[] t = new double[MAX_POINTS];
   private Point[] splinePunkt = new Point[MAX_POINTS];
   // Array mit (inter) Spline-Punkten

   /**
    * Loest das Gleichungssystem zur Bestimmung der Koeffizienten a,b,c
    * @param n Anzahl der Stuetzpunkte
    * @param t[] Array mit x-Werten der Stuetzpunkte
    * @param f[] Array mit den entsprechenden y-Werten zu t[]
    * @param splineSize Anzahl der Interpolationswerte
    * @param calcf[] Ausgabe-Array mit Interpolationswerten
    */
   private void cubicSpline(int n, double t[], double f[], int splineSize, double calcf[]) {
      int i, j;
      double a[] = new double[MAX_POINTS];
      double b[] = new double[MAX_POINTS];
      double c[] = new double[MAX_POINTS];
      double deltaT[] = new double[MAX_POINTS];
      double D[] = new double[MAX_POINTS];
      double m[] = new double[MAX_POINTS];
      double k[] = new double[MAX_POINTS];
      double bh, dh, e, h, wt, dt;
      for (i = 1; i < n; i++) {
         deltaT[i] = t[i] - t[i - 1];
         D[i] = (f[i] - f[i - 1]) / deltaT[i];
      }
      m[0] = deltaT[2];
      deltaT[0] = t[2] - t[0];
      h = deltaT[1];
      k[0] = ((h + 2 * deltaT[0]) * D[1] * deltaT[2] + h * h * D[2]) / deltaT[0];
      for (i = 1; i < (n - 1); i++) {
         h = -deltaT[i + 1] / m[i - 1];
         k[i] = h * k[i - 1] + 3 * (deltaT[i] * D[i + 1] + deltaT[i + 1] * D[i]);
         m[i] = h * deltaT[i - 1] + 2 * (deltaT[i] + deltaT[i + 1]);
      }
      h = t[n - 1] - t[n - 3];
      dh = deltaT[n - 1];
      k[n - 1] = ((dh + h + h) * D[n - 1] * deltaT[n - 2] + dh * dh * D[n - 2]) / h;
      h = -h / m[n - 2];
      m[n - 1] = deltaT[n - 2];
      m[n - 1] = h * deltaT[n - 2] + m[n - 1];
      a[n - 1] = (h * k[n - 2] + k[n - 1]) / m[n - 1];
      for (i = n - 2; i >= 0; i--)
         a[i] = (k[i] - deltaT[i] * a[i + 1]) / m[i];
      for (i = 1; i < n; i++) {
         dh = D[i];
         bh = deltaT[i];
         e = a[i - 1] + a[i] - dh - dh;
         b[i - 1] = 2 * (dh - a[i - 1] - e) / bh;
         c[i - 1] = 6 * e / (bh * bh);
      }
      wt = 0;
      j = 0;
      dt = t[n - 1] / (double) (splineSize - 1);
      for (i = 0; i < splineSize; i++) {
         while ((t[j + 1] < wt) && (j < splineSize))
            j++;
         h = wt - t[j];
         calcf[i] = f[j] + h * (a[j] + h * (b[j] + h * c[j] / 3) / 2);
         wt = wt + dt;
      }
      calcf[splineSize - 1] = f[n - 1];
   }

   // berechnet Intervallgrenzen          
   private void besetzeArrays(Vector punkt) {
      Circle k; // Hilfsfkreis
      int i;
      double ax, ay, dd;

      for (i = 0; i < punkt.size(); i++) { // Fuer alle Stuetzpunkte
         k = (Circle) punkt.elementAt(i);
         x[i] = (double) k.getXCoord(); // besetze x- / y-Koordinaten 
         y[i] = (double) k.getYCoord();
      }

      t[0] = 0.0; // fuell t-Werte mit Stuetz-
      for (i = 1; i < punkt.size(); i++) { // punktabstand nach Euklid
         ax = Math.abs(x[i] - x[i - 1]);
         ay = Math.abs(y[i] - y[i - 1]);
         dd = ax + ay;
         if (ax > ay)
            dd = dd + 2 * ax; // Naeherungsformel f. Euklid:
         else
            dd = dd + 2 * ay; // 1/3*(dx+dy+2*max{dx,dy})   
         t[i] = t[i - 1] + dd / 3;
      }
   }

   // berechnet die Approximation der Kurve 
   // uebergeben im Vector punkt             
   // fuer splineSize Interpolationswerte  
   private void curveFitting(Vector punkt, int splineSize) {
      int i, n = 0;

      // erzeuge Intervallgrenzen und Gleitkommaversion von punkt      
      besetzeArrays(punkt);

      // bestimme die Interpolationswerte fuer x- und y-Richtung getrennt
      cubicSpline(punkt.size(), t, x, splineSize, calcfx);
      cubicSpline(punkt.size(), t, y, splineSize, calcfy);
      for (i = 0; i < splineSize; i++) // uebertrage sie ins Array
         splinePunkt[i + 1] = new Point((int) (calcfx[i] + 0.5), (int) (calcfy[i] + 0.5), model.getFactor());
   }

   /**
    * Zeichnet anhand der gegebenen Stuetzpunkte eine Splineinterpolierte
    * in den entsprechenden graphischen Kontext
    * @param g der graphische Kontext in den sich die Kurve zeichnen soll
    */
   private void drawSpline(Graphics g) { // Zeichne kub. Splines
      int i;
      if (stuetzPunkte.size() < 4)
         System.err.println("Keine Spline-Interpolation mit weniger als vier Punkten moeglich");
      else { // werden -> neu berechnen
         curveFitting(stuetzPunkte, splineiter);
         for (i = 1; i < splineiter; i++) { // splineinter Punkte zeichnen
            Bresenham.line(splinePunkt[i], splinePunkt[i + 1], g, faktor);
         }
      }
   }

   private int splineiter = 20; //Anzahl der Iterationspunkte fuer splineinterpolation

   /**
    * Setzt die Anzahl der Interpolationspunkte
    * @param k Anzahl der Interpolationspunkte
    */
   public void setSplineInterpolPunkte(int k) {
      if (k >= 0) {
         splineiter = k;
         model.notifyChanged();
      }
   }

   /**************************/
   /* B-Spline Interpolation */
   /**************************/

   private int grad = 3; //Grad der B-Splineinterpolation
   private int binter = 20; //Anzahl der Interpolationspunkte

   /**
    * Setzt den Interpolationsgrad fuer die B-Spline Interpolation
    * @param k Grad der B-Spline Interpolation
    */
   public void setBsplineGrad(int k) {
      grad = k;
      model.notifyChanged();
   }

   /**
    * Setzt die Anzahl der Interpolationspunkte fuer die B-Spline Interpolation
    * @param k Anzahl der Interpolationspunkte
    */
   public void setBsplineInterpolPunkte(int k) {
      binter = k;
      model.notifyChanged();
   }

   private Point[] bsplinePunkt = new Point[MAX_POINTS];
   // Array mit (binter) B-Spline-Punkten
   private int[] T = new int[MAX_POINTS];
   // Knotenvektor fuer B-Splines                            

   private double N(int i, int k, double t) {
      int nenner; // Nenner der Faktoren
      double ergebnis = 0.0; // Rueckgabewert

      if (k == 1) { // Rekursionsverankerung
         if ((T[i] <= t) && (t < T[i + 1])) {
            return 1.0;
         } else {
            return 0.0;
         }
      }

      if ((nenner = T[i + k - 1] - T[i]) != 0) { // rekursive Definition
         ergebnis += ((t - T[i]) / nenner) * N(i, k - 1, t);
      }
      if ((nenner = T[i + k] - T[i + 1]) != 0) {
         ergebnis += ((T[i + k] - t) / nenner) * N(i + 1, k - 1, t);
      }
      return ergebnis;
   }

   // Konstruiert den Knotenvektor
   private void fillT(int n) {
      int k = grad;
      for (int j = 0; j <= n + k; j++) {
         if (j < k)
            T[j] = 0;
         else if (j >= k && j <= n)
            T[j] = j - k + 1;
         else if (j > n)
            T[j] = n - k + 2;
      }
   }

   private void calculateBSpline(int n, int k) {
      int f = model.getFactor();
      double I = (n - k + 2.0) / (binter - 1.0); // Intervall-Breite
      Circle kr; // Hilfskreis

      //kr = (Circle)stuetzPunkte.firstElement();     // 1. Stuetzpunkt im BSpline
      //bsplinePunkt[0] = new Point(kr.getXCoord(), kr.getYCoord(), f);

      bsplinePunkt[0] = ((Circle) stuetzPunkte.firstElement()).getMiddle();

      for (int j = 1; j < binter - 1; j++) { // binter-2 Werte durchlaufen
         double t = j * I; // Parameter-Wert
         double bx = 0.0; // Initialisiere bx
         double by = 0.0; // Initialisiere by

         for (int i = 0; i <= n; i++) { // n+1 mal N*Punkt aufsumm.
            double N_i_k_t = N(i, k, t); // 1 Rekursion sparen
            kr = (Circle) stuetzPunkte.elementAt(i);
            bx += N_i_k_t * kr.getXCoord();
            by += N_i_k_t * kr.getYCoord();
         }
         // Neuer Interpol-Punkt
         bsplinePunkt[j] = new Point((int) (0.5 + bx), (int) (0.5 + by), f);
      }
      //kr = (Circle)stuetzPunkte.lastElement();      // letzter Stuetzp. im Spline
      //bsplinePunkt[binter-1] = new Point(kr.getXCoord(), kr.getYCoord(), f);
      bsplinePunkt[binter - 1] = ((Circle) stuetzPunkte.lastElement()).getMiddle();
   }

   /**
    * Zeichnet in den entsprechenden graphischen Kontext 
    * eine B-Splineinterpolierte anhand der Stuetzpunkte
    * @param g graphischer Kontext in dem die Interpolierte gezeichnet wird
    */
   private void drawBSpline(Graphics g) {
      int groesse = stuetzPunkte.size();
      if (groesse >= grad) { // ab grad Stueck zeichnen
         fillT(stuetzPunkte.size() - 1); // Knotenvektor T berechnen
         calculateBSpline(stuetzPunkte.size() - 1, grad); // Kurven-Punkte berechnen
         for (int i = 0; i < binter - 1; i++) { // Linien zwischen Interpolations-Punkten zeichnen
            Bresenham.line(bsplinePunkt[i], bsplinePunkt[i + 1], g, faktor);
         }
      }
   }

   /**
    * SVG Ausgabe des Interpol Objektes als SVG-Tag.
    */
   public String toSVG() {
      StringBuffer s = new StringBuffer();
      //Tag einleiten
      s.append("<path d=\"");

	  //Anzahl der Bezierabschnitte bestimmen
	  int n = 0;
	  if (stuetzPunkte.size() > 3)
	    n = stuetzPunkte.size() / 3;
       
      //n-Abschnitte aus 4 Punkten zeichnen
      for (int m = 0; m < n; m++){
 		//Grenzen überprüfen (kann sonst fehler geben, 
 		//wenn kein vollständiger Abschnitt mehr im Vektor ist)
      	if (m * 3 + 3 < stuetzPunkte.size()){
      	
      	//MoveTo
        s.append("M" + ((Circle)stuetzPunkte.get(m * 3)).getXCoord() + " " +
		    ((Circle)stuetzPunkte.get(m * 3)).getYCoord());
		//"CurveTo"   
		s.append(" C" + ((Circle)stuetzPunkte.get(m * 3 + 1)).getXCoord() + " " +
		   ((Circle)stuetzPunkte.get(m * 3 + 1)).getYCoord());
		   
		s.append(" " + ((Circle)stuetzPunkte.get(m * 3 + 2)).getXCoord() + " " +
		  ((Circle)stuetzPunkte.get(m * 3 + 2)).getYCoord());
		  
		s.append(" " + ((Circle)stuetzPunkte.get(m * 3 + 3)).getXCoord() + " " +
		  ((Circle)stuetzPunkte.get(m * 3 + 3)).getYCoord() + " ");
      	}
      }
      //Tag beenden und farben setzen
      s.append("\" fill=\"white\" stroke=\"black\" />\n");
      //ergebnis zurückgeben
      return s.toString();
   }
}
