import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Raktar {
    private List<IKotrofej> fejek;
    private Map<Arucikk, Integer> keszletek;

    public Raktar() {
        fejek = new ArrayList<>();
        keszletek = new HashMap<>();
        // Alapértelmezésben minden árucikkből 0 van
        for (Arucikk a : Arucikk.values()) {
            keszletek.put(a, 0);
        }
    }

    public void hozzaadFej(IKotrofej f) {
        fejek.add(f);
    }

    public void eroforrasBovit(Arucikk targy, int mennyiseg) {
        int jelenlegi = keszletek.getOrDefault(targy, 0);
        keszletek.put(targy, jelenlegi + mennyiseg);
    }

    public boolean eroforrasCsokkent(Arucikk targy, int mennyiseg) {
        int jelenlegi = keszletek.getOrDefault(targy, 0);
        if (jelenlegi >= mennyiseg) {
            keszletek.put(targy, jelenlegi - mennyiseg);
            return true;
        }
        return false;
    }
    public int getKeszlet(Arucikk cikk) {
        return keszletek.getOrDefault(cikk, 0);
    }
}