import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Takarítóhoz tartozó tárolóegység, amely nyilvántartja a birtokolt felszereléseket és erőforrásokat.
 * Itt tárolódnak a megvásárolt kotrófejek (IKotrofej) és a fogyóeszközök (Arucikk), mint a só vagy a kerozin.
 */
public class Raktar {
    /** A birtokolt kotrófejek listája. */
    private List<IKotrofej> fejek;

    /** Az egyes árucikkekből (erőforrásokból) rendelkezésre álló mennyiségek. */
    private Map<Arucikk, Integer> keszletek;

    /**
     * Raktar konstruktor.
     * Inicializálja a listákat és a készleteket minden ismert árucikkre nullázva.
     */
    public Raktar() {
        fejek = new ArrayList<>();
        keszletek = new HashMap<>();
        // Alapértelmezésben minden árucikkből 0 van
        for (Arucikk a : Arucikk.values()) {
            keszletek.put(a, 0);
        }
    }

    /**
     * Új kotrófej hozzáadása a raktárkészlethez.
     * @param f A hozzáadandó felszerelés.
     */
    public void hozzaadFej(IKotrofej f) {
        fejek.add(f);
    }

    /**
     * Növeli egy adott típusú erőforrás (pl. zúzottkő) mennyiségét a raktárban.
     * @param targy A bővítendő árucikk típusa.
     * @param mennyiseg A hozzáadandó mennyiség.
     */
    public void eroforrasBovit(Arucikk targy, int mennyiseg) {
        int jelenlegi = keszletek.getOrDefault(targy, 0);
        keszletek.put(targy, jelenlegi + mennyiseg);
    }

    /**
     * Csökkenti az erőforrás mennyiségét a raktárban, ha van belőle elegendő.
     * Ezt a metódust a Hókotró hívja meg munkavégzés közben.
     * @param targy A felhasználni kívánt árucikk.
     * @param mennyiseg A levonandó mennyiség.
     * @return Igaz, ha volt elegendő készlet és a levonás sikeres, különben hamis.
     */
    public boolean eroforrasCsokkent(Arucikk targy, int mennyiseg) {
        int jelenlegi = keszletek.getOrDefault(targy, 0);

        // Ellenőrizzük, hogy rendelkezésre áll-e a kért mennyiség
        if (jelenlegi >= mennyiseg) {
            keszletek.put(targy, jelenlegi - mennyiseg);
            return true;
        }
        return false; // Nincs elég erőforrás a művelethez
    }

    /**
     * Visszaadja egy adott árucikkből raktáron lévő mennyiséget.
     */
    public int getKeszlet(Arucikk cikk) {
        return keszletek.getOrDefault(cikk, 0);
    }
}