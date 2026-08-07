package mineSweeper;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * The panel that contains the Minesweeper game grid.
 * Written by James Hutchings, 08/07/2026
*/
public class MineSweeperPanel extends JPanel implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;

	private final int rows;
	private final int cols;
	private final double bombPercent;

	private final JLabel timerLabel;
	private final Timer timer;
	private int secondsElapsed = 0;

	private final MineSweeperButton[][] buttons;
	private boolean firstClick = true;

	public MineSweeperPanel(int rows, int cols, double bombPercent, JLabel timerLabel) {
		this.rows = rows;
		this.cols = cols;
		this.bombPercent = bombPercent;
		this.timerLabel = timerLabel;
		this.buttons = new MineSweeperButton[rows][cols];

		this.timer = new Timer(1000, e -> {
			secondsElapsed++;
			timerLabel.setText("Time: " + secondsElapsed);
		});

		setLayout(new GridLayout(rows, cols));
		initBoard();
	}

	// Initializes the game board with buttons.
	private void initBoard() {
		removeAll();

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				MineSweeperButton button = new MineSweeperButton(r, c);
				button.addActionListener(this);
				button.addMouseListener(this);
				buttons[r][c] = button;
				add(button);
			}
		}

		revalidate();
		repaint();
	}

	// Handles the action when a button is clicked.
	@Override
	public void actionPerformed(ActionEvent e) {
		MineSweeperButton button = (MineSweeperButton) e.getSource();

		if (button.isFlagged() || button.isRevealed()) {
			return;
		}

		int row = button.getRow();
		int col = button.getCol();

		if (firstClick) {
			placeMines(row, col);
			calculateAllBombCounts();
			firstClick = false;
			timer.start();
		}

		if (button.isBomb()) {
			revealAllBombs();
			JOptionPane.showMessageDialog(this, "You woke up a sleeping cat! Try again!", "Game Over", JOptionPane.INFORMATION_MESSAGE);

			resetGame();
		} else {
			revealCell(row, col);
			if (hasWon()) {
				timer.stop();

				JOptionPane.showMessageDialog(
						this,
						"You found all the friendly cats!\nYou won in "
								+ secondsElapsed + " seconds!");

				resetGame();
			}
		}
	}

	// Handles right-click to toggle flag on a button.
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			MineSweeperButton button = (MineSweeperButton) e.getSource();
			button.toggleFlag();
		}
	}

	// Places mines on the board, ensuring the first clicked cell and its neighbors
	// are safe
	private void placeMines(int safeRow, int safeCol) {
		int totalCells = rows * cols;
		int desiredBombs = (int) Math.round(totalCells * bombPercent);

		int safeCells = 0;
		for (int r = safeRow - 1; r <= safeRow + 1; r++) {
			for (int c = safeCol - 1; c <= safeCol + 1; c++) {
				if (inBounds(r, c)) {
					safeCells++;
				}
			}
		}

		int maxBombs = Math.min(desiredBombs, totalCells - safeCells);
		int bombsPlaced = 0;
		Random random = new Random();

		while (bombsPlaced < maxBombs) {
			int r = random.nextInt(rows);
			int c = random.nextInt(cols);

			if (buttons[r][c].isBomb()) {
				continue;
			}

			if (isInSafeZone(r, c, safeRow, safeCol)) {
				continue;
			}

			buttons[r][c].setBomb();
			bombsPlaced++;
		}
	}

	private boolean isInSafeZone(int row, int col, int safeRow, int safeCol) {
		return Math.abs(row - safeRow) <= 1 && Math.abs(col - safeCol) <= 1;
	}

	private void calculateAllBombCounts() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (!buttons[r][c].isBomb()) {
					buttons[r][c].setBombsNear(countAdjacentBombs(r, c));
				}
			}
		}
	}

	private int countAdjacentBombs(int row, int col) {
		int count = 0;

		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = col - 1; c <= col + 1; c++) {
				if (r == row && c == col) {
					continue;
				}
				if (inBounds(r, c) && buttons[r][c].isBomb()) {
					count++;
				}
			}
		}

		return count;
	}

	// Reveals the cell and, if it has no adjacent bombs, reveals its neighbors
	// recursively.
	private void revealCell(int startRow, int startCol) {
		Queue<int[]> queue = new ArrayDeque<>();
		queue.add(new int[] { startRow, startCol });

		while (!queue.isEmpty()) {
			int[] current = queue.remove();
			int row = current[0];
			int col = current[1];

			if (!inBounds(row, col)) {
				continue;
			}

			MineSweeperButton button = buttons[row][col];

			if (button.isRevealed() || button.isFlagged() || button.isBomb()) {
				continue;
			}

			button.revealNumber();

			if (button.getBombsNear() == 0) {
				for (int r = row - 1; r <= row + 1; r++) {
					for (int c = col - 1; c <= col + 1; c++) {
						if (inBounds(r, c)) {
							MineSweeperButton neighbor = buttons[r][c];
							if (!neighbor.isRevealed() && !neighbor.isFlagged() && !neighbor.isBomb()) {
								queue.add(new int[] { r, c });
							}
						}
					}
				}
			}
		}
	}

	// Reveals all bombs on the board, typically called when the player hits a bomb.
	private void revealAllBombs() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (buttons[r][c].isBomb()) {
					buttons[r][c].revealBomb();
				}
			}
		}
	}

	private boolean hasWon() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {

				MineSweeperButton button = buttons[r][c];

				// If we find any safe cell that hasn't been revealed,
				// the game is not won yet.
				if (!button.isBomb() && !button.isRevealed()) {
					return false;
				}
			}
		}

		return true;
	}

	public void resetGame() {
		firstClick = true;
		secondsElapsed = 0;
		timer.stop();
		timerLabel.setText("Time: 0");

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				buttons[r][c].resetButton();
			}
		}
	}

	public void stopTimer() {
		timer.stop();
	}

	private boolean inBounds(int row, int col) {
		return row >= 0 && row < rows && col >= 0 && col < cols;
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}
