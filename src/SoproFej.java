/**
 * Olyan kotrófej, amely sepréssel távolítja el a havat az útról.
 * A mechanikus tisztítás sajátossága, hogy a havat nem tünteti el a rendszerből, 
 * hanem áttolja egy szomszédos sávba.
 */
public class SoproFej implements IKotrofej {
    /**
     * Elvégzi a söprési munkát a sávon.
     * Megméri a jelenlegi hóvastagságot, letakarítja az aktuális sávot,
     * majd a havat áthelyezi az első elérhető szomszédos sávra.
     * 
     * @param s A sáv, amelyet le kell söpörni.
     * @param h A hókotró jármű, amely a fejet működteti.
     * @return Mindig igaz, mivel a söprés nem igényel külön erőforrást (pl. üzemanyagot).
     */
    @Override
    public boolean dolgozik(Sav s, Hokotro h) {
        // Elmentjük a jelenlegi hómennyiséget, mielőtt takarítani kezdenénk
        int aktualisHo = s.getHovastagsag();

        // Letakarítjuk a havat az aktuális sávól
        s.hoCsokkent(Sav.EXTREM_HO_SZINT);

        // Ha van szomszédos sáv, a letakarított havat oda halmozzuk fel
        if (!s.getSzomszedok().isEmpty()) {
            Sav szomszed = s.getSzomszedok().get(0);
            szomszed.hoNovel(aktualisHo);
        }
        
        return true;
    }
}