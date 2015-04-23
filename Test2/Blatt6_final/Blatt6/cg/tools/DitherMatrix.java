package cg.tools;

import cg.grOb.Point;

/**
 * Ein Interface fuer allgemeines Dithering
 * 
 * @version 1.0 (A 26.04.2004)
 * @author Ralf Kunze
 */
public interface DitherMatrix {

   /**Liefert true, wenn an der dem erhaltenen Punkt entsprechenden Stelle
    * ein Pixel gesetzt werden sollte, um den gewuenschten Grauwert
    * anzunaehern.
    *
    * @param p wird in die Matrix abgebildet. Das gefundene
    * Matrixelement wird mit der Schwelle verglichen.
    * @param schwelle Lieggt der gefundene Wert unter schwelle wird true geliefert
    * @return true: falls schwelle kleiner als Matrixwert; sonst false
    */
   public boolean kleinerSchwelle(Point p, int schwelle);

}
