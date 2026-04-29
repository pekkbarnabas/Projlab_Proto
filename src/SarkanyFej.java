public class SarkanyFej implements IKotrofej {
    @Override
    public void dolgozik(Sav s, Hokotro h) {
        boolean sikeresFogyasztas = h.kerozintFogyaszt(1);
        if (sikeresFogyasztas) {
            s.jegTorese();
            s.hoCsokkent(Integer.MAX_VALUE); // Teljes olvasztás
        } else {
            System.out.println("> ERROR: Nincs elegendo kerozin");
        }
    }
}