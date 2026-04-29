import java.util.ArrayList;
import java.util.List;

public class Utszakasz {
    private List<Sav> savok;
    private Csomopont kezdoPont;
    private Csomopont vegPont;
    private int hossz;
    private int magassag; // -1: alagút, 0: normál, 1: híd

    public Utszakasz(Csomopont kezdoPont, Csomopont vegPont, int hossz, int magassag) {
        this.kezdoPont = kezdoPont;
        this.vegPont = vegPont;
        this.hossz = hossz;
        this.magassag = magassag;
        this.savok = new ArrayList<>();
    }

    public void setVegpont(Csomopont vegpont) { this.vegPont = vegpont; }
    public void setKezdoPont(Csomopont kezdopont) { this.kezdoPont = kezdopont; }
    public void setSavok(List<Sav> savok) { this.savok = savok; }
    public void setMagassag(int magassag) { this.magassag = magassag; }

    public List<Sav> getSavok() { return savok; }
    public Csomopont getVegpont() { return vegPont; }
    public Csomopont getKezdopont() { return kezdoPont; }
    public int getMagassag() { return magassag; }
    public int getHossz() { return hossz; }

    public void addSav(Sav s) {
        this.savok.add(s);
        s.setUtszakasz(this); // Automatikusan beállítjuk a kétirányú kapcsolatot
    }
}