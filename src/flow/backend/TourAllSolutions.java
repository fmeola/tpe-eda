package flow.backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TourAllSolutions {

	/* Idea: Soluci—n Ej 3, Pr‡ctica 9 */
	private static final int[][] moves = {{1,0},{-1,0},{0,1},{0,-1}};
	
	private int[][] board;
	private int rows;
	private int cols;
	private int endrow;
	private int endcol;
	private List<int[][]> solvedPaths;
	private long cant;
	
	public TourAllSolutions(int rows, int cols, int endrow, int endcol) {
		this.rows = rows;
		this.cols = cols;
		this.endrow = endrow;
		this.endcol = endcol;
		this.board = new int[rows][cols];
		this.solvedPaths = new ArrayList<int[][]>();
		this.cant = 0;
	}
	/*
	public void solve(){
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				solve(1,i,j,endrow,endcol);
			}
		}
	}
	*/
	
	private void solve(int current, int row, int col) {
		board[row][col] = current;
		if(row == endrow && col == endcol) {
			cant++;
		/*
		if(row == endrow && col == endcol) {
			int[][] newBoard = new int[rows][cols];
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < cols; j++) {
					newBoard[i][j] = board[i][j];
				}
			}
			solvedPaths.add(newBoard);
			//printSolution();
			 */
		} else {
			for(int[] move : moves) {
				int i = row + move[0];
				int j = col + move[1];
				if(i >= 0 && i < rows && j >= 0 && j < cols && board[i][j] == 0) {
					solve(current+1,i,j);
				}
			}
		}
		board[row][col] = 0;
	}
	
	public void printSolution() {
		System.out.println("SOLUCION");
		for(int[] row : board) {
			for(int n : row) {
				System.out.printf("%3d",n);
			}
			System.out.println();
		}
	}
	
	public void printSolvedPathsList() {
		System.out.println("SOLUCION");
		for(int[][] board : solvedPaths) {
			for(int[] row : board) {
				for(int n : row) {
					System.out.printf("%2d",n);
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		long lStartTime = new Date().getTime(); // start time
		TourAllSolutions tour = new TourAllSolutions(6,6,1,1);
		tour.solve(1,0,0);
		//tour.printSolvedPathsList();
		System.out.println(tour.cant);
		long lEndTime = new Date().getTime(); // end time
		long difference = lEndTime - lStartTime; // check different
		System.out.println("Elapsed milliseconds: " + difference);
	}
	
}
