package view;

import model.Vezerlo;
import observer.IObserver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class SimulationPanel extends JPanel implements IObserver {
    private List<View> viewList = new ArrayList<>();
    private Vezerlo vezerlo; // Hivatkozás a Vezérlőre, hogy tudjunk neki szólni

    public SimulationPanel() {
        setBackground(new Color(34, 139, 34)); // Zöld játéktér

        // --- EGÉRKATTINTÁS FIGYELÉSE ---
        // --- EGÉRKATTINTÁS FIGYELÉSE ---
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (vezerlo == null) return;
                int mx = e.getX();
                int my = e.getY();
                
                // 1. JÁRMŰVEK ELLENŐRZÉSE (Ezek vannak legfelül!)
                for (int i = viewList.size() - 1; i >= 0; i--) {
                    View v = viewList.get(i);
                    if (v instanceof JarmuView) {
                        JarmuView jv = (JarmuView) v;
                        // A JarmuView egy 20x20-as négyzet (x-10, y-10 kezdőponttal)
                        Rectangle rect = new Rectangle(jv.getX() - 10, jv.getY() - 10, 20, 20);
                        if (rect.contains(mx, my)) {
                            vezerlo.kattintasJarmuvon(jv.getModell());
                            return; // Ha eltaláltuk a járművet, kilépünk!
                        }
                    }
                }

                // 2. SÁVOK ELLENŐRZÉSE (Ahogy eddig volt)
                for (View v : viewList) {
                    if (v instanceof SavView) {
                        SavView sv = (SavView) v;
                        Shape vastagVonal = new BasicStroke(12).createStrokedShape(
                                new Line2D.Float(sv.getX1(), sv.getY1(), sv.getX2(), sv.getY2()));
                        if (vastagVonal.contains(mx, my)) {
                            vezerlo.kattintasSavon(sv.getModell());
                            return; 
                        }
                    }
                }
            }
        });
    }

    public void setVezerlo(Vezerlo v) {
        this.vezerlo = v;
    }

    public void clearViews() {
        viewList.clear();
    }

    public void addView(View v) {
        viewList.add(v);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (View v : viewList) {
            v.draw(g);
        }
    }

    @Override
    public void update() {
        repaint();
    }
}