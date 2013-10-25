package flow.backend;

import java.io.IOException;
import java.util.Date;

public class ExactAlgorithm3 {
	public static void main(String[] args) throws IOException {
		int[][] startboard = ReadFile.readFile("grids/flowgrids/level308x8");
		long lStartTime = new Date().getTime();
		Solver tour = new Solver(startboard);
		int painted = tour.getFichas().size() * 2;
		tour.solve(painted,true);
		/* Incluirlo en solve? */
		for (int[] fil : tour.getAuxMatrix()) {
			for (int r : fil)
				System.out.printf("%2d", r);
			System.out.println();
		}
		/* */
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Milisegundos transcurridos: " + difference + ".");
	}
}
