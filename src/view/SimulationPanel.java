package view;

import observer.IObserver;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class SimulationPanel extends JPanel implements IObserver {
    private List<View> viewList = new ArrayList<>();

    public SimulationPanel() {
        setBackground(new Color(34, 139, 34)); 
    }

    public void addView(View v) {
        viewList.add(v);
    }

    public void clearViews() {
        viewList.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        // Végigmegyünk a View listán:
        for (View v : viewList) {
            v.draw(g);
        }
    }

    @Override
    public void update() {
        // Ezt hívja meg a Vezerlo.notifyObservers() a tick végén!
        // A repaint() beütemez egy újrarajzolást (meghívja a paintComponent-et).
        this.repaint(); 
    }
}