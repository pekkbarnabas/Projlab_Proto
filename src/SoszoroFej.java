/**
 * Olyan kotrófej, amellyel a hókotró sót tud szórni a sávra.
 * A sózás segít megolvasztani a havat és megakadályozza a jégpáncél kialakulását, 
 * de hatása idővel elmúlik.
 */
public class SoszoroFej implements IKotrofej {
    /**
     * Elvégzi a sózási munkát a paraméterként kapott sávon.
     * Működéséhez 1 egység sóra van szükség a takarító raktárából.
     * 
     * @param s A sáv, amelyet be kell sózni.
     * @param h A hókotró jármű, amely a sót biztosítja a munkához.
     * @return Igaz, ha volt elég só és a szórás sikeres volt, egyébként hamis.
     */
    @Override
    public boolean dolgozik(Sav s, Hokotro h) {
        // Megpróbálunk 1 egység sót levonni a hókotró tulajdonosának raktárából
        boolean sikeresFogyasztas = h.sotFogyaszt(1);
        if (sikeresFogyasztas) {
            // Ha a fogyasztás sikeres volt, aktiváljuk a sáv sózási állapotát/időzítőjét
            s.sozas();
            return true;
        }
        // Nincs elég só, a munka meghiúsult
        return false;
    }
}