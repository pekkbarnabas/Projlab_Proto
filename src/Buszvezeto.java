import java.util.ArrayList;
import java.util.List;

public class Buszvezeto {
    private int pontszam;
    private List<Busz> buszok;

    public Buszvezeto() {
        this.pontszam = 0;
        this.buszok = new ArrayList<>();
    }

    public void pontotKap() {
        this.pontszam++;
    }

    public void busztHozzaad(Busz b) {
        if (!buszok.contains(b)) {
            buszok.add(b);
        }
        if (b.getTulajdonos() != this) {
            b.setTulajdonos(this);
        }
    }

    public int getPontszam() {
        return pontszam;
    }
}