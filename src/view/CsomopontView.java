package view;

import model.palya.Csomopont;
import java.awt.Color;
import java.awt.Graphics;

public class CsomopontView extends View {
    private Csomopont modell;

    public CsomopontView(Csomopont modell, int x, int y) {
        this.modell = modell;
        this.x = x; 
        this.y = y;
    }

    public int getX() { return this.x; }
    
    public int getY() { return this.y; }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillOval(x - 15, y - 15, 30, 30); 
        
        g.setColor(Color.WHITE);
        g.drawString(modell.getNev(), x - 10, y + 5);
    }
}
