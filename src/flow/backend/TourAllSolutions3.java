package flow.backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TourAllSolutions3 {

	/* Idea: Soluci—n Ej 3, Pr‡ctica 9 */
	private static final int[][] moves = {{1,0},{-1,0},{0,1},{0,-1}};
	
	private int[][] board;
	private int rows;
	private int cols;
	private List<List<int[][]>> solvedPaths;
	
	public TourAllSolutions3(int[][] startboard, int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.board = startboard;
		this.solvedPaths = new ArrayList<List<int[][]>>();
	}
	
	private void solve(int color, int row, int col, int endrow, int endcol) {
		if(board[row][col] != 0 && board[row][col] != color )
			return ;
		board[row][col] = color;
		if(row == endrow && col == endcol) {
			int[][] newBoard = new int[rows][cols];
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < cols; j++) {
					newBoard[i][j] = board[i][j];
				}
			}
			solvedPaths.get(color).add(newBoard);
			//printSolution();
		} else {
			for(int[] move : moves) {
				int i = row + move[0];
				int j = col + move[1];
				if(i >= 0 && i < rows && j >= 0 && j < cols && ((board[i][j] == 0) || (i == endrow && j == endcol) )) {
					solve(color,i,j,endrow,endcol);
				}
			}
		}
		if(board[row][col] == color)
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
	
	public void printSolvedPathsList(int n) {
		System.out.println("SOLUCION");
		for(int[][] board : solvedPaths.get(n)) {
			for(int[] row : board) {
				for(int r : row) {
					System.out.printf("%2d",r);
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	public void printIntersectionList(List<int[][]> l) {
		System.out.println("SOLUCION");
		for(int[][] board : l) {
			for(int[] row : board) {
				for(int r : row) {
					System.out.printf("%2d",r);
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
		TourAllSolutions3 tour = new TourAllSolutions3(startboard,6,6);
		tour.solvedPaths.add(0,null);
		tour.solvedPaths.add(1, new ArrayList<int[][]>());
		tour.solve(1,4,2,1,1);
		System.out.println("Caminos del 1: " + tour.solvedPaths.get(1).size());
		//tour.printSolvedPathsList(1);
		int[][] startboard2 = { { 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 4, 0 },
				{ 0, 0, 0, 3, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 1, 2, 4, 0 }, { 0, 0, 2, 3, 0, 0 } };
		tour.board = startboard2;
		tour.solvedPaths.add(2, new ArrayList<int[][]>());
		tour.solve(2,5,2,4,3);
		System.out.println("Caminos del 2: " + tour.solvedPaths.get(2).size());
		//tour.printSolvedPathsList(2);
		int[][] startboard3 = { { 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 4, 0 },
				{ 0, 0, 0, 3, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 1, 2, 4, 0 }, { 0, 0, 2, 3, 0, 0 } };
		tour.board = startboard3;
		tour.solvedPaths.add(3, new ArrayList<int[][]>());
		tour.solve(3,5,3,2,3);
		System.out.println("Caminos del 3: " + tour.solvedPaths.get(3).size());	
		//tour.printSolvedPathsList(3);
		int[][] startboard4 = { { 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 4, 0 },
				{ 0, 0, 0, 3, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 1, 2, 4, 0 }, { 0, 0, 2, 3, 0, 0 } };
		tour.board = startboard4;
		tour.solvedPaths.add(4, new ArrayList<int[][]>());
		tour.solve(4,1,4,4,4);
		System.out.println("Caminos del 4: " + tour.solvedPaths.get(4).size());	
		//tour.printSolvedPathsList(4);
		List<int[][]> oneintersecttwolist = new ArrayList<int[][]>();
		for(int[][] board1 : tour.solvedPaths.get(1)) {
			for(int [][] board2 : tour.solvedPaths.get(2)) {
				boolean flag = true;
				for(int i = 0; i < tour.rows && flag; i++ ) {
					for(int j = 0; j < tour.cols && flag; j++ ) {
						if(board1[i][j] == 1 && board2[i][j] == 2) {
							flag = false;
						}
					}
				}
				if(flag) {
					int[][] aux = new int[tour.rows][tour.cols];
					for(int i = 0; i < tour.rows; i++ ) {
						for(int j = 0; j < tour.cols; j++ ) {
							if(board1[i][j] == board2[i][j])
								aux[i][j] = board1[i][j];
							else
								aux[i][j] = board1[i][j] + board2[i][j];
						}
					}
					oneintersecttwolist.add(aux);
				}
			}
		}
		System.out.println("Intersecciones de 1 con 2: " + oneintersecttwolist.size());
		//tour.printIntersectionList(oneintersecttwolist);
		List<int[][]> twointersectionthreelist = new ArrayList<int[][]>();
		for(int[][] board1and2 : oneintersecttwolist) {
			for(int [][] board3 : tour.solvedPaths.get(3)) {
				boolean flag = true;
				for(int i = 0; i < tour.rows && flag; i++ ) {
					for(int j = 0; j < tour.cols && flag; j++ ) {
						if((board1and2[i][j] == 1 && board3[i][j] == 3) || (board1and2[i][j] == 2 && board3[i][j] == 3)) {
							flag = false;
						}
					}
				}
				if(flag) {
					int[][] aux = new int[tour.rows][tour.cols];
					for(int i = 0; i < tour.rows; i++ ) {
						for(int j = 0; j < tour.cols; j++ ) {
							if(board1and2[i][j] == board3[i][j])
								aux[i][j] = board1and2[i][j];
							else
								aux[i][j] = board1and2[i][j] + board3[i][j];
						}
					}
					twointersectionthreelist.add(aux);
				}
			}
		}
		System.out.println("Intersecciones de 1 y 2 con 3: " + twointersectionthreelist.size());
		
		List<int[][]> threeintersectionfourlist = new ArrayList<int[][]>();
		for(int[][] board1and2and3 : twointersectionthreelist) {
			for(int [][] board4 : tour.solvedPaths.get(4)) {
				boolean flag = true;
				for(int i = 0; i < tour.rows && flag; i++ ) {
					for(int j = 0; j < tour.cols && flag; j++ ) {
						if((board1and2and3[i][j] == 1 && board4[i][j] == 4) || (board1and2and3[i][j] == 2 && board4[i][j] == 4)  || (board1and2and3[i][j] == 3 && board4[i][j] == 4)) {
							flag = false;
						}
					}
				}
				if(flag) {
					int[][] aux = new int[tour.rows][tour.cols];
					for(int i = 0; i < tour.rows; i++ ) {
						for(int j = 0; j < tour.cols; j++ ) {
							if(board1and2and3[i][j] == board4[i][j])
								aux[i][j] = board1and2and3[i][j];
							else
								aux[i][j] = board1and2and3[i][j] + board4[i][j];
						}
					}
					threeintersectionfourlist.add(aux);
				}
			}
		}
		System.out.println("Intersecciones de 1 y 2 y 3 con 4: " + threeintersectionfourlist.size());
		tour.printIntersectionList(threeintersectionfourlist);
		long lEndTime = new Date().getTime(); // end time
		long difference = lEndTime - lStartTime; // check different
		System.out.println("Milisegundos transcurridos: " + difference);
	}
	
}
