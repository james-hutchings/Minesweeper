package mineSweeper;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.JFrame;

/*
 * The main frame for the Minesweeper game.
 * Written by James Hutchings, 08/07/2026
*/
public class MineSweeperFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public MineSweeperFrame() {
		this(10, 10);
	}

	// Constructs the MineSweeperFrame with the specified number of rows and columns.
	public MineSweeperFrame(int rows, int cols) {
		setTitle("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		MineSweeperPanel gamePanel = new MineSweeperPanel(rows, cols);
		add(gamePanel, BorderLayout.CENTER);

		setPreferredSize(new Dimension(900, 900));
		pack();
		setLocationRelativeTo(null);
	}
}
