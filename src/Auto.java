import java.util.*;

/**
 * A szimulációban közlekedő alapvető járműtípus.
 * Dijkstra-algoritmussal képes útvonalat tervezni a lakása és a munkahelye között,
 * illetve reagál a csúszós utakra és a blokkolt sávokra.
 */
public class Auto extends Jarmu {
    private boolean elakadt;
    private int buntetoido;
    private boolean tesztSodrodas;
    private Csomopont lakas;
    private Csomopont munkahely;
    private Csomopont aktualisCel;

    /**
     * Auto konstruktor.
     * @param lakas A jármű kiindulópontja.
     * @param munkahely A jármű célpontja.
     * @param tesztSodrodas Determinisztikus tesztekhez használt flag a megcsúszás irányítására.
     */
    public Auto(Csomopont lakas, Csomopont munkahely, boolean tesztSodrodas) {
        this.lakas = lakas;
        this.munkahely = munkahely;
        this.aktualisCel = munkahely; // Alapértelmezetten a munkahelyre indul
        this.tesztSodrodas = tesztSodrodas;
        this.elakadt = false;
        this.buntetoido = 0;
        this.pozicio = 0.0f;
    }

    // --- Getters / Setters ---
    public void setElakadt(boolean e) { this.elakadt = e; }
    public void setBuntetoido(int b) { this.buntetoido = b; }
    public void setTesztSodrodas(boolean t) { this.tesztSodrodas = t; }

    /**
     * Beállítja vagy módosítja az autó útvonalának végpontjait.
     */
    public void setUtvonal(Csomopont lakas, Csomopont munkahely) {
        this.lakas = lakas;
        this.munkahely = munkahely;
        this.aktualisCel = munkahely;
    }

    /**
     * Elakasztja az autót, meggátolva a további haladást.
     */
    @Override
    public void elakad() {
        this.elakadt = true;
    }

    /**
     * Ütközést szimulál egy másik járművel.
     * Mindkét jármű elakad és globális baleseti büntetőidőt kap.
     * @param masik A másik jármű, amellyel az ütközés történik.
     */
    @Override
    public void utkozik(Jarmu masik) {
        this.setElakadt(true);
        this.setBuntetoido(3); // Globális baleseti büntetőidő
    }

    /**
     * Átmozgatja az autót egy másik sávra, frissítve a sávok nyilvántartását.
     * @param ujSav A sáv, amelyre az autó átlép.
     */
    public void savotValt(Sav ujSav) {
        if (aktualisSav != null) {
            aktualisSav.eltavolit(this);
        }
        ujSav.elfogad(this);
        this.aktualisSav = ujSav;
        this.aktualisUtszakasz = ujSav.getUtszakasz();
    }

    /**
     * Kezeli az autó jégen való megcsúszását.
     * Normál esetben 50% eséllyel átsodródik egy szomszédos sávba. 
     * Teszt módban (Vezerlo.isRandom == false) a tesztSodrodas változó garantálja a kimenetet.
     */
    @Override
    public void megcsuszik() {
        boolean atcsuszik = false;

        // Ha a játék normál módban fut (Random ON)
        if (Vezerlo.isRandom) { 
            Random rand = new Random();
            atcsuszik = rand.nextBoolean(); // nem fix, 50-50% esély!
        } 
        // Ha teszt módban vagyunk (Random OFF)
        else {
            // Itt viszont nem sorsolunk, hanem szigorúan azt csináljuk, amit a teszt bemenet mond!
            atcsuszik = this.tesztSodrodas; 
        }

        // Átsodródik a szomszédba (ha az 50% így dobta, ÉS van szomszédos sáv)
        if (atcsuszik && aktualisSav != null && !aktualisSav.getSzomszedok().isEmpty()) {
            Sav s2 = aktualisSav.getSzomszedok().get(0);
            aktualisSav.eltavolit(this);
            s2.elfogad(this);
            this.aktualisSav = s2;
            this.aktualisUtszakasz = s2.getUtszakasz();

            // Ütközés az új sávon lévőkkel
            List<Jarmu> rajta = new ArrayList<>(s2.getRajtaAllok());
            for (Jarmu j : rajta) {
                if (j != this) {
                    j.utkozik(this);
                    this.utkozik(j);
                    break;
                }
            }
        } 
        // Marad a saját sávjában, de elakad
        else {
            this.elakad(); 
            this.buntetoido = 1;

            if (aktualisSav != null) {
                List<Jarmu> rajta = new ArrayList<>(aktualisSav.getRajtaAllok());
                for (Jarmu j : rajta) {
                    if (j != this) {
                        j.utkozik(this);
                        break; 
                    }
                }
            }
        }
    }

    /**
     * Az idő múlásával frissíti az autó állapotát (haladás, büntetés csökkentése, útvonaltervezés).
     */
    @Override
    public void idotLep() {
        // Büntetőidő csökkentése, ha van
        if (buntetoido > 0) {
            buntetoido--;
            return;
        }
        
        if (elakadt) return;

        boolean blokkolt = false;
        if (aktualisSav != null) {
            blokkolt = aktualisSav.isBlokkolt();
        }

        // Ha jégen van, azonnal megcsúszik
        if (aktualisSav != null && aktualisSav.isJegpancel()) {
            this.megcsuszik();
            return;
        }

        // Kikerülés logikája
        if (blokkolt && aktualisSav != null) {
            List<Sav> szomszedok = aktualisSav.getSzomszedok();
            for (Sav szomszedos : szomszedok) {
                if (!szomszedos.isBlokkolt()) {
                    savotValt(szomszedos);
                    blokkolt = false; // Sikeresen kikerültük az akadályt
                    break;
                }
            }
        }

        // Haladás és újratervezés
        if (!blokkolt) {
            pozicio += 0.1f;
            
            // Ha elérte a sáv (útszakasz) végét
            if (pozicio >= 1.0f) {
                if (aktualisUtszakasz != null) {
                    Csomopont elerteCsomopont = aktualisUtszakasz.getVegpont();
                    
                    if (elerteCsomopont != null) {
                        // Célcsere
                        if (elerteCsomopont == aktualisCel) {
                            aktualisCel = (aktualisCel == lakas) ? munkahely : lakas;
                        }
                        
                        // Új sáv keresése a következő célhoz
                        Sav ujSav = utvonalatTervez(elerteCsomopont);
                        if (ujSav != null) {
                            savotValt(ujSav);
                            pozicio = 0.0f; // Újra a sáv elején van
                        } else {
                            pozicio = 1.0f; // Várakozik a csomópontban
                        }
                    }
                }
            }
        }
    }

    /**
     * Dijkstra algoritmus implementációja a legrövidebb út megtalálására az aktuális célig.
     * @param start A csomópont, ahonnan a tervezés indul.
     * @return A következő utszakasz első szabad sávja, vagy null, ha nincs elérhető út.
     */
    private Sav utvonalatTervez(Csomopont start) {
        Map<Csomopont, Integer> tavolsagok = new HashMap<>();
        Map<Csomopont, Csomopont> elozoCsomopont = new HashMap<>();
        Map<Csomopont, Utszakasz> elozoUtszakasz = new HashMap<>();

        // Prioritási sor a legkisebb távolságú csomópontok kiválasztására
        PriorityQueue<Csomopont> latogatatlan = new PriorityQueue<>(
            Comparator.comparingInt(c -> tavolsagok.getOrDefault(c, Integer.MAX_VALUE))
        );

        tavolsagok.put(start, 0);
        latogatatlan.add(start);

        while (!latogatatlan.isEmpty()) {
            Csomopont aktualis = latogatatlan.poll();
            if (aktualis == aktualisCel) break; // Megtaláltuk a célt

            for (Utszakasz k : aktualis.getKijaratok()) {
                Csomopont szomszed = k.getVegpont();
                int ujTavolsag = tavolsagok.getOrDefault(aktualis, Integer.MAX_VALUE) + k.getHossz();

                if (ujTavolsag < tavolsagok.getOrDefault(szomszed, Integer.MAX_VALUE)) {
                    tavolsagok.put(szomszed, ujTavolsag);
                    elozoCsomopont.put(szomszed, aktualis);
                    elozoUtszakasz.put(szomszed, k);
                    
                    latogatatlan.remove(szomszed); // Frissítéshez kivesszük
                    latogatatlan.add(szomszed);
                }
            }
        }

        // Visszakövetés a startpontig
        Csomopont bejarat = aktualisCel;
        if (!elozoCsomopont.containsKey(aktualisCel)) return null; // Nincs út

        while (elozoCsomopont.get(bejarat) != start) {
            bejarat = elozoCsomopont.get(bejarat);
            if (bejarat == null) return null;
        }

        // A startpontból kivezető útszakasz megtalálása
        Utszakasz kovetkezoUtszakasz = elozoUtszakasz.get(bejarat);
        if (kovetkezoUtszakasz != null) {
            for (Sav s : kovetkezoUtszakasz.getSavok()) {
                if (!s.isBlokkolt()) return s; // Első szabad sáv kiválasztása
            }
        }
        return null;
    }

    public boolean isElakadt() {
        return this.elakadt;
    }

    public int getBuntetoido() {
        return this.buntetoido;
    }

    public Csomopont getLakas() { return this.lakas; }

    public Csomopont getMunkahely() { return this.munkahely; }

    public boolean isTesztSodrodas() {
        return this.tesztSodrodas;
    }
}