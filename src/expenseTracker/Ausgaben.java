package expenseTracker;

public class Ausgaben {
    private String name;
    private String datum;
    private String beschreibung;
    private double kosten;

    public Ausgaben(String name, String datum, String beschreibung, double kosten) {
        this.name = name;
        this.datum = datum;
        this.beschreibung = beschreibung;
        this.kosten = kosten;
    }

    public String getName() {
        return name;
    }

    public String getDatum() {
        return datum;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public double getKosten() {
        return kosten;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Datum: " + datum + ", Beschreibung: " + beschreibung + ", Kosten: " + kosten + " EUR";
    }
}

