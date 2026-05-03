import java.util.ArrayList;
import java.util.List;

/**
 * A térkép gráfjának éleit reprezentálja. 
 * Két csomópontot köt össze, és fizikai keretet ad a rajta lévő sávoknak.
 * Meghatározza az út típusát (alagút, híd, normál), ami alapvető az időjárási hatások szempontjából.
 */
public class Utszakasz {
    /** Az útszakaszon futó forgalmi sávok listája. */
    private List<Sav> savok;

    /** Az útszakasz kezdőpontja (gráf csúcs). */
    private Csomopont kezdoPont;

    /** Az útszakasz végpontja (gráf csúcs). */
    private Csomopont vegPont;

    /** Az útszakasz hossza, amelyet a Dijkstra algoritmus súlyként használ az útvonaltervezésnél. */
    private int hossz;

    /** 
     * Az útszakasz tengerszint feletti elhelyezkedése:
     * -1: alagút (ide nem esik hó), 0: normál út, 1: híd.
     */
    private int magassag;

    /**
     * Utszakasz konstruktor.
     * @param kezdoPont A kiindulási csomópont.
     * @param vegPont A cél csomópont.
     * @param hossz Az út hossza egységekben.
     * @param magassag Az út típusa (-1, 0, 1).
     */
    public Utszakasz(Csomopont kezdoPont, Csomopont vegPont, int hossz, int magassag) {
        this.kezdoPont = kezdoPont;
        this.vegPont = vegPont;
        this.hossz = hossz;
        this.magassag = magassag;
        this.savok = new ArrayList<>();
    }

    // --- Getters & Setters ---
    public void setVegpont(Csomopont vegpont) { this.vegPont = vegpont; }
    public void setKezdoPont(Csomopont kezdopont) { this.kezdoPont = kezdopont; }
    public void setSavok(List<Sav> savok) { this.savok = savok; }
    public void setMagassag(int magassag) { this.magassag = magassag; }

    public List<Sav> getSavok() { return savok; }
    public Csomopont getVegpont() { return vegPont; }
    public Csomopont getKezdopont() { return kezdoPont; }
    public int getMagassag() { return magassag; }
    public int getHossz() { return hossz; }

    /**
     * Új sávot ad az útszakaszhoz, és automatikusan beállítja a kétirányú kapcsolatot:
     * a sáv is tudni fogja, melyik útszakaszhoz tartozik.
     * @param s A hozzáadandó sáv.
     */
    public void addSav(Sav s) {
        this.savok.add(s);
        s.setUtszakasz(this); // Automatikusan beállítjuk a kétirányú kapcsolatot
    }
}