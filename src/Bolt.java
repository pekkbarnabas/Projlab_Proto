import java.util.HashMap;
import java.util.Map;

public class Bolt {
    private Map<Arucikk, Integer> arak;

    public Bolt() {
        arak = new HashMap<>();
        // Tesztárak inicializálása
        arak.put(Arucikk.ZUZOTTKO, 100);
        arak.put(Arucikk.SO, 50);
        arak.put(Arucikk.KEROZIN, 200);
        arak.put(Arucikk.HOKOTRO, 1000);
    }

    public boolean elad(Takarito vevo, Arucikk targy) {
        if (!arak.containsKey(targy)) return false;
        int ar = arak.get(targy);
        boolean sikeres = vevo.fizet(ar);
        
        if (sikeres) {
            vevo.getRaktar().eroforrasBovit(targy, 1);
            return true;
        } else {
            System.out.println("> ERROR: Nincs elegendo penz");
            return false; 
        }
    }
}