package view;

import model.jarmuvek.Jarmu;
import model.palya.Sav;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class JarmuView extends View {
    private Jarmu modell;
    private List<SavView> osszesSavView;

    public JarmuView(Jarmu modell, List<SavView> osszesSavView) {
        this.modell = modell;
        this.osszesSavView = osszesSavView;
    }

    @Override
    public void draw(Graphics g) {
        // A Jarmu.java-ban lévő valós metódus!
        float poz = modell.getPozicio();
        
        // JAVÍTÁS: Itt getAktualisSav() van a kódod alapján!
        Sav jarmuSavja = modell.getAktualisSav();
        SavView aktualisSavView = null;
        
        for (SavView sv : osszesSavView) {
            if (sv.getModell() == jarmuSavja) {
                aktualisSavView = sv;
                break;
            }
        }

        if (aktualisSavView != null) {
            this.x = (int)(aktualisSavView.getX1() + (aktualisSavView.getX2() - aktualisSavView.getX1()) * poz);
            this.y = (int)(aktualisSavView.getY1() + (aktualisSavView.getY2() - aktualisSavView.getY1()) * poz);

            g.setColor(Color.BLUE);
            g.fillRect(x - 8, y - 8, 16, 16); 
            
            g.setColor(Color.BLACK);
            g.drawRect(x - 8, y - 8, 16, 16);
        }
    }
}
