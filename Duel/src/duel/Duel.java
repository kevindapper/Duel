package duel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;

import javax.swing.border.MatteBorder;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.Font;

/**
 * Displays the game and reacts to user input.
 * @author Kevin Dapper
 * @author Quentin Howa
 */
public class Duel extends JFrame {

	private JPanel contentPane;
	private ImagePanel gamePanel;
	
	private static Random rand = new Random();
	Timer timer = new Timer();
	
	private JLabel enemyDefendHealthLbl;
	public static JLabel turnLbl;
	private JLabel playerDefendHealthLbl;
	private JLabel energyLbl;
	
	private JPanel menuPanel;
	private JPanel startScreen;
	private JPanel endScreenPanel;
	
	public static String playerTurn = "Player Turn";
	public static String enemyTurn = "Enemy Turn";
	
	private int enemyHealth = 15;
	private int enemyDefend = 5;
	private int playerHealth = 10;
	private int playerDefend = 5;
	
	private int totalAttacks = 0;
	private int totalDefense = 0; 
	
	private int playerEnergy = 10;
	
	private static JButton firstCard = new JButton();
	private static JButton secondCard = new JButton();
	private static JButton thirdCard = new JButton();
	private static JButton fourthCard = new JButton();
	
	private static List<Card> deck = new ArrayList<Card>();
	
	String filePlayerStats = "src/duel/TextFiles/PlayerStats.txt";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Duel frame = new Duel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	/**
	 * Create the frame.
	 */
	public Duel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout(0, 0));
		setResizable(false);
		setContentPane(contentPane);
		
		createGame();
		
	}

	/**
	 * Creates the game that the user can interact with
	 */
	private void createGame() {
		JPanel statusPanel = createStatusPanel();
		contentPane.add(statusPanel, BorderLayout.NORTH);
		
	    gamePanel = createImagePanel();
		contentPane.add(gamePanel, BorderLayout.CENTER);

		menuPanel = createMenuPanel();
		contentPane.add(menuPanel, BorderLayout.SOUTH);
	}

	/**
	 * Creates the menu panel that includes the energy,
	 * cards, and end turn elements. Also shuffles deck
	 * initially and performs card functions.
	 * @return
	 */
	private JPanel createMenuPanel() {
		JPanel menuPanel = new JPanel();
		
		energyLbl = new JLabel("Energy " + playerEnergy + "/10");
		energyLbl.setForeground(Color.BLUE);
		menuPanel.add(energyLbl);
		
		
		JPanel panel = new JPanel();
		menuPanel.add(panel);
		
		shuffle();
		
		firstCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerCardActions(0);
			}
		});
		panel.add(firstCard);
			
		secondCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerCardActions(1);
			}
		});
		panel.add(secondCard);
			
		thirdCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerCardActions(2);
			}
		});
		panel.add(thirdCard);
		
		fourthCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playerCardActions(3);
			}

		});
		
		panel.add(fourthCard);
			
		JButton button = new JButton("End Turn");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			 
				turnLbl.setText(enemyTurn);
				if(playerEnergy < 7) {
					playerEnergy += 3;
				} else {
					playerEnergy = 10;
				}
				setEnergy();
				
				deck.removeAll(deck);
				shuffle();
				enemyCardActions();
				
				turnLbl.setText(playerTurn);
				
			}
		});
		button.setHorizontalTextPosition(SwingConstants.RIGHT);
		button.setHorizontalAlignment(SwingConstants.RIGHT);
		menuPanel.add(button);

		return menuPanel;
	}

	
	/**
	 * Creates the image panel to display player and enemy.
	 * @return
	 */
	private ImagePanel createImagePanel() {
		ImagePanel gamePanel = new ImagePanel();
		gamePanel.setBackground(Color.WHITE);
		return gamePanel;
	}

	/**
	 * Creates a panel that displays player/enemy health
	 * and also the turn status.
	 * @return
	 */
	private JPanel createStatusPanel() {
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		statusPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		playerDefendHealthLbl = createplayerDefendHealthLbl();
		statusPanel.add(playerDefendHealthLbl);
		
	    turnLbl = createTurnLbl();
		statusPanel.add(turnLbl);
		
		enemyDefendHealthLbl = createEnemyLbl();
		statusPanel.add(enemyDefendHealthLbl);
		
		return statusPanel;
	}

	/**
	 * Creates a label that displays enemy health.
	 * @return
	 */
	private JLabel createEnemyLbl() {
		JLabel enemyDefendHealthLbl = new JLabel("Health " + enemyHealth + "/15" + " | Defense " + enemyDefend);
		enemyDefendHealthLbl.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		enemyDefendHealthLbl.setHorizontalAlignment(SwingConstants.CENTER);
		return enemyDefendHealthLbl;
	}

	/**
	 * Creates a label that displays player health.
	 * @return
	 */
	private JLabel createplayerDefendHealthLbl() {
		JLabel playerDefendHealthLbl = new JLabel("Health " + playerHealth + "/10" + " | Defense " + playerDefend);
		playerDefendHealthLbl.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		playerDefendHealthLbl.setHorizontalAlignment(SwingConstants.CENTER);
		return playerDefendHealthLbl;
	}
	
	/**
	 * Creates a label that displays who's turn it is.
	 * @return
	 */
	private JLabel createTurnLbl() {
		JLabel turnLbl = new JLabel(playerTurn);
		turnLbl.setOpaque(true);
		turnLbl.setBackground(Color.GREEN);
		turnLbl.setForeground(Color.BLACK);
		turnLbl.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		turnLbl.setHorizontalAlignment(SwingConstants.CENTER);
		return turnLbl;
	}
	
	/**
	 * Randomly assigns cards and their corresponding 
	 * values to a deck (buttons).
	 */
	private static void shuffle() {
		for(int i = 0; i < 8; i++) {
			deck.add(new Card(rand.nextInt(6) + 1, rand.nextBoolean(), false));
		}
		firstCard.setText(deck.get(0).toString());
		secondCard.setText(deck.get(1).toString());
		thirdCard.setText(deck.get(2).toString());
		fourthCard.setText(deck.get(3).toString());
	}
	
	/**
	 * Allows the ability to change the
	 * remaining energy.
	 */
	private void setEnergy() {
		energyLbl.setText("Energy " + playerEnergy + "/10");
	}
	
	/**
	 * Performs card type specific action based on button pressed. 
	 * Calls to move player
	 * @param position
	 */
	private void playerCardActions(int position) {
		if(turnLbl.getText() == playerTurn) {
			if(!(deck.get(position).getPlayed())) {
				if((playerEnergy - deck.get(position).getEnergyCost() >= 0)) {
					if(deck.get(position).getCardType()) {
						gamePanel.setStatusPlayer(1);
						totalAttacks += deck.get(position).attack(deck.get(position).getEnergyCost());
						if(enemyDefend >= 0) {
							enemyDefend -= deck.get(position).attack(deck.get(position).getEnergyCost());
							enemyDefendHealthLbl.setText("Health " + enemyHealth + "/15" + " | Defense " + enemyDefend);
						}
						if(enemyDefend < 0) {
							enemyHealth += enemyDefend;
							if(enemyHealth <= 0) {
								enemyHealth = 0;
								endGame();
							}
							changeHealthColor(enemyHealth, enemyDefendHealthLbl);
							enemyDefend = 0;
							enemyDefendHealthLbl.setText("Health " + enemyHealth + "/15" + " | Defense " + enemyDefend);
						}
						
						playerEnergy -= deck.get(position).attack(deck.get(position).getEnergyCost());
					} else {
						gamePanel.setStatusPlayer(3);
						totalDefense += deck.get(position).defend(deck.get(position).getEnergyCost());
						playerDefend += deck.get(position).defend(deck.get(position).getEnergyCost());
						playerEnergy -= deck.get(position).defend(deck.get(position).getEnergyCost());
						playerDefendHealthLbl.setText("Health " + playerHealth + "/10" + " | Defense " + playerDefend);
					}	
					setEnergy();
				}
			
			}
			deck.get(position).setPlayed(true);
		}
	
	}

	private void changeHealthColor(int health, JLabel healthLbl) {
		if(health <= 7) {
			healthLbl.setForeground(Color.ORANGE);
		}
		if(health <= 4) {
			healthLbl.setForeground(Color.RED);
		}
	}
	
	/**
	 * Performs card type specific action for enemy randomly
	 * Calls to animate enemy
	 * 
	 */
	private void enemyCardActions() {
		Random action = new Random();
		Card enemyCard;
		switch(action.nextInt(10)) {
		case 1:
			enemyCard = new Card(4, true, false);
			break;
		case 2:
			enemyCard = new Card(8, true, false);
			break;
		case 3:
			enemyCard = new Card(4, false, false);
			break;
		case 4:
			enemyCard = new Card(6, false, false);
			break;
		default:
			enemyCard = new Card(1, true, false);
			break;
		}
		
		if(enemyCard.getCardType()) {
			gamePanel.setStatusEnemy(1);
			if(playerDefend >= 0) {
				playerDefend -= enemyCard.attack(enemyCard.getEnergyCost());
				playerDefendHealthLbl.setText("Health " + playerHealth + "/10" + " | Defense " + playerDefend);
			}
			if(playerDefend < 0) {
				playerHealth += playerDefend;
				if(playerHealth <= 0) {
					playerHealth = 0;
					endGame();
				}
				changeHealthColor(playerHealth, playerDefendHealthLbl);
				playerDefend = 0;
				playerDefendHealthLbl.setText("Health " + playerHealth + "/10" + " | Defense " + playerDefend);
			}
		} else {
			gamePanel.setStatusEnemy(3);
			enemyDefend += enemyCard.defend(enemyCard.getEnergyCost());
			enemyDefendHealthLbl.setText("Health " + enemyHealth + "/15" + " | Defense " + enemyDefend);
		}
	}
	
	/**
	 * Removes game screen for end game screen
	 */
	public void endGame() {
		contentPane.removeAll();
	
		try(PrintWriter writer = new PrintWriter(filePlayerStats)){
			writer.printf("Total damage done: %d\n", totalAttacks);
			writer.printf("Total defense played: %d\n", totalDefense);
		} catch (FileNotFoundException e) {
			System.err.println("file not created");
		}
		
		endScreenPanel = createEndScreenPanel();
		contentPane.add(endScreenPanel, BorderLayout.CENTER);
		
		contentPane.repaint();
	}
	
	/**
	 * Creates a panel that displays the end game screen which includes whether player won or
	 * lost, total attack power, and total defenses
	 * @return
	 */
	private JPanel createEndScreenPanel() {
		JPanel endScreenPanel = new JPanel();
		endScreenPanel.setLayout(new GridLayout(3, 0));
		
		String endMessage; 
		if(playerHealth == 0) {
			endMessage = "/duel/Images/lose.png"; 
			endScreenPanel.setBackground(new Color(255, 0, 0));
		}
		else {
			endScreenPanel.setBackground(new Color(34, 139, 34));
			endMessage = "/duel/Images/win.png";
		}
		
		JLabel endMessageLbl = new JLabel();
		endMessageLbl.setHorizontalAlignment(SwingConstants.CENTER);
		endMessageLbl.setIcon(new ImageIcon(ImagePanel.class.getResource(endMessage)));
		
		endScreenPanel.add(endMessageLbl);
		
		String stat;
		try(Scanner reader = new Scanner(new File(filePlayerStats))){
			while(reader.hasNextLine()) {
				stat = reader.nextLine();
				endScreenPanel.add(createPlayerStatsLbl(stat));
			}
		} catch (FileNotFoundException e) {
			System.err.println("file not found");
		}
		
		return endScreenPanel;
	}

	/**
	 * Creates a label that displays players stats
	 * @param stat
	 * @return
	 */
	private JLabel createPlayerStatsLbl(String stat) {
		JLabel playerStatsLbl = new JLabel(stat);
		playerStatsLbl.setForeground(Color.WHITE);
		playerStatsLbl.setFont(new Font("Segoe UI", Font.BOLD, 40));
		playerStatsLbl.setHorizontalAlignment(SwingConstants.CENTER);
		return playerStatsLbl;
	}
}
