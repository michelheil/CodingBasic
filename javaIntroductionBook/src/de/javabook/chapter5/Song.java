package de.javabook.chapter5;

public class Song {

    // class fields
    private String interpret;
    private String titel;
    private int laengeInSekunden;

    private int dauerStunden;
    private int dauerMinuten;
    private int dauerSekunden;

    private static long gesamtLaenge = 0;

    // setter methods
    public void setInterpret(String interpret) {
        if (interpret == null) {
            throw new IllegalArgumentException("Interpret is null");
        }
        this.interpret = interpret;
    }

    public void setTitel(String titel) {
        if (titel == null) {
            throw new IllegalArgumentException("Titel is null");
        }
        this.titel = titel;
    }

    public void setLaengeInSekunden(int laengeInSekunden) {
        if (laengeInSekunden < 0) {
            throw new IllegalArgumentException("LaengeinSekunden ist negativ");
        }
        Song.gesamtLaenge -= this.laengeInSekunden;
        Song.gesamtLaenge += laengeInSekunden;
        this.laengeInSekunden = laengeInSekunden;
    }

    public void setLaenge(int dauerStunden, int dauerMinuten, int dauerSekunden) {
        if (dauerStunden < 0 || dauerMinuten < 0 || dauerSekunden < 0) {
            throw new IllegalArgumentException("Angaben zur zeitlichen Dauer koennen nicht negativ sein");
        }
        Song.gesamtLaenge = Song.gesamtLaenge - (this.dauerStunden * 3600 + this.dauerMinuten * 60 + this.dauerSekunden);
        Song.gesamtLaenge = Song.gesamtLaenge + (dauerStunden * 3600 + dauerMinuten * 60 + dauerSekunden);
        this.dauerStunden = dauerStunden;
        this.dauerMinuten = dauerMinuten;
        this.dauerSekunden = dauerSekunden;
    }

    // getter methods
    public String getInterpret() {
        return this.interpret;
    }

    public String getTitel() {
        return this.titel;
    }

    public int getLaengeInSekunden() {
        return this.laengeInSekunden;
    }

    public int getDauerStunden() {
        return this.dauerStunden;
    }

    public int getDauerMinuten() {
        return this.dauerMinuten;
    }

    public int getDauerSekunden() {
        return this.dauerSekunden;
    }

    public static long getGesamtLaenge() {
        return Song.gesamtLaenge;
    }

    // constructor methods
    Song(String interpret, String titel, int laengeInSekunden) {
        this.setInterpret(interpret);
        this.setTitel(titel);
        this.setLaengeInSekunden(laengeInSekunden);
    }

    Song(String titel, int laengeInSekunden) {
        this("Unbekannter Interpret", titel, laengeInSekunden);
    }

    Song(String titel, int dauerStunden, int dauerMinuten, int dauerSekunden) {
        this.setInterpret("Unbekannter Interpret");
        this.setTitel(titel);
        this.setLaenge(dauerStunden, dauerMinuten, dauerSekunden);
    }


    // methods
    private String formatiereZeit(int dauer) {
        int minuten = dauer / 60;
        int sekunden = dauer % 60;

        if (dauerStunden != 0 && dauerMinuten != 0 && dauerSekunden != 0) {
            return (dauerStunden < 10 ? "0" + dauerStunden : dauerStunden) + ":"
                    + (dauerMinuten < 10 ? "0" + dauerMinuten : dauerMinuten) + ":"
                    + (dauerSekunden < 10 ? "0" + dauerSekunden : dauerSekunden);
        } else {
            return (minuten < 10 ? "0" + minuten : minuten) + ":"
                    + (sekunden < 10 ? "0" + sekunden : sekunden);
        }
    }

    void drucke(){ // access: can be used in all classes of the same package.
        System.out.println(this);
    }

    public String toString() { // overrides the already existing "toString" method that every class has by default.
        return "Der Interpret lautet: " + interpret + "\nDer Titel lautet: " + titel + "\nDie Dauer ist: " + formatiereZeit(laengeInSekunden);
    }
}
