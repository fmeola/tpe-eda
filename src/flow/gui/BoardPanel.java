package flow.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import flow.FrontEnd.ImageSet;

public class BoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int cellSize;
	private int rows, columns;
	private Image[][] cells;
	private ImageSet imageMap;

	public BoardPanel(int rows, int columns, int cellSize, ImageSet imgset) {
		this.rows = rows;
		this.columns = columns;
		this.cellSize = cellSize;
		this.cells = new Image[rows][columns];

		setSize(columns * cellSize + 1, rows * cellSize + 1);
		this.imageMap = imgset;
	}

	public void clearImage(int row, int column) {
		cells[row][column] = null;
	}

	public void setImage(int row, int column, Image image) {
		cells[row][column] = new BufferedImage(cellSize, cellSize,
				BufferedImage.TYPE_INT_ARGB);
		cells[row][column].getGraphics().drawImage(image, 0, 0, null);
	}
	
	public void appendImage(int row, int column, Image image) {
		if (cells[row][column] == null) {
			cells[row][column] = new BufferedImage(cellSize, cellSize,
					BufferedImage.TYPE_INT_ARGB);
		}
		cells[row][column].getGraphics().drawImage(image, 0, 0, null);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (cells[i][j] != null) {
					g.drawImage(cells[i][j], j * cellSize, i * cellSize, null);
				}
			}
		}
	}

	public void setBoard(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				this.clearImage(i, j);
				this.appendImage(i, j, imageMap.getMap().get(board[i][j]));
			}
		}
		repaint();
	}
}