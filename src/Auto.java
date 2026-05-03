import java.util.*;

public class Auto extends Jarmu {
    private boolean elakadt;
    private int buntetoido;
    private boolean tesztSodrodas;
    private Csomopont lakas;
    private Csomopont munkahely;
    private Csomopont aktualisCel;

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
    public void setUtvonal(Csomopont lakas, Csomopont munkahely) {
        this.lakas = lakas;
        this.munkahely = munkahely;
        this.aktualisCel = munkahely;
    }

    @Override
    public void elakad() {
        this.elakadt = true;
    }

    @Override
    public void utkozik(Jarmu masik) {
        this.setElakadt(true);
        this.setBuntetoido(3); // Globális baleseti büntetőidő
    }

    public void savotValt(Sav ujSav) {
        if (aktualisSav != null) {
            aktualisSav.eltavolit(this);
        }
        ujSav.elfogad(this);
        this.aktualisSav = ujSav;
        this.aktualisUtszakasz = ujSav.getUtszakasz();
    }

    @Override
    public void megcsuszik() {
        boolean atcsuszik = false;

        // Ha a random be van kapcsolva, 50% eséllyel átsodródik
        if (tesztSodrodas) {
            Random rand = new Random();
            atcsuszik = rand.nextBoolean(); // true vagy false (50-50 esély)
        }else{
            atcsuszik = false;
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
        // Marad a saját sávjában (Random OFF, vagy a Random ON úgy sorsolta)
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


    @Override
    public void idotLep() {
        if (buntetoido > 0) {
            buntetoido--;
            return;
        }
        
        if (elakadt) return;

        boolean blokkolt = false;
        if (aktualisSav != null) {
            blokkolt = aktualisSav.isBlokkolt();
        }

        if (aktualisSav != null && aktualisSav.isJegpancel()) {
            this.megcsuszik();
            return;
        }

        // Kikerülés logikája (strukturált vezérlés)
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
            
            if (pozicio >= 1.0f) {
                if (aktualisUtszakasz != null) {
                    Csomopont elerteCsomopont = aktualisUtszakasz.getVegpont();
                    
                    if (elerteCsomopont != null) {
                        // Célcsere
                        if (elerteCsomopont == aktualisCel) {
                            aktualisCel = (aktualisCel == lakas) ? munkahely : lakas;
                        }
                        
                        Sav ujSav = utvonalatTervez(elerteCsomopont);
                        if (ujSav != null) {
                            savotValt(ujSav);
                            pozicio = 0.0f;
                        } else {
                            pozicio = 1.0f; // Várakozik a csomópontban
                        }
                    }
                }
            }
        }
    }

    // A Dijkstra algoritmus implementációja
    private Sav utvonalatTervez(Csomopont start) {
        Map<Csomopont, Integer> tavolsagok = new HashMap<>();
        Map<Csomopont, Csomopont> elozoCsomopont = new HashMap<>();
        Map<Csomopont, Utszakasz> elozoUtszakasz = new HashMap<>();
        PriorityQueue<Csomopont> latogatatlan = new PriorityQueue<>(
            Comparator.comparingInt(c -> tavolsagok.getOrDefault(c, Integer.MAX_VALUE))
        );

        tavolsagok.put(start, 0);
        latogatatlan.add(start);

        while (!latogatatlan.isEmpty()) {
            Csomopont aktualis = latogatatlan.poll();
            if (aktualis == aktualisCel) break;

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