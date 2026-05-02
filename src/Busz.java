import java.util.List;

public class Busz extends Jarmu {
    private int buntetoido;
    private boolean elakadt;
    private List<Csomopont> vegallomasok;
    private Csomopont aktualisVegallomas;
    private Buszvezeto tulajdonos;

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
    public void setUtvonal(List<Csomopont> vegallomasok) {
        this.vegallomasok = vegallomasok;
        if (vegallomasok != null && vegallomasok.size() >= 2) {
            this.aktualisVegallomas = vegallomasok.get(1);
        }
    }

    @Override
    public void idotLep() {
        // A busz a tick hatására magától nem megy előre, csak a büntetés csökken
        if (buntetoido > 0) buntetoido--;
    }

    public void vegallomasraEr(Csomopont aktualis) {
        if (aktualis == aktualisVegallomas) {
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

    @Override
    public void utkozik(Jarmu masik) {
        this.elakadt = true;
        this.buntetoido = 3;
    }

    @Override
    public void megcsuszik() {
        boolean sodrodikE = true; // Tesztkörnyezetben ez egy flag lenne
        
        if (sodrodikE && aktualisSav != null && !aktualisSav.getSzomszedok().isEmpty()) {
            Sav celSav = aktualisSav.getSzomszedok().get(0); // Átsodródik az első szomszédra
            aktualisSav.eltavolit(this);
            
            if (!celSav.getRajtaAllok().isEmpty()) {
                Jarmu masikJarmu = celSav.getRajtaAllok().get(0);
                this.utkozik(masikJarmu);
                masikJarmu.utkozik(this);
            }
            celSav.elfogad(this);
            this.aktualisSav = celSav;
        } else {
            this.elakad();
            this.buntetoido = 3;
        }
    }

    public boolean lep(Sav celSav) {
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