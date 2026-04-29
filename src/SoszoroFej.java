public class SoszoroFej implements IKotrofej {
    @Override
    public void dolgozik(Sav s, Hokotro h) {
        boolean sikeresFogyasztas = h.sotFogyaszt(1);
        if (sikeresFogyasztas) {
            s.sozas();
        } else {
            System.out.println("> ERROR: Nincs elegendo so");
        }
    }
}