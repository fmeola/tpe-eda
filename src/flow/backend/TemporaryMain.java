package flow.backend;

import java.io.IOException;
import java.util.Date;

public class TemporaryMain {
	/* Exacto */
	/*
	public static void main(String[] args) {
		int[][] startboard = null;
		try {
			startboard = ReadFile.readFile("grids/flowgrids/level306x6");
		} catch (IOException e) {
			e.printStackTrace();
		}
		long lStartTime = new Date().getTime();
		Solver s = new Solver(startboard);
		s.solve(true, true);
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		s.printBoard();
		System.out.println("Milisegundos transcurridos: " + difference);
	}
	*/
	public static void main(String[] args) {
		int[][] startboard = null;
		try {
			startboard = ReadFile.readFile("grids/francogrids/15x15");
		} catch (IOException e) {
			e.printStackTrace();
		}
		long lStartTime = new Date().getTime();
		Solver s = new Solver(startboard);
		Approx a = new Approx(s);
		a.solve(6000);
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Milisegundos transcurridos: " + difference);
	}
}
