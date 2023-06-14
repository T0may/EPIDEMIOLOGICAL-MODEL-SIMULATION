package epidemicsimulation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;
import epidemicsimulation.Quarantine;


public class Agent{
    public enum AgentStatus {
        SUSCEPTIBLE,
        INFECTED,
        RECOVERED,
        DEAD,
        QUARANTINED
    }
    private AgentStatus status; // Aktualny status agenta
    private Disease disease; // Choroba, którą agent przechodzi
    private int recoveryTime; // Pozostały czas do wyzdrowienia
    private double mortality_rate; // Współczynnik śmiertelności
    private int incubationPeriod; // Okres wyleczania choroby
    private int row; // Wiersz, w którym znajduje się agent na planszy
    private int col; // Kolumna, w której znajduje się agent na planszy
    private int currentRow; // Aktualny wiersz, w którym znajduje się agent na planszy
    private int currentCol; // Aktualna kolumna, w której znajduje się agent na planszy
    private int targetRow; // Wiersz, do którego agent zmierza
    private int targetCol; // Kolumna, do której agent zmierza
    private Color color; // Kolor agenta na planszy
    private Quarantine quarantine; // Obiekt kwarantanny
    private SimulationGUI simulationGUI; // Obiekt GUI symulacji
    private boolean quarantined; // Czy agent jest w kwarantannie
    private boolean hasSeverSymptoms; // Czy agent ma poważne objawy



// konstruktor domyślny klasy Agent
    public Agent(){
        this.status = status.SUSCEPTIBLE;
        this.recoveryTime = 0;
    }

//    Konstruktor parametryczny
    public Agent(int row, int col, boolean hasSeverSymptoms, Quarantine quarantine, SimulationGUI simulationGUI){
        this(); // Wywołanie domyślnego konstruktora
        this.row = row;
        this.col = col;
        this.currentRow = row;
        this.currentCol = col;
        this.targetRow = row;
        this.targetCol = col;
        this.disease = new Disease(); // Tworzenie nowej choroby dla agenta
        this.quarantine = quarantine;
        this.hasSeverSymptoms = hasSeverSymptoms;
        this.simulationGUI = simulationGUI;
    }

    // Metoda infekująca agenta daną chorobą
    public void infect(Disease disease)
    {
        if (this.status == AgentStatus.SUSCEPTIBLE) {
            this.status = status.INFECTED;
            this.disease = disease;
            this.recoveryTime = disease.getIncubationPeriod();
            this.color = Color.RED;
            this.incubationPeriod = disease.getIncubationPeriod();
//            System.out.println("Agent has been infected with " + disease.getName());
        }else{
//            System.out.println("Agent is already" + status);
        }
    }

    // Metoda zwiększająca licznik zmarłych agentów
    public synchronized void death_count(Disease disease) {
        if (this.status == AgentStatus.INFECTED) {
            this.status = status.DEAD;
            this.disease = disease;
        }
    }

    // Metoda zmniejszająca czas wyleczania choroby i powodująca wyzdrowienie agenta po zakończeniu okresu wyleczania
    public void deacreaseIncubationPeriod(List<Agent> agents){
        if(this.status == status.INFECTED && this.incubationPeriod > 0){
            this.incubationPeriod--;
        }
        if(this.incubationPeriod == 0){
            recover();
        }
    }

    // Metoda powodująca wyzdrowienie agenta
    public void recover()
    {
        if (status == status.INFECTED && this.incubationPeriod <= 0) {
            this.status = status.RECOVERED;
            this.disease = null;
            this.color = Color.GREEN;
//            System.out.println("Agent has recovered from the disease");
        } else if (status == status.INFECTED && this.incubationPeriod > 0) {
//            System.out.println("Agent is still infected");

        }else if(status == status.QUARANTINED)
        {
            this.color = Color.YELLOW;
        }else {
            // System.out.println("Agent is not infected");
        }
    }

//    settery i gettery
    public Disease getDisease()
    {
        return disease;
    }
    public void setDisease(Disease disease)
    {
        this.disease = disease;
    }
    public AgentStatus getStatus() {
        return status;
    }

    public void setStatus(AgentStatus status) {
        this.status = status;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (status == AgentStatus.QUARANTINED) {
            // Ustawianie innego koloru dla agentów w stanie kwarantanny
            this.color = Color.YELLOW;
        } else {
            this.color = color;
        }
    }

    public void setCurrentPosition(int row, int col) {
        this.row = row;
        this.col = col;
        this.currentRow = row;
        this.currentCol = col;
    }

    public void setTargetPosition(int row, int col) {
        this.targetRow = row;
        this.targetCol = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public int getTargetCol() {
        return targetCol;
    }
    public void setIncubationPeriod(int incubationPeriod){
        this.incubationPeriod = incubationPeriod;
    }
    public void setMortality_rate(double mortalityRate){
        this.mortality_rate = mortalityRate;
    }

    // Metoda odpowiedzialna za poruszanie się agenta
    public void move() {
        int currentRow = getCurrentRow();
        int currentCol = getCurrentCol();
        int targetRow = getTargetRow();
        int targetCol = getTargetCol();

        if (currentRow == targetRow && currentCol == targetCol) {
            Random random = new Random();
            int newRow = random.nextInt(60);
            int newCol = random.nextInt(80);
            setTargetPosition(newRow, newCol);
            targetRow = newRow;
            targetCol = newCol;
        }

        int newRow = Integer.compare(targetRow, currentRow) + currentRow;
        int newCol = Integer.compare(targetCol, currentCol) + currentCol;

//        System.out.println("Row" + newRow);
//        System.out.println("Col" +newCol);
        setCurrentPosition(newRow, newCol); // Przypisanie nowej pozycji do agenta

        if (recoveryTime > 0) {
            recoveryTime--;
            if (recoveryTime == 0) {
                recover();
                simulationGUI.getQuarantine().releaseAgent(this);
            }
        }
    }

    // Metoda sprawdzająca, czy agent został zakażony przez innych agentów
    public void checkInfection(List<Agent> agents) {
        for (Agent otherAgent : agents) {
            if (this == otherAgent) {
                continue;
            }

            int rowDiff = Math.abs(getRow() - otherAgent.getRow());
            int colDiff = Math.abs(getCol() - otherAgent.getCol());

            // Sprawdzenie, czy inny agent jest oddalony o jedno pole
            if (rowDiff <= 1 && colDiff <= 1 && otherAgent.getStatus() == AgentStatus.SUSCEPTIBLE && this.getStatus() == AgentStatus.INFECTED) {
                double infectionProbability = getDisease().getInfectionRate();
//                System.out.println(infectionProbability);

                double mortalityProbability = getDisease().getMortalityRate();
//                System.out.println(mortalityProbability);
                if (Math.random() < infectionProbability) {
                    otherAgent.infect(this.getDisease());
                }
                if (Math.random()*(9) < infectionProbability) {
                    otherAgent.death_count(this.getDisease());
                    System.out.println("Agent just died");
                }

                if(this.hasSeverSymptoms()){
                    quarantine.addToQuarantine(otherAgent);
                }

            }
        }
    }

    // Metoda sprawdzająca, czy agent ma poważne objawy, przez co trafi do kwarantanny
    public boolean hasSeverSymptoms() {
        double probability = 0.3; // Prawdopodobieństwo wystąpienia poważnych objawów
        return Math.random() < probability;
    }

    public boolean isQuarantined() {
        return quarantined;
    }
    public void setQuarantined(boolean quarantined) {
        this.quarantined = quarantined;
    }

}