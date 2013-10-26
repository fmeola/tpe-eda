package FrontEnd;

import BackEnd.*;
import gui.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class GameFrame extends JFrame {

	static private final int cellSize = 50;
	static private final int panelHeight = 500;
	static private final int panelWidth = 800;
	private Toolkit toolkit;
	private JPanel mainPanel;
	private JMenuBar menuBar = new JMenuBar();
	private ImageSet imgset;
	ProgressBar progbar;

	public GameFrame(String s, String fileRoute, boolean bestSolution, int maxTime, boolean progress) {

		super(s);
		// Config de la ventana
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(panelWidth, panelHeight);
		// Pone la ventana en el medio de la pantalla
		toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2
				- getHeight() / 2);

		
		Image icon = ImageUtils.loadImage("resources" + File.separator + "img" + File.separator + "icon.png");
		setIconImage(icon);

		int[][] board = ReadFile(fileRoute);
		startGame(board, bestSolution, maxTime, progress);

	}

	public void startGame(int[][] board, boolean bestSolution, int maxTime, boolean progress) throws IOException {
		final BoardPanel bp = new BoardPanel(board.length, board[0].length, cellSize);
		int rows = board.length * cellSize;
		int cols = board[0].length * cellSize;

		imgset = new ImageSet();
		mainPanel = bp;
		bp.setBoard(board, imgset.getMap());
		setJMenuBar(menuBar);
		setSize(cols, rows + 45);
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2
				- getHeight() / 2);
		add(mainPanel);
		mainPanel.setVisible(true);

		JPanel timer = new JPanel();
		timer.setSize(cols, rows * 2 + 23);

		BoxLayout layout = new BoxLayout(timer, BoxLayout.X_AXIS);
		timer.setLayout(layout);

		progbar = new ProgressBar(timer);
		add(timer);
		timer.setVisible(true);

		
		
	}
}
