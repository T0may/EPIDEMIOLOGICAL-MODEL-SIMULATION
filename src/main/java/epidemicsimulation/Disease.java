package epidemicsimulation;

public class Disease extends Agent{
    private String name;
    private double infectionRate;
    private int incubationPeriod;
    private double mortalityRate;

//    konstruktor klasy Disease
    public Disease() {

    }

//    Ustawienie właściwości choroby na podjstawie jej nazwy
    public void setDiseaseProperties(String name)
    {
        switch(name)
        {
            case "COVID-19":
                infectionRate = 0.8;
                incubationPeriod = 14;
                mortalityRate = 4;
                break;
            case "Influenza":
                infectionRate = 0.6;
                incubationPeriod = 10;
                mortalityRate = 2;
                break;
            case "Common Cold":
                infectionRate = 0.3;
                incubationPeriod = 5;
                mortalityRate = 0;
            default:
                System.out.println("Invalid disease name");
                break;
        }
    }

//    Pobieranie nazwy choroby
    public String getName() {
        return name;
    }

//    Ustawienie nazwy choroby
    public void setName(String name) {
        this.name = name;
    }

    // Pobieranie wskaźnika zarażalności
    public double getInfectionRate() {
        return infectionRate;
    }

    // Pobieranie okresu wylęgania
    public int getIncubationPeriod() {
        return incubationPeriod;
    }
    // Pobieranie wskaźnika śmiertelności
    public double getMortalityRate() {
        return mortalityRate;
    }

}