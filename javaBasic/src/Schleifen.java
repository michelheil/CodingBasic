package de.javabook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
/**
 * Playing with Wiederholungen, as in Book Chapter 4
 * @author michael
 */

public class Schleifen {

    public static void main(String[] args) throws IOException {

        Random zufall = new Random();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int gesucht = zufall.nextInt((101));
        int tipp;

        System.out.println("Gesucht ist die Zahl: " + gesucht);

        do {
            System.out.println("Bitte gib eine Zahl zwischen 0 und 100 ein: ");
            tipp = Integer.parseInt(reader.readLine());

            if (tipp == gesucht) {
                System.out.println("Richtig geraten!");
            } else if (tipp < gesucht) {
                System.out.println("Die gesuchte Zahl ist groesser als " + tipp);
            } else {
                System.out.println("Die gesuchte Zahl ist kleiner als " + tipp);
            }

        } while (gesucht != tipp);

    }

}
