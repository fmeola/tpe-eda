package flow.backend;

import java.io.IOException;
import java.util.Date;

public class AproxAlgorithm {
	
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
		while(elapsedTime < maxTime) {
			localSolution = randomSolution(solver);
			if(firsttime) {
				System.out.println("primera solucion:"+ localSolution.evaluate());
				localSolution.print();
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
				System.out.println("local: " + localSolutionEval + " best: " + bestSolutionEval);
				bestSolutionEval = localSolutionEval;
				bestSolution = localSolution;
			}
			elapsedTime = new Date().getTime();
		}
		bestSolution.print();
	}

	private Solution randomSolution(Solver solver) {
		solver.solve(solver.getLastPainted(), false);
		int[][] endboard = solver.getAuxMatrix();
		return new Solution(endboard, solver.getLastPainted());
	}
	
	public static void main(String[] args) throws IOException {
		int[][] startboard = ReadFile.readFile("grids/numberlinkgrids/mastermindlevel10016x16");
		long lStartTime = new Date().getTime();
		AproxAlgorithm aprox = new AproxAlgorithm();
		aprox.solver = new Solver(startboard);
		aprox.solve(20000);
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Milisegundos transcurridos: " + difference + ".");
	}
}