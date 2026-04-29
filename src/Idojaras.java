import java.util.ArrayList;
import java.util.List;

public class Idojaras implements IIdoMulo {
    private int intenzitas;
    private List<Sav> savok;

    public Idojaras(int initIntenzitas) {
        this.intenzitas = initIntenzitas;
        this.savok = new ArrayList<>();
    }

    public void setIntenzitas(int intenzitas) {
        this.intenzitas = intenzitas;
    }

    public void setSavok(List<Sav> savok) {
        this.savok = savok;
    }

    public int getIntenzitas() {
        return intenzitas;
    }

    @Override
    public void idotLep() {
        for (Sav s : savok) {
            // 18.3-as Teszteset implementációja: Alagútban nem havazhat!
            if (s.getUtszakasz() != null && s.getUtszakasz().getMagassag() == -1) {
                continue; 
            }
            s.hoNovel(intenzitas);
        }
    }
}