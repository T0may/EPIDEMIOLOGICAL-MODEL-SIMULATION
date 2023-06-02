package epidemicsimulation;

import java.util.List;

public class Agent{
    public enum State {
        SUSCEPTIBLE,
        INFECTED,
        RECOVERED
    }
    private String name;
    private State state;
    private Disease disease;
    private int recoveryTime;
    private int row;
    private int col;

    public Agent(){
        this.name = name;
        this.state = State.SUSCEPTIBLE;
        this.disease = null;
        this.recoveryTime = 0;
    }
    public Agent(int row, int col){
        this();
        this.row = row;
        this.col = col;    }

    public void infect(Disease disease)
    {
        if (state == State.SUSCEPTIBLE) {
            this.state = State.INFECTED;
            this.disease = disease;
            this.recoveryTime = disease.getIncubationPeriod();
            System.out.println("Agent has been infected with" + disease.getName());
        }else{
            System.out.println("Agent is already" + state);
        }
    }

    public void recover()
    {
        if (state == State.INFECTED && recoveryTime <= 0)
        {
            this.state = State.RECOVERED;
            this.disease = null;
            System.out.println("Agent has recovered from the disease");
        }else if (state == State.INFECTED && recoveryTime > 0)
        {
            System.out.println("Agent has recovered from the disease");
        } else {
            System.out.println("Agent is not infected");
        }
    }

    public State getState(){
        return state;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public Disease getDisease()
    {
        return disease;
    }

    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }




}
