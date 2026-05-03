/**
 * Olyan kotrófej, amellyel a hókotró zúzottkövet (zúzalékot) tud szórni a sávra.
 * A zúzalék környezetbarát módon meggátolja a járművek megcsúszását a jégen,
 * de a hatása megszűnik, ha újabb hóréteg esik rá.
 */
public class KoszoroFej implements IKotrofej {
    /**
     * Elvégzi a zúzalékszórási munkát a paraméterként kapott sávon.
     * Működéséhez 1 egységnyi zúzottkő szükséges a takarító raktárából.
     * 
     * @param s A sáv, amelyre a zúzalékot szórjuk.
     * @param h A hókotró jármű, amely ezt a fejet használja és a nyersanyagot biztosítja.
     * @return Igaz, ha volt elég zúzottkő és a szórás sikeres volt.
     */
    @Override
    public boolean dolgozik(Sav s, Hokotro h) {
        // Megpróbálunk levonni 1 egység zúzottkövet a raktárból
        boolean sikeresFogyasztas = h.zuzalekotFogyaszt(1);
        if (sikeresFogyasztas) {
            // Ha volt elég nyersanyag, a sáv csúszásgátló réteget kap
            s.setZuzalekos(true);
            return true;
        }
        // Ha nem volt elég nyersanyag, a munka meghiúsul
        return false;
    }
}