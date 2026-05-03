import java.util.HashMap;
import java.util.Map;

/**
 * A szimulációban szereplő virtuális bolt.
 * Itt a takarítók a megszerzett pénzükből különböző felszereléseket 
 * és erőforrásokat (pl. zúzottkő, kerozin) vásárolhatnak.
 */
public class Bolt {
    /** Az árucikkekhez tartozó árakat tároló térkép. */
    private Map<Arucikk, Integer> arak;

    /**
     * Bolt konstruktor.
     * Létrehozza a boltot és feltölti az alapértelmezett (teszt) árakkal.
     */
    public Bolt() {
        arak = new HashMap<>();
        // Alapértelmezett árucikkek és áraik inicializálása
        arak.put(Arucikk.ZUZOTTKO, 100);
        arak.put(Arucikk.SO, 50);
        arak.put(Arucikk.KEROZIN, 200);
        arak.put(Arucikk.HOKOTRO, 1000);
    }

    /**
     * Lebonyolít egy vásárlási tranzakciót egy vevő és a bolt között.
     * @param vevo A takarító, aki vásárolni szeretne.
     * @param targy A megvásárolni kívánt árucikk.
     * @return Igaz, ha a vásárlás sikeres volt (volt elég pénz), különben hamis.
     */
    public boolean elad(Takarito vevo, Arucikk targy) {
        // Ha az árucikk nem kapható, a tranzakció sikertelen
        if (!arak.containsKey(targy)) return false;
        int ar = arak.get(targy);
        // Megpróbáljuk levonni az összeget a vevőtől
        boolean sikeres = vevo.fizet(ar);
        
        if (sikeres) {
            // Ha volt elég pénze, a termék bekerül a raktárába (1 darab)
            vevo.getRaktar().eroforrasBovit(targy, 1);
            return true;
        } else {
            // Ha nincs elég pénz, hibaüzenetet küldünk a konzolra (tesztekhez szükséges)
            System.out.println("> ERROR: Nincs elegendo penz");
            return false; 
        }
    }
}