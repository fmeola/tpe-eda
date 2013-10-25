package flow.backend;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

	private int[][] board;
	private int painted;

	public Solution(int[][] board, int painted) {
		this.board = board;
		this.painted = painted;
	}

	public int evaluate() {
		int painted = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++)
				if (board[i][j] != 0) {
					painted++;
				}
		}
		this.painted = painted;
		return painted;
	}

	public List<Solution> neighbors(Solver s) {
		List<Solution> res = new ArrayList<Solution>();
		int colorLength = 0;
		int rows = s.getRows();
		int cols = s.getCols();
		int[][] auxboard = s.getAuxMatrix();
		for (Integer ficha : s.getFichas().keySet()) {
			int[][] aux = new int[rows][cols];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++)
					if (auxboard[i][j] == ficha) {
						aux[i][j] = 0;
					} else {
						aux[i][j] = auxboard[i][j];
						colorLength++;
					}
			}
			aux[s.getFichas().get(ficha).get(0).x][s.getFichas().get(ficha).get(0).y] = ficha;
			aux[s.getFichas().get(ficha).get(1).x][s.getFichas().get(ficha).get(1).y] = ficha;
			colorLength += 2;
			Solver solve = new Solver(aux);
			Map<Integer, List<Point>> newFichas = new HashMap<Integer, List<Point>>();
			List<Point> l = new ArrayList<Point>();
			l.add(0,s.getFichas().get(ficha).get(0));
			l.add(1,s.getFichas().get(ficha).get(1));
			newFichas.put(ficha, l);
			solve.setFichas(newFichas);
			solve.solveAprox(colorLength, ficha);
			evaluate();
			Solution newsol = new Solution(solve.getAuxMatrix(),painted);
			res.add(newsol);
		}
		return res;
	}

	public void print() {
		for (int[] fil : board) {
			for (int r : fil)
				System.out.printf("%2d", r);
			System.out.println();
		}
	}

}
