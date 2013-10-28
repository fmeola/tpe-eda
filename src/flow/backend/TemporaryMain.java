package flow.backend;

import java.io.IOException;
import java.util.Date;

public class TemporaryMain {
	/* Exacto */
	
	public static void main(String[] args) {
		int[][] startboard = null;
		try {
			startboard = ReadFile.readFile("grids/francogrids/5x4SinSolucion");
		} catch (IOException e) {
			e.printStackTrace();
		}
		long lStartTime = new Date().getTime();
		Solver s = new Solver(startboard);
		if(!s.solve(true, true)) {
			System.out.println("Lo sentimos, este tablero no tiene soluci—n.");
			return;
		}
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		s.printBoard();
		System.out.println("Milisegundos transcurridos: " + difference);
	}
	/*
	public static void main(String[] args) {
		int[][] startboard = null;
		try {
			startboard = ReadFile.readFile("grids/numberlinkgrids/mastermindlevel10016x16");
		} catch (IOException e) {
			e.printStackTrace();
		}
		long lStartTime = new Date().getTime();
		Solver s = new Solver(startboard);
		Approx a = new Approx(s);
		a.solve(5000);
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Milisegundos transcurridos: " + difference);
	}
	*/
}
