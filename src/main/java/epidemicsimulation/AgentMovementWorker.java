package epidemicsimulation;

import javax.swing.*;

import java.awt.*;
import java.util.List;


public class AgentMovementWorker extends SwingWorker<Void, Void> {
    private List<Agent> agents;
    private JPanel[][] cellPanels;


    public AgentMovementWorker(List<Agent> agents, JPanel[][] cellPanels) {
        this.agents = agents;
        this.cellPanels = cellPanels;

    }

    @Override
    protected Void doInBackground() throws Exception {
        while (true) {
            for (Agent agent : agents) {
                agent.move();
                agent.checkInfection(agents);
            }

            publish(); // user interface update

            Thread.sleep(2000); // movement speed
        }

    }
    private void moveAgent(Agent agent) {
        agent.move();
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
            }
        }


    }
}