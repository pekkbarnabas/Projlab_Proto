package view;

import model.Vezerlo;
import javax.swing.*;
import java.awt.*;

public class BoltPanel extends JPanel {
    private Vezerlo vezerlo;

    public BoltPanel(Vezerlo vezerlo) {
        this.vezerlo = vezerlo;
        setBackground(new Color(220, 220, 220)); 
        setPreferredSize(new Dimension(200, 0)); 
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel cim = new JLabel("--- BOLT ---");
        cim.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(10));
        add(cim);

        add(Box.createVerticalStrut(20));
        JButton vasarolSo = new JButton("Só vásárlás");
        vasarolSo.setAlignmentX(Component.CENTER_ALIGNMENT);
        // GOMBNYOMÁS FIGYELÉSE
        vasarolSo.addActionListener(e -> {
            vezerlo.parancsFeldolgoz("buy so");
            vezerlo.notifyObservers(); // Azonnal szólunk a felületnek, hogy frissítse a pénzt!
        });
        add(vasarolSo);

        add(Box.createVerticalStrut(10));
        JButton vasarolZuzalek = new JButton("Zúzottkő vásárlás");
        vasarolZuzalek.setAlignmentX(Component.CENTER_ALIGNMENT);
        // GOMBNYOMÁS FIGYELÉSE
        vasarolZuzalek.addActionListener(e -> {
            vezerlo.parancsFeldolgoz("buy zuzottko");
            vezerlo.notifyObservers();
        });
        add(vasarolZuzalek);

        add(Box.createVerticalStrut(20));

        JButton vasarolSopro = new JButton("+ Söprőfej");
        vasarolSopro.setAlignmentX(Component.CENTER_ALIGNMENT);
        vasarolSopro.addActionListener(e -> { vezerlo.parancsFeldolgoz("buy soprofej"); vezerlo.notifyObservers(); });
        add(vasarolSopro);

        add(Box.createVerticalStrut(5));
        JButton vasarolJegtoro = new JButton("+ Jégtörőfej");
        vasarolJegtoro.setAlignmentX(Component.CENTER_ALIGNMENT);
        vasarolJegtoro.addActionListener(e -> { vezerlo.parancsFeldolgoz("buy jegtorofej"); vezerlo.notifyObservers(); });
        add(vasarolJegtoro);

        add(Box.createVerticalStrut(5));
        JButton vasarolSarkany = new JButton("+ Sárkányfej");
        vasarolSarkany.setAlignmentX(Component.CENTER_ALIGNMENT);
        vasarolSarkany.addActionListener(e -> { vezerlo.parancsFeldolgoz("buy sarkanyfej"); vezerlo.notifyObservers(); });
        add(vasarolSarkany);
    }
}