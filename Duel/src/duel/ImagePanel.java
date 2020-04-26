package duel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * TODO// Display player, enemy, card icons.
 * 
 * @auther Kevin Dapper
 * @author Quentin Howa
 */
public class ImagePanel extends JPanel{

	Timer timer = new Timer();
	private int statusEnemy = 0;
	private int statusPlayer = 0;
	
	private int xPlayer = 70;
	private int yPlayer = 200;
	
	private int xEnemy = 570;
	private int yEnemy = 135;
		
	/**
	 * Create the panel.
	 */
	public ImagePanel() {

	}
	
	/**
	 * Changes the enemy status
	 * @param status
	 */
	public void setStatusEnemy(int status) {
		statusEnemy = status;
	}
	
	/**
	 * Changes the player status
	 * @param status
	 */
	public void setStatusPlayer(int status) {
		statusPlayer = status;
	}
	
	/**
	 * Moves enemy left to right
	 */
	public void moveEnemyForward() {
		if(xEnemy > 270) {
			xEnemy -= 4;	
		}
		if(xEnemy == 270) {
			setStatusEnemy(2);
		}
	}
	
	/**
	 * Moves enemy right to left
	 */
	public void moveEnemyBackward() {
		if(xEnemy < 570) {
			xEnemy += 4;	
		}
		if(xEnemy == 570) {
			setStatusEnemy(0);
		}
	}
	
	/**
	 * Moves enemy up
	 */
	public void moveEnemyUp() {
		if(yEnemy > 0) {
			yEnemy -= 3;
		}
		if(yEnemy == 0) {
			setStatusEnemy(4);
		}
	}
	
	/**
	 * Moves enemy down
	 */
	public void moveEnemyDown() {
		if(yEnemy < 138) {
			yEnemy += 3;
		}
		if(yEnemy == 138) {
			setStatusEnemy(0);
		}
	}
	
	/**
	 * Moves player left to right
	 */
	public void movePlayerBackward() {
		if(xPlayer > 70) {
			xPlayer -= 4;	
		}
		if(xEnemy == 70) {
			setStatusPlayer(0);
		}
	}
	
	/**
	 * Moves player right to left
	 */
	public void movePlayerForward() {
		if(xPlayer < 350) {
			xPlayer += 4;	
		}
		if(xPlayer == 350) {
			setStatusPlayer(2);
		}
	}
	
	/**
	 * Moves player up
	 */
	public void movePlayerUp() {
		if(yPlayer > 110) {
			yPlayer -= 2;
		}
		if(yPlayer == 110) {
			setStatusPlayer(4);
		}
	}
	
	/**
	 * Moves player down
	 */
	public void movePlayerDown() {
		if(yPlayer < 200) {
			yPlayer += 2;
		}
		if(yPlayer == 200) {
			setStatusPlayer(0);
		}
	}
	
	/**
	 * Animates either player or enemy based on their status
	 */
	private void animate() {
		int i = 0;
		while(i != 5) {
			if(statusEnemy == 1) {
				moveEnemyForward();
			}
			if(statusEnemy == 2) {
				moveEnemyBackward();
			}
			if(statusEnemy == 3) {
				moveEnemyUp();
			}
			if(statusEnemy == 4) {
				moveEnemyDown();
			}
			if(statusPlayer == 1) {
				movePlayerForward();
			}
			if(statusPlayer == 2) {
				movePlayerBackward();
			}
			if(statusPlayer == 3) {
				movePlayerUp();
			}
			if(statusPlayer == 4) {
				movePlayerDown();
			}
			repaint();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
	}
	
	/**
	 * Takes an image and returns a rescaled version
	 * @param icon
	 * @param width
	 * @param height
	 * @return
	 */
	public static ImageIcon scaleImage(ImageIcon icon, int width, int height) {
		Image image = icon.getImage(); 
		Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); 
		icon = new ImageIcon(newimg);
		return icon;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		ImageIcon background = new ImageIcon(
				ImagePanel.class.getResource("/duel/Images/background.jpeg"));	
		background = scaleImage(background, 900, 498);
		background.paintIcon(this, g, 0, 0);
		
		ImageIcon playerIcon = new ImageIcon(
				ImagePanel.class.getResource("/duel/Images/player.png"));	
		playerIcon.paintIcon(this, g, xPlayer, yPlayer);
		
		ImageIcon enemyIcon = new ImageIcon(
				ImagePanel.class.getResource("/duel/Images/enemy.png"));	
		enemyIcon = scaleImage(enemyIcon, 270, 400);
		enemyIcon.paintIcon(this, g, xEnemy, yEnemy);
		
		if(statusEnemy > 0) {
			Duel.turnLbl.setText(Duel.enemyTurn);
			Duel.turnLbl.setBackground(Color.RED);
		}
		else {
			Duel.turnLbl.setText(Duel.playerTurn);
			Duel.turnLbl.setBackground(Color.GREEN);
		}
		animate();
		
	}

	

}
