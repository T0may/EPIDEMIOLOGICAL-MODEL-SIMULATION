package epidemicsimulation;
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
    }

    public void addToQuarantine(Agent agent) {
        if (currentOccupancy < capacity) {
            quarantinedAgents.add(agent);
            agent.setStatus(Agent.AgentStatus.QUARANTINED);
            currentOccupancy++;
            System.out.println("Agent " + agent + " has been added to the quarantine");
        } else {
            agent.setStatus(Agent.AgentStatus.SUSCEPTIBLE);
            System.out.println("Quarantine is full, cannot add more agents");
        }
    }

    public void monitorHealthStatus() {
        int infectedCount = 0;

        for (Agent agent : quarantinedAgents) {
            if (agent.getStatus() == Agent.AgentStatus.INFECTED) {
                infectedCount++;
            }
        }

        if (infectedCount > 20) {
            System.out.println("Quarantine zone established for infected agents");

            int startRow = 0;
            int startCol = 0;
            int endRow = 19;
            int endCol = 19;

            for (Agent agent : quarantinedAgents) {
                int row = agent.getRow();
                int col = agent.getCol();

                if (row >= startRow && row <= endRow && col >= startCol && col <= endCol) {
                    agent.setStatus(Agent.AgentStatus.SUSCEPTIBLE);
                    System.out.println("Agent " + agent + " is in the quarantine zone");
                }
            }
        }
    }

    public void releaseAgent(Agent agent) {
        if (quarantinedAgents.contains(agent)) {
            quarantinedAgents.remove(agent);
            currentOccupancy--;
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
