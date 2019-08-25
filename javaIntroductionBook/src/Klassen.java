package de.javabook;

import java.io.IOException;

/**
 * Playing with classes and objects, as in Book Chapter 5
 * @author michael
 */

public class Klassen {
    public int zahl = 11;

    public static void main(String[] args) throws IOException {
        zahlDarstellung();
    }

    public static void zahlDarstellung() {
        int zahl = 17;
        System.out.println(zahl);
    }
}
