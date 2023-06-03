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

    public List<Agent> getAgents()
    {
        return agents;
    }

    public int getInfectedCount()
    {
        return infectedCount;
    }
    public int getRecoveredCount()
    {
        return getRecoveredCount();
    }
    public void addAgent(Agent agent)
    {
        agents.add(agent);
    }
    public void updatePopulationState()
    {
//        iterujemy przez liste agentow w populacji dla kazdego sprawdzamy jego stan
        infectedCount = 0;
        recoveredCount = 0;

        for (Agent agent : agents) {
            if (agent.getState() == Agent.State.INFECTED) {
                infectedCount++;
            } else if (agent.getState() == Agent.State.RECOVERED) {
                recoveredCount++;
            }
        }

        this.infectedCount = infectedCount;
        this.recoveredCount = recoveredCount;
    }
}
