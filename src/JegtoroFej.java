/**
 * Olyan kotrófej, amellyel a hókotró fel tudja törni a sávon lévő jégpáncélt.
 * Megszünteti a csúszásveszélyt az adott útszakaszon.
 */
public class JegtoroFej implements IKotrofej {
    /**
     * Elvégzi a jégtörési munkát a paraméterként kapott sávon.
     * Ez a művelet eltávolítja a jégpáncélt, így a járművek (pl. autók) nem fognak megcsúszni.
     * 
     * @param s A sáv, amelyen a jégtörést végre kell hajtani.
     * @param h A hókotró jármű, amely ezt a fejet használja (itt nincs külön erőforrás-fogyasztás).
     * @return Igaz, mivel ez a művelet nem igényel nyersanyagot, mindig sikeresen lefut.
     */
    @Override
    public boolean dolgozik(Sav s, Hokotro h) {
        // Feltöri a jeget az aktuális sávon
        s.jegTorese();
        return true; 
    }
}