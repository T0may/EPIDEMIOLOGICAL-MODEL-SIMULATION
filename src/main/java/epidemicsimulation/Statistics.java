package epidemicsimulation;

import java.util.List;

public class Statistics{
    private Population population;
    private Disease disease;

    // Konstruktor klasy Statistics, przyjmuje populację jako parametr
    public Statistics(Population population) {
        this.population = population;
    }
    // Obliczanie liczby zarażonych agentów
    public int calculateInfectedCount() {
        return population.getInfectedCount();
    }
    // Obliczanie liczby wyzdrowiałych agentów
    public int calculateRecoveredCount() {
        return population.getRecoveredCount();
    }
}