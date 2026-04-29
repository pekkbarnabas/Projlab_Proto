import java.util.ArrayList;
import java.util.List;

public class Csomopont {
    private String nev;
    private List<Utszakasz> kijaratok;

    public Csomopont(String nev) {
        this.nev = nev;
        this.kijaratok = new ArrayList<>();
    }

    public void addKijarat(Utszakasz u) {
        this.kijaratok.add(u);
    }

    public String getNev() {
        return this.nev;
    }
    
    public List<Utszakasz> getKijaratok() {
        return this.kijaratok;
    }
}