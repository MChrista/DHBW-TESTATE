package cg.grOb;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Diese Klasse beinhalted verschiedene Algorithmen zum zeichnen von
 * Kreisen und Linien mit dem Bresenham-Algorithmus
 *
 * @version 1.5 (A 19.05.2004)
 * @author Ralf Kunze
 */
public class Bresenham {

   /** 
   * Zeichnet mit Bresenham-Algorithmus einen gestrichelten Kreis
   *
   * @param c Kreises, der gezeichnet werden soll
   * @param g der grafische Kontext in den sich der gestrichelte Kreis
   * zeichnen soll
   */
   public static void dashedCircle(Circle c, Graphics g) {
      Point m = c.getMiddle();
      int faktor = c.getFactor();
      int radius = c.getRadius();
      dashedCircle(m, radius, g, faktor);
   }

   /** 
    * zeichnet mit Bresenham-Algorithmus einen gestrichelten Kreis um den
    * Punkt p mit Radius r
    *
    * @param p Mittelpunkt des Kreises
    * @param r Radius des Kreises
    * @param g der grafische Kontext in den sich der gestrichelte Kreis
    * zeichnen soll
    */
   public static void dashedCircle(Point p, int r, Graphics g, int faktor) {
      double x, y, d, dx, dxy;
      int steps = 0;

      x = 0;
      y = r;
      d = 1 - r;
      dx = 3;
      dxy = -2 * r + 5;
      while (y >= x) {
         if (((steps++ / 3) % 2) == 0) {
            setPixel((int) (0.5 + p.x + x), (int) (0.5 + p.y + y), faktor, g); // alle 8 Oktanden
            setPixel((int) (0.5 + p.x + y), (int) (0.5 + p.y + x), faktor, g); // gleichzeitig zeichnen
            setPixel((int) (0.5 + p.x + y), (int) (0.5 + p.y - x), faktor, g);
            setPixel((int) (0.5 + p.x + x), (int) (0.5 + p.y - y), faktor, g);
            setPixel((int) (0.5 + p.x - x), (int) (0.5 + p.y - y), faktor, g);
            setPixel((int) (0.5 + p.x - y), (int) (0.5 + p.y - x), faktor, g);
            setPixel((int) (0.5 + p.x - y), (int) (0.5 + p.y + x), faktor, g);
            setPixel((int) (0.5 + p.x - x), (int) (0.5 + p.y + y), faktor, g);
         }
         if (d < 0) {
            d = d + dx;
            dx = dx + 2;
            dxy = dxy + 2;
            x++;
         } else {
            d = d + dxy;
            dx = dx + 2;
            dxy = dxy + 4;
            x++;
            y--;
         }
      }
   }

   /** 
    * zeichnet mit Bresenham-Algorithmus einen Kreis
    *
    * @param c Kreises, der gezeichnet werden soll
    * @param g der grafische Kontext in den sich der Kreis
    * zeichnen soll
    */
   public static void circle(Circle c, Graphics g) {
      Point m = c.getMiddle();
      int faktor = c.getFactor();
      int radius = c.getRadius();
      circle(m, radius, g, faktor);
   }

   /** zeichnet mit Bresenham-Algorithmus  einen Kreis um 
    * den Punkt p mit Radius r   
    * @param p der Mittelpunkt des Kreises
    * @param r Radius des Kreises
    * @param g der grafische Kontext in den sich der Kreis zeichnen soll
    */
   public static void circle(Point p, int r, Graphics g, int faktor) {
      double x, y, d, dx, dxy;
      x = 0;
      y = r;
      d = 1 - r;
      dx = 3;
      dxy = -2 * r + 5;
      while (y >= x) {
         setPixel((int) (0.5 + p.x + x), (int) (0.5 + p.y + y), faktor, g); // alle 8 Oktanden 
         setPixel((int) (0.5 + p.x + y), (int) (0.5 + p.y + x), faktor, g); // gleichzeitig zeichnen
         setPixel((int) (0.5 + p.x + y), (int) (0.5 + p.y - x), faktor, g);
         setPixel((int) (0.5 + p.x + x), (int) (0.5 + p.y - y), faktor, g);
         setPixel((int) (0.5 + p.x - x), (int) (0.5 + p.y - y), faktor, g);
         setPixel((int) (0.5 + p.x - y), (int) (0.5 + p.y - x), faktor, g);
         setPixel((int) (0.5 + p.x - y), (int) (0.5 + p.y + x), faktor, g);
         setPixel((int) (0.5 + p.x - x), (int) (0.5 + p.y + y), faktor, g);

         if (d < 0) {
            d = d + dx;
            dx = dx + 2;
            dxy = dxy + 2;
            x++;
         } else {
            d = d + dxy;
            dx = dx + 2;
            dxy = dxy + 4;
            x++;
            y--;
         }

      }
   }

   /** zeichnet mit Bresenham-Algorithmus  einen Kreis um 
    * den Punkt p mit Radius r   
    * @param p der Mittelpunkt des Kreises
    * @param r Radius des Kreises
    * @param g der grafische Kontext in den sich der Kreis zeichnen soll
    */
   public static void filledCircle(Point p, int r, Graphics g, int faktor) {
      int x, y, d, dx, dxy;
      x = 0;
      y = r;
      d = 1 - r;
      dx = 3;
      dxy = -2 * r + 5;
      while (y >= x) {
         g.setColor(Color.BLACK);
         setPixel(p.x + x, p.y + y, faktor, g); // alle 8 Oktanden 
         setPixel(p.x + y, p.y + x, faktor, g); // gleichzeitig zeichnen
         setPixel(p.x + y, p.y - x, faktor, g);
         setPixel(p.x + x, p.y - y, faktor, g);
         setPixel(p.x - x, p.y - y, faktor, g);
         setPixel(p.x - y, p.y - x, faktor, g);
         setPixel(p.x - y, p.y + x, faktor, g);
         setPixel(p.x - x, p.y + y, faktor, g);

         if (d < 0) {
            d = d + dx;
            dx = dx + 2;
            dxy = dxy + 2;
            x++;
         } else {
            d = d + dxy;
            dx = dx + 2;
            dxy = dxy + 4;
            x++;
            y--;
            // dieses if wird benoetigt, damit die Kreisausschnitte nicht
            // soweit in den Kreis reichen, bis sie sich ueberschneiden.
            // Das = sorgt dafuer, dass ich aus der Differenz x-y die
            // Verkleinerung des Quadrats berechnen kann. Diese ist abhaengig
            // davon, ob der Durchmesser gerade oder ungerade ist und betraegt
            // entweder 1 oder 2. Ohne diese Unterscheidung im Offset kann man
            // nicht erreichen, dass kein Punkt doppelt gezeichnet wird.

            if (y >= x) {

               g.setColor(Color.GREEN);
               line(new Point(p.x + x - 1, p.y + y, faktor), new Point(p.x - x + 1, p.y + y, faktor), g, faktor);

               //g.setColor(Color.GREEN);
               line(new Point(p.x - x + 1, p.y - y, faktor), new Point(p.x + x - 1, p.y - y, faktor), g, faktor);

               //g.setColor(Color.YELLOW);
               line(new Point(p.x + y, p.y + x - 1, faktor), new Point(p.x + y, p.y - x + 1, faktor), g, faktor);

               //g.setColor(Color.MAGENTA);
               line(new Point(p.x - y, p.y + x - 1, faktor), new Point(p.x - y, p.y - x + 1, faktor), g, faktor);

            }
         }

      }

      int offset = x - y;

      //g.setColor(Color.CYAN);
      g.setColor(Color.GREEN);
      for (int l = -x + offset; l < x - offset + 1; l++)
         line(new Point(p.x - x + offset, p.y + l, faktor), new Point(p.x + x - offset, p.y + l, faktor), g, faktor);
   }

/**
 * Methode zum Zeichnen eines gestrichelten Rechtecks
 *
 * @param r Zu zeichnendes Rechteck
 * @param g der Grafikkontext, in den das Rechteck gezeichnet werden soll
 */
public static void dashedRectangle(Rectangle r, Graphics g) {
   int faktor = r.getFactor();
   Point e = r.getE();
   Point a = r.getA();
   Point ur = new Point(e.x, a.y, faktor);
   Point ll = new Point(a.x, e.y, faktor);
   // zeichnen im Uhrzeigersinn ul -> ur -> lr -> ll -> ul
   Bresenham.dashedLine(a, ur, g, faktor);
   Bresenham.dashedLine(ur, e, g, faktor);
   Bresenham.dashedLine(e, ll, g, faktor);
   Bresenham.dashedLine(ll, a, g, faktor);

}

/**
 * Methode zum Zeichnen eines Rechtecks
 *
 * @param r Zu zeichnendes Rechteck
 * @param g der Grafikkontext, in den das Rechteck gezeichnet werden soll
 */
public static void rectangle(Rectangle r, Graphics g) {
   int faktor = r.getFactor();
   Point e = r.getE();
   Point a = r.getA();
   Point ur = new Point(e.x, a.y, faktor);
   Point ll = new Point(a.x, e.y, faktor);
   // zeichnen im Uhrzeigersinn ul -> ur -> lr -> ll -> ul
   Bresenham.line(a, ur, g, faktor);
   Bresenham.line(ur, e, g, faktor);
   Bresenham.line(e, ll, g, faktor);
   Bresenham.line(ll, a, g, faktor);

}

/**
 * Methode zum Zeichnen einer gestrichelten Bresenham-Linie
 *
 * @param dl LineObjekt
 * @param g der Grafikkontext, in den die Linie gezeichnet werden soll
 */
public static void dashedLine(Line dl, Graphics g) {
   dashedLine(dl.getA(), dl.getE(), g, dl.getFactor());
}

/**
 * Methode zum Zeichnen einer gestrichelten Bresenham-Linie
 *
 * @param r Startpunkt der Linie
 * @param q Endpunkt der Linie
 * @param g der Grafikkontext, in den die Linie gezeichnet werden soll
 */
public static void dashedLine(Point r, Point q, Graphics g, int faktor) {
   Point p = new Point(r);

   int error, delta, schwelle, dx, dy, inc_x, inc_y;
   int steps = 0;

   dx = q.getXCoord() - p.getXCoord();
   dy = q.getYCoord() - p.getYCoord();

   if (dx > 0)
      inc_x = 1;
   else
      inc_x = -1;
   if (dy > 0)
      inc_y = 1;
   else
      inc_y = -1;

   if (Math.abs(dy) < Math.abs(dx)) { // flach nach oben oder unten

      error = -Math.abs(dx);
      delta = 2 * Math.abs(dy);
      schwelle = 2 * error;
      while (p.getXCoord() != q.getXCoord()) {
         if (((steps++ / 3) % 2) == 0)
            setPixel(p.getXCoord(), p.getYCoord(), faktor, g);
         p.x += inc_x;
         error = error + delta;
         if (error > 0) {
            p.y += inc_y;
            error = error + schwelle;
         }
      }
   } else // steil nach oben oder unten
      {
      error = -Math.abs(dy);
      delta = 2 * Math.abs(dx);
      schwelle = 2 * error;
      while (p.getYCoord() != q.getYCoord()) {
         if (((steps++ / 3) % 2) == 0)
            setPixel(p.getXCoord(), p.getYCoord(), faktor, g);
         p.y += inc_y;
         error = error + delta;
         if (error > 0) {
            p.x += inc_x;
            error = error + schwelle;
         }
      }
   }
   if (((steps++ / 3) % 2) == 0) {
      setPixel(q.getXCoord(), q.getYCoord(), faktor, g);
   }
}

/**
 * Methode zum Zeichnen einer Bresenham-Linie
 *
 * @param dl LineObjekt
 * @param g der Grafikkontext, in den die Linie gezeichnet werden soll
 */
public static void line(Line dl, Graphics g) {
   line(dl.getA(), dl.getE(), g, dl.getFactor());
}

/**
 * Methode zum Zeichnen einer Bresenham-Linie
 *
 * @param r Startpunkt der Linie
 * @param q Endpunkt der Linie
 * @param g der Grafikkontext, in den die Linie gezeichnet werden soll
 * @param faktor Der Zoomfaktor mit dem die Linie gezeichnet werden soll
 */
public static void line(Point r, Point q, Graphics g, int faktor) {
   Point p = new Point(r);

   int error, delta, schwelle, dx, dy, inc_x, inc_y;
   dx = q.getXCoord() - p.getXCoord();
   dy = q.getYCoord() - p.getYCoord();

   if (dx > 0)
      inc_x = 1;
   else
      inc_x = -1;
   if (dy > 0)
      inc_y = 1;
   else
      inc_y = -1;

   if (Math.abs(dy) < Math.abs(dx)) { // flach nach oben oder unten 
      error = -Math.abs(dx);
      delta = 2 * Math.abs(dy);
      schwelle = 2 * error;
      while (p.getXCoord() != q.getXCoord()) {
         p.paint(g);
         p.x += inc_x;
         error = error + delta;
         if (error > 0) {
            p.y += inc_y;
            error = error + schwelle;
         }
      }
   } else { // steil nach oben oder unten

      error = -Math.abs(dy);
      delta = 2 * Math.abs(dx);
      schwelle = 2 * error;
      while (p.getYCoord() != q.getYCoord()) {
         p.paint(g);
         p.y += inc_y;
         error = error + delta;
         if (error > 0) {
            p.x += inc_x;
            error = error + schwelle;
         }
      }
   }
   q.setFactor(faktor);
   setPixel(q.x, q.y, faktor, g);
}

/**
 * Setzt Pixel in einen grafischen Kontext entsprechend dem Zoomfaktor
 *
 * @param x X-Koordinate des Punktes
 * @param y Y-Koordinate des Punktes
 * @param faktor der zu beruecksichtigende Zoomfaktor
 * @param g der grafische Kontext in dem das Pixel gesetzt werden soll
 */
private static void setPixel(double x, double y, int faktor, Graphics g) {
   //je nach Zoomfaktor ein entsprechend grosses Oval zeichnen
   g.fillOval((int) (0.5 + x) * faktor, (int) (0.5 + y) * faktor, faktor, faktor);
}
}