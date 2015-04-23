package cg.grOb;

import java.util.Vector;

/**
 * Ein Objekt dieser Klasse repraesentiert die 
 * Koch'sche-Schneeflocke.
 * @author Ralf Kunze
 * @version 1.0 (A 18.05.2004)
 */
public class Koch extends Polygon implements Fractal {

	/**
	 * Die maximale Punktzahl
	 */
	private int maxPoints;

	/**
	 * Leerer Konstruktor, soll von niemandem aufgerufen werden.
	 */
	private Koch() {
		super();
	}

	/**
	 * Konstruktor zum Erzeugen einer Kochkurve in Abhaengigkeit der ihr uebergebenen Groesse der 
	 * Flaeche und der maximalen Anzahl an Polygonpunkten
	 * @param width Breite der Flaeche, in die sich die Kurve zeichnen darf
	 * @param height Hoehe der Flaeche, in die sich die Kurve zeichnen darf
	 * @param maxPoints Maximale Zahl an Polygonpunkten, die die Kurve besitzen darf
	 */
	public Koch(
		int width,
		int height,
		int maxPoints) { // Beim Erzeugen des Objekts wird die Ausgangsfigur gezeichnet
		super();
		this.maxPoints = maxPoints;

		this.createInitialSchneeflocke(width, height);

	}

	/**
	 * Erzeugt ein ein grosses Dreieck (Punktreihenfolge wichtig !) 
	 * @param w max. Breite
	 * @param h max. Hoehe
	 */
	private void createInitialSchneeflocke(int w, int h) {
		append(new Point(0, 0));
		append(new Point(0, h));
		append(new Point(w, h));
		append(new Point(0, 0));

	}

	/**
	 * Nur zum Testen ein kleines Quadrat
	 */
	private void createInitialSchneeflocke2(int w, int h) {
		append(new Point(320, 100));
		append(new Point(540, 400));
		append(new Point(100, 400));
		append(new Point(320, 100));
	}

	/**
	 * Fuehrt eine Iteration der Kochkurve durch.
	 * Jede Iteration erhoeht die Punktezahl um Faktor 4.
	 * Falls nach der naechsten Iteration die max. Punktezahl ueberschritten 
	 * waere, wird sie nicht durchgefuehrt.
	 */
	public void iterate() {
		//wird Grenze nach It. nicht ueberschritten, fuehre Iteration durch
		if (this.pv.size() * 4 >= maxPoints) {

			System.err.println(
				"Die maximale Punktzahl wuerde beim naechsten mal ueberschritten. Es sind aktuell "
					+ pv.size()
					+ " Punkte");
			return;
		}

		int poly_punkt_cnt = this.pv.size(); // Punktanzahl
		//		Jede Iteration erhoeht die Punktezahl um Faktor 4
		Point poly_punkt[] = new Point[poly_punkt_cnt * 4];

		// VectorZuArray
		for (int i = 0; i < poly_punkt_cnt; i++) {
			poly_punkt[i] = (Point) pv.get(i);
		}
		
		
		int i, j;
		double x0, x1, x2, x3, x4, y0, y1, y2, y3, y4; // tmp Koord.
		Point[] HPunkt = new Point[maxPoints]; // Hilspunktearray
		j = 0;

		/*
		 * Zwischen je zwei Punkten(P0,P4) werden zwei Punkte P1,P3 erzeugt,
		 * die quf 1/3 und auf 2/3 der Strecke liegen.
		 * Der Punkt P2 wird von P1 aus auf den um 60 Grad gedrehten
		 * Endpunkt Strecke P1P3 gesetzt 
		 */
		for (i = 0; i < poly_punkt_cnt; i++) {
			x0 = poly_punkt[i].x;
			y0 = poly_punkt[i].y;
			x4 = poly_punkt[(i + 1) % (poly_punkt_cnt)].x;
			y4 = poly_punkt[(i + 1) % (poly_punkt_cnt)].y;
			x1 = (x4 - x0) / 3 + x0;
			y1 = (y4 - y0) / 3 + y0;
			x3 = x4 - (x4 - x0) / 3;
			y3 = y4 - (y4 - y0) / 3;

			x2 =
				(int) (x1
					+ ((x3 - x1) * Math.cos(Math.PI / 3))
					- ((y3 - y1) * Math.sin(Math.PI / 3))
					+ 0.5);
			y2 =
				(int) (y1
					+ ((y3 - y1) * Math.cos(Math.PI / 3))
					+ ((x3 - x1) * Math.sin(Math.PI / 3))
					+ 0.5);




			HPunkt[j] = new Point();
			HPunkt[j + 1] = new Point();
			HPunkt[j + 2] = new Point();
			HPunkt[j + 3] = new Point();
			HPunkt[j] = poly_punkt[i];
			HPunkt[j + 1].x = (int) x1;
			HPunkt[j + 1].y = (int) y1;
			HPunkt[j + 2].x = (int) x2;
			HPunkt[j + 2].y = (int) y2;
			HPunkt[j + 3].x = (int) x3;
			HPunkt[j + 3].y = (int) y3;
			j = j + 4;

		}
		for (i = 0; i < j; i++) // neu berechnetes Polygon

			poly_punkt[i] = HPunkt[i]; // kopieren
		pv = new Vector();

		// ArrayZuVector
		for (int k = 0; k < j; k++)
			pv.add(k, poly_punkt[k]);

	}

	/**
	 * Liefert eine Beschreibung der Koch'schen Kurve.
	 * @return String, der die Kurve beschreibt
	 */
	public String toString() {
		return ("Anzahl der Polygonpunkte der Kochkurve: " + getLength());
	}
}