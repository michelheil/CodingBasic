package de.javabook;

import java.io.*;
/**
 * Playing with decisions, as in Book Chapter 3
 * @author michael
 */

public class Decision {
	
	public static void main(String[] args) throws IOException {
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String eingabe = reader.readLine();
		
		if(Integer.parseInt(eingabe) % 2 == 0) {
			System.out.println("Nummer " + eingabe + " ist ein toller Film!");
		} else {
			System.out.println("Nummer " + eingabe + " ist ein schlechter Film!");
		}

		System.out.println(bmi(86,191));

		// Testen wie man Strings vergleichen kann
		String myWord = "Hallo1337";
		if (myWord == "Hallo1337") {
			System.out.println("Der Vergleich mit dem == Operator zeigt: Das Wort " + myWord + " entspricht dem Wort Hallo1337");
		} else {
			System.out.println("Der Vergleich mit dem == Operator zeigt: Das Wort " + myWord + " entspricht nicht dem Wort Hallo1337");
		}

		// Besser ist es folgenden Vergleich mit Strings zu machen
		if ("Hallo1337".equals(myWord)) {
			System.out.println("Der Vergleich mit dem == Operator zeigt: Das Wort " + myWord + " entspricht dem Wort Hallo1337");
		}

		System.out.println("Uebung mit durchfallendem Switch");
		int kleineZahl = 5;
		switch (kleineZahl) {
			case 3:
				System.out.println("Die Variable kleineZahl hat nicht den Wert 3");
				break;
			case 8:
				System.out.println("Die Variable kleineZahl hat nicht den Wert 8");
				break;
			case 5:
				System.out.println("Die Variable kleineZahl hat den Wert 5");
//				break;
			default:
				System.out.println("Dieser Satz wird gezeigt, wenn kein Case eintritt oder wenn vorher ein break vergessen wurde.");
		}
		
	}
	
	/* 
	 * weight in [kg]
	 * height in [cm]
	 */
	public static double bmi(int weight, int height) {
		
		if(height == 0) {
			throw new IllegalArgumentException("Die Werte fuer height muss ungleich 0 sein.");
			
		} else {
			double bmiValue = (double)weight / (height * height);
			return 1000*bmiValue;
		}

	}
	
		
}
