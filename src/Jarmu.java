/**
 * Absztrakt ősosztály a szimulációban mozgó összes fizikai entitás (Auto, Busz, Hokotro) számára.
 * Tartalmazza a járművek térbeli elhelyezkedését leíró alapvető tulajdonságokat,
 * valamint előírja a fizikai eseményekre (elakadás, megcsúszás, ütközés) kötelezően megvalósítandó reakciókat.
 */
public abstract class Jarmu implements IIdoMulo {
    /** A jármű pozíciója az aktuális útszakaszon (0.0 a kezdete, 1.0 a vége). */
    protected float pozicio;

    /** A jármű pozíciója az aktuális útszakaszon (0.0 a kezdete, 1.0 a vége). */
    protected Sav aktualisSav;

    /** Az útszakasz, amelyhez az aktuális sáv tartozik. */
    protected Utszakasz aktualisUtszakasz;

    /**
     * Beállítja a járművet egy adott sávra, és automatikusan szinkronizálja az útszakaszt is.
     * @param s A sáv, ahová a jármű kerül.
     */
    public void setAktualisSav(Sav s) { 
        this.aktualisSav = s; 
        if (s != null) {
            this.aktualisUtszakasz = s.getUtszakasz();
        }
    }
    public Sav getAktualisSav() { return this.aktualisSav; }
    
    public void setPozicio(float p) { this.pozicio = p; }

    public float getPozicio() { return this.pozicio; }

    // --- Fizikai interakciók ---

    /**
     * Meghívódik, ha a jármű mozgásképtelenné válik (pl. hóakadály miatt).
     */
    public abstract void elakad();

    /**
     * Meghívódik, ha a jármű jégpáncélra hajt és megcsúszik.
     */
    public abstract void megcsuszik();

    /**
     * Meghívódik, ha a jármű jégpáncélra hajt és megcsúszik.
     */
    public abstract void utkozik(Jarmu masik);
}