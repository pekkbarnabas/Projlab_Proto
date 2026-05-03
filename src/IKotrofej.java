/**
 * Interfész a hókotrókra felszerelhető különböző kotrófejek számára.
 * Lehetővé teszi, hogy a hókotrók viselkedése (takarítási módja) 
 * dinamikusan cserélhető legyen futásidőben.
 */
public interface IKotrofej {
    /**
     * A paraméterként kapott sávon elvégzi a fej típusára (pl. sózás, söprés) jellemző módosításokat.
     * 
     * @param s A sáv, amelyen a takarítási műveletet el kell végezni.
     * @param h A hókotró jármű, amely ezt a fejet használja (szükséges pl. az erőforrások fogyasztásához).
     * @return Igaz, ha a művelet sikeresen végrehajtódott (pl. volt elég nyersanyag a hókotró raktárában).
     */
    boolean dolgozik(Sav s, Hokotro h);
}