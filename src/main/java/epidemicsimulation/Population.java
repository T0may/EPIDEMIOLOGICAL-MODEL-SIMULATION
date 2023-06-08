package epidemicsimulation;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private List<Agent> agents;
    private int infectedCount;
    private int recoveredCount;

    public Population() {
        agents = new ArrayList<>();
        infectedCount = 0;
        recoveredCount = 0;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public int getInfectedCount() {
        return infectedCount;
    }

    public int getRecoveredCount() {
        return recoveredCount;
    }

    public void addAgent(Agent agent) {
        agents.add(agent);

        // check agents state and update
        if (agent.getStatus() == Agent.AgentStatus.INFECTED) {
            infectedCount++;
        } else if (agent.getStatus() == Agent.AgentStatus.RECOVERED) {
            recoveredCount++;
        }
    }

    public void updatePopulationState() {
        infectedCount = 0;
        recoveredCount = 0;

        for (Agent agent : agents) {
            if (agent.getStatus() == Agent.AgentStatus.INFECTED) {
                infectedCount++;
            } else if (agent.getStatus() == Agent.AgentStatus.RECOVERED) {
                recoveredCount++;
            }
        }
    }
}
