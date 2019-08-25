package de.home.michael;

public class Main {

    public static void main(String[] args) {

        int zahl1 = 80432;
        int zahl2 = 41;

        KGV berechnung = new KGV();

        System.out.println("Das kleinste gemeinsame Vielfache der Zahlen " + zahl1 + " und " + zahl2 +
                " lautet " + berechnung.berechneKgv(zahl1, zahl2));

    }

}
