import java.util.ArrayList;
import java.util.List;

/**
 * Az úthálózat legkisebb egysége, egy forgalmi sávot reprezentál.
 * Felelős a fizikai állapotok (hóvastagság, jegesedés, sózottság) tárolásáért
 * és a járművek fogadásakor fellépő fizikai interakciók (elakadás, megcsúszás) kezeléséért.
 */
public class Sav implements IIdoMulo {
    private List<Sav> szomszedok;
    private List<Jarmu> rajtaAllok;
    private Utszakasz utszakasz;
    private Idojaras idojaras;
    
    private double so;
    private int hoVastagsag;
    private boolean jegPancel;
    private boolean blokkolt;
    private int athaladasokSzama;
    private int jegesedesKuszob;
    private int sozasIdozito;
    private boolean zuzalekos;
    private boolean csuszos;
    private ArrayList savok;
    
    /** A hóvastagság azon szintje, ami felett a nem speciális járművek elakadnak. */
    public static final int EXTREM_HO_SZINT = 10;

    /**
     * Sav konstruktor.
     * Alapértelmezetten tiszta állapottal indul, de a csúszósság alapvető tulajdonság, 
     * ha jég alakulna ki rajta.
     */
    public Sav() {
        this.szomszedok = new ArrayList<>();
        this.rajtaAllok = new ArrayList<>();
        this.hoVastagsag = 0;
        this.jegPancel = false;
        this.blokkolt = false;
        this.athaladasokSzama = 0;
        this.jegesedesKuszob = 5;
        this.sozasIdozito = 0;
        this.zuzalekos = false;
        this.csuszos = true;
    }

    // --- Getters / Setters ---
    public void setBlokkolt(boolean blokkolt) { this.blokkolt = blokkolt; }
    public void setSzomszedok(List<Sav> szomszedok) { this.szomszedok = szomszedok; }
    public void setSoMennyiseg(int mennyiseg) { this.so = mennyiseg; }
    public void setAthaladasokSzama(int szam) { this.athaladasokSzama = szam; }
    public void setSozasIdozito(int idozito) { this.sozasIdozito = idozito; }
    public void setHovastagsag(int mennyiseg) { this.hoVastagsag = mennyiseg; }
    public void setUtszakasz(Utszakasz u) { this.utszakasz = u; }
    public void setIdojaras(Idojaras i) { this.idojaras = i; }
    public void setZuzalekos(boolean z) { this.zuzalekos = z; }
    public void setSavok(java.util.Collection<Sav> savok) {
        this.savok = new java.util.ArrayList<>(savok);
    }

    /**
     * Beállítja a jégpáncélt. Ha a sáv jeges, automatikusan csúszóssá is válik.
     */
    public void setJegpancel(boolean jeges) { 
        this.jegPancel = jeges; 
        if (jeges) {
            this.csuszos = true;
        }
    }

    public List<Jarmu> getRajtaAllok() { return rajtaAllok; }
    public List<Sav> getSzomszedok() { return szomszedok; }
    public int getHovastagsag() { return hoVastagsag; }
    public boolean isJegpancel() { return jegPancel; }
    public boolean isBlokkolt() { return blokkolt; }
    public boolean isSozott() { return sozasIdozito > 0; }
    public Utszakasz getUtszakasz() { return utszakasz; }
    public int getSozasIdozito() {return sozasIdozito;}
    public boolean isZuzalekos() { return this.zuzalekos; }

    // --- Fizikai Logika ---
    
    /**
     * Egy jármű rálép a sávra. Itt dől el, hogy elakad-e a hóban vagy megcsúszik-e a jégen.
     * @param j A sávra érkező jármű.
     */
    public void elfogad(Jarmu j) {
        rajtaAllok.add(j);
        athaladasRegisztralasa();
        if (hoVastagsag > EXTREM_HO_SZINT) {
            j.elakad();
        } else if (jegPancel && csuszos) {
            if (zuzalekos && hoVastagsag == 0) {
                // Biztonságos áthaladás, a zúzalék megvéd
            } else {
                //j.megcsuszik();
            }
        }
    }

    /**
     * Eltávolítja a járművet a sáv nyilvántartásából.
     */
    public void eltavolit(Jarmu j) {
        rajtaAllok.remove(j);
    }

    /**
     * Csökkenti a hóvastagságot (takarítás hatására).
     * Ha a hó eléggé lecsökken, a sávon várakozó elakadt járművek újra mozgásképessé válnak.
     */
    public void hoCsokkent(int mennyiseg) {
        this.hoVastagsag -= mennyiseg;
        if (this.hoVastagsag < 0){
            this.hoVastagsag = 0;
        }
        this.zuzalekos = false; // Csökkentés után a zúzalék hatástalanná válik

        if (this.hoVastagsag <= EXTREM_HO_SZINT) {
            for (Jarmu j : rajtaAllok) {
                if (j instanceof Auto) {
                    ((Auto) j).setElakadt(false);
                } else if (j instanceof Busz) {
                    ((Busz) j).setElakadt(false);
                }
            }
        }
    }

    /**
     * Növeli a hóvastagságot (havazás hatására).
     * A friss hó betemeti a zúzalékot, így az hatástalanná válik.
     */
    public void hoNovel(int mennyiseg) {
        this.hoVastagsag += mennyiseg;
        if (this.hoVastagsag > 0) {
            this.zuzalekos = false; // A hó betemeti a zúzalékot!
        }
    }

    /**
     * Megszünteti a sávon a jégpáncélt és a csúszásveszélyt (pl. jégtörő fej használata után).
     */
    public void jegTorese() {
        this.jegPancel = false;
        this.csuszos = false;
    }

    /**
     * Aktiválja a sózás hatását, ami bizonyos idő elteltével felolvasztja a jeget.
     */
    public void sozas() {
        this.sozasIdozito = 3; // 3 tick múlva olvad fel
    }

    /**
     * Aktiválja a sózás hatását, ami bizonyos idő elteltével felolvasztja a jeget.
     */
    public void zuzalekSzoras() {
        this.zuzalekos = true;
    }

    /**
     * Minden áthaladó jármű után nő a számláló. Ha eléri a küszöböt, a sáv letömörödik és eljegesedik.
     */
    public void athaladasRegisztralasa() {
        this.athaladasokSzama++;
        if (this.athaladasokSzama >= this.jegesedesKuszob) {
            setJegpancel(true);
        }
    }

    /**
     * Az idő múlásával a sózás hatása ketyeg lefelé. Amikor lejár, a jég elolvad.
     */
    @Override
    public void idotLep() {
        if (this.sozasIdozito > 0) {
            this.sozasIdozito--;
            if (this.sozasIdozito == 0) {
                setJegpancel(false);
            }
        }
    }

}