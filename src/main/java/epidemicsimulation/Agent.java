package epidemicsimulation;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Agent{
    public enum AgentStatus {
        SUSCEPTIBLE,
        INFECTED,
        RECOVERED
    }
    private AgentStatus status;
    private Disease disease;
    private int recoveryTime;
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
        if (status == status.SUSCEPTIBLE) {
            this.status = status.INFECTED;
            this.disease = disease;
            this.recoveryTime = disease.getIncubationPeriod();
            this.color = Color.RED;
            this.disease.simulateInfection(this);
            System.out.println("Agent has been infected with " + disease.getName());
        }else{
            System.out.println("Agent is already" + status);
        }
    }

    public void recover()
    {
        if (status == status.INFECTED && recoveryTime <= 0) {
            this.status = status.RECOVERED;
            this.disease = null;
            this.color = Color.GREEN;
            System.out.println("Agent has recovered from the disease");
        } else if (status == status.INFECTED && recoveryTime > 0) {
            System.out.println("Agent is still infected");
        } else {
            System.out.println("Agent is not infected");
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

//            System.out.println("Row difference: " + rowDiff);
//            System.out.println("Column difference: " + colDiff);
//            System.out.println("Other agent status: " + otherAgent.getStatus());
//            System.out.println("This agent status: " + this.getStatus());

            // Check if the other agent is one square away
            if (rowDiff <= 1 && colDiff <= 1 && otherAgent.getStatus() == AgentStatus.SUSCEPTIBLE && this.getStatus() == AgentStatus.INFECTED) {
                double infectionProbability = getDisease().getInfectionRate();
                System.out.println(infectionProbability);
                if (Math.random() < infectionProbability) {
                    otherAgent.infect(this.getDisease());
                }

            }
        }
    }

}