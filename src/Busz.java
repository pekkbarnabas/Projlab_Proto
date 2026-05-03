import java.util.List;

/**
 * A szimulációban játékos által irányított jármű, amelyet a Buszvezető felügyel.
 * A busz nem magától mozog, hanem a felhasználó (Buszvezető) ad ki lépési parancsokat.
 * Célja, hogy eljusson az előre definiált végállomások között.
 */
public class Busz extends Jarmu {
    private int buntetoido;
    private boolean elakadt;

    /** A busz útvonalát meghatározó végállomások listája. */
    private List<Csomopont> vegallomasok;

    /** A csomópont, ahová a busz jelenleg tart. */
    private Csomopont aktualisVegallomas;

    /** A buszt irányító játékos referenciája. */
    private Buszvezeto tulajdonos;

    /**
     * Busz konstruktor.
     * @param vegallomasok A csomópontok listája, amelyek között a busz ingázik.
     */
    public Busz(List<Csomopont> vegallomasok) {
        this.vegallomasok = vegallomasok;
        if (vegallomasok != null && !vegallomasok.isEmpty()) {
            this.aktualisVegallomas = vegallomasok.get(0);
        }
        this.elakadt = false;
        this.buntetoido = 0;
    }

    // --- Getters / Setters ---
    public Buszvezeto getTulajdonos() { return tulajdonos; }
    public void setTulajdonos(Buszvezeto tulajdonos) { this.tulajdonos = tulajdonos; }
    public void setBuntetoido(int b) { this.buntetoido = b; }
    public void setElakadt(boolean e) { this.elakadt = e; }

    /**
     * Beállítja az új útvonalat és rögtön az első érvényes végállomás felé irányítja a buszt.
     */
    public void setUtvonal(List<Csomopont> vegallomasok) {
        this.vegallomasok = vegallomasok;
        if (vegallomasok != null && vegallomasok.size() >= 2) {
            this.aktualisVegallomas = vegallomasok.get(1);
        }
    }

    /**
     * Időlépés hatására a busz magától nem halad, csupán a büntetőideje csökken.
     * A tényleges haladást a lep() metódus végzi játékosi utasításra.
     */
    @Override
    public void idotLep() {
        if (buntetoido > 0) buntetoido--;
    }

    /**
     * Ellenőrzi, hogy a busz elérte-e a megcélzott végállomást.
     * Ha igen, a tulajdonos (Buszvezeto) pontot kap, és a busz irányt vált.
     * @param aktualis A csomópont, ahová a busz épp megérkezett.
     */
    public void vegallomasraEr(Csomopont aktualis) {
        if (aktualis == aktualisVegallomas) {
            // Pontszerzés
            if (tulajdonos != null) tulajdonos.pontotKap();
            
            // Célcsere a két végállomás között
            if (vegallomasok != null && vegallomasok.size() >= 2) {
                if (aktualisVegallomas == vegallomasok.get(0)) {
                    aktualisVegallomas = vegallomasok.get(1);
                } else {
                    aktualisVegallomas = vegallomasok.get(0);
                }
            }
        }
    }

    @Override
    public void elakad() {
        this.elakadt = true;
    }

    /**
     * Ütközést szimulál egy másik járművel.
     * A busz elakad és 3-as büntetőidőt kap.
     * @param masik A másik jármű.
     */
    @Override
    public void utkozik(Jarmu masik) {
        this.elakadt = true;
        this.buntetoido = 3;
    }

    /**
     * A busz megcsúszása jégpáncélon.
     * Jelenleg (tesztkörnyezeti logika szerint) a busz fixen átsodródik a szomszédos sávra,
     * és ha ott áll valaki, akkor ütköznek.
     */
    @Override
    public void megcsuszik() {
        boolean sodrodikE = true; // TODO: Később bekötni a Vezerlo.isRandom flaget, mint az Auto-nál
        
        if (sodrodikE && aktualisSav != null && !aktualisSav.getSzomszedok().isEmpty()) {
            Sav celSav = aktualisSav.getSzomszedok().get(0); // Átsodródik az első szomszédra
            aktualisSav.eltavolit(this);
            
            // Ha a célsáv nem üres, ütközünk az ott lévő járművel
            if (!celSav.getRajtaAllok().isEmpty()) {
                Jarmu masikJarmu = celSav.getRajtaAllok().get(0);
                this.utkozik(masikJarmu);
                masikJarmu.utkozik(this);
            }
            celSav.elfogad(this);
            this.aktualisSav = celSav;
        } else {
            // Ha nem tud átsodródni, elakad a saját sávjában
            this.elakad();
            this.buntetoido = 3;
        }
    }

    /**
     * A játékos (Buszvezető) által kiadott lépési parancs végrehajtása.
     * Csak szomszédos sávra lehet rálépni, és csak akkor, ha a busz nincs elakadva/büntetésben.
     * @param celSav A sáv, ahová a busz lépni próbál.
     * @return Igaz, ha a lépés sikeres és legális volt, egyébként hamis.
     */
    public boolean lep(Sav celSav) {
        // Állapot ellenőrzése
        if (buntetoido > 0 || elakadt) {
            System.out.println("> ERROR: A jarmu jelenleg mozgaskeptelen!");
            return false;
        }

        // --- Szomszédság ellenőrzése ---
        boolean talalt = false;
        if (aktualisSav != null) {
            for (Sav sz : aktualisSav.getSzomszedok()) {
                if (sz == celSav) {
                    talalt = true;
                    break;
                }
            }
        }

        // Ha nincs a szomszédok között, kiírjuk a hibát és kilépünk
        if (!talalt) {
            System.out.println("> ERROR: A cel sav nem szomszedos");
            return false;
        }
        // ----------------------------------------------------------------

        // Ha idáig eljut, akkor legális a lépés!
        if (aktualisSav != null) {
            aktualisSav.eltavolit(this);
        }
        
        celSav.elfogad(this);
        this.aktualisSav = celSav;
        
        // Ellenőrizzük, hogy a lépéssel elértünk-e egy csomópontot (végállomást)
        if (celSav.getUtszakasz() != null) {
            this.aktualisUtszakasz = celSav.getUtszakasz();
            Csomopont elerteCsomopont = celSav.getUtszakasz().getVegpont();
            
            if (elerteCsomopont != null) {
                vegallomasraEr(elerteCsomopont);
            }
        }
        return true;
    }

    public boolean isElakadt() {
        return elakadt;
    }

    public int getBuntetoido() {
        return buntetoido;
    }
}