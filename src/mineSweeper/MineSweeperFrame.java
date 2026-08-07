package mineSweeper;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/*
 * The main frame for the Minesweeper game.
 * Written by James Hutchings, 08/07/2026
*/
public class MineSweeperFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private MineSweeperPanel gamePanel;
	private final JLabel timerLabel = new JLabel("Time: 0");

	// Constructs the MineSweeperFrame with the specified number of rows and columns.
	    public MineSweeperFrame() {
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
		timerLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JComboBox<Difficulty> difficultyBox = new JComboBox<>(Difficulty.values());
        JButton resetButton = new JButton("Reset");

        controls.add(difficultyBox);
        controls.add(resetButton);
        controls.add(timerLabel);

        add(controls, BorderLayout.NORTH);

        Difficulty startDifficulty = Difficulty.EASY;
        gamePanel = new MineSweeperPanel(
                startDifficulty.getRows(),
                startDifficulty.getCols(),
                startDifficulty.getBombPercent(),
                timerLabel
        );
        add(gamePanel, BorderLayout.CENTER);

        difficultyBox.addActionListener(e -> {
            Difficulty selected = (Difficulty) difficultyBox.getSelectedItem();
            if (selected != null) {
				gamePanel.stopTimer();
                remove(gamePanel);
                gamePanel = new MineSweeperPanel(
                        selected.getRows(),
                        selected.getCols(),
                        selected.getBombPercent(),
                        timerLabel
                );
                add(gamePanel, BorderLayout.CENTER);
                revalidate();
                repaint();
                pack();
            }
        });

        resetButton.addActionListener(e -> {
            gamePanel.resetGame();
            timerLabel.setText("Time: 0");
        });

        setPreferredSize(new Dimension(900, 900));
        pack();
        setLocationRelativeTo(null);
    }
}
