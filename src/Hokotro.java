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

    public boolean kerozintFogyaszt(int mennyiseg) {
        if (tulajdonos != null && tulajdonos.getRaktar() != null) {
            boolean siker = tulajdonos.getRaktar().eroforrasCsokkent(Arucikk.KEROZIN, mennyiseg);
            if (siker) {
                return true;
            }
        }
        System.out.println("> ERROR: Nincs elegendo kerozin");
        return false;
    }

    public boolean zuzalekotFogyaszt(int mennyiseg) {
        if (tulajdonos != null && tulajdonos.getRaktar() != null) {
            boolean siker = tulajdonos.getRaktar().eroforrasCsokkent(Arucikk.ZUZOTTKO, mennyiseg);
            if (siker) {
                return true;
            }
        }
        System.out.println("> ERROR: Nincs elegendo zuzottko");
        return false;
    }

    public boolean sotFogyaszt(int mennyiseg) {
        if (tulajdonos != null && tulajdonos.getRaktar() != null) {
            boolean siker = tulajdonos.getRaktar().eroforrasCsokkent(Arucikk.SO, mennyiseg);
            if (siker) {
                return true;
            }
        }
        System.out.println("> ERROR: Nincs elegendo so");
        return false;
    }

    public boolean takarit() {
        if (fej != null && aktualisSav != null) {
            int hoElotte = aktualisSav.getHovastagsag();
            boolean jegElotte = aktualisSav.isJegpancel();
            boolean zuzalekElotte = aktualisSav.isZuzalekos();
            int sozasElotte = aktualisSav.getSozasIdozito();
            boolean sikeresMunka = fej.dolgozik(aktualisSav, this);
            
            if (!sikeresMunka) {
                return false; // Kilépünk hamissal, ha nem volt elég anyag (pl. kerozin)
            }

            boolean tortentMunka = 
                (aktualisSav.getHovastagsag() < hoElotte) ||       
                (jegElotte && !aktualisSav.isJegpancel()) ||       
                (!zuzalekElotte && aktualisSav.isZuzalekos()) ||   
                (sozasElotte < aktualisSav.getSozasIdozito());     
                
            if (tortentMunka && tulajdonos != null) {
                tulajdonos.penztKap();
            }
            return true;
        }
        return false;
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