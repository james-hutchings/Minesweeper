package mineSweeper;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A button in the Minesweeper game.
 * * Written by James Hutchings, 08/07/2026
 */
public class MineSweeperButton extends JButton {
	private static final long serialVersionUID = 1L;

	private static final ImageIcon BOMB_ICON = new ImageIcon("src/main/resources/images/bomb.jpg");

	private static final ImageIcon FLAG_ICON = new ImageIcon("src/main/resources/images/flag.jpg");

	private static final ImageIcon HAPPY_CAT_ICON = new ImageIcon("src/main/resources/images/happyCat.jpg");

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
		int iconSize = Math.max(16, (int) (size * 0.9));

		Image scaled = original.getImage().getScaledInstance(
				iconSize,
				iconSize,
				Image.SCALE_SMOOTH);

		return new ImageIcon(scaled);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (revealed && !bomb && bombsNear > 0) {
			Graphics2D g2 = (Graphics2D) g.create();

			ImageIcon cat = scaleIcon(HAPPY_CAT_ICON);
			g2.drawImage(cat.getImage(), 0, 0, getWidth(), getHeight(), this);

			if (bombsNear > 0) {
				int fontSize = Math.max(10, getWidth() / 3);
				g2.setFont(new Font("Arial", Font.BOLD, fontSize));
				g2.setColor(Color.BLACK);

				String text = String.valueOf(bombsNear);
				FontMetrics fm = g2.getFontMetrics();
				int x = (getWidth() - fm.stringWidth(text)) / 2;
				int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

				g2.drawString(text, x, y);
			}

			g2.dispose();
		}
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
	}

	public void revealNumber() {
		revealed = true;
		setEnabled(false);
		repaint();
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
