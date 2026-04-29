public class HanyoFej implements IKotrofej {
    @Override
    public void dolgozik(Sav s, Hokotro h) {
        // Végleges eltávolítás
        s.hoCsokkent(Integer.MAX_VALUE);
    }
}