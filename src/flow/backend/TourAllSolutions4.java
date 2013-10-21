package flow.backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TourAllSolutions4 {

	/* Idea: Solución Ej 3, Práctica 9 */
	private static final int[][] moves = { { 1, 0 }, { -1, 0 }, { 0, 1 },
			{ 0, -1 } };

	private int[][] board;
	private int rows;
	private int cols;
	private Map<Integer, List<int[][]>> solvedPaths;

	public TourAllSolutions4(int[][] startboard) {
		this.rows = startboard.length;
		this.cols = startboard[0].length;
		this.board = startboard;
		this.solvedPaths = new HashMap<Integer, List<int[][]>>();
	}

	private void solve(int color, int row, int col, int endrow, int endcol,
			List<int[][]> res) {
		/* Si me choqué con un color distinto al mío me voy. */
		if (board[row][col] != 0 && board[row][col] != color)
			return;
		/* Sino pinto el casillero vacío con mi color. */
		board[row][col] = color;
		/* Si llegué a la meta entonces guardo una copia del tablero
		 * en la lista de soluciones. */
		if (row == endrow && col == endcol) {
			int[][] newBoard = new int[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					newBoard[i][j] = board[i][j];
				}
			} 
			res.add(newBoard);
		/* Sino me llamo recursivamente con cada posible movimiento. */
		} else {
			for (int[] move : moves) {
				int i = row + move[0];
				int j = col + move[1];
				if (i >= 0 && i < rows && j >= 0 && j < cols
						&& ((board[i][j] == 0) || (i == endrow && j == endcol))) {
					solve(color, i, j, endrow, endcol, res);
				}
			}
		}
		/* Backtracking: Me despinto para intentar otro camino. */
		if (board[row][col] == color)
			board[row][col] = 0;
	}

	public void printList(List<int[][]> l) {
		System.out.println("SOLUCIÓN");
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

	public static void main(String[] args) {
		//int[][] startboard = { { 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 4, 0 },
		//		{ 0, 0, 0, 3, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
		//		{ 0, 0, 1, 2, 4, 0 }, { 0, 0, 2, 3, 0, 0 } };
		int[][] startboard = { 
				{0,0,4,0,0,0,2},
				{0,0,0,0,1,0,0},
				{0,0,0,0,0,0,0},
				{0,0,0,5,0,0,0},
				{0,0,1,3,0,0,0},
				{0,5,2,0,0,3,0},
				{0,0,0,4,0,0,0}};
		long lStartTime = new Date().getTime(); // start time
		TourAllSolutions4 tour = new TourAllSolutions4(startboard);
		Map<Integer, List<Point>> fichas = new HashMap<Integer, List<Point>>();
		for (int i = 0; i < tour.rows; i++) {
			for (int j = 0; j < tour.cols; j++) {
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
		for (Integer n : fichas.keySet()) {
			Point start = fichas.get(n).get(0);
			Point end = fichas.get(n).get(1);
			List<int[][]> aux = new ArrayList<int[][]>();
			tour.solve(n, start.x, start.y, end.x, end.y, aux);
			tour.solvedPaths.put(n, aux);
			// Arreglar este parche!
			//int[][] newBoard = { { 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 4, 0 },
			//		{ 0, 0, 0, 3, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
			//		{ 0, 0, 1, 2, 4, 0 }, { 0, 0, 2, 3, 0, 0 } };
			int[][] newBoard = { { 0, 0, 4, 0, 0, 0, 2 },
					{ 0, 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 5, 0, 0, 0 }, { 0, 0, 1, 3, 0, 0, 0 },
					{ 0, 5, 2, 0, 0, 3, 0 }, { 0, 0, 0, 4, 0, 0, 0 } };
			tour.setBoard(newBoard);
		}
		for (Integer k : tour.solvedPaths.keySet()) {
			System.out.println("Ficha " + k + ": " + tour.solvedPaths.get(k).size() + " caminos.");
		}
		// falta caso si hay un sólo tipo de ficha
		List<int[][]> boardsA = tour.solvedPaths.get(tour.solvedPaths.keySet()
				.toArray()[0]);
		Integer boardsAcolor = (Integer) tour.solvedPaths.keySet().toArray()[0];
		List<Integer> colorsIntersected = new ArrayList<Integer>();
		colorsIntersected.add(boardsAcolor);
		for (int n = 1; n < tour.solvedPaths.keySet().toArray().length; n++) {
			List<int[][]> boardsB = tour.solvedPaths.get(tour.solvedPaths
					.keySet().toArray()[n]);
			Integer boardsBcolor = (Integer) tour.solvedPaths.keySet()
					.toArray()[n];
			List<int[][]> auxList = new ArrayList<int[][]>();
			for (int[][] boardA : boardsA) {
				for (int[][] boardB : boardsB) {
					boolean flag = true;
					for (int i = 0; i < tour.rows && flag; i++) {
						for (int j = 0; j < tour.cols && flag; j++) {
							if (colorsIntersected.contains(boardA[i][j])
									&& boardB[i][j] == boardsBcolor) {
								flag = false;
							}
						}
					}
					if (flag) {
						int[][] aux = new int[tour.rows][tour.cols];
						for (int i = 0; i < tour.rows; i++) {
							for (int j = 0; j < tour.cols; j++) {
								if (boardA[i][j] == boardB[i][j])
									aux[i][j] = boardA[i][j];
								else
									aux[i][j] = boardA[i][j] + boardB[i][j];
							}
						}
						auxList.add(aux);
					}
				}
			}
			boardsA = auxList;
			colorsIntersected.add(boardsBcolor);
		}
		System.out.println("Soluciones Encontradas: " + boardsA.size() + ".");
		tour.printList(boardsA);
		long lEndTime = new Date().getTime(); // end time
		long difference = lEndTime - lStartTime; // check different
		System.out.println("Milisegundos transcurridos: " + difference + ".");
	}
}
