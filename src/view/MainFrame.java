package view;

import model.Vezerlo;
import javax.swing.JFrame;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {
    private SimulationPanel simulationPanel;

    public MainFrame(Vezerlo vezerlo) {
        setTitle("Zúzmaraváros Hóeltakarítás Szimulátor");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Vászon létrehozása és hozzáadása az ablakhoz (középre)
        simulationPanel = new SimulationPanel();
        add(simulationPanel, BorderLayout.CENTER);

        // A legfontosabb lépés: a vászon feliratkozik a Vezérlőre!
        vezerlo.addObserver(simulationPanel);

        setLocationRelativeTo(null); // Középre igazítja az ablakot a képernyőn
    }
    
    public SimulationPanel getSimulationPanel() {
        return simulationPanel;
    }
}
