package view;

import model.palya.Sav;
import java.awt.*;

public class SavView extends View {
    private Sav modell;
    private int x2, y2; 
    private int offsetX, offsetY; // Eltolás a párhuzamos sávokhoz

    public SavView(Sav modell, int x1, int y1, int x2, int y2, int offsetX, int offsetY) {
        this.modell = modell;
        this.x = x1; 
        this.y = y1; 
        this.x2 = x2;
        this.y2 = y2;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10)); // 10 pixel vastag egy sáv

        // 1. Állapot szerinti színezés a specifikáció alapján
        if (modell.isJegpancel()) {
            g2.setColor(new Color(135, 206, 250)); // Jég: Világoskék
        } else if (modell.getHovastagsag() > 0) {
            g2.setColor(Color.WHITE); // Hó: Fehér
        } else {
            g2.setColor(Color.DARK_GRAY); // Tiszta aszfalt: Sötétszürke
        }

        // Vonal kirajzolása eltolva, hogy a sávok egymás mellett legyenek
        g2.drawLine(x + offsetX, y + offsetY, x2 + offsetX, y2 + offsetY);

        // 2. Ha zúzalékos, ráhúzunk egy szaggatott barna vonalat (pöttyözés)
        if (modell.isZuzalekos()) {
            g2.setColor(new Color(139, 69, 19)); // Barna
            g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{4, 10}, 0));
            g2.drawLine(x + offsetX, y + offsetY, x2 + offsetX, y2 + offsetY);
        }
    }

    public int getX1() { return x + offsetX; }
    public int getY1() { return y + offsetY; }
    public int getX2() { return x2 + offsetX; }
    public int getY2() { return y2 + offsetY; }
    
    public Sav getModell() { return modell; }
}
