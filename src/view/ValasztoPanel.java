package view;

import model.Vezerlo;
import model.palya.Sav;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ValasztoPanel extends JDialog {
    private Vezerlo vezerlo;

    public ValasztoPanel(JFrame parent, Vezerlo vezerlo) {
        // A "true" jelenti azt, hogy Modális (megállítja a program többi részét, amíg nyitva van)
        super(parent, "Irányválasztás", true); 
        this.vezerlo = vezerlo;
        setSize(300, 200);
        setLocationRelativeTo(parent); // Az ablak közepén jelenik meg
        setLayout(new GridLayout(0, 1, 10, 10)); // Egymás alatti gombok
    }

    // Ezt hívjuk meg, amikor a hókotró csomóponthoz ér
    public void megjelenit(List<Sav> elerhetoSavok) {
        getContentPane().removeAll(); // Töröljük a korábbi gombokat
        
        JLabel label = new JLabel("Csomóponthoz értél! Merre tovább?", SwingConstants.CENTER);
        add(label);

        // Készítünk egy gombot minden elérhető sávnak
        for (int i = 0; i < elerhetoSavok.size(); i++) {
            Sav s = elerhetoSavok.get(i);
            JButton btn = new JButton("Irány a(z) " + (i + 1) + ". sáv");
            
            // Gombnyomás érzékelése
            btn.addActionListener(e -> {
                vezerlo.iranytValaszt(s); // Szólunk a vezérlőnek (Modell)
                setVisible(false);        // Eltüntetjük az ablakot
            });
            add(btn);
        }
        
        revalidate();
        repaint();
        setVisible(true); // Megjelenítjük a felugró ablakot
    }
}