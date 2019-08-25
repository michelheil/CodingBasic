package de.javabook.chapter5;


public class Musicplayer {

    public static void main(String[] args){

        // initialize class Song using one of the three constructors
        // 1.
        Song erstesLied = new Song("Avicii", "Levels", 230);
        erstesLied.drucke();

        // 2.
        Song zweitesLied = new Song("Levels", 230);
        zweitesLied.drucke();

        // 3.
        Song drittesLied = new Song("Levels", 1, 20, 40);
        drittesLied.drucke();

        System.out.println(Song.getGesamtLaenge());

        drittesLied.setLaenge(1,20,39);
        System.out.println(Song.getGesamtLaenge());

    }

}
