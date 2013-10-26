package flow.backend;

import java.io.IOException;
import java.util.Date;

public class Aprox {
	private int bestSolutionEval;
	private Solution bestSolution;
	private Solver solver;

	public void solve(long maxTime) {
		boolean better = false;
		int neighborEval, localSolutionEval;
		Solution localSolution;
		long elapsedTime = 0;
		maxTime += new Date().getTime();
		boolean firsttime = true;
		while (elapsedTime < maxTime) {
			localSolution = randomSolution(solver);
			if (firsttime) {
				System.out.println("Soluci—n Base: " + localSolution.evaluate()
						+ " celdas pintadas.");
				localSolution.printSolution();
				firsttime = false;
				System.out.println();
			}
			localSolutionEval = localSolution.evaluate();
			do {
				better = false;
				for (Solution neighbor : localSolution.neighbors(solver)) {
					neighborEval = neighbor.evaluate();
					if (neighborEval > localSolutionEval) {
						localSolution = neighbor;
						localSolutionEval = neighborEval;
						better = true;
					}
				}
			} while (better);
			if (localSolutionEval > bestSolutionEval) {
				if (localSolutionEval == localSolution.getCellsSize()) {
					System.out
							.println("Felicitaciones!: Ha llegado a la soluci—n exacta.");
					localSolution.printSolution();
					return;
				}
				System.out.println("Mejor Soluci—n Encontrada: "
						+ localSolutionEval + " celdas pintadas.");
				bestSolutionEval = localSolutionEval;
				bestSolution = localSolution;
			}
			elapsedTime = new Date().getTime();
		}
		bestSolution.printSolution();
	}

	private Solution randomSolution(Solver solver) {
		solver.solve(false);
		return new Solution(solver.getSolvedBoard(), solver.getPaintedCells());
	}

	public static void main(String[] args) throws IOException {
		int[][] startboard = ReadFile.readFile("grids/francogrids/5x10");
		long lStartTime = new Date().getTime();
		Aprox aprox = new Aprox();
		aprox.solver = new Solver(startboard);
		aprox.solve(20000);
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Milisegundos transcurridos: " + difference + ".");
	}
}