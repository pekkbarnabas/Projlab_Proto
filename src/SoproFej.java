public class SoproFej implements IKotrofej {
    @Override
    public boolean dolgozik(Sav s, Hokotro h) {
        int aktualisHo = s.getHovastagsag();
        s.hoCsokkent(Sav.EXTREM_HO_SZINT); // Vagy MAX_ERTEK
        
        if (!s.getSzomszedok().isEmpty()) {
            Sav szomszed = s.getSzomszedok().get(0);
            szomszed.hoNovel(aktualisHo);
        }
        return true;
    }
}