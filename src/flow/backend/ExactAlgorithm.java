package flow.backend;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExactAlgorithm {
	/* Idea: Soluci—n Ej 3, Pr‡ctica 9 */
	private static final int [][] moves = {{1,0},{-1,0},{0,1},{0,-1}};
	private int [][] board;
	private int rows;
	private int cols;
	private Map<Integer, List<Point>> fichas;

	public ExactAlgorithm(int[][] startboard) {
		this.rows = startboard.length;
		this.cols = startboard[0].length;
		this.board = startboard;
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
	}
	
	public boolean solve(int painted) {
		return solve(1,fichas.get(1).get(0).x,fichas.get(1).get(0).y,fichas.get(1).get(1).x,fichas.get(1).get(1).y,painted);
	}

	private boolean solve(int color, int row, int col, int endrow, int endcol,
			int painted) {
		if (row == endrow && col == endcol) {
			if (color < fichas.size()) {
				Point start = fichas.get(color + 1).get(0);
				Point end = fichas.get(color + 1).get(1);
				if (solve(color + 1, start.x, start.y, end.x, end.y, painted))
					return true;
			}
			if (painted == rows * cols && fichas.size() == color) {
				for (int[] fil : board) {
					for (int r : fil) 
						System.out.printf("%2d", r);
					System.out.println();
				}
				return true;
			}
			return false;
		} else {
			boolean flag = false;
			if (board[row][col] == 0) {
				board[row][col] = color;
				flag = true;
				painted++;
			}
			for (int[] move : moves) {
				int i = row + move[0];
				int j = col + move[1];
				if (i >= 0 && i < rows && j >= 0 && j < cols && ((board[i][j] == 0) || (i == endrow && j == endcol))) {
					if (solve(color, i, j, endrow, endcol, painted))
						return true;
				}
			}
			if (flag) {
				board[row][col] = 0;
				painted--;
			}
			return false;
		}
	}

	public static void main(String[] args) throws IOException {
		int[][] startboard = ReadFile.readFile("grids/otros/test3jere");
		long lStartTime = new Date().getTime();
		ExactAlgorithm tour = new ExactAlgorithm(startboard);
		int painted = tour.fichas.size() * 2;
		tour.solve(painted);
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		System.out.println("Milisegundos transcurridos: " + difference + ".");
	}
}