/**
 * Egy speciális kotrófej, amely képes a sávon lévő összes havat egyszerre eltávolítani.
 * Ezt a felszerelést a Hókotró járművek használhatják a munkájuk során.
 */
public class HanyoFej implements IKotrofej {
    /**
     * Elvégzi a takarítási munkát az adott sávon.
     * A Hányófej azonnal és maradéktalanul eltünteti a havat.
     * 
     * @param s A sáv, amelyet le kell takarítani.
     * @param h A hókotró jármű, amely ezt a fejet használja.
     * @return Igaz, ha a munka sikeresen lefutott.
     */
    @Override
    public boolean dolgozik(Sav s, Hokotro h) {
        // Az Integer.MAX_VALUE paraméter biztosítja, hogy bármilyen vastag is a hó, a sáv teljesen hómentes lesz.
        s.hoCsokkent(Integer.MAX_VALUE);
        return true; 
    }
}