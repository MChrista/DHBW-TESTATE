package cg.grOb;

import java.util.Vector;

/**
 * Klasse die (zufaellige) Fraktale Baeume erzeugen kann.
 * @version 1.0 (A 18.05.2004)
 * @author Ralf Kunze, Thomas Wiemann, Leszek Urban, Henning Wenke
 * 
 */
public class Baum extends Polygon implements Fractal {
	/**
	 *  Im 5eck ABCDE soll gelten BC = 0.75 * AE und CD = 0.47 * AE.
	 *  N`paar Rechnungen spaeter ergibt sich C als:
	 *  C.x = B.x + 0.6708 * (D.x-B.x)
	 *  C.y = B.y + 0.3355 * (D.x-B.x).-
	 *  Vielleich etwas genauer, hat aber auch nix gebracht...
	 *  (Wird beim Zufallsbaum natuerlich nicht benutzt)
	 */
	private double wX = 0.6708; // entspricht im Script 2/3
	private double wY = 0.3355; // entspricht im Script 1/3
	/**
	 * Anzahl der Punkte im Ansatzpunktearray ansatzPoints
	 */
	private int ansatzPointCnt;
	/**
	 * Gesammte Anzahl der Punkte 
	 */
	private int polyPointCnt;
	/**
	 * Maximale punktanzahl
	 */
	private int maxPoints;

	/**
	 * Breite des Anfangsbaums
	 */
	private int breite;
	/**
	 * Zufallsbaum oder nicht
	 */
	private boolean zufallsBaum;

	/**
	 * Die Punkte an denen neue Aeste ansetzen koennen
	 */
	private Point ansatzPoints[];

	/**
	 * Leerer Konstruktor. Soll nicht von anderen Klassen aufgerufen werden koennen.
	 */
	private Baum() {
		super();
	}

	/**
	 * Konstruktor zum Erzeugen eines Baumes in Abhaengigkeit der Breite und Hoehe der Flaeche,
	 * auf der er sich darstellen darf und der maximalen Anzahl an Polygonpunkten, wodurch die 
	 * Zahl der Iterationen begrenzt wird
	 * @param width Breite der Flaeche, die dem Baum zur Verfuegung steht
	 * @param height Hoehe der Flaeche, die dem Baum zur Verfuegung steht
	 * @param maxPoints maximale Anzahl an Punkten, die der Baum nicht uebersteigen darf
	 */
	public Baum(int width, int height, int maxPoints) {
		super();

		this.maxPoints = maxPoints;
		this.ansatzPoints = new Point[maxPoints];

		this.breite = 70; // Die Breite (am besten ganzzahlig waehlen!)

		this.createInitialTree(width, height);
	}
	/**
	 * Zusaetzlicher Konstruktor zum Erzeugen von Zufallsbauemen
	 * @param width Breite der Flaeche, die dem Baum zur Verfuegung steht
	 * @param height Hoehe der Flaeche, die dem Baum zur Verfuegung steht
	 * @param maxPoints maximale Anzahl an Punkten, die der Baum nicht uebersteigen darf
	 * @param zufallsBaum zufallsBaum oder nicht
	 */
	public Baum(int width, int height, int maxPoints, boolean zufallsBaum) {

		this(width, height, maxPoints);
		this.pv.removeAllElements(); // Der Schrott muss wech...
		this.setZufallsBaum(zufallsBaum);
		this.createInitialTree(width, height); // und nochmal...
	}

	/**
	 * Erzeugt den ersten "Baum".
	 * Dieser befindet sich unten in der Mitte der View.
	 * Er hat die Breite this.breite und eine von der Breite abhaengige Hoehe 
	 * @param w die Breite der View
	 * @param h die Hoehe der View
	 */
	private void createInitialTree(int w, int h) {
		// die 5 Punkte (Reihenfolge wichtig!)
		Point A, B, C, D, E;
		A = new Point((w / 2) - (breite / 2), h);
		B = new Point((w / 2) - (breite / 2), h - (breite * 2));
		D = new Point((w / 2) + (breite / 2), h - (breite * 2));
		E = new Point((w / 2) + (breite / 2), h);

		/*
		 * Wenn Zufallsbaum liegt der "x-Wert" des Punktes C zufaellig im Bereich
		 * [1/5 ; 4/5] * (D.x-B.x);
		 */
		if (zufallsBaum) {
			C =
				new Point(
					(w / 2) - (0.3 * breite) + (Math.random() * 0.6 * breite),
					h - 1.17 * breite * 2);

		} else { // Wenn kein Zufallsbaum  (Verhaeltniss gemaes Script)

			C =
				new Point(
					(w / 2) - (breite / 2) + (wX * breite),
					h - ((breite * 2) + (wY * breite)));
		}

		//Punkte zu Polygon 
		append(A);
		append(B);
		append(C);
		append(D);
		append(E);
		append(A);

		this.polyPointCnt = 6;
		this.ansatzPointCnt = 4;
		// die anfaenglichen Ansatzpunkte
		ansatzPoints[0] = B;
		ansatzPoints[1] = C;
		ansatzPoints[2] = C;
		ansatzPoints[3] = D;

	}

	/**
	 * Fuehrt eine Iteration des Baumes durch.
	 * Jede Iteration erhoeht die Punktezahl um Faktor 2.
	 * Falls nach der naechsten Iteration die max. Punktezahl ueberschritten 
	 * waere, wird sie nicht durchgefuehrt.
	 */
	public void iterate() {
		// wird Grenze nach der Iteration nicht ueberschritten, fuehre Iteration durch
		if (this.pv.size() * 2 >= maxPoints) {

			System.err.println(
				"Die maximale Punktzahl wuerde beim naechsten mal ueberschritten. Es sind aktuell "
					+ pv.size()
					+ " Punkte");
			return;
		}

		int i, j, k, p;
		double dy, dx;
		Point p1, p2, A, B, C;

		Point[] ast = new Point[3];
		// Neue Punkte die durch neuen Ast entstehen
		Point[] tmpPolyPkts = new Point[maxPoints];
		Point[] tmpAnsatzPkts = new Point[maxPoints];
		/*
		 * Die punkte schema liegen in einem bestimmten 
		 * Verhaeltniss zueinander.
		 *  
		 */
		Point[] schema = new Point[3];
		ast[0] = new Point();
		ast[1] = new Point();
		ast[2] = new Point();

		Point polyPoints[] = new Point[maxPoints]; // Vector zu Array...
		for (int m = 0; m < polyPointCnt; m++)
			polyPoints[m] = (Point) pv.get(m);

		schema[0] = new Point(0.0, 2.0);
		/*
		 * Wenn Zufallsbaum, liegt der "x-Wert" des 2ten Punktes  zufaellig im Bereich
		 * [1/5 ; 4/5] * (Punkt3.x-Punkt1.x);
		 */
		if (zufallsBaum)
			schema[1] =
				new Point((Math.random() * 3.0 / 5.0) + (1.0 / 5.0), 7.0 / 3.0);
		else // Wenn nicht zufallsBaum (Verhaeltniss gemaes Script)

			schema[1] = new Point(schema[0].x + wX, schema[0].y + wY);

		schema[2] = new Point(1.0, 2.0);

		j = 0; // Index fuer neue Ansatzstuecke
		k = 0; // Index fuer neue Polygonpunkte
		/*
		 * Es werden jeweils 2 fuer einen Ast in Frage kommende Punkte
		 * untersucht. Erst 0 und eins dann 2 und drei usw.
		 * 
		 * In Relation zum ersten Punkt werden dann die drei Astpunkte, deren 
		 * relative Lage durch 'schema' festgelgt ist, entsprechen der Lage
		 * der Punkte p1 und p2 skaliert ,gedreht und gespeichert. 
		 * 
		 */
		for (p = 0; p < ansatzPointCnt; p = p + 2) {
			p1 = ansatzPoints[p];
			p2 = ansatzPoints[p + 1];

			dy = p1.y - p2.y; // bestimme dx, dy
			dx = p2.x - p1.x;

			for (i = 0; i < 3; i++) // fuehre Skalierung und Rotation aus
				{
				ast[i].x = p1.x + (int) (schema[i].x * dx - schema[i].y * dy);
				ast[i].y = p1.y - (int) (schema[i].y * dx + schema[i].x * dy);
			}

			A = ast[0]; // kreiere neue Ast-Punkte
			B = ast[1];
			C = ast[2];

			//	   speichere neue Ansatzstuecke
			tmpAnsatzPkts[j] = new Point(A);
			tmpAnsatzPkts[j + 1] = new Point(B);
			tmpAnsatzPkts[j + 2] = new Point(B);
			tmpAnsatzPkts[j + 3] = new Point(C);
			j = j + 4;
		}

		i = 0; // Index fuer altes Polygon
		k = 0; // Index fuer neues Polygon
		for (p = 0; p < ansatzPointCnt; p = p + 2) {
			while ((polyPoints[i].x != ansatzPoints[p].x)
				|| (polyPoints[i].y != ansatzPoints[p].y))
				//	   uebernimm unveraenderte Polygonpunkte
				tmpPolyPkts[k++] = polyPoints[i++];
			tmpPolyPkts[k] = ansatzPoints[p]; // uebernimm neue Polygonpunke
			tmpPolyPkts[k + 1] = tmpAnsatzPkts[2 * p];
			tmpPolyPkts[k + 2] = tmpAnsatzPkts[2 * p + 1];
			tmpPolyPkts[k + 3] = tmpAnsatzPkts[2 * p + 3];
			tmpPolyPkts[k + 4] = ansatzPoints[p + 1];
			k += 4;
			i++;
		}
		while (i < polyPointCnt)
			tmpPolyPkts[k++] = polyPoints[i++];
		for (i = 0; i < j; i++)
			ansatzPoints[i] = tmpAnsatzPkts[i];
		// neu berechnete Ansatzstuecke kopieren
		ansatzPointCnt = j;
		for (i = 0; i < k; i++)
			polyPoints[i] = tmpPolyPkts[i];
		// neu berechnete Polygonpunkte kopieren
		polyPointCnt = k;

		this.pv = new Vector(); // Array zu Vector
		for (int m = 0; m < polyPointCnt; m++)
			pv.add(m, polyPoints[m]);

	}

	/**
	 * Liefert die Anzahl der Polygonpunkte des Baumes und damit seine Beschreibung 
	 * @return String, der den Baum beschreibt
	 */
	public String toString() {
		return ("Anzahl der Polygonpunkte des Baumes: " + getLength());
	}
	/**
	 * @return
	 */
	public boolean isZufallsBaum() {
		return zufallsBaum;
	}

	/**
	 * @param b
	 */
	public void setZufallsBaum(boolean b) {
		zufallsBaum = b;
	}

}