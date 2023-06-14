package epidemicsimulation;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class AgentMovementWorker extends SwingWorker<Void, Void> {
    private List<Agent> agents;
    private JPanel[][] cellPanels;
    private int timeCounter;
    private static final int RECOVERY_DELAY = 10;

// Konstruktor klasy AgentMovementWorker
    public AgentMovementWorker(List<Agent> agents, JPanel[][] cellPanels) {
        this.agents = agents;
        this.cellPanels = cellPanels;
        this.timeCounter = 0;
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
        }

    }

    @Override
    protected void process(List<Void> chunks) {


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