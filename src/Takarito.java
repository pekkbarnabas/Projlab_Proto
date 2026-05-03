import java.util.ArrayList;
import java.util.List;

/**
 * A szimulációban a takarítási folyamatokat koordináló játékos.
 * Felelős a pénzügyek kezeléséért, a saját hókotró flotta menedzseléséért, 
 * valamint a raktárkészlet és a bolt közötti interakciókért.
 */
public class Takarito {
    /** A takarító aktuális egyenlege. */
    private int penz;

    /** A takarító tulajdonában lévő hókotrók listája. */
    private List<Hokotro> flotta;

    /** A takarítóhoz tartozó raktár, ahol az erőforrásokat tárolja. */
    private Raktar raktar;

    /** A bolt, ahol a takarító vásárolni tud. */
    private Bolt bolt;

    /**
     * Takarito konstruktor.
     * Inicializálja az üres flottát és a kezdőtőkét.
     */
    public Takarito() {
        this.flotta = new ArrayList<>();
        this.penz = 0;
    }

    // --- Getters & Setters ---
    public void setRaktar(Raktar raktar) { this.raktar = raktar; }
    public void setBolt(Bolt bolt) { this.bolt = bolt; }
    public void setPenz(int penz) { this.penz = penz; }
    public Raktar getRaktar() { return raktar; }
    public int getPenz() { return penz; }

    /**
     * Új hókotrót ad a takarító flottájához.
     * @param h A hozzáadandó Hokotro példány.
     */
    public void addHokotro(Hokotro h) {
        flotta.add(h);
    }

    /**
     * Jutalmat ad a takarítónak a sikeresen elvégzett munka után.
     * Jelenleg fixen 100 egységnyi pénzt kap minden érdemi takarítási lépésért.
     */
    public void penztKap() {
        this.penz += 100; // Példa jutalom takarításért
    }

    public boolean fizet(int ar) {
        if (penz >= ar) {
            penz = penz - ar;
            return true;
        }
        return false;
    }

    /**
     * Árucikk vásárlása a boltból.
     * @param a A megvásárolni kívánt árucikk típusa.
     * @return Igaz, ha a bolt sikeresen eladta a terméket.
     */
    public boolean vasarol(Arucikk a) {
        if (bolt != null) {
            return bolt.elad(this, a);
        }
        return false;
    }

    /**
     * Eszköz (kotrófej) kiosztása a flotta egyik járművére.
     * @param h A cél jármű a flottából.
     * @param ujFej A felszerelni kívánt kotrófej.
     */
    public void eszkozkiosztas(Hokotro h, IKotrofej ujFej) {
        if (flotta.contains(h)) {
            h.fejetCserel(ujFej);
        }
    }
}