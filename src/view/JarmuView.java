package view;

import model.jarmuvek.Jarmu;
import model.jarmuvek.Auto;
import model.jarmuvek.Busz;
import model.jarmuvek.Hokotro;
import model.palya.Sav;
import java.awt.*;
import java.util.List;

public class JarmuView extends View {
    private Jarmu modell;
    private List<SavView> osszesSavView;
    private String felirat; // Pl. "A1", "H2"
    private Color szin;

    public JarmuView(Jarmu modell, List<SavView> osszesSavView, String azonosito) {
        this.modell = modell;
        this.osszesSavView = osszesSavView;
        
        // 1. Kiszedjük a számot az azonosítóból (pl. "auto1" -> "1")
        String szam = azonosito.replaceAll("[^0-9]", "");
        if (szam.isEmpty()) szam = "1"; // Ha nincs benne szám, alapértelmezetten 1-es
        
        // 2. Típus alapján beállítjuk a specifikáció szerinti betűt és színt
        if (modell instanceof Auto) {
            this.szin = Color.RED; // Autó: Piros
            this.felirat = "A" + szam;
        } else if (modell instanceof Busz) {
            this.szin = Color.BLUE; // Busz: Kék
            this.felirat = "B" + szam;
        } else if (modell instanceof Hokotro) {
            this.szin = new Color(255, 140, 0); // Hókotró: Narancssárga
            this.felirat = "H" + szam;
        } else {
            this.szin = Color.GRAY;
            this.felirat = "?" + szam;
        }
    }

    @Override
    public void draw(Graphics g) {
        float poz = modell.getPozicio();
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

            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(1)); 

            // 1. Kocka (Négyzet) kirajzolása a megadott színnel
            g2.setColor(this.szin);
            g2.fillRect(x - 10, y - 10, 20, 20); 
            
            // Fekete keret a kockának
            g2.setColor(Color.BLACK);
            g2.drawRect(x - 10, y - 10, 20, 20);
            
            // 2. Felirat (Betű + Szám) kirajzolása fehérrel, pontosan középre igazítva
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(felirat);
            int textHeight = fm.getAscent();
            
            g2.drawString(felirat, x - (textWidth / 2), y + (textHeight / 2) - 2);
        }
    }

    // Új lekérdezők az egérkattintáshoz
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public Jarmu getModell() { return this.modell; }
}