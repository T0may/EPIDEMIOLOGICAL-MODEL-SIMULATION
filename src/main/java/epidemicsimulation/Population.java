package epidemicsimulation;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private List<Agent> agents;
    private int infectedCount;
    private int recoveredCount;

    // Konstruktor klasy Population
    public Population() {
        agents = new ArrayList<>();
        infectedCount = 0;
        recoveredCount = 0;
    }

    // Dodanie agenta do populacji
    public void addAgent(Agent agent) {
        agents.add(agent);
    }

    // Pobranie liczby zainfekowanych agentów
    public int getInfectedCount() {
        int count = 0;
        for (Agent agent : agents) {
            if (agent.getStatus() == Agent.AgentStatus.INFECTED) {
                count++;
            }
        }
        return count;
    }

    // Pobranie liczby wyzdrowiałych agentów
    public int getRecoveredCount() {
        int count = 0;
        for (Agent agent : agents) {
            if (agent.getStatus() == Agent.AgentStatus.RECOVERED) {
                count++;
            }
        }
        return count;
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
