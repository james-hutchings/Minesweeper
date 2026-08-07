package mineSweeper;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MineSweeperPanel extends JPanel implements ActionListener, MouseListener {
	
	MineSweeperButton[][] buttonList = new MineSweeperButton[10][10];
	boolean isClicked = false;
	int bombCount;
	
	public MineSweeperPanel() {
		this.setLayout(new GridLayout(10, 10));
		
		for (int i = 0; i < buttonList.length; i++) {
			for (int j = 0; j < buttonList[0].length; j++) {
				MineSweeperButton gameButton = new MineSweeperButton(false, 0, i, j);
				gameButton.addActionListener(this);
				gameButton.addMouseListener(this);
				buttonList[i][j] = gameButton;
				this.add(gameButton);
		
			}
			
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			MineSweeperButton button = (MineSweeperButton) e.getSource();
			if (button.isFlagged) {
				button.eraseIcon();
			}
			
			else 
				button.setFlagIamge();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MineSweeperButton button = (MineSweeperButton) e.getSource();
		int startX = button.getXLocation();
		int startY = button.getYLocation();
//		System.out.println("StartX is: " + startX + " And startY is: " + startY);
		
		// Checks if player has started game.
		if (isClicked == false) {

			buttonList[startX][startY].setEnabled(false);
			isClicked = true;
			
			
			// Handles Edge Cases.
			// Top left 4 squares.
			if (startX < 2) {
				if (startY < 2) {
					for (int i = 0; i < buttonList.length / 2; i++) {
						for (int j = 0; j < buttonList[0].length / 2; j++) {
							buttonList[i][j].setEnabled(false);
						}
					}
					placeMines();
					

				}
				
				// Top Right Four Squares
				else if (startY >= 8) {
					for (int i = 0; i < buttonList.length / 2; i++) {
						for (int j = buttonList[0].length / 2; j < buttonList[0].length; j++) {
							buttonList[i][j].setEnabled(false);
						}
					}
					placeMines();
				}
				
				// Top Center 6 Squares
				else {
					for (int i = 0; i < buttonList.length / 2; i++) {
						for (int j = startY - 2; j < startY + 3; j++) {
							buttonList[i][j].setEnabled(false);
						}
					}
					placeMines();
				}

			}

			// Bottom Left 4 Squares
			else if (startX >= 8) {
				if (startY < 2) {
					for (int i = buttonList.length / 2; i < buttonList.length; i++) {
						for (int j = 0; j < buttonList[0].length / 2; j++) {
							buttonList[i][j].setEnabled(false);
						}
					}
					placeMines();
				}
				
				// Bottom Right 4 Squares
				else if (startY > 7) {
					for (int i = buttonList.length / 2; i < buttonList.length; i++) {
						for (int j = buttonList[0].length / 2; j < buttonList[0].length; j++) {
							buttonList[i][j].setEnabled(false);
						}
					}
					placeMines();
				}
				
				
				// Bottom Center Squares
				else {
					for (int i = buttonList.length / 2; i < buttonList.length; i++) {
						for (int j = startY - 2; j < startY + 3; j++) {
							buttonList[i][j].setEnabled(false);
						}
					}
					placeMines();
				}
			}
			
			// Left Center Squares
			else if (startX >= 2 && startY <= 1) {
				for (int i = startX - 2; i < startX + 3; i++) {
					for (int j = 0; j < buttonList[0].length / 2; j++) {
						buttonList[i][j].setEnabled(false);
					}
				}
				placeMines();
				
			}
			
			
			// Right Center Squares
			else if (startX <= 8 && startY == 8) {
				for (int i = startX - 2; i < startX + 3; i++) {
					for (int j = buttonList[0].length / 2; j < buttonList[0].length; j++) {
						buttonList[i][j].setEnabled(false);
					}
				}
				placeMines();
				
			}
			
			
			
			// Handles all other cases. 
			else {
				for (int i = startX - 2; i < startX + 3; i++) {
					for (int j = startY - 2; j < startY + 3; j++) {
						buttonList[i][j].setEnabled(false);
					}
				}
				placeMines();
			}
			
			checkNearBombs();
			buttonList[0][4].revealBombsNear();
			buttonList[1][4].revealBombsNear();
			buttonList[2][4].revealBombsNear();
			buttonList[3][4].revealBombsNear();
			buttonList[4][4].revealBombsNear();
			
			buttonList[4][0].revealBombsNear();
			buttonList[4][1].revealBombsNear();
			buttonList[4][2].revealBombsNear();
			buttonList[4][3].revealBombsNear();
			buttonList[4][4].revealBombsNear();
			
				
				}
		
		// Handles normal game case where button is not a bomb.
		else {
			if (!buttonList[startX][startY].isBomb) {
				buttonList[startX][startY].revealBombsNear();
			}
			
			// Handles game case where button is a bomb.
			else {
				buttonList[startX][startY].setBombImage();
				for (int i = 0; i < buttonList.length; i++) {
					for (int j = 0; j < buttonList[0].length; j++) {
						if (buttonList[i][j].isBomb) {
							buttonList[i][j].setBombImage();
							
						}
					}
				}
				
				JOptionPane.showMessageDialog(this, "You hit a bomb! The game will now reset.");
				resetMineSweeper();
				
			}
		}
				
			}

	public void placeMines() {
		int maxBomb = (int) ((buttonList.length * buttonList[0].length) * .25);
		int bombAmount = 0;
		Random random = new Random();
		
		while (bombAmount < maxBomb) {
			int randomX = random.nextInt(0,10);
			int randomY = random.nextInt(0,10);
			
			if (buttonList[randomX][randomY].isEnabled() && !buttonList[randomX][randomY].isBomb) {
				buttonList[randomX][randomY].setBomb();
				bombAmount++;
			}
		}
	}
	
	public void checkNearBombs() {

				for (int i = 0; i < buttonList.length; i++) {
					for (int j = 0; j < buttonList[0].length; j++) {
						int bombsNear = 0;
						if (!buttonList[i][j].isBomb) {
							
							
							// Top left.
							if (i == 0) {
								if (j == 0) {
									for (int x = 0; x < 2; x++) {
										for (int y = 0; y < 2; y++) {
											if (buttonList[x][y].isBomb) {
												bombsNear++;
											}
											
										}
									}
									buttonList[i][j].setBombsNear(bombsNear);
									bombsNear = 0;
									
								}
								
								// Top Center
								else if (j < 9) {
									for (int x = 0; x < 2; x++) {
										for (int y = j - 1; y < j + 2; y++) {
											if (buttonList[x][y].isBomb) {
												bombsNear++;
											}
										}
									}
									buttonList[i][j].setBombsNear(bombsNear);
									bombsNear = 0;
									
								}
								
								// Top Right
								else if (j == 9) {
									for (int x = 0; x < 2; x++) {
										for (int y = 8; y < 10; y++) {
											if (buttonList[x][y].isBomb) {
												bombsNear++;
											}
										}
									}
									
									buttonList[i][j].setBombsNear(bombsNear);
									bombsNear = 0;
								}
								
								
							}
							
							// Left Center
							else if (j == 0) {
								if (i < 9) {
									for (int x = i - 1; x < i + 2; x++) {
										for (int y = 0; y < 2; y++) {
											if (buttonList[x][y].isBomb) {
												bombsNear++;
											}
										}
									}
									buttonList[i][j].setBombsNear(bombsNear);
									bombsNear = 0;
								}

								// Bottom Left
								else if (i == 9) {
									for (int x = 8; x < 10; x++) {
										for (int y = 0; y < 2; y++) {
											if (buttonList[x][y].isBomb) {
												bombsNear++;
											}
											
										}
									}
									buttonList[i][j].setBombsNear(bombsNear);
									bombsNear = 0;
									
								}
								
								
							}
							
							// Bottom Center
							else if (i == 9) {
								if (j < 9) {
									for (int x = 8; x < 10; x++) {
										for (int y = j - 1; y < j + 2; y++) {
											if (buttonList[x][y].isBomb) {
												bombsNear++;
											}
										}
									}
									buttonList[i][j].setBombsNear(bombsNear);
									bombsNear = 0;
								}

								// Bottom Right
								else if (j == 9) {
									for (int x = 8; x < 10; x++) {
										for (int y = 8; y < 10; y++) {
											if (buttonList[x][y].isBomb) {
												bombsNear++;
											}
											
										}
									}
									buttonList[i][j].setBombsNear(bombsNear);
									bombsNear = 0;
									
								}
								
								
							}
							
							// Right Center
							else if (j == 9) {
								if (i < 9) {
									for (int x = i - 1; x < i + 2; x++) {
										for (int y = 8; y < 10; y++) {
											if (buttonList[x][y].isBomb) {
												bombsNear++;
											}
										}
									}
									buttonList[i][j].setBombsNear(bombsNear);
									bombsNear = 0;
								}
							}
							
							// All other Cases.
							else {
								for (int x = i - 1; x < i + 2; x++) {
									for (int y = j - 1; y < j + 2; y++) {
										if (buttonList[x][y].isBomb) {
											bombsNear++;
										}
									}
								}
								buttonList[i][j].setBombsNear(bombsNear);
								bombsNear = 0;
								
							}
							
							
						}
			}
		}
		
	}


	public void resetMineSweeper() {
		for (int i = 0; i < buttonList.length; i++) {
			for (int j = 0; j < buttonList[0].length; j++) {
				buttonList[i][j].eraseButton();
				isClicked = false;
				
			}
		}	
		
		
	}
	

	
	private static final long serialVersionUID = 1L;

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
