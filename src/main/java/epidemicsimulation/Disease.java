package epidemicsimulation;

public class Disease extends Agent{
    private String name;
    private double infectionRate;
    private int incubationPeriod;
    private double mortalityRate;

    //    public Disease(String name, double infectionRate, int incubationPeriod, double mortalityRate) {
    public Disease() {

    }

    public void setDiseaseProperties(String name)
    {
        switch(name)
        {
            case "COVID-19":
                infectionRate = 0.8;
                incubationPeriod = 14;
                mortalityRate = 3;
                break;
            case "Influenza":
                infectionRate = 0.6;
                incubationPeriod = 2;
                mortalityRate = 1;
                break;
            case "Common Cold":
                infectionRate = 0.3;
                incubationPeriod = 2;
                mortalityRate = 0;
            default:
                System.out.println("Invalid disease name");
                break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public double getInfectionRate() {
        return infectionRate;
    }

    public int getIncubationPeriod() {
        return incubationPeriod;
    }

    public double getMortalityRate() {
        return mortalityRate;
    }

    public void simulateInfection(Agent agent) {
        if (agent.getStatus() == Agent.AgentStatus.SUSCEPTIBLE) {
            double infectionProbability = getInfectionRate();
            if (Math.random() < infectionProbability) {
                agent.setStatus(Agent.AgentStatus.INFECTED);
                System.out.println("Agent has been infected with " + getName());
            }
        }
    }
    public void updateAgentState(Agent agent) {
        // Logika aktualizacji stanu agenta zgodnie z chorobą
    }
}