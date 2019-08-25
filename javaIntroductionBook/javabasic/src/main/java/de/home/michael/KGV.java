package de.home.michael;

public class KGV {

    public int berechneKgv(int zahl1, int zahl2) {
        if (zahl1 <= 0 || zahl2 <= 0)
            throw new IllegalArgumentException("Beide Zahlen muessen >0 sein.");

        int kleinereZahl;
        int groessereZahl;

        if (zahl1 < zahl2) {
            kleinereZahl = zahl1;
            groessereZahl = zahl2;
        } else {
            kleinereZahl = zahl2;
            groessereZahl = zahl1;
        }

        int multiplikator = 1;
        while ((kleinereZahl * multiplikator) % groessereZahl != 0) {
            if(Integer.MAX_VALUE - kleinereZahl < kleinereZahl * multiplikator)
                throw new ArithmeticException("KGV ist groesser als Integer Wertebereich.");
            multiplikator++;
        }

        return kleinereZahl * multiplikator;
    }
}
