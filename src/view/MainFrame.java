package view;

import model.Vezerlo;
import javax.swing.JFrame;
import java.awt.BorderLayout;

public class MainFrame extends JFrame {
    private SimulationPanel simulationPanel;
    private ControlPanel controlPanel;
    private BoltPanel boltPanel;
    private StatPanel statPanel;

    public MainFrame(Vezerlo vezerlo) {
        setTitle("Zúzmaraváros Hóeltakarítás Szimulátor");
        setSize(1100, 750); // Kicsit megnöveljük az ablakot, hogy kényelmesen elférjenek az oldalsó panelek
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Játékfelület létrehozása és hozzáadása (KÖZÉPRE)
        simulationPanel = new SimulationPanel();
        simulationPanel.setVezerlo(vezerlo);
        add(simulationPanel, BorderLayout.CENTER);

        // 2. ÚJ PANELEK HOZZÁADÁSA A TERV SZERINT
        controlPanel = new ControlPanel(vezerlo);
        add(controlPanel, BorderLayout.SOUTH); // ALULRA

        boltPanel = new BoltPanel(vezerlo);
        add(boltPanel, BorderLayout.EAST); // JOBBRA

        statPanel = new StatPanel(vezerlo);
        add(statPanel, BorderLayout.WEST); // BALRA

        // A vászon továbbra is feliratkozik a Vezérlőre
        vezerlo.addObserver(simulationPanel);
        vezerlo.addObserver(statPanel);

        setLocationRelativeTo(null); 
    }
    
    public SimulationPanel getSimulationPanel() {
        return simulationPanel;
    }
}
