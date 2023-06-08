package epidemicsimulation;

import java.util.ArrayList;
import java.util.List;

public class Quarantine {
    private String location;
    private int capacity;
    private int currentOccupancy;
    private List<Agent> quarantinedAgents;

    public Quarantine(String location, int capacity) {
        this.location = location;
        this.capacity = capacity;
        this.currentOccupancy = 0;
        this.quarantinedAgents = new ArrayList<>();
    }

    public void addToQuarantine(Agent agent) {
        if (currentOccupancy < capacity) {
            quarantinedAgents.add(agent);
            currentOccupancy++;
        }
    }

    public void monitorHealthStatus() {
        for (Agent agent : quarantinedAgents) {
            agent.getStatus(); // getState() is a method in the Agent class
        }
    }

    public void releaseAgent(Agent agent) {
        if (quarantinedAgents.contains(agent)) {
            quarantinedAgents.remove(agent);
            currentOccupancy--;
        } else {

        }
    }
}