package epidemicsimulation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class Agent{
    public enum AgentStatus {
        SUSCEPTIBLE,
        INFECTED,
        RECOVERED,
        DEAD,
        QUARANTINED
    }
    private AgentStatus status;
    private Disease disease;
    private int recoveryTime;
    private double mortality_rate;
    private int incubationPeriod;
    private int row;
    private int col;
    private int currentRow;
    private int currentCol;
    private int targetRow;
    private int targetCol;
    private Color color;


    public Agent(){
        this.status = status.SUSCEPTIBLE;
        this.recoveryTime = 0;
    }
    public Agent(int row, int col){
        this();
        this.row = row;
        this.col = col;
        this.currentRow = row;
        this.currentCol = col;
        this.targetRow = row;
        this.targetCol = col;
        this.disease = new Disease();
    }

    public void infect(Disease disease)
    {
        if (this.status == AgentStatus.SUSCEPTIBLE) {
            this.status = status.INFECTED;
            this.disease = disease;
            this.recoveryTime = disease.getIncubationPeriod();
            this.color = Color.RED;
            this.incubationPeriod = disease.getIncubationPeriod();
            System.out.println("Agent has been infected with " + disease.getName());
        }else{
            System.out.println("Agent is already" + status);
        }
    }
    public synchronized void death_count(Disease disease) {


            if (this.status == AgentStatus.INFECTED) {
                    this.status = status.DEAD;
                    this.disease = disease;
                    }
            }

    public void deacreaseIncubationPeriod(List<Agent> agents){
        if(this.status == status.INFECTED && this.incubationPeriod > 0){
            this.incubationPeriod--;
//            System.out.println(this.incubationPeriod);
        }
        if(this.incubationPeriod == 0){
            recover();
        }
    }
    public void recover()
    {
        if (status == status.INFECTED && this.incubationPeriod <= 0) {
            this.status = status.RECOVERED;
            this.disease = null;
            this.color = Color.GREEN;
            System.out.println("Agent has recovered from the disease");
        } else if (status == status.INFECTED && this.incubationPeriod > 0) {
            System.out.println("Agent is still infected");
        } else {
            // System.out.println("Agent is not infected");
        }
    }

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
        this.color = color;
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
        setCurrentPosition(newRow, newCol);
    }

    public void checkInfection(List<Agent> agents) {
        for (Agent otherAgent : agents) {
            if (this == otherAgent) {
                continue;
            }

            int rowDiff = Math.abs(getRow() - otherAgent.getRow());
            int colDiff = Math.abs(getCol() - otherAgent.getCol());

            // Check if the other agent is one square away
            if (rowDiff <= 1 && colDiff <= 1 && otherAgent.getStatus() == AgentStatus.SUSCEPTIBLE && this.getStatus() == AgentStatus.INFECTED) {
                double infectionProbability = getDisease().getInfectionRate();
                System.out.println(infectionProbability);

                double mortalityProbability = getDisease().getMortalityRate();
                System.out.println(mortalityProbability);
                if (Math.random() < infectionProbability) {
                    otherAgent.infect(this.getDisease());
                }
                if (Math.random()*(9) < infectionProbability) {
                    otherAgent.death_count(this.getDisease());
                    System.out.println("AGENT DEAD");
                }

            }
        }
    }

}