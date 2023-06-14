package epidemicsimulation;

import javax.swing.*;

import java.awt.*;
import java.util.List;


public class AgentMovementWorker extends SwingWorker<Void, Void> {
    private List<Agent> agents;
    private JPanel[][] cellPanels;
    private int timeCounter;
    private static final int RECOVERY_DELAY = 10;




    public AgentMovementWorker(List<Agent> agents, JPanel[][] cellPanels) {
        this.agents = agents;
        this.cellPanels = cellPanels;
        this.timeCounter = 0;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (true) {
            for (Agent agent : agents) {
                agent.move();
                agent.checkInfection(agents);
                agent.deacreaseIncubationPeriod(agents);


            }

            timeCounter++;

            if (timeCounter >= RECOVERY_DELAY) {
                for (Agent agent : agents) {
                    if (agent.getStatus() == Agent.AgentStatus.RECOVERED) {
                        agent.setStatus(Agent.AgentStatus.SUSCEPTIBLE);
                    }

                }
                timeCounter = 0;
            }

            publish(); // user interface update

            Thread.sleep(2000); // movement speed
        }

    }



    @Override
    protected void process(List<Void> chunks) {
        //  interface update
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