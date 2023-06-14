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
    private Quarantine quarantine;
    private static final double SEVERE_SYMPTOMS_PERCENTAGE = 0.3;



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

        quarantine = new Quarantine(10, agents, this);


    }

    private void createGridPanel() {
        gridPanel = new JPanel(new GridLayout(60, 80));
        cellPanels = new JPanel[60][80];

        for (int row = 0; row < 60; row++) {
            for (int col = 0; col < 80; col++) {
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


        JLabel diseaseNameLabel = new JLabel("Disease Name (COVID-19, Influenza, Common Cold): ");
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
            int row = random.nextInt(60);
            int col = random.nextInt(80);

            boolean hasSevereSymptoms = Math.random() < SEVERE_SYMPTOMS_PERCENTAGE;

            Agent agent = new Agent(row, col, hasSevereSymptoms, quarantine, this);
            agent.setCurrentPosition(row, col);
            agent.setTargetPosition(row, col);
            if (i < infectedCount) {
                String diseaseName = diseaseNameTextField.getText();
                Disease disease = new Disease();
                disease.setDiseaseProperties(diseaseName);
                disease.setName(diseaseName);

                agent.setDisease(disease);
                agent.setStatus(Agent.AgentStatus.INFECTED);
                agent.setColor(Color.RED);
                agent.setIncubationPeriod(disease.getIncubationPeriod());
                agent.setMortality_rate(disease.getMortalityRate());
            }
            agents.add(agent);
        }

    }

    private void updateGridAppearance() {
        int infectedCount = 0;
        for (Agent agent : agents) {
            if (agent.getStatus() == Agent.AgentStatus.INFECTED) {
                infectedCount++;
                System.out.println("INFECTED COUNT" + infectedCount);
            }
        }
        quarantine.monitorHealthStatus(infectedCount);

        for (int row = 0; row < 60; row++) {
            for (int col = 0; col < 80; col++) {
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
                case DEAD:
                    cellPanel.setBackground(Color.darkGray);
                    break;
                case QUARANTINED:
                    cellPanel.setBackground(Color.YELLOW);
                    break;
            }
        }
    }

    public Quarantine getQuarantine() {
        return quarantine;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulationGUI gui = new SimulationGUI();
            gui.setVisible(true);
        });
    }
}