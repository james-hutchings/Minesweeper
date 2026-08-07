package mineSweeper;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MineSweeperButton extends JButton {

	boolean isBomb = false;
	int bombsNear;
	int xLocation;
	int yLocation;
	
	boolean isFlagged = false;
	
	ImageIcon bomb = new ImageIcon("bomb.jpg");
	Image newBomb = bomb.getImage().getScaledInstance(85,  85, Image.SCALE_SMOOTH);
	ImageIcon scaledBomb = new ImageIcon(newBomb);
	
	ImageIcon flag = new ImageIcon("Flag.png");
	Image newFlag = flag.getImage().getScaledInstance(85,  85, Image.SCALE_SMOOTH);
	ImageIcon scaledFlag = new ImageIcon(newFlag);
	
	public MineSweeperButton(boolean isBomb, int bombsNear, int xLocation, int yLocation) {
		this.isBomb = isBomb;
		this.bombsNear = bombsNear;
		this.xLocation = xLocation;
		this.yLocation = yLocation;
	}
	
	
	public void setXLocation (int x) {
		this.xLocation = x;
	}
	
	public void setYLocation (int y) {
		this.yLocation = y;
	}
	
	public int getXLocation () {
		return xLocation;
	}
	
	public int getYLocation () {
		return yLocation;
	}
	
	public void setBomb() {
		this.isBomb = true;
	}
	
	public void setBombsNear(int n) {
		this.bombsNear = n;
	}

	public void setBombImage() {
		this.setIcon(scaledBomb);
		this.isBomb = true;
	}
	
	public void setFlagIamge() {
		this.setIcon(scaledFlag);
		this.isFlagged = true;
	}
	
	public void eraseIcon() {
		this.setIcon(null);
		this.setText("");
		this.isFlagged = false;
	}
	
	public void revealBombsNear() {
		this.setFont(new Font("Arial", Font.BOLD, 40));
		this.setText("" + bombsNear);
		this.setEnabled(false);

	}
	
	public void eraseButton() {
		this.isBomb = false;
		this.bombsNear = 0;
		this.eraseIcon();
		this.setEnabled(true);
	}
	
	
	

	private static final long serialVersionUID = 1L;
}
