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
        this.disease = null;
        this.recoveryTime = 0;
        this.color = Color.WHITE;
    }
    public Agent(int row, int col){
        this();
        this.row = row;
        this.col = col;
        this.currentRow = row;
        this.currentCol = col;
        this.targetRow = row;
        this.targetCol = col;}

    public void infect(Disease disease)
    {
        if (status == status.SUSCEPTIBLE) {
            this.status = status.INFECTED;
            this.disease = disease;
            this.recoveryTime = disease.getIncubationPeriod();
            this.color = Color.RED;
            System.out.println("Agent has been infected with" + disease.getName());
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
            int newRow = random.nextInt(40);
            int newCol = random.nextInt(50);
            setTargetPosition(newRow, newCol);
            targetRow = newRow;
            targetCol = newCol;
        }

        int newRow = Integer.compare(targetRow, currentRow) + currentRow;
        int newCol = Integer.compare(targetCol, currentCol) + currentCol;

        setCurrentPosition(newRow, newCol);
    }





}