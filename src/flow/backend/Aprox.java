package flow.backend;

import java.io.IOException;
import java.util.Date;

public class Aprox {
	private int bestSolutionEval;
	private Solution bestSolution;
	public Solver solver;

	public void solve(long maxTime) {
		boolean better = false;
		int neighborEval, localSolutionEval;
		Solution localSolution;
		long elapsedTime = 0;
		maxTime += new Date().getTime();
		boolean firstTime = true;
		while (elapsedTime < maxTime) {
			if (firstTime) {
				localSolution = firstSolution(solver);
				System.out.println("Soluci—n Base: " + localSolution.evaluate()
						+ " celdas pintadas.");
				localSolution.printSolution();
				firstTime = false;
				System.out.println();
			} else
				localSolution = randomSolution(solver);
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
		solver.solve(false, false);
		return new Solution(solver.getSolvedBoard(), solver.getPaintedCells());
	}
	
	private Solution firstSolution(Solver solver) {
		solver.solve(false, true);
		return new Solution(solver.getSolvedBoard(), solver.getPaintedCells());
	}

}