/**
 * Közös interfész a szimuláció azon elemei számára, amelyek reagálnak az idő múlására.
 * Ezt valósítják meg a járművek (haladás, büntetőidő csökkenése) és az időjárás (havazás) is.
 * A Vezérlő 'tick' parancsára az összes IIdoMulo objektum megkapja a vezérlést.
 */
public interface IIdoMulo {
    /**
     * Meghívásakor az objektum végrehajtja az egy időegységre jutó logikáját.
     */
    void idotLep();
}