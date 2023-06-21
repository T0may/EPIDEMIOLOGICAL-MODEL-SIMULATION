package epidemicsimulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa Population reprezentuje populacje, zawiera informacje o jego liczbie, stanach.
 */
public class Population {
    private List<Agent> agents;
    private int infectedCount;
    private int recoveredCount;

    /**
     * Konstruktor klasy Population.
     */
    public Population() {
        agents = new ArrayList<>();
        infectedCount = 0;
        recoveredCount = 0;
    }

    /**
     * Dodanie agenta do populacji.
     * @param agent
     */
    public void addAgent(Agent agent) {
        agents.add(agent);
    }

    /**
     * Pobranie liczby zainfekowanych agentów
     * @return liczba zainfekowanych agentów
     */
    public int getInfectedCount() {
        int count = 0;
        for (Agent agent : agents) {
            if (agent.getStatus() == Agent.AgentStatus.INFECTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Pobranie liczby wyzdrowiałych agentów
     * @return liczba wyzdrowiałych agentów
     */
    public int getRecoveredCount() {
        int count = 0;
        for (Agent agent : agents) {
            if (agent.getStatus() == Agent.AgentStatus.RECOVERED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Pobranie liczby podarnych na zarażenie agentów.
     * @return liczba podarnych agentów
     */
    public int getSusceptibleCount() {
        int count = 0;
        for (Agent agent : agents) {
            if (agent.getStatus() == Agent.AgentStatus.SUSCEPTIBLE) {
                count++;
            }
        }
        return count;
    }

    /**
     * Aktualizacja stanu populacji.
     * Metoda aktualizuje liczbę zainfekowanych i wyzdrowiałych agentów w populacji.
     */
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
