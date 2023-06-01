package epidemicsimulation;

public class Disease extends Agent{
    private String name;
    private double infectionRate;
    private int incubationPeriod;
    private double mortalityRate;

    public Disease(String name, double infectionRate, int incubationPeriod, double mortalityRate) {
        this.name = name;
        this.infectionRate = infectionRate;
        this.incubationPeriod = incubationPeriod;
        this.mortalityRate = mortalityRate;
    }

    public String getName() {
        return name;
    }

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
        // Logika symulacji zakażenia agenta
    }

    public void updateAgentState(Agent agent) {
        // Logika aktualizacji stanu agenta zgodnie z chorobą
    }
}