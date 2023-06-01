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
            System.out.println(agent.getName() + " has been added to quarantine at " + location);
        } else {
            System.out.println("Quarantine at " + location + " is at full capacity. Cannot add " + agent.getName());
        }
    }

    public void monitorHealthStatus() {
        for (Agent agent : quarantinedAgents) {
            agent.getState(); // getState() is a method in the Agent class
        }
    }

    public void releaseAgent(Agent agent) {
        if (quarantinedAgents.contains(agent)) {
            quarantinedAgents.remove(agent);
            currentOccupancy--;
            System.out.println(agent.getName() + " has been released from quarantine at " + location);
        } else {
            System.out.println(agent.getName() + " is not currently in quarantine at " + location);
        }
    }
}