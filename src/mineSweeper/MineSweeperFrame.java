package mineSweeper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;

/*
 * The main frame for the Minesweeper game.
 * Written by James Hutchings, 08/07/2026
*/
public class MineSweeperFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private MineSweeperPanel gamePanel;
	private final JLabel timerLabel = new JLabel("Time: 0");

	// Constructs the MineSweeperFrame with the specified number of rows and
	// columns.
	public MineSweeperFrame() {
		setTitle("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Header
		JLabel headerLabel = new JLabel(
				"Find the friendly cats and don't wake the sleeping cats!",
				SwingConstants.CENTER);
		headerLabel.setFont(new Font("Arial", Font.BOLD, 22));

		// Controls
		JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JComboBox<Difficulty> difficultyBox = new JComboBox<>(Difficulty.values());

		JButton resetButton = new JButton("Reset");

		timerLabel.setFont(new Font("Arial", Font.BOLD, 24));

		controls.add(difficultyBox);
		controls.add(resetButton);
		controls.add(timerLabel);

		// Combine header + controls
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(headerLabel, BorderLayout.NORTH);
		topPanel.add(controls, BorderLayout.SOUTH);

		add(topPanel, BorderLayout.NORTH);

		// Start with Easy difficulty
		Difficulty startDifficulty = Difficulty.EASY;

		gamePanel = new MineSweeperPanel(
				startDifficulty.getRows(),
				startDifficulty.getCols(),
				startDifficulty.getBombPercent(),
				timerLabel);

		add(gamePanel, BorderLayout.CENTER);

		// Change difficulty
		difficultyBox.addActionListener(e -> {
			Difficulty selected = (Difficulty) difficultyBox.getSelectedItem();

			if (selected != null) {
				gamePanel.stopTimer();
				remove(gamePanel);

				timerLabel.setText("Time: 0");

				gamePanel = new MineSweeperPanel(
						selected.getRows(),
						selected.getCols(),
						selected.getBombPercent(),
						timerLabel);

				add(gamePanel, BorderLayout.CENTER);

				revalidate();
				repaint();
				pack();
			}
		});

		// Reset current game
		resetButton.addActionListener(e -> {
			gamePanel.resetGame();
		});

		// Window setup
		setPreferredSize(new Dimension(900, 900));
		pack();
		setLocationRelativeTo(null);
	}
}
