public class Hokotro extends Jarmu {
    private IKotrofej fej;
    private Takarito tulajdonos;

    public Hokotro(Takarito tulajdonos) {
        this.tulajdonos = tulajdonos;
        this.fej = null;
    }

    public void fejetCserel(IKotrofej ujFej) {
        this.fej = ujFej;
    }

    public boolean sotFogyaszt(int mennyiseg) {
        if (tulajdonos == null || tulajdonos.getRaktar() == null) return false;
        return tulajdonos.getRaktar().eroforrasCsokkent(Arucikk.SO, mennyiseg);
    }

    public boolean kerozintFogyaszt(int mennyiseg) {
        if (tulajdonos == null || tulajdonos.getRaktar() == null) return false;
        return tulajdonos.getRaktar().eroforrasCsokkent(Arucikk.KEROZIN, mennyiseg);
    }

    public boolean zuzalekotFogyaszt(int mennyiseg) {
        if (tulajdonos == null || tulajdonos.getRaktar() == null) return false;
        return tulajdonos.getRaktar().eroforrasCsokkent(Arucikk.ZUZOTTKO, mennyiseg);
    }

    public void takarit() {
        if (fej != null && aktualisSav != null) {
            fej.dolgozik(aktualisSav, this);
            if (tulajdonos != null) {
                tulajdonos.penztKap();
            }
        }
    }

    public boolean lep(Sav cel) {
        boolean talalt = false;
        if (aktualisSav != null) {
            for (Sav sz : aktualisSav.getSzomszedok()) {
                if (sz == cel) {
                    talalt = true;
                    break;
                }
            }
        }
        
        if (talalt) {
            aktualisSav.eltavolit(this);
            cel.elfogad(this);
            aktualisSav = cel;
            pozicio = 0;
            return true;
        } else {
            System.out.println("> ERROR: A cel sav nem szomszedos");
            return false;
        }
    }

    // Ezeket majd a járművek fizikai logikájánál kidolgozzuk
    @Override public void idotLep() { }
    @Override public void elakad() { }
    @Override public void megcsuszik() { }
    @Override public void utkozik(Jarmu masik) { }

    public Object getFej() {
        return fej;
    }
}