package flow.backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TourAllSolutions2 {

	/* Idea: Soluci—n Ej 3, Pr‡ctica 9 */
	private static final int[][] moves = {{1,0},{-1,0},{0,1},{0,-1}};
	
	private int[][] board;
	private int rows;
	private int cols;
	private int endrow;
	private int endcol;
	private List<int[][]> solvedPaths;
	
	public TourAllSolutions2(int[][] startboard, int rows, int cols, int endrow, int endcol) {
		this.rows = rows;
		this.cols = cols;
		this.endrow = endrow;
		this.endcol = endcol;
		this.board = startboard;
		this.solvedPaths = new ArrayList<int[][]>();
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
		if(board[row][col] != 0 && board[row][col] != 1 )
			return ;
		board[row][col] = current;
		if(row == endrow && col == endcol) {
			int[][] newBoard = new int[rows][cols];
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < cols; j++) {
					newBoard[i][j] = board[i][j];
				}
			}
			solvedPaths.add(newBoard);
			//printSolution();
		} else {
			for(int[] move : moves) {
				int i = row + move[0];
				int j = col + move[1];
				if(i >= 0 && i < rows && j >= 0 && j < cols && ((board[i][j] == 0) || (i == endrow && j == endcol) )) {
					solve(1,i,j);
				}
			}
		}
		if(board[row][col] == 1)
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
		int[][] startboard = {{0,0,0,0,0,0},
				              {0,1,0,0,4,0},
				              {0,0,0,3,0,0},
				              {0,0,0,0,0,0},
				              {0,0,1,2,4,0},
				              {0,0,2,3,0,0}
		};
		long lStartTime = new Date().getTime(); // start time
		TourAllSolutions2 tour = new TourAllSolutions2(startboard,6,6,1,1);
		tour.solve(1,4,2);
		//tour.printSolvedPathsList();
		System.out.println(tour.solvedPaths.size());
		long lEndTime = new Date().getTime(); // end time
		long difference = lEndTime - lStartTime; // check different
		System.out.println("Elapsed milliseconds: " + difference);
	}
	
}
