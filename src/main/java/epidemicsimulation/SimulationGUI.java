package epidemicsimulation;

import javax.swing.*;
import java.awt.*;

public class SimulationGUI extends JFrame{
    private JPanel gridPanel;
    private JLabel populationLabel;
    private JButton startButton;

    public SimulationGUI()
    {
        setTitle("Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);


        createGridPanel();
        createControlPanel();

        pack();
        setLocationRelativeTo(null);
    }

    private void createGridPanel()
    {
        gridPanel = new JPanel(new GridLayout(50,80));

        // LAYOUT APPEARANCE OF GRID PANEL
        for(int row = 0; row < 50; row++)
        {
            for(int col = 0; col < 80; col++)
            {

                JPanel fieldPanel = new JPanel();
                fieldPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(fieldPanel);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
    }
    private void createControlPanel()
    {
        JPanel controPanel = new JPanel();


        populationLabel = new JLabel("Population: ");
        controPanel.add(populationLabel);

        startButton = new JButton("Start");
        controPanel.add(startButton);


        add(controPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulationGUI gui = new SimulationGUI();
            gui.setVisible(true);
        });
    }
}
