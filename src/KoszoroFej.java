public class KoszoroFej implements IKotrofej {
    @Override
    public boolean dolgozik(Sav s, Hokotro h) {
        boolean sikeresFogyasztas = h.zuzalekotFogyaszt(1);
        if (sikeresFogyasztas) {
            s.setZuzalekos(true);
            return true;
        }
        return false;
    }
}