package flow.backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {
	private int rows;
	private int cols;
	private int[][] board;
	private int[][] solvedBoard;
	private Map<Integer, List<Point>> colors;
	private Map<Integer, int[][]> movesmap;
	private int paintedCells;

	public Solver(int[][] startboard) {
		this.rows = startboard.length;
		this.cols = startboard[0].length;
		this.board = startboard;
		this.solvedBoard = new int[rows][cols];

		Map<Integer, List<Point>> colors = new HashMap<Integer, List<Point>>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (startboard[i][j] != 0) {
					if (!colors.containsKey(startboard[i][j])) {
						colors.put(startboard[i][j], new ArrayList<Point>());
						colors.get(startboard[i][j]).add(0, new Point(i, j));
					} else {
						colors.get(startboard[i][j]).add(1, new Point(i, j));
					}
				}
			}
		}
		this.colors = colors;

		this.movesmap = new HashMap<Integer, int[][]>();
		int[][] moves = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		int m = 0;
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				if (j != i)
					for (int k = 0; k < 4; k++)
						if (k != j && k != i)
							for (int l = 0; l < 4; l++)
								if (l != k && l != i && l != j) {
									int[][] aux = new int[4][2];
									aux[0] = moves[i];
									aux[1] = moves[j];
									aux[2] = moves[k];
									aux[3] = moves[l];
									movesmap.put(m++, aux);
								}

		this.paintedCells = 0;
	}

	public boolean solve(boolean bestSolution) {
		return solve(1, colors.get(1).get(0).x, colors.get(1).get(0).y, colors
				.get(1).get(1).x, colors.get(1).get(1).y, colors.size() * 2,
				bestSolution, colors.size());
	}

	public boolean solveAprox(int color) {
		Map<Integer, List<Point>> oneColor = new HashMap<Integer, List<Point>>();
		List<Point> oneColorPoints = new ArrayList<Point>();
		oneColorPoints.add(0, colors.get(color).get(0));
		oneColorPoints.add(1, colors.get(color).get(1));
		oneColor.put(color, oneColorPoints);
		colors = oneColor;
		return solve(color, colors.get(color).get(0).x, colors.get(color)
				.get(0).y, colors.get(color).get(1).x,
				colors.get(color).get(1).y, getPaintedCells(), false, color);
	}

	private boolean solve(int color, int currentRow, int currentCol,
			int endRow, int endCol, int paintedCells, boolean bestSolution,
			int maxColor) {
		int[][] moves = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		if (!bestSolution)
			moves = movesmap.get((int) (Math.random() * 24));
		if (currentRow == endRow && currentCol == endCol) {
			if (color < colors.size()) {
				Point start = colors.get(color + 1).get(0);
				Point end = colors.get(color + 1).get(1);
				if (solve(color + 1, start.x, start.y, end.x, end.y,
						paintedCells, bestSolution, maxColor))
					return true;
			}
			if (maxColor == color) {
				if (paintedCells == rows * cols) {
					solvedBoard = board;
					return true;
				} else {
					if (paintedCells > this.paintedCells) {
						for (int i = 0; i < rows; i++) {
							for (int j = 0; j < cols; j++)
								solvedBoard[i][j] = board[i][j];
						}
						this.paintedCells = paintedCells;
					}
					if (!bestSolution)
						return true;
				}
			}
			return false;
		} else {
			boolean flag = false;
			if (board[currentRow][currentCol] == 0) {
				board[currentRow][currentCol] = color;
				flag = true;
				paintedCells++;
			}

			for (int[] move : moves) {
				int i = currentRow + move[0];
				int j = currentCol + move[1];
				if (i >= 0 && i < rows && j >= 0 && j < cols
						&& ((board[i][j] == 0) || (i == endRow && j == endCol))) {
					if (solve(color, i, j, endRow, endCol, paintedCells,
							bestSolution, maxColor))
						return true;
				}
			}
			if (flag) {
				board[currentRow][currentCol] = 0;
				paintedCells--;
			}
			return false;
		}
	}

	public int[][] getSolvedBoard() {
		return solvedBoard;
	}

	public int getFichasSize() {
		return colors.size();
	}

	public int getPaintedCells() {
		return paintedCells;
	}

	public void printBoard() {
		for (int[] x : solvedBoard) {
			for (int y : x)
				System.out.printf("%2d", y);
			System.out.println();
		}
	}

	public int getBoardRows() {
		return rows;
	}

	public int getBoardCols() {
		return cols;
	}

	public Iterable<Integer> getAllColors() {
		return colors.keySet();
	}

	public Point getStartColorPosition(int color) {
		return colors.get(color).get(0);
	}

	public Point getEndColorPosition(int color) {
		return colors.get(color).get(1);
	}

}