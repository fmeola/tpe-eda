package flow.FrontEnd;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import flow.backend.Aprox;
import flow.backend.ReadFile;
import flow.backend.Solver;
import flow.gui.BoardPanel;
import flow.gui.ImageUtils;
import flow.gui.ProgressBar;

public class GameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private final int cellSize = 50;
	static private final int panelHeight = 500;
	static private final int panelWidth = 800;
	private Toolkit toolkit;
	private BoardPanel mainPanel;
	private JMenuBar menuBar = new JMenuBar();
	private ImageSet imgset;
	ProgressBar progbar;

	public GameFrame(String s, String fileRoute, boolean bestSolution,
			int maxTime, boolean progress) throws IOException, InterruptedException {

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

//		Image icon = ImageUtils.loadImage("resources" + File.separator + "img"
//				+ File.separator + "icon.png");
//		setIconImage(icon);

		int[][] board = ReadFile.readFile(fileRoute);
		startGame(board, bestSolution, maxTime, progress);

	}

	public void startGame(int[][] board, boolean bestSolution, int maxTime,
			boolean progress) throws IOException, InterruptedException {
		ImageSet imgset = new ImageSet();
		final BoardPanel bp = new BoardPanel(board.length, board[0].length,
				cellSize, imgset);
		int rows = board.length * cellSize;
		int cols = board[0].length * cellSize;

		mainPanel = bp;
		bp.setBoard(board);
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
		
		long lStartTime = new Date().getTime();
		Solver tour = new Solver(board, progress, mainPanel);
		long lEndTime = new Date().getTime();
		long difference = lEndTime - lStartTime;
		
		if(bestSolution){
			tour.solve(bestSolution, true);
		}
		else{
			Aprox aprox = new Aprox();
			aprox.solver = tour;
			aprox.solve(maxTime);
		}
	}
}
