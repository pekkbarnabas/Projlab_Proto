public abstract class Jarmu implements IIdoMulo {
    protected float pozicio;
    protected Sav aktualisSav;
    protected Utszakasz aktualisUtszakasz;

    public void setAktualisSav(Sav s) { 
        this.aktualisSav = s; 
        if (s != null) {
            this.aktualisUtszakasz = s.getUtszakasz();
        }
    }
    public Sav getAktualisSav() { return this.aktualisSav; }
    
    public void setPozicio(float p) { this.pozicio = p; }
    public float getPozicio() { return this.pozicio; }

    // Minden jármű máshogy reagál ezekre, ezért absztraktak
    public abstract void elakad();
    public abstract void megcsuszik();
    public abstract void utkozik(Jarmu masik);
}