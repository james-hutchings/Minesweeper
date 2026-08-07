package mineSweeper;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;

public class MineSweeperFrame extends JFrame implements ActionListener {
	
	JMenu gameMenu = new JMenu();
	
		public MineSweeperFrame() {
			
			this.setPreferredSize(new Dimension(900,900));
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			MineSweeperPanel gamePanel = new MineSweeperPanel();
			this.setContentPane(gamePanel);
			this.pack();

			
			
		}
		
		
		
		
		
	
	
	
	
	
	
	
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private static final long serialVersionUID = 1L;
}
