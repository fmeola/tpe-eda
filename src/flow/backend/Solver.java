package flow.backend;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {
	/* Idea: Soluci—n Ej 3, Pr‡ctica 9 */
	private int[][] board;
	private int[][] auxMatrix;
	private int rows;
	private int cols;
	private Map<Integer, List<Point>> fichas;
	private int lastPainted;
	private Map<Integer, int[][]> movesmap;

	public Solver(int[][] startboard) {
		this.setRows(startboard.length);
		this.setCols(startboard[0].length);
		this.setBoard(startboard);
		Map<Integer, List<Point>> fichas = new HashMap<Integer, List<Point>>();
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
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
		this.setFichas(fichas);
		this.setAuxMatrix(new int[getRows()][getCols()]);
		this.setLastPainted(0);
		this.movesmap = new HashMap<Integer,int[][]>();
		int[][] moves = {{1,0},{-1,0},{0,1},{0,-1}};
		int m = 0;
		for(int i = 0; i < 4; i++)
			for(int j = 0; j< 4; j++)
				if(j != i)
				for(int k = 0; k < 4; k++)
					if( k!=j && k!=i)
					for(int l = 0; l < 4; l++)
						if(l!=k && l!=i && l !=j){
							int[][] aux = new int[4][2];
							aux[0] = moves[i];
							aux[1] = moves[j];
							aux[2] = moves[k];
							aux[3] = moves[l];
							movesmap.put(m++,aux);
						}
	}

	public boolean solve(int painted, boolean bestSolution) {
		return solve(1, getFichas().get(1).get(0).x, getFichas().get(1).get(0).y, getFichas()
				.get(1).get(1).x, getFichas().get(1).get(1).y, painted, bestSolution, getFichas().size());
	}
	
	public boolean solveAprox(int painted, int color) {
		return solve(color, getFichas().get(color).get(0).x, getFichas().get(color).get(0).y, getFichas()
				.get(color).get(1).x, getFichas().get(color).get(1).y, painted, false, color);
	}

	private boolean solve(int color, int row, int col, int endrow, int endcol,
			int painted, boolean bestSolution, int maxColor) {
		int[][] moves = movesmap.get((int)(Math.random()*24));//{{1,0},{-1,0},{0,1},{0,-1}}; 
		if (row == endrow && col == endcol) {

			if (color < getFichas().size()) {
				Point start = getFichas().get(color + 1).get(0);
				Point end = getFichas().get(color + 1).get(1);
				if (solve(color + 1, start.x, start.y, end.x, end.y, painted,
						bestSolution, maxColor))
					return true;
			}
			if (maxColor == color) {
				if (painted == getRows() * getCols()) {
					setAuxMatrix(getBoard());
					return true;
				} else {
					if (painted > getLastPainted()) {
						for (int i = 0; i < getRows(); i++) {
							for (int j = 0; j < getCols(); j++)
								getAuxMatrix()[i][j] = getBoard()[i][j];
						}
						setLastPainted(painted);

					}
					if (!bestSolution)
						return true;
				}
			}
			return false;
		} else {
			boolean flag = false;
			if (getBoard()[row][col] == 0) {
				getBoard()[row][col] = color;
				flag = true;
				painted++;
			}

			for (int[] move : moves) {
				int i = row + move[0];
				int j = col + move[1];
				if (i >= 0 && i < getRows() && j >= 0 && j < getCols()
						&& ((getBoard()[i][j] == 0) || (i == endrow && j == endcol))) {
					if (solve(color, i, j, endrow, endcol, painted,
							bestSolution, maxColor))
						return true;
				}
			}
			if (flag) {
				getBoard()[row][col] = 0;
				painted--;
			}
			return false;
		}
	}

	public Map<Integer, List<Point>> getFichas() {
		return fichas;
	}

	public void setFichas(Map<Integer, List<Point>> fichas) {
		this.fichas = fichas;
	}

	public int[][] getAuxMatrix() {
		return auxMatrix;
	}

	public void setAuxMatrix(int[][] auxMatrix) {
		this.auxMatrix = auxMatrix;
	}

	public int getLastPainted() {
		return lastPainted;
	}

	public void setLastPainted(int lastPainted) {
		this.lastPainted = lastPainted;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}


	
}