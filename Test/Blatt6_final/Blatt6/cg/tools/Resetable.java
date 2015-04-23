package cg.tools;

/**
 * Interface Resetable. Alle Klassen, die dieses Interface implementieren
 * verpflichten sich, dass sie in einen Initialen Zustand zurueckkehren
 * koennen.
 *
 * @author Ralf Kunze
 * @version (ML 19.04.2004)
 */
public interface Resetable {
    /**
     * Die Methode reset versetzt ein Objekt in den Ursprungszustand
     */
    public void reset();
}
