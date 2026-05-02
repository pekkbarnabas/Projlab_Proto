public class SarkanyFej implements IKotrofej {
    @Override
    public boolean dolgozik(Sav s, Hokotro h) {
        boolean sikeresFogyasztas = h.kerozintFogyaszt(1);
        if (sikeresFogyasztas) {
            s.jegTorese();
            s.hoCsokkent(Sav.EXTREM_HO_SZINT); // Vagy MAX_ERTEK
            return true;
        }
        return false; // Ezt figyeli majd a hókotró!
    }
}