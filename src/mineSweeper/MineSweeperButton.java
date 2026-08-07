package mineSweeper;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A button in the Minesweeper game.
 * * Written by James Hutchings, 08/07/2026
 */
public class MineSweeperButton extends JButton {
	private static final long serialVersionUID = 1L;

	private static final ImageIcon BOMB_ICON = new ImageIcon("src/main/resources/images/bomb.jpg");

	private static final ImageIcon FLAG_ICON = new ImageIcon("src/main/resources/images/flag.png");

	private final int row;
	private final int col;

	private boolean bomb;
	private boolean flagged;
	private boolean revealed;
	private int bombsNear;

	// Button Constructor.
	public MineSweeperButton(int row, int col) {
		this.row = row;
		this.col = col;
		setMargin(new java.awt.Insets(0, 0, 0, 0));
		setFocusPainted(false);
		setHorizontalAlignment(JButton.CENTER);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateDisplaySize();
			}
		});
	}

	// Scales the icon to fit the button size while maintaining aspect ratio.
	private ImageIcon scaleIcon(ImageIcon original) {
		int size = Math.min(getWidth(), getHeight());

		// Leave some padding around the image.
		int iconSize = Math.max(16, (int) (size * 0.65));

		Image scaled = original.getImage().getScaledInstance(
				iconSize,
				iconSize,
				Image.SCALE_SMOOTH);

		return new ImageIcon(scaled);
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public boolean isBomb() {
		return bomb;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public boolean isRevealed() {
		return revealed;
	}

	public void setBomb() {
		this.bomb = true;
	}

	public int getBombsNear() {
		return bombsNear;
	}

	public void setBombsNear(int bombsNear) {
		this.bombsNear = bombsNear;
	}

	// Toggles flag.
	public void toggleFlag() {
		if (revealed) {
			return;
		}

		if (flagged) {
			clearDisplay();
			flagged = false;
		} else {
			setIcon(scaleIcon(FLAG_ICON));
			setText("");
			flagged = true;
		}
	}

	private void updateDisplaySize() {
		int size = Math.min(getWidth(), getHeight());

		if (size <= 0) {
			return;
		}

		// Resize number
		int fontSize = Math.max(8, (int) (size * 0.35));
		setFont(new Font("Arial", Font.BOLD, fontSize));

		// Resize currently displayed icon
		if (flagged) {
			setIcon(scaleIcon(FLAG_ICON));
		} else if (revealed && bomb) {
			setIcon(scaleIcon(BOMB_ICON));
		}
	}

	public void revealBomb() {
		setIcon(scaleIcon(BOMB_ICON));
		setText("");
		revealed = true;
		setEnabled(false);
	}

	public void revealNumber() {
		clearDisplay();
		revealed = true;

		if (bombsNear > 0) {
			setText(String.valueOf(bombsNear));
		}

		updateDisplaySize();
		setEnabled(false);
	}

	// Resets the button to its initial state.
	public void resetButton() {
		bomb = false;
		flagged = false;
		revealed = false;
		bombsNear = 0;
		clearDisplay();
		setEnabled(true);
	}

	private void clearDisplay() {
		setIcon(null);
		setText("");
	}
}
