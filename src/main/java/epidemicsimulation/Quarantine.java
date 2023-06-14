package epidemicsimulation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Quarantine {
    private int capacity;
    private int currentOccupancy;
    private List<Agent> quarantinedAgents;
    private List<Agent> agents;
    private SimulationGUI simulationGUI;

    public Quarantine(int capacity, List<Agent> agents, SimulationGUI simulationGUI) {
        this.capacity = capacity;
        this.currentOccupancy = 0;
        this.quarantinedAgents = new ArrayList<>();
        this.agents = agents;
        this.simulationGUI = simulationGUI;
    }

    public void addToQuarantine(Agent agent) {
        if (currentOccupancy < capacity) {
            quarantinedAgents.add(agent);
            agent.setStatus(Agent.AgentStatus.QUARANTINED);
            currentOccupancy++;
            System.out.println("Agent " + agent + " has been added to the quarantine");
            agent.setQuarantined(true);


        } else {
            agent.setStatus(Agent.AgentStatus.SUSCEPTIBLE);
            agent.setColor(Color.PINK);
            System.out.println("Quarantine is full, cannot add more agents");
        }
    }

    public void monitorHealthStatus(int infectedCount) {

        if (infectedCount > 20) {
            System.out.println("Quarantine zone established for infected agents");


            for (Agent agent : quarantinedAgents) {
                System.out.println("Agent " + agent + " is in the quarantine zone");

            }
        }
    }

    public void releaseAgent(Agent agent) {
        if (quarantinedAgents.contains(agent)) {
            quarantinedAgents.remove(agent);
            currentOccupancy--;
            agent.setStatus(Agent.AgentStatus.SUSCEPTIBLE);
            agent.setColor(Color.PINK);
            System.out.println("Agent " + agent + " has been released from the quarantine");
        } else {
            System.out.println("Agent " + agent + " is not in the quarantine");
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    public void setCurrentOccupancy(int currentOccupancy) {
        this.currentOccupancy = currentOccupancy;
    }
}
