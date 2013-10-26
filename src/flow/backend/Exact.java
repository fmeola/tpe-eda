package flow.backend;

import java.io.IOException;
import java.util.Date;

public class Exact {
	public static void main(String[] args) throws IOException {
		int[][] startboard = ReadFile.readFile("grids/otros/test3jere");
		long lStartTime = new Date().getTime();
		Solver tour = new Solver(startboard);
		tour.solve(true, true);
		tour.printBoard();
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Milisegundos transcurridos: " + difference + ".");
	}
}
