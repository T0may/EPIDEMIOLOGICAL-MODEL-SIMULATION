package epidemicsimulation;

/**
 * Klasa Statistics zawiera informacje dotyczące statystyk epidemii.
 */
public class Statistics {
    private int susceptibleCount;
    private int infectedCount;
    private SimulationGUI simulationGUI;
    private Quarantine quarantine;

    /**
     * Metoda ustawiająca obiekt GUI symulacji.
     * @param simulationGUI obiekt GUI symulacji
     */
    public void setSimulationGUI(SimulationGUI simulationGUI) {
        this.simulationGUI = simulationGUI;
    }

    /**
     * Metoda zwracająca liczbę zarażonych agentów.
     * @return liczba zarażonych agentów
     */
    public int getInfectedCount() {
        return infectedCount;
    }

    /**
     * Metoda aktualizująca liczbę podanych agentów.
     * @param count liczba podatnych agentów
     */
    public void updateSusceptibleCount(int count) {
        susceptibleCount = count;
        simulationGUI.updateSusceptibleCountLabel(count);
    }

    /**
     * Metoda aktualizująca liczbę zarażonych agentów.
     * @param count liczba zarażonych agentów
     */
    public void updateInfectedCount(int count) {
        infectedCount = count;
        simulationGUI.updateInfectedCountLabel(count);
        if (infectedCount == 0 && quarantine != null) {
            quarantine.stopQuarantine();
        }
    }
}
