package flow.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgressBar {

	private JProgressBar progressBar;

	public ProgressBar(JPanel f) {
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		f.add(progressBar, BorderLayout.NORTH);
		f.setVisible(true);
	}

	public void setProgress(int progress) {
		progressBar.setValue(progress);
	}
}