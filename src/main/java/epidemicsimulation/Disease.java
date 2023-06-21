package epidemicsimulation;

/**
 * Klasa Disease reprezentuje choroby na które może zachorować agent
 */
public class Disease extends Agent{
    private String name;
    private double infectionRate;
    private int incubationPeriod;
    private double mortalityRate;

    /**
     * Konstruktor klasy Disease.
     */
    public Disease() {

    }

    /**
     * Ustawienie właściwości choroby na podjstawie jej nazwy.
     * @param name nazwa
     */
    public void setDiseaseProperties(String name)
    {
        switch(name)
        {
            case "COVID-19":
                infectionRate = 0.8;
                incubationPeriod = 14;
                mortalityRate = 0.4;
                break;
            case "Influenza":
                infectionRate = 0.6;
                incubationPeriod = 10;
                mortalityRate = 0.2;
                break;
            case "Common Cold":
                infectionRate = 0.5;
                incubationPeriod = 8;
                mortalityRate = 0;
            default:
                System.out.println("Invalid disease name");
                break;
        }
    }

    /**
     * Metoda pobierająca nazwę choroby.
     * @return nazwa
     */
    public String getName() {
        return name;
    }

    /**
     * metoda która ustawia nazwę choroby.
     * @param name nazwa
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metoda pobierająca wskaźnik zarażalności przesyłany do klasy Agent.java.
     * @return współczynnik zarażalności
     */
    public double getInfectionRate() {
        return infectionRate;
    }

    /**
     * Metoda która pobiera czas kwarantanny.
     * @return czas kwarantanny
     */
    public int getIncubationPeriod() {
        return incubationPeriod;
    }

    /**
     * Metoda pobierająca wskaźnik śmiertelności.
     * @return współczynnik śmiertelności
     */
    public double getMortalityRate() {
        return mortalityRate;
    }

}