package epidemicsimulation;

public class Statistics {
    private int susceptibleCount;
    private int infectedCount;
    private Population population;
    private SimulationGUI simulationGUI;
    private Quarantine quarantine;


    public void setSimulationGUI(SimulationGUI simulationGUI) {
        this.simulationGUI = simulationGUI;
    }


    public int getInfectedCount() {
        return infectedCount;
    }

    public void updateSusceptibleCount(int count) {
        susceptibleCount = count;
        simulationGUI.updateSusceptibleCountLabel(count);
    }

    public void updateInfectedCount(int count) {
        infectedCount = count;
        simulationGUI.updateInfectedCountLabel(count);
        if (infectedCount == 0 && quarantine != null) {
            quarantine.stopQuarantine();
        }
    }


}
