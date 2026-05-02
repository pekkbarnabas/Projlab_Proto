public class HanyoFej implements IKotrofej {
    @Override
    public boolean dolgozik(Sav s, Hokotro h) {
        s.hoCsokkent(Integer.MAX_VALUE);
        return true; 
    }
}