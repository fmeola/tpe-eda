package flow.backend;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExactAlgorithm {

	/* Idea: Solución Ej 3, Práctica 9 */
	private static final int[][] moves = { { 1, 0 }, { -1, 0 }, { 0, 1 },
			{ 0, -1 } };

	private static int[][] board;
	private int rows;
	private int cols;
	private Map<Integer, List<int[][]>> solvedPaths;
	private Map<Integer, List<Point>> fichas;
	private int cant;
	private boolean foundSolution;

	public ExactAlgorithm(int[][] startboard) {
		this.rows = startboard.length;
		this.cols = startboard[0].length;
		this.board = startboard;
		this.solvedPaths = new HashMap<Integer, List<int[][]>>();
		this.foundSolution = false;
		Map<Integer, List<Point>> fichas = new HashMap<Integer, List<Point>>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (startboard[i][j] != 0) {
					if (!fichas.containsKey(startboard[i][j])) {
						fichas.put(startboard[i][j], new ArrayList<Point>());
						fichas.get(startboard[i][j]).add(0, new Point(i, j));
					} else {
						fichas.get(startboard[i][j]).add(1, new Point(i, j));
					}
				}
			}
		}
		this.fichas = fichas;
		this.cant = 0;
	}

	private boolean solve(int color, int row, int col, int endrow, int endcol,
			 int painted) {

			
		if (row == endrow && col == endcol) {
						
			/*
			 * Si llegué a la meta entonces guardo una copia del tablero en la
			 * lista de soluciones.
			 */
			if (color < fichas.size()) {
				Point start = fichas.get(color+1).get(0);
				Point end = fichas.get(color+1).get(1);
				if (solve(color+1, start.x, start.y, end.x, end.y, painted))
					return true;
			}
			if(painted == rows*cols && fichas.size()== color)
		    {
				for (int[] fil : board) {
					for (int r : fil) {
						System.out.printf("%2d", r);
					}
					System.out.println();
				}
				return true;
			}
			return false;
			
			/* Sino me llamo recursivamente con cada posible movimiento. */
		} else {
			
			boolean flag = false;
			if (board[row][col] == 0) {
				board[row][col] = color;
				flag=true;
				painted++;
				
			}
			for (int[] move : moves) {
				int i = row + move[0];
				int j = col + move[1];

				if (i >= 0 && i < rows && j >= 0 && j < cols
						&& ((board[i][j] == 0) || (i == endrow && j == endcol))) {
					if (solve(color, i, j, endrow, endcol, painted))
						return true;
				}

			}
			/* Backtracking: Me despinto para intentar otro camino. */
			if(flag){
				board[row][col] = 0;
				painted--;
				
			}
			return false;
		}

	}

	public void printList(List<int[][]> l) {
		for (int[][] board : l) {
			for (int[] row : board) {
				for (int r : row) {
					System.out.printf("%2d", r);
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	public void setBoard(int[][] newboard) {
		board = newboard;
	}

	public static void main(String[] args) throws IOException {
		/*
		 * int[][] startboard = { { 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 4, 0 }, {
		 * 0, 0, 0, 3, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 2, 4, 0 }, { 0,
		 * 0, 2, 3, 0, 0 } };
		 */
		int[][] startboard2 = { { 0, 0, 4, 0, 0, 0, 2 },
				{ 0, 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 5, 0, 0, 0 }, { 0, 0, 1, 3, 0, 0, 0 },
				{ 0, 5, 2, 0, 0, 3, 0 }, { 0, 0, 0, 4, 0, 0, 0 } };
		
		int[][] startboard = ReadFile.readFile("grids/regular9x9level30");
		board = startboard;
		long lStartTime = new Date().getTime(); // start time
		ExactAlgorithm tour = new ExactAlgorithm(startboard);		
		int painted = tour.fichas.size() *2;
		tour.solve(1, tour.fichas.get(1).get(0).x, tour.fichas.get(1).get(0).y,
				tour.fichas.get(1).get(1).x, tour.fichas.get(1).get(1).y, painted);
		long lEndTime = new Date().getTime(); // end time
		long difference = lEndTime - lStartTime; // check different
		System.out.println("Milisegundos transcurridos: " + difference + ".");
	}
}
