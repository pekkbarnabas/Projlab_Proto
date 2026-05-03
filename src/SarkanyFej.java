/**
 * Extrém tisztítóeszköz, amely kerozin égetésével olvasztja le a jeget és a havat az útról.
 * Bár rendkívül hatékony, működtetése költséges az üzemanyagigénye miatt.
 */
public class SarkanyFej implements IKotrofej {
    /**
     * Elvégzi az extrém tisztítást a megadott sávon.
     * Működéséhez 1 egység kerozinra van szükség a takarító raktárából.
     * 
     * @param s A sáv, amelyen a jég és hó leolvasztását végezzük.
     * @param h A hókotró jármű, amely az üzemanyagot biztosítja a működéshez.
     * @return Igaz, ha volt elég kerozin és a tisztítás sikeres volt, egyébként hamis.
     */
    @Override
    public boolean dolgozik(Sav s, Hokotro h) {
        boolean sikeresFogyasztas = h.kerozintFogyaszt(1);
        if (sikeresFogyasztas) {
            // A lángszóró hatására a jégpáncél azonnal feltörik/leolvad
            s.jegTorese();
            s.hoCsokkent(Sav.EXTREM_HO_SZINT); // Vagy MAX_ERTEK
            return true;
        }
        return false; // Ezt figyeli majd a hókotró!
    }
}