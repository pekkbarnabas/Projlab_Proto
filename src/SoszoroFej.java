public class SoszoroFej implements IKotrofej {
    @Override
    public boolean dolgozik(Sav s, Hokotro h) {
        boolean sikeresFogyasztas = h.sotFogyaszt(1);
        if (sikeresFogyasztas) {
            s.sozas();
            return true;
        }
        return false;
    }
}