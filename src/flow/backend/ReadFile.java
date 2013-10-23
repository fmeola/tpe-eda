package flow.backend;

import java.awt.Point;
import java.io.FileReader;
import java.io.IOException;


public class ReadFile {

	private enum States {
		ROW, COLUMN;
	}

	public static int[][] readFile(String inName) throws IOException {
		FileReader inStream = null;
		Point dimensions;
		try {
			inStream = new FileReader(inName);
			dimensions = parseDimensions(inStream);
			int[][] matrix = new int[dimensions.x][dimensions.y];
			matrix = parseMatrix(inStream, matrix);
			return matrix;
		} finally {
			if (inStream != null) {
				inStream.close();
			}
		}
	}
	
	private static int[][] parseMatrix(FileReader inStream, int[][] matrix) throws IOException {
		int[] count = new int[10];
		int c;
		for(int i = 0; i < matrix.length; i++) {
			int j = 0;
			while((c = inStream.read()) != -1) {
				if(Character.isDigit((char)c)) {
					int color = (char)c - '0';
					if(count[color] == 2) {
						throw new IOException("Error: Mas de 2 fichas de un mismo color.");
					}
					else {
						matrix[i][j++] = color;
						count[color]++;
					}
				} else if((char)c == ' ') {
					matrix[i][j++] = 0;
				} else if((char)c == '\n') {
					if(j == matrix[0].length) {
						break;
					} else {
						throw new IOException("Error: Numero de columnas incorrecto en la fila " + i);
					}
				} else {
					throw new IOException("Error: Caracter invalido en la posicion (" + i + "," + j + ")");
				}
			}
			if(c == -1) {
				if(j != matrix[0].length) {
					throw new IOException("Error: Faltan caracteres al final de la fila " + i);
				}
			}
		}
		if(inStream.read() != -1)
			throw new IOException("El archivo posee datos de mas al final.");
		for(int i: count) {
			if(i == 1)
				throw new IOException("Error: Falta una ficha de un color");
		}
		return matrix;
	}

	private static Point parseDimensions(FileReader inStream) throws IOException {
		int c;
		int dimRows = 0;
		int dimColumns = 0;
		States state = States.ROW;
		while ((c = inStream.read()) != '\n' && c != -1) {
			switch (state) {
			case ROW:
				if (Character.isDigit((char) c)) {
					dimRows = dimRows * 10 + c - '0';
				} else if ((char) c == ',') {
					state = States.COLUMN;
				} else {
					throw new IOException();
				}
				break;
			case COLUMN:
				if (Character.isDigit((char) c)) {
					dimColumns = dimColumns * 10 + c - '0';
				} else if(!Character.isSpaceChar((char)c)) {
					System.out.println(c);
					throw new IOException();
				}
				break;
			}
		}
		return new Point(dimRows, dimColumns);
	}
	
	public static void printMatrix(int[][] matrix) {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				if(matrix[i][j] != -1) {
					System.out.print(matrix[i][j]);
				} else {
					System.out.print('-');
				}
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
//		File myFile = new File("/Users/lucas/git/tpe-eda/grids/grid1");
		try {
			printMatrix(readFile("/Users/lucas/git/tpe-eda/grids/grid1"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
