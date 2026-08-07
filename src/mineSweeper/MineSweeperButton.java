package mineSweeper;

import java.awt.Font;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A button in the Minesweeper game.
 */
public class MineSweeperButton extends JButton {
	private static final long serialVersionUID = 1L;

	private static final int ICON_SIZE = 32;
	private static final ImageIcon BOMB_ICON = loadScaledIcon("/images/bomb.jpg", ICON_SIZE, ICON_SIZE);
	private static final ImageIcon FLAG_ICON = loadScaledIcon("/images/flag.png", ICON_SIZE, ICON_SIZE);

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

		setFont(new Font("Arial", Font.BOLD, 18));
		setFocusPainted(false);
	}

	// Loads and scales and image icon from the given resource path.
	private static ImageIcon loadScaledIcon(String resourcePath, int width, int height) {
		URL url = MineSweeperButton.class.getResource(resourcePath);
		if (url == null) {
			return new ImageIcon();
		}

		ImageIcon icon = new ImageIcon(url);
		Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
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
			setIcon(FLAG_ICON);
			setText("");
			flagged = true;
		}
	}

	public void revealBomb() {
		setIcon(BOMB_ICON);
		setText("");
		revealed = true;
		setEnabled(false);
	}

	public void revealNumber() {
		clearDisplay();
		setText(bombsNear > 0 ? String.valueOf(bombsNear) : "");
		revealed = true;
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
	}
}
