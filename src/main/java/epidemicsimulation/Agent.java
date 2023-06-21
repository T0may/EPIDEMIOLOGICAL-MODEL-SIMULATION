package epidemicsimulation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;
import epidemicsimulation.Quarantine;

/**
 * Klasa Agent reprezentuje Agenta (człowieka) w populacji.
 */
public class Agent{
    /**
     * Reprezentuje różne stany agentów
     */
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


    /**
     * Konstruktor domyślny klasy Agent.
     * Ustawia domyślny status agenta jako podatny i zerowy czas wyzdrowienia.
     */
    public Agent(){
        this.status = status.SUSCEPTIBLE;
        this.recoveryTime = 0;
    }

    /**
     * Konstruktor parametryczny klasy Agent.
     * Inicjalizuje agenta na określonej pozycji planszy, z określonymi objawami i obiektami kwarantanny i GUI symulacji.
     * @param row
     * @param col
     * @param hasSeverSymptoms
     * @param quarantine
     * @param simulationGUI
     */
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

    /**
     * Metoda infekująca agenta daną chorobą.
     * @param disease choroba
     */
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

    /**
     * Metoda zwiększająca licznik zmarłych agentów.
     * Ustawia status agenta na DEAD
     * @param disease choroba
     */
    public synchronized void death_count(Disease disease) {
        if (this.status == AgentStatus.INFECTED) {
            this.status = status.DEAD;
            this.disease = disease;
        }
    }

    /**
     * Metoda zmniejszająca czas wyleczania choroby i powodująca wyzdrowienie agenta po zakończeniu okresu wyleczania.
     * @param agents agenci
     */
    public void deacreaseIncubationPeriod(List<Agent> agents){
        if(this.status == status.INFECTED && this.incubationPeriod > 0){
            this.incubationPeriod--;
        }
        if(this.incubationPeriod == 0){
            recover();
        }
    }

    /**
     * Metoda monitorująca wyzdrowienie agenta oraz status jego kwarantanny.
     */
    public void recover()
    {
        if (status == status.INFECTED && this.incubationPeriod >= 0) {
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

    /**
     * Metoda zwracająca obiekt choroby, którą przechodzi agent.
     * @return obiekt choroby
     */
    public Disease getDisease()
    {
        return disease;
    }

    /**
     * Metoda ustawiająca obiekt choroby dla agenta.
     *
     * @param disease obiekt choroby
     */
    public void setDisease(Disease disease)
    {
        this.disease = disease;
    }

    /**
     * Metoda zwracająca status agenta.
     *
     * @return status agenta
     */
    public AgentStatus getStatus() {
        return status;
    }

    /**
     * Metoda ustawiająca status agenta.
     *
     * @param status nowy status agenta
     */
    public void setStatus(AgentStatus status) {
        this.status = status;
    }

    /**
     * Metoda zwracająca kolor agenta na planszy.
     *
     * @return kolor agenta
     */
    public Color getColor() {
        return color;
    }

    /**
     * Metoda ustawiająca kolor agenta na planszy.
     * Jeśli agent jest w stanie kwarantanny, inny kolor zostaje ustawiony.
     *
     * @param color nowy kolor agenta
     */
    public void setColor(Color color) {
        if (status == AgentStatus.QUARANTINED) {
            // Ustawianie innego koloru dla agentów w stanie kwarantanny
            this.color = Color.YELLOW;
        } else {
            this.color = color;
        }
    }

    /**
     * Metoda ustawiająca aktualną pozycję agenta na planszy.
     *
     * @param row wiersz
     * @param col kolumna
     */
    public void setCurrentPosition(int row, int col) {
        this.row = row;
        this.col = col;
        this.currentRow = row;
        this.currentCol = col;
    }

    /**
     * Metoda ustawiająca docelową pozycję agenta na planszy.
     *
     * @param row wiersz
     * @param col kolumna
     */
    public void setTargetPosition(int row, int col) {
        this.targetRow = row;
        this.targetCol = col;
    }

    /**
     * Metoda zwracająca wartość wiersza, w którym znajduje się agent na planszy.
     *
     * @return wiersz agenta
     */
    public int getRow() {
        return row;
    }

    /**
     * Metoda zwracająca wartość kolumny, w której znajduje się agent na planszy.
     *
     * @return kolumna agenta
     */
    public int getCol() {
        return col;
    }

    /**
     * Metoda zwracająca aktualną wartość wiersza, w którym znajduje się agent na planszy.
     *
     * @return aktualny wiersz agenta
     */
    public int getCurrentRow() {
        return currentRow;
    }

    /**
     * Metoda zwracająca aktualną wartość kolumny, w której znajduje się agent na planszy.
     *
     * @return aktualna kolumna agenta
     */
    public int getCurrentCol() {
        return currentCol;
    }

    /**
     * Metoda zwracająca docelową wartość wiersza, do którego agent zmierza na planszy.
     *
     * @return docelowy wiersz agenta
     */
    public int getTargetRow() {
        return targetRow;
    }

    /**
     * Metoda zwracająca docelową wartość kolumny, do której agent zmierza na planszy.
     *
     * @return docelowa kolumna agenta
     */
    public int getTargetCol() {
        return targetCol;
    }

    /**
     * Metoda ustawiająca aktualną aktualny czas kwarantanny.
     * @param incubationPeriod czas kwarantanny
     */
    public void setIncubationPeriod(int incubationPeriod){
        this.incubationPeriod = incubationPeriod;
    }

    /**
     * Metoda ustawiająca aktualny współczynnik śmiertelności.
     * @param mortalityRate wskaźnik śmiertelności
     */
    public void setMortality_rate(double mortalityRate){
        this.mortality_rate = mortalityRate;
    }

    /**
     * Metoda odpowiedzialna za funkcjonalność poruszania się agenta.
     * Metoda zmniejsza czas zlwolnienia agenta z kwarantanny.
     */
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


    /**
     * Metoda sprawdzająca, czy agent został zakażony przez innych agentów.
     * @param agents agenci
     */
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
                double randMortality = Math.random();
                if (randMortality < mortalityProbability) {
                    otherAgent.death_count(this.getDisease());
                }

                if(this.hasSeverSymptoms()){
                    quarantine.addToQuarantine(otherAgent);
                }

            }
        }
    }


    /**
     * Metoda sprawdzająca, czy agent ma poważne objawy, przez co trafi do kwarantanny.
     * @return możliwość trafienia do kwarantanny
     */
    public boolean hasSeverSymptoms() {
        double probability = 0.3; // Prawdopodobieństwo wystąpienia poważnych objawów
        return Math.random() < probability;
    }

    /**
     * Metoda ustawiająca aktualną kwarantannę (true or false).
     * @param quarantined kwarantanna
     */
    public void setQuarantined(boolean quarantined) {
        this.quarantined = quarantined;
    }

}