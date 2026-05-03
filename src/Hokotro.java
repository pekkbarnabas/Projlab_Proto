/**
 * A hókotró járművet reprezentálja, amelyet egy Takarító játékos irányít.
 * Különböző kotrófejekkel (IKotrofej) szerelhető fel, amelyekkel eltérő típusú
 * karbantartási munkákat (hóeltakarítás, jégtörés, sózás, zúzalékszórás) tud végezni a sávokon.
 */
public class Hokotro extends Jarmu {
    /** Az aktuálisan felszerelt kotrófej, amely meghatározza, milyen munkát végez a gép. */
    private IKotrofej fej;

    /** A hókotrót irányító játékos. Ő kapja a fizetséget és az ő raktárából fogynak az anyagok. */
    private Takarito tulajdonos;

    /**
     * Hokotro konstruktor.
     * @param tulajdonos A járművet birtokló és irányító Takarító.
     */
    public Hokotro(Takarito tulajdonos) {
        this.tulajdonos = tulajdonos;
        this.fej = null;
    }

    /**
     * Lecseréli a hókotró aktuális felszerelését egy újra.
     */
    public void fejetCserel(IKotrofej ujFej) {
        this.fej = ujFej;
    }

    /**
     * Kerozint fogyaszt a tulajdonos raktárából (pl. a Sárkányfej működtetéséhez).
     * @return Igaz, ha volt elég kerozin és a levonás sikeres, különben hamis.
     */
    public boolean kerozintFogyaszt(int mennyiseg) {
        if (tulajdonos != null && tulajdonos.getRaktar() != null) {
            boolean siker = tulajdonos.getRaktar().eroforrasCsokkent(Arucikk.KEROZIN, mennyiseg);
            if (siker) {
                return true;
            }
        }
        System.out.println("> ERROR: Nincs elegendo kerozin");
        return false;
    }

    /**
     * Zúzottkövet fogyaszt a tulajdonos raktárából.
     */
    public boolean zuzalekotFogyaszt(int mennyiseg) {
        if (tulajdonos != null && tulajdonos.getRaktar() != null) {
            boolean siker = tulajdonos.getRaktar().eroforrasCsokkent(Arucikk.ZUZOTTKO, mennyiseg);
            if (siker) {
                return true;
            }
        }
        System.out.println("> ERROR: Nincs elegendo zuzottko");
        return false;
    }

    /**
     * Sót fogyaszt a tulajdonos raktárából.
     */
    public boolean sotFogyaszt(int mennyiseg) {
        if (tulajdonos != null && tulajdonos.getRaktar() != null) {
            boolean siker = tulajdonos.getRaktar().eroforrasCsokkent(Arucikk.SO, mennyiseg);
            if (siker) {
                return true;
            }
        }
        System.out.println("> ERROR: Nincs elegendo so");
        return false;
    }

    /**
     * Elvégzi a takarítási műveletet az aktuális sávon a felszerelt fej segítségével.
     * Ha a munka tényleges változást idézett elő (pl. eltűnt a jég, csökkent a hó), a tulajdonos pénzt kap.
     * @return Igaz, ha a művelet lefutott (volt elég nyersanyag).
     */
    public boolean takarit() {
        if (fej != null && aktualisSav != null) {
            // Elmentjük a sáv eredeti állapotát a munka megkezdése előtt
            int hoElotte = aktualisSav.getHovastagsag();
            boolean jegElotte = aktualisSav.isJegpancel();
            boolean zuzalekElotte = aktualisSav.isZuzalekos();
            int sozasElotte = aktualisSav.getSozasIdozito();

            // Meghívjuk a felszerelt kotrófej specifikus munkáját
            boolean sikeresMunka = fej.dolgozik(aktualisSav, this);
            
            // Ha valamiért nem tudott dolgozni (pl. elfogyott a kerozin)
            if (!sikeresMunka) {
                return false;
            }

            // Megvizsgáljuk, történt-e ÉRDEMI változás a sávon
            boolean tortentMunka = 
                (aktualisSav.getHovastagsag() < hoElotte) ||       
                (jegElotte && !aktualisSav.isJegpancel()) ||       
                (!zuzalekElotte && aktualisSav.isZuzalekos()) ||   
                (sozasElotte < aktualisSav.getSozasIdozito());     
                
            // Ha hasznos munkát végzett, a takarító fizetséget kap
            if (tortentMunka && tulajdonos != null) {
                tulajdonos.penztKap();
            }
            return true;
        }
        return false;
    }

    /**
     * A hókotró átléptetése egy szomszédos sávra.
     * @param cel A cél sáv, ahová lépni szeretnénk.
     * @return Igaz, ha a lépés legális volt és sikeresen végrehajtódott.
     */
    public boolean lep(Sav cel) {
        boolean talalt = false;
        // Szomszédság ellenőrzése
        if (aktualisSav != null) {
            for (Sav sz : aktualisSav.getSzomszedok()) {
                if (sz == cel) {
                    talalt = true;
                    break;
                }
            }
        }
        
        // Ha megtaláltuk a szomszédok között, átlépünk
        if (talalt) {
            aktualisSav.eltavolit(this);
            cel.elfogad(this);
            aktualisSav = cel;
            pozicio = 0;
            return true;
        } else {
            System.out.println("> ERROR: A cel sav nem szomszedos");
            return false;
        }
    }

    // --- Járműtől örökölt, hókotrókra specifikusan később kidolgozandó fizikai viselkedések ---
    @Override public void idotLep() { }
    @Override public void elakad() { }
    @Override public void megcsuszik() { }
    @Override public void utkozik(Jarmu masik) { }

    public Object getFej() {
        return fej;
    }
}