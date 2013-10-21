package flow.backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TourAllSolutions5 {

	/* Idea: Solución Ej 3, Práctica 9 */
	private static final int[][] moves = { { 1, 0 }, { -1, 0 }, { 0, 1 },
			{ 0, -1 } };

	private int[][] board;
	private int rows;
	private int cols;
	private Map<Integer, List<int[][]>> solvedPaths;
	private Map<Integer, List<Point>> fichas;
	private int cant;

	public TourAllSolutions5(int[][] startboard) {
		this.rows = startboard.length;
		this.cols = startboard[0].length;
		this.board = startboard;
		this.solvedPaths = new HashMap<Integer, List<int[][]>>();
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

	private void solve(int color, int row, int col, int endrow, int endcol) {
		/* Si me choqué con un color distinto al mío me voy. */
		if (board[row][col] != 0 && board[row][col] != color)
			return;
		/* Sino pinto el casillero vacío con mi color. */
		board[row][col] = color;
		/* Si llegué a la meta entonces guardo una copia del tablero
		 * en la lista de soluciones. */
		if (row == endrow && col == endcol) {
			while (cant < 5) {
				Point start = fichas.get(++cant).get(0);
				Point end = fichas.get(cant).get(1);
				solve(cant, start.x, start.y, end.x, end.y);
			}
			for (int[] fila : board) {
				for (int r : fila) {
					System.out.printf("%2d", r);
				}
				System.out.println();
			}
		/* Sino me llamo recursivamente con cada posible movimiento. */
		} else {
			for (int[] move : moves) {
				int i = row + move[0];
				int j = col + move[1];
				if (i >= 0 && i < rows && j >= 0 && j < cols
						&& ((board[i][j] == 0) || (i == endrow && j == endcol))) {
					solve(color, i, j, endrow, endcol);
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
		TourAllSolutions5 tour = new TourAllSolutions5(startboard);		
		tour.solve(0,0,0,0,0);
		long lEndTime = new Date().getTime(); // end time
		long difference = lEndTime - lStartTime; // check different
		System.out.println("Milisegundos transcurridos: " + difference + ".");
	}
}
