package de.javabook;

/**
 * Playing with variables, as in Book Chapter 2
 * @author michael
 */
public class Variables {

	public static void main(String[] args) {
		
		int summand1 = 10;
		int summand2 = 20;
		int summe;
		
		summe = summand1 + summand2;
		System.out.println(summe);
		
		variablenTypen();

	}
	
	public static void variablenTypen() {
		byte kleineZahl = -100; // byte kann Zahlen von -128 bis 127 speichern (in 8Bit bzw. 1Byte).
		System.out.println("Variable kleineZahl: " + kleineZahl);
		
		char zeichen = '\u0001'; // unicode Zeichen
		System.out.println("Variable zeichen: " + zeichen);
		
		int[] vector = new int[]{10,20,30,40,50}; // Wenn Array initialisiert wird kann die Groesse nicht deklariert werden.
		System.out.println("Variable vector: " + vector[2]);
		
		boolean[] vectorBoolean = new boolean[3]; // Der Default-Wert "false" wird fuer alle Elemente zugewiesen
		System.out.println("Variable vectorBoolean: " + vectorBoolean[2]);
		
		System.out.println("Max_Value byte: "  + Byte.MAX_VALUE);
		System.out.println("Max_Value short: " + Short.MAX_VALUE);
		System.out.println("Max_Value int: "   + Integer.MAX_VALUE);
	}

}

