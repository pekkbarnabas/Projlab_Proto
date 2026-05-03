import java.util.ArrayList;
import java.util.List;

/**
 * A szimuláció globális időjárását vezérlő osztály.
 * Felelős a havazás mértékének (intenzitásának) tárolásáért, és azért,
 * hogy az idő múlásával havat juttasson a pályán lévő sávokra.
 */
public class Idojaras implements IIdoMulo {
    /** A havazás intenzitása (mennyi hó esik egy időegység alatt). */
    private int intenzitas;

    /** A pályán található összes sáv referenciája, amelyekre a hó eshet. */
    private List<Sav> savok;

    /**
     * Idojaras konstruktor.
     * @param initIntenzitas A havazás kezdeti intenzitása.
     */
    public Idojaras(int initIntenzitas) {
        this.intenzitas = initIntenzitas;
        this.savok = new ArrayList<>();
    }

    /**
     * Beállítja azokat a sávokat, amelyekre az időjárás hatással lesz.
     * Általában a Vezérlő hívja meg a pálya felépítése után.
     */
    public void setIntenzitas(int intenzitas) {
        this.intenzitas = intenzitas;
    }

    public void setSavok(List<Sav> savok) {
        this.savok = savok;
    }

    public int getIntenzitas() {
        return intenzitas;
    }

    /**
     * Az idő múlásával lefutó metódus.
     * Végigmegy az összes ismert sávon, és a beállított intenzitásnak megfelelő
     * mennyiségű havat helyez el rajtuk, kivéve, ha a sáv egy alagútban van.
     */
    @Override
    public void idotLep() {
        for (Sav s : savok) {
            // Alagútban (magassag == -1) nem havazhat!
            if (s.getUtszakasz() != null && s.getUtszakasz().getMagassag() == -1) {
                continue; 
            }
            // Havazás az aktuális sávon
            s.hoNovel(intenzitas);
        }
    }
}