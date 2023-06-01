package epidemicsimulation;

import java.util.List;

public class Statistics{
    private Population population;
    private Disease disease;


    public Statistics(Population population) {
        this.population = population;
    }
    public int calculateInfectedCount() {
        return population.getInfectedCount();
    }

    public int calculateRecoveredCount() {
        return population.getRecoveredCount();
    }
}