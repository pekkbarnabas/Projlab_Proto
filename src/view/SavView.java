package view;

import model.palya.Sav;
import java.awt.Color;
import java.awt.Graphics;

public class SavView extends View {
    private Sav modell;
    private int x2, y2; // A sáv végpontja

    public SavView(Sav modell, int x1, int y1, int x2, int y2) {
        this.modell = modell;
        this.x = x1; // Örökölt x (kezdőpont)
        this.y = y1; // Örökölt y (kezdőpont)
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics g) {
        // Rajzolunk egy vastagabb szürke vonalat
        g.setColor(Color.LIGHT_GRAY);
        // Swingben nincs beépített vastag vonal, de szimulálhatjuk így:
        g.drawLine(x, y, x2, y2);
        g.drawLine(x+1, y+1, x2+1, y2+1);
        g.drawLine(x-1, y-1, x2-1, y2-1);
    }

    // Ezek a JarmuView-nak kellenek majd, hogy tudja a vonal koordinátáit
    public int getX1() { return x; }
    public int getY1() { return y; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }
    
    public Sav getModell() { return modell; }
}
