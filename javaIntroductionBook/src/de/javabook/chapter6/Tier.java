package de.javabook.chapter6;

public class Tier {

    // fields
    public static final char MAENNLICH = 'm';
    public static final char WEIBLICH = 'w';

    private String name;
    private int gewichtInGramm;
    private char geschlecht;

    // setter methods
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name ist null");
        }
        this.name = name;
    }

    public void setGewichtInGramm(int gewichtInGramm) {
        if (gewichtInGramm < 0) {
            throw new IllegalArgumentException("GewichtInGramm ist negativ");
        }
        this.gewichtInGramm = gewichtInGramm;
    }

    public void setGeschlecht(char geschlecht) {
        if (geschlecht != MAENNLICH && geschlecht != WEIBLICH) {
            throw new IllegalArgumentException("Geschlecht kann nur die Werte 'm' oder 'w' annehmen");
        }
        this.geschlecht = geschlecht;
    }

    // getter methods
    public String getName() {
        return this.name;
    }

    public int getGewichtInGramm() {
        return this.gewichtInGramm;
    }

    public char getGeschlecht() {
        return this.geschlecht;
    }

    // constructor
    Tier() {
        this.setName("Horst");
        this.setGewichtInGramm(6500);
        this.setGeschlecht('m');
    }

//    Tier(String name, int gewichtInGramm, char geschlecht) {
//        this.setName(name);
//        this.setGewichtInGramm(gewichtInGramm);
//        this.setGeschlecht(geschlecht);
//    }

    // toString method
    public String toString() {
        return "Name lautet: " + name + "\n" +
                "Gewicht in Gramm betraegt: " + gewichtInGramm + "\n" +
                "Geschlecht ist: " + geschlecht;
    }

}
