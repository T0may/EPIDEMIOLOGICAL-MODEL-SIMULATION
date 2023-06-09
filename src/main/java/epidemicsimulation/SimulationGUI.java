package epidemicsimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationGUI extends JFrame{
    private JPanel gridPanel;
    private JTextField diseaseNameTextField;
    private JTextField populationSizeTextField;
    private JTextField infectedCountLabelTextField;
    private JButton startButton;

    private List<Agent> agents;
    private JPanel[][] cellPanels;
    private boolean simulationStarted;



    public SimulationGUI()
    {
        setTitle("Epidemiological Model Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);


        createGridPanel();
        createControlPanel();

        pack();
        setLocationRelativeTo(null);
        simulationStarted = false;

    }

    private void createGridPanel() {
        gridPanel = new JPanel(new GridLayout(40, 50));
        cellPanels = new JPanel[50][80];

        for (int row = 0; row < 40; row++) {
            for (int col = 0; col < 50; col++) {
                JPanel cellPanel = new JPanel();
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
                cellPanel.setBackground(Color.darkGray);

                gridPanel.add(cellPanel);
                cellPanels[row][col] = cellPanel;
            }
        }

        add(gridPanel, BorderLayout.CENTER);
    }

    private void createControlPanel()
    {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 2));
        controlPanel.setBackground(Color.DARK_GRAY);


        JLabel diseaseNameLabel = new JLabel("Disease Name: ");
        diseaseNameLabel.setForeground(Color.WHITE);
        diseaseNameTextField = new JTextField();

        JLabel populationSizeLabel = new JLabel("Population Size: ");
        populationSizeLabel.setForeground(Color.WHITE);
        populationSizeTextField = new JTextField();

        JLabel infectedCountLabel = new JLabel("Infected Count: ");
        infectedCountLabel.setForeground(Color.WHITE);
        infectedCountLabelTextField = new JTextField();


        controlPanel.add(diseaseNameLabel);
        controlPanel.add(diseaseNameTextField);
        controlPanel.add(populationSizeLabel);
        controlPanel.add(populationSizeTextField);
        controlPanel.add(infectedCountLabel);
        controlPanel.add(infectedCountLabelTextField);


        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (simulationStarted)
                {
                    return;
                }
                String diseaseName = diseaseNameTextField.getText();
                int populationSize = Integer.parseInt(populationSizeTextField.getText());
                int infectedCount = Integer.parseInt(infectedCountLabelTextField.getText());

                gridPanel.removeAll();

                initializeAgents(populationSize, infectedCount);

                updateGridAppearance();

                gridPanel.revalidate();
                gridPanel.repaint();



//                here parameters are going to be used
                System.out.println("Disease Name: " + diseaseName);
                System.out.println("Population size: " + populationSize);

                AgentMovementWorker worker = new AgentMovementWorker(agents, cellPanels);
                worker.execute();


                simulationStarted = true;
            }
        });


        JPanel controlPanel2 = new JPanel();
        controlPanel2.setLayout(new GridLayout(1, 1));
//        controlPanel2.setBackground(Color.DARK_GRAY);
        controlPanel2.add(startButton);


        add(controlPanel, BorderLayout.SOUTH);
        add(controlPanel2, BorderLayout.NORTH);
    }

    private void initializeAgents(int populationSize, int infectedCount) {
        agents = new ArrayList<>();

        // create agents and randomly assign them to grid cells
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            int row = random.nextInt(40);
            int col = random.nextInt(50);


            Agent agent = new Agent(row, col);
            agent.setCurrentPosition(row, col);
            agent.setTargetPosition(row, col);
            if (i < infectedCount) {
                agent.setStatus(Agent.AgentStatus.INFECTED);
                agent.setColor(Color.RED);
            }
            agents.add(agent);
        }

    }

    private void updateGridAppearance() {
        for (int row = 0; row < 40; row++) {
            for (int col = 0; col < 50; col++) {
                JPanel cellPanel = cellPanels[row][col];
                cellPanel.setBackground(Color.darkGray);
                gridPanel.add(cellPanel);
            }
        }

        for (Agent agent : agents) {
            int row = agent.getRow();
            int col = agent.getCol();
            JPanel cellPanel = cellPanels[row][col];
            switch (agent.getStatus()) {
                case SUSCEPTIBLE:
                    cellPanel.setBackground(Color.PINK);
                    break;
                case INFECTED:
                    cellPanel.setBackground(agent.getColor());
                    break;
                case RECOVERED:
                    cellPanel.setBackground(Color.GREEN);
                    break;
            }
        }

    }



    private void moveAgent(Agent agent) {
        agent.move();

        SwingUtilities.invokeLater(() -> {
            updateGridAppearance();
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulationGUI gui = new SimulationGUI();
            gui.setVisible(true);
        });
    }
}