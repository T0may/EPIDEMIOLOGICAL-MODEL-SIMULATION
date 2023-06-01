package epidemicsimulation;
public class Simulation {
    private Population population;
    private Disease disease;
    private Quarantine quarantine;
    private int initialInfectedCount;
    private int populationSize;

    public Simulation() {
        // Inicjalizacja domyślnych wartości parametrów początkowych
        initialInfectedCount = 0;
        populationSize = 0;

        // Inicjalizacja pustych obiektów populacji, choroby i kwarantanny
        population = new Population();


        // Inicjalizacja choroby
        String diseaseName = "COVID-19";
        double infectionRate = 0.1;
        int incubationPeriod = 14;
        int mortalityRate = 5;

        disease = new Disease(diseaseName, infectionRate, incubationPeriod, mortalityRate);

        // Inicjalizacja kwarantanny
        String quarantineLocation = "City A";
        int quarantineCapacity = 100;

        quarantine = new Quarantine(quarantineLocation, quarantineCapacity);
    }

    // getter i setter dla initialInfectedCount
    public void setInitialInfectedCount(int count) {
        this.initialInfectedCount = count;
    }

    public int getInitialInfectedCount() {
        return initialInfectedCount;
    }

    // getter i setter dla populationSize
    public void setPopulationSize(int size) {
        this.populationSize = size;
    }

    public int getPopulationSize() {
        return populationSize;
    }

}



