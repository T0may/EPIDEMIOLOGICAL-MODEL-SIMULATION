package epidemicsimulation;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * Klasa AgentMovementWorker umożliwia płynne poruszanie się agentów po planszy.
 * Rozszerzenie SwingWorker
 */
public class AgentMovementWorker extends SwingWorker<Void, Void> {
    private List<Agent> agents;
    private JPanel[][] cellPanels;
    private int timeCounter;
    private static final int RECOVERY_DELAY = 12;
    private SimulationGUI simulationGUI;
    private Quarantine quarantine;

    /**
     * Konstruktor klasy AgentMovementWorker
     * @param agents
     * @param cellPanels
     * @param simulationGUI
     */
    public AgentMovementWorker(List<Agent> agents, JPanel[][] cellPanels, SimulationGUI simulationGUI) {
        this.agents = agents;
        this.cellPanels = cellPanels;
        this.timeCounter = 0;
        this.simulationGUI = simulationGUI;
        this.quarantine = simulationGUI.getQuarantine();
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (true) {
            // Poruszanie agentami, sprawdzanie zakażeń, zmniejszanie okresu uleczenia
            for (Agent agent : agents) {
                agent.move();
                agent.checkInfection(agents);
                agent.deacreaseIncubationPeriod(agents);
            }

            timeCounter++;

            // Przywracanie agentów do stanu pierwotnego po określonym czasie
            if (timeCounter >= RECOVERY_DELAY) {
                for (Agent agent : agents) {
                    if (agent.getStatus() == Agent.AgentStatus.RECOVERED) {
                        agent.setStatus(Agent.AgentStatus.SUSCEPTIBLE);
                    }

                }
                timeCounter = 0;
            }

            publish(); // Aktualizacja interfejsu użytkownika


            Thread.sleep(2000); // Prędkość poruszania agentów


            int infectedCount = simulationGUI.getInfectedCount();
            if (infectedCount == 0) {
                break;
            }

        }
        JOptionPane.showMessageDialog(simulationGUI, "Symulacja zakonczona. Liczba zarazonych agentow wynosi 0.", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        return null;
    }

    @Override
    protected void process(List<Void> chunks) {

        int susceptibleCount = 0;
        int infectedCount = 0;

        for (Agent agent : agents) {
            if (agent.getStatus() == Agent.AgentStatus.SUSCEPTIBLE) {
                susceptibleCount++;
            } else if (agent.getStatus() == Agent.AgentStatus.INFECTED) {
                infectedCount++;
            }
        }

        simulationGUI.updateSusceptibleCountLabel(susceptibleCount);
        simulationGUI.updateInfectedCountLabel(infectedCount);


        // Aktualizacja interfejsu użytkownika
        for (int row = 0; row < 60; row++) {
            for (int col = 0; col < 80; col++) {
                JPanel cellPanel = cellPanels[row][col];
                cellPanel.setBackground(Color.darkGray);
            }
        }

        for (Agent agent : agents) {
            int row = agent.getRow();
            int col = agent.getCol();
            JPanel cellPanel = cellPanels[row][col];

            // warunki dla których agent zmienia swój kolor dla poszczególnego stanu
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
}