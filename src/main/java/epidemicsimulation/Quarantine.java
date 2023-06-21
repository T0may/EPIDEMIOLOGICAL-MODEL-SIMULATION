package epidemicsimulation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa Quarantine odpowiedzialna jest za funkcjonalność kwarantanny podczas symulacji
 */
public class Quarantine {
    private int capacity;
    private int currentOccupancy;
    private List<Agent> quarantinedAgents;
    private List<Agent> agents;
    private SimulationGUI simulationGUI;

    /**
     * Konstruktor klasy Quarantine.
     * @param capacity pojemność kwarantanny
     * @param agents lista agentów
     * @param simulationGUI obiekt SimulationGUI
     */
    public Quarantine(int capacity, List<Agent> agents, SimulationGUI simulationGUI) {
        this.capacity = capacity;
        this.currentOccupancy = 0;
        this.quarantinedAgents = new ArrayList<>();
        this.agents = agents;
        this.simulationGUI = simulationGUI;
    }

    /**
     * Konstruktor klasy Quarantine.
     * @param agent
     */
    public void addToQuarantine(Agent agent) {
        if (currentOccupancy < capacity) {
            quarantinedAgents.add(agent);
            agent.setStatus(Agent.AgentStatus.QUARANTINED);
            currentOccupancy++;
//            System.out.println("Agent " + agent + " has been added to the quarantine");
            agent.setQuarantined(true);
        } else {
            agent.setStatus(Agent.AgentStatus.SUSCEPTIBLE);
            agent.setColor(Color.PINK);
//            System.out.println("Quarantine is full, cannot add more agents");
        }
    }

    /**
     * Zatrzymanie kwarantanny na podstawie aktualnej liczby chorych agentów
     */
    public void stopQuarantine() {
        for (Agent agent : quarantinedAgents) {
            agent.setQuarantined(false);
        }
        quarantinedAgents.clear();
    }

    // Zwolnienie agenta z kwarantanny

    /**
     * Zwolnienie agenta z kwarantanny
     * @param agent
     */
    public void releaseAgent(Agent agent) {
        if (quarantinedAgents.contains(agent)) {
            quarantinedAgents.remove(agent);
            currentOccupancy--;
            agent.setStatus(Agent.AgentStatus.SUSCEPTIBLE);
            agent.setColor(Color.PINK);
//            System.out.println("Agent " + agent + " has been released from the quarantine");
        } else {
//            System.out.println("Agent " + agent + " is not in the quarantine");
        }
    }

    /**
     * Pobranie listy agentów w kwarantannie.
     *
     * @return lista agentów w kwarantannie
     */
    public List<Agent> getQuarantinedAgents()
    {
        return quarantinedAgents;
    }
}
