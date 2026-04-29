public class SoproFej implements IKotrofej {
    @Override
    public void dolgozik(Sav s, Hokotro h) {
        int aktualisHo = s.getHovastagsag();
        s.hoCsokkent(Integer.MAX_VALUE);
        
        if (!s.getSzomszedok().isEmpty()) {
            Sav szomszed = s.getSzomszedok().get(0);
            szomszed.hoNovel(aktualisHo); // Áttolja a szomszédra
        }
    }
}