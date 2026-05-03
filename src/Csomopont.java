import java.util.ArrayList;
import java.util.List;

/**
 * A térkép (gráf) csomópontjait reprezentálja.
 * Kereszteződésként vagy végállomásként funkcionál, ahonnan különböző útszakaszok indulnak ki.
 * Az autók ezekben a csomópontokban döntenek a további útvonalukról.
 */
public class Csomopont {
    /** A csomópont egyedi azonosítója vagy neve (pl. "c1"). */
    private String nev;

    /** A csomópontból kiinduló (kimenő) útszakaszok listája. */
    private List<Utszakasz> kijaratok;

    /**
     * Csomopont konstruktor.
     * @param nev A csomópont neve.
     */
    public Csomopont(String nev) {
        this.nev = nev;
        this.kijaratok = new ArrayList<>();
    }

    /**
     * Hozzáad egy kimenő útszakaszt a csomóponthoz.
     * Ennek segítségével épül fel a várostáblát alkotó úthálózat (gráf).
     * @param u A csomópontból kiinduló új útszakasz.
     */
    public void addKijarat(Utszakasz u) {
        this.kijaratok.add(u);
    }

    // --- Getters ---
    public String getNev() {
        return this.nev;
    }
    
    public List<Utszakasz> getKijaratok() {
        return this.kijaratok;
    }
}