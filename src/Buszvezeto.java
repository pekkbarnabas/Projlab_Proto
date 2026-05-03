import java.util.ArrayList;
import java.util.List;

/**
 * A szimulációban a Buszokat irányító játékos.
 * Felelős a saját buszainak nyilvántartásáért és a sikeres utakért járó pontszám gyűjtéséért.
 */
public class Buszvezeto {
    /** A játékos által eddig megszerzett pontok száma. */
    private int pontszam;

    /** A játékoshoz (buszvezetőhöz) tartozó buszok listája. */
    private List<Busz> buszok;

    /**
     * Buszvezeto konstruktor.
     * Inicializálja a pontszámot nullára, és létrehozza a buszok üres listáját.
     */
    public Buszvezeto() {
        this.pontszam = 0;
        this.buszok = new ArrayList<>();
    }

    /**
     * Növeli a buszvezető pontszámát eggyel.
     * Ezt a metódust jellemzően a Busz hívja meg, amikor sikeresen elér egy végállomást.
     */
    public void pontotKap() {
        this.pontszam++;
    }

    /**
     * Hozzárendel egy buszt a buszvezetőhöz.
     * Kétirányú kapcsolatot épít ki: a buszt beteszi a vezető listájába, 
     * a busznak pedig beállítja a tulajdonosát erre a vezetőre.
     * @param b A hozzáadandó Busz objektum.
     */
    public void busztHozzaad(Busz b) {
        if (!buszok.contains(b)) {
            buszok.add(b);
        }

        // Ha a busz még nem tudja, hogy ez a vezető a tulajdonosa, beállítjuk
        if (b.getTulajdonos() != this) {
            b.setTulajdonos(this);
        }
    }

    public int getPontszam() {
        return pontszam;
    }
}