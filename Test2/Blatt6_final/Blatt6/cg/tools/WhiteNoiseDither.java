package cg.tools;

import java.util.Random;

import cg.grOb.Point;

/** 
 * WhiteNoise-Dithering.
 * Es werden zufallswerte erzeugt und mit einem Schwellenwert verglichen.
 * Liegt der Zufallswert unter dem schwellenwert soll der Pixel gesetzt
 * werden, sonst nicht.
 *
 * @version 1.0 (ML 26.04.2004)
 * @author Ralf Kunze
 */
public class WhiteNoiseDither implements DitherMatrix {

    private int stufen;
    private Random r;

    /**
     * Konstruktor, der ein WhiteNoise Objekt erzeugt mit der angabe, wieviel
     * verschiedene Grauwerte dargestellt werden sollen
     *
     * @param stufen Anzahl der unterschiedlichen Grauwerte
     */
    public WhiteNoiseDither(int stufen) {
        this.stufen = stufen;
        r = new Random();
    }

    /**
     * Diese Methode testet, ob ein Punkt gesetzt werden soll oder nicht.
     * 
     * @param p  Der zu testende Punkt
     * @param schwelle der Schwellwert
     * @return true, wenn Pixel eingefaerbt werden soll
     */
    public boolean kleinerSchwelle(Point p, int schwelle) {
        if (schwelle + 1 == stufen)
            return true;
        return (r.nextInt(stufen) < schwelle);
    }
}
