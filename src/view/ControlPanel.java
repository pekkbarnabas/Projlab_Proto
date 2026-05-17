package view;

import model.Vezerlo;
import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private Vezerlo vezerlo;
    private JButton tickButton;

    public ControlPanel(Vezerlo vezerlo) {
        this.vezerlo = vezerlo;
        setBackground(Color.LIGHT_GRAY); 
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // Gomb létrehozása
        tickButton = new JButton("Tick (Léptetés)");
        
        // --- ÚJ RÉSZ: Kattintás érzékelése ---
        tickButton.addActionListener(e -> {
            // Amikor rákattintanak, kiadjuk a Vezérlőnek a "tick 1" parancsot!
            // Ez pontosan olyan, mintha a konzolba írtad volna be.
            vezerlo.parancsFeldolgoz("tick 1");
            
            // Kérhetjük, hogy a gombnyomás után is írjon a konzolra egy visszajelzést (opcionális)
            System.out.println("Gombnyomás: Tick lefutott.");
        });

        add(tickButton);

        JButton tesztValasztoBtn = new JButton("TEST: Csomóponthoz értem");
        tesztValasztoBtn.addActionListener(e -> {
            // Lekérjük a Vezérlőtől az ÖSSZES sávot, és mondjuk az első 3-at odaadjuk a panelnek,
            // mintha ezek lennének az elérhető kijáratok
            java.util.List<model.palya.Sav> opciok = new java.util.ArrayList<>(vezerlo.getSavok().values());
            if (opciok.size() > 3) opciok = opciok.subList(0, 3);
            
            // Itt most egy trükkel meghívjuk a felugró ablakot
            // (A végleges kódban ezt a Vezerlo fogja meghívni, nem a ControlPanel!)
            view.ValasztoPanel vp = new view.ValasztoPanel(null, vezerlo);
            vp.megjelenit(opciok);
        });
        add(tesztValasztoBtn);
    }
}