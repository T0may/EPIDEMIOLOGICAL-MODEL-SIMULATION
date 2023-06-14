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
    private JLabel susceptibleCountLabel; // Dodane pole do wyświetlania liczby podatnych agentów
    private JLabel quarantinedCountLabel; // Dodane pole do wyświetlania liczby agentów pod kwarantanną
    private JLabel infectedCounterLabel;
    private SimulationGUI simulationGUI;



    //  domyślna klasa SimlationGUI
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

//      zainicjalizowana klasa Quarantine z parametrami
        quarantine = new Quarantine(10, agents, this);




    }

//    Utworzenie siatki dla poruszania się agentów (60x80)
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

//    Panel kontrolny dla użytkownika
    private void createControlPanel()
    {
        simulationGUI = this;

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5, 2));
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

        infectedCounterLabel = new JLabel("Intected agents: 0");
        infectedCounterLabel.setForeground(Color.WHITE);

        susceptibleCountLabel = new JLabel("Susceptible Agents: 0");
        susceptibleCountLabel.setForeground(Color.WHITE);




        controlPanel.add(diseaseNameLabel);
        controlPanel.add(diseaseNameTextField);
        controlPanel.add(populationSizeLabel);
        controlPanel.add(populationSizeTextField);
        controlPanel.add(infectedCountLabel);
        controlPanel.add(infectedCountLabelTextField);


        controlPanel.add(infectedCounterLabel);
        controlPanel.add(susceptibleCountLabel);



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

                // Sprawdzanie, czy pola tekstowe są puste
                if (!diseaseName.equalsIgnoreCase("COVID-19") &&
                        !diseaseName.equalsIgnoreCase("Influenza") &&
                        !diseaseName.equalsIgnoreCase("Common Cold"))  {
                    JOptionPane.showMessageDialog(simulationGUI, "Wprowadz poprawna nazwe choroby!", "Blad", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                gridPanel.removeAll();

                initialize(populationSize, infectedCount);

                updateGridAppearance();

                gridPanel.revalidate();
                gridPanel.repaint();

//              Parametry ktore zostaną użyte w simulationGUI
                System.out.println("Disease Name: " + diseaseName);
                System.out.println("Population size: " + populationSize);

                AgentMovementWorker worker = new AgentMovementWorker(agents, cellPanels, simulationGUI);
                worker.execute();

                simulationStarted = true;
            }
        });

        JPanel controlPanel2 = new JPanel();
        controlPanel2.setLayout(new GridLayout(1, 1));
        controlPanel2.add(startButton);

        add(controlPanel, BorderLayout.SOUTH);
        add(controlPanel2, BorderLayout.NORTH);
    }

//    inicjalizacja symulacji
    private void initialize(int populationSize, int infectedCount) {
        agents = new ArrayList<>();

        // Tworzenie agentów i losowe przypisanie ich do komórek siatki
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            int row = random.nextInt(60);
            int col = random.nextInt(80);

            boolean hasSevereSymptoms = Math.random() < SEVERE_SYMPTOMS_PERCENTAGE;

            Agent agent = new Agent(row, col, hasSevereSymptoms, quarantine, this);
            agent.setCurrentPosition(row, col);
            agent.setTargetPosition(row, col);

//            Kolorowanie zainfekowanych agentów podanych w panelu klienta na czerwono
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

    // Aktualizacja wyglądu siatki
    private void updateGridAppearance() {
        int infectedCount = 0;
        for (Agent agent : agents) {
            if (agent.getStatus() == Agent.AgentStatus.INFECTED) {
                infectedCount++;
//                System.out.println("INFECTED COUNT" + infectedCount);
            }
        }
        quarantine.monitorHealthStatus(infectedCount);


        // Aktualizacja wyświetlanych informacji o liczbie zarażonych, liczbie podatnych agentów i liczbie agentów pod kwarantanną
        infectedCounterLabel.setText("Infected Count: " + infectedCount);
        susceptibleCountLabel.setText("Susceptible Agents: " + (agents.size() - infectedCount));



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

//            warunki dla których agent zmienia swój kolor dla poszczególnego stanu
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

//    Getter
    public Quarantine getQuarantine() {
        return quarantine;
    }

    public void updateSusceptibleCountLabel(int count) {
        susceptibleCountLabel.setText("Susceptible Agents: " + count);
    }

    public void updateInfectedCountLabel(int count) {
        infectedCounterLabel.setText("Infected Count: " + count);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulationGUI gui = new SimulationGUI();
            gui.setVisible(true);
        });
    }
}