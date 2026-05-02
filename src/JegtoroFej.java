public class JegtoroFej implements IKotrofej {
    @Override
    public boolean dolgozik(Sav s, Hokotro h) {
        s.jegTorese();
        return true; 
    }
}