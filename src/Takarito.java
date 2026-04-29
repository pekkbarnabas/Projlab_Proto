import java.util.ArrayList;
import java.util.List;

public class Takarito {
    private int penz;
    private List<Hokotro> flotta;
    private Raktar raktar;
    private Bolt bolt;

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

    public void addHokotro(Hokotro h) {
        flotta.add(h);
    }

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

    public void vasarol(Arucikk a) {
        if (bolt != null) {
            boolean sikeres = bolt.elad(this, a);
            if (!sikeres) {
                System.out.println("> ERROR: Nincs elegendo penz");
            }
        }
    }

    public void eszkozkiosztas(Hokotro h, IKotrofej ujFej) {
        if (flotta.contains(h)) {
            h.fejetCserel(ujFej);
        }
    }
}