package flow.backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Solution {

	private int[][] board;
	private int paintedCells;

	public Solution(int[][] board, int paintedCells) {
		this.board = board;
		this.paintedCells = paintedCells;
	}

	public int evaluate() {
		int painted = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++)
				if (board[i][j] != 0) {
					painted++;
				}
		}
		paintedCells = painted;
		return painted;
	}

	public List<Solution> neighbors(Solver s) {
		List<Solution> neighborsList = new ArrayList<Solution>();
		int rows = s.getBoardRows();
		int cols = s.getBoardCols();
		int[][] currentBoard = s.getSolvedBoard();
		for (Integer currentColor : s.getAllColors()) {
			int[][] auxBoard = new int[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++)
					if (currentBoard[i][j] == currentColor) {
						auxBoard[i][j] = 0;
					} else {
						auxBoard[i][j] = currentBoard[i][j];
					}
			}
			Point startColor = s.getStartColorPosition(currentColor);
			auxBoard[startColor.x][startColor.y] = currentColor;
			Point endColor = s.getEndColorPosition(currentColor);
			auxBoard[endColor.x][endColor.y] = currentColor;
			Solver solve = new Solver(auxBoard);
			solve.solveAprox(currentColor);
			evaluate();
			neighborsList
					.add(new Solution(solve.getSolvedBoard(), paintedCells));
		}
		return neighborsList;
	}

	public void printSolution() {
		for (int[] x : board) {
			for (int y : x)
				System.out.printf("%2d", y);
			System.out.println();
		}
	}

	public int getCellsSize() {
		return board.length * board[0].length;
	}
}
