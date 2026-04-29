public class KoszoroFej implements IKotrofej {
    @Override
    public void dolgozik(Sav s, Hokotro h) {
        boolean sikeresFogyasztas = h.zuzalekotFogyaszt(1);
        if (sikeresFogyasztas) {
            s.zuzalekSzoras();
        } else {
            System.out.println("> ERROR: Nincs elegendo zuzottko");
        }
    }
}