package view;

import model.Vezerlo;
import model.gazdasag.Arucikk; // Beimportáljuk az árucikket!
import observer.IObserver;
import javax.swing.*;
import java.awt.*;

public class StatPanel extends JPanel implements IObserver {
    private Vezerlo vezerlo;
    private JLabel penzLabel;
    private JLabel pontLabel;
    
    // --- ÚJ CÍMKÉK A KÉSZLETNEK ---
    private JLabel soLabel;
    private JLabel zuzalekLabel;
    private JLabel fejLabel;

    public StatPanel(Vezerlo vezerlo) {
        this.vezerlo = vezerlo;
        setBackground(new Color(240, 240, 240));
        setPreferredSize(new Dimension(180, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel cim = new JLabel("--- STATISZTIKÁK ---");
        cim.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(10));
        add(cim);

        add(Box.createVerticalStrut(20));
        penzLabel = new JLabel("Pénz: 0 Ft");
        penzLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(penzLabel);

        add(Box.createVerticalStrut(10));
        pontLabel = new JLabel("Pontszám: 0");
        pontLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(pontLabel);

        // --- ÚJ CÍMKÉK HOZZÁADÁSA A FELÜLETHEZ ---
        add(Box.createVerticalStrut(20));
        soLabel = new JLabel("Só készlet: 0 db");
        soLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(soLabel);

        add(Box.createVerticalStrut(10));
        zuzalekLabel = new JLabel("Zúzalék készlet: 0 db");
        zuzalekLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(zuzalekLabel);

        add(Box.createVerticalStrut(20));
        fejLabel = new JLabel("Fejek (Söp/Jég/Sárk): 0/0/0");
        fejLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(fejLabel);
    }

    @Override
    public void update() {
        penzLabel.setText("Pénz: " + vezerlo.getTakaritoPenz() + " Ft");
        pontLabel.setText("Pontszám: " + vezerlo.getBuszvezetoPontszam());
        soLabel.setText("Só készlet: " + vezerlo.getKeszlet(Arucikk.SO) + " db");
        zuzalekLabel.setText("Zúzalék készlet: " + vezerlo.getKeszlet(Arucikk.ZUZOTTKO) + " db");

        // Kotrófejek statisztikája
        fejLabel.setText("Fejek (Söp/Jég/Sárk): " 
            + vezerlo.getKeszlet(Arucikk.SOPROFEJ) + "/" 
            + vezerlo.getKeszlet(Arucikk.JEGTOROFEJ) + "/" 
            + vezerlo.getKeszlet(Arucikk.SARKANYFEJ));
    }
}