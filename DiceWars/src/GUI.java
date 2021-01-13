import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class GUI extends JFrame implements ActionListener {

	private JLabel label;
	private JPanel panel, mainPanel, bottomPanel, choosePlayerPane;
	private JLabel outcome;
	private JButton buttonSelected;
	private JButton[][] map;
	private JLabel[] playerList;
	private Game g;
	private Map m;
	private int playerCount, fId, eId, nullCounter;
	private Color colour1 = Color.CYAN, colour2 = new Color(127, 0, 255), colour3 = Color.ORANGE, colour4 = Color.PINK,
			colour5 = Color.MAGENTA, colour6 = Color.green, currentPlayerColour;
	private Border neighbourBorder = new LineBorder(new Color(0, 51, 102), 6);

	// Constructor
	public GUI() {
		super("BTEC Dice Wars");
		this.setLayout(new FlowLayout());
		this.setSize(380, 565);
		this.setBackground(Color.LIGHT_GRAY);

		panel = new JPanel();
		mainPanel = new JPanel();
		bottomPanel = new JPanel();
		panel.setBackground(new Color(248, 248, 255));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		this.add(panel, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);

		label = new JLabel();
		Image logo;
		try {
			logo = ImageIO.read(new File("logo.png"));
			label.setIcon(new ImageIcon(logo));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		label.setToolTipText("Why are you messing with this? Go play the game!!!! :D");
		panel.add(label);

		this.getNumPlayers();
		this.getContentPane().setBackground(new Color(248, 248, 255));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}

	public void getNumPlayers() {
		choosePlayerPane = new JPanel(new BorderLayout());
		choosePlayerPane.setBackground(new Color(248, 248, 255));
		JPanel tempPane = new JPanel();
		choosePlayerPane.add(new JLabel("Choose the number of players: ", JLabel.CENTER), BorderLayout.PAGE_START);
		choosePlayerPane.add(tempPane, BorderLayout.CENTER);

		for (int i = 2; i < 7; i++) {
			JButton playerSelect = new JButton(String.valueOf(i));
			playerSelect.addActionListener(this);
			tempPane.add(playerSelect);
		}
		this.add(choosePlayerPane);
		SwingUtilities.updateComponentTreeUI(this);
	}

	// Creates, displays and updates the map whilst assigning and handling button
	// presses within the mainPanel
	public void displayMap(Map m) {
		int index = 1;
		mainPanel.setLayout(new GridLayout(m.territoryMap.length, m.territoryMap.length));
		map = new JButton[m.territoryMap.length][m.territoryMap.length];
		Image img = null;
		for (int i = 0; i < m.territoryMap.length; i++) {
			for (int j = 0; j < m.territoryMap[i].length; j++) {
				JButton b = new JButton(m.territoryMap[i][j].getStrength().toString());
				b.setActionCommand(Integer.toString(index++));
				try {
					img = ImageIO.read(new File("dice.png"));
					b.setIcon(new ImageIcon(img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
				} catch (Exception ex) {
					System.out.println(ex);
				}
				switch (m.territoryMap[i][j].getPlayerId()) {
				case 1:
					b.setBackground(colour1);
					b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
					break;
				case 2:
					b.setBackground(colour2);
					b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
					break;
				case 3:
					b.setBackground(colour3);
					b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
					break;
				case 4:
					b.setBackground(colour4);
					b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
					break;
				case 5:
					b.setBackground(colour5);
					b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
					break;
				case 6:
					b.setBackground(colour6);
					b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
					break;
				default:
					b.setActionCommand(Integer.toString(0));
					b.setIcon(null);
					b.setBorderPainted(false);
					b.setOpaque(false);
					b.setText("");
					b.setBackground(Color.LIGHT_GRAY);
					break;
				}
				b.setFont(new Font("Arial", Font.BOLD, 25));
				b.setHorizontalTextPosition(JButton.CENTER);
				b.setVerticalTextPosition(JButton.CENTER);
				b.setPreferredSize(new Dimension(50, 50));
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton source = ((JButton) e.getSource());
						currentPlayerColour = null;
						if (playerList != null) {
							for (int k = 0; k < playerList.length; k++) {
								if (playerList[k].getBorder() != null) {
									currentPlayerColour = playerList[k].getBackground();
								}
							}
						}

						if (source.getBorder() != null && source.getBorder().equals(neighbourBorder)) {
							eId = Integer.parseInt(source.getActionCommand());
							for (int l = 0; l < g.getlistOfPlayers().length; l++) {
								if (playerList[l].getBorder() != null) {
									Point p = g.getlistOfPlayers()[l].attackTerritory(fId, eId, m,
											g.getlistOfPlayers());
									mainPanel.removeAll();
									displayMap(m);
									outcome = new JLabel("You rolled: " + p.x + "; Enemy rolled: " + p.y);
									outcome.setHorizontalTextPosition(JButton.CENTER);
									panel.add(outcome, BorderLayout.SOUTH);

									for (Player player : g.getlistOfPlayers()) {
										if (player.getTerritories().size() == 0) {
											player.setId(0);
											nullCounter++;
										}
									}
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {
										@Override
										public void run() {
											panel.remove(outcome);
										}
									}, 1000);
								}
							}
							if (nullCounter == g.getlistOfPlayers().length - 1) {
								for (Player player : g.getlistOfPlayers()) {
									if (player.getId() != 0) {
										mainPanel.removeAll();
										JLabel crown = new JLabel();
										Image crownPic = null;
										try {
											crownPic = ImageIO.read(new File("crown.png"));
										} catch (Exception ex) {
											System.out.println(ex);
										}
										ImageIcon img = new ImageIcon(crownPic);
										crown.setIcon(img);
										bottomPanel.removeAll();
										mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
										mainPanel.add(crown);
										mainPanel.add(new JLabel("Player " + player.getId() + " won with "
												+ player.getTerritories().size()
												+ " territories and a total strength of " + player.totalStrength()));
										mainPanel.add(new JLabel("Game will exit in 15 seconds"));
										Timer timer = new Timer();
										timer.schedule(new TimerTask() {
											@Override
											public void run() {
												System.exit(0);
											}
										}, 15000);

									}
								}
							}
						}
						if (buttonSelected != null) {
							buttonSelected.setBorder(null);
							buttonSelected.setBackground(currentPlayerColour);
							buttonSelected = null;
							for (JButton[] jButtons : map) {
								for (JButton jButton : jButtons) {
									if (jButton.getBorder() != null) {
										jButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
									}
								}
							}
						} else if (!e.getActionCommand().equals("0") && buttonSelected == null
								&& Integer.valueOf(source.getText()) > 1
								&& source.getBackground().equals(currentPlayerColour)) {
							source.setBorder(new LineBorder(Color.GREEN, 4));
							source.setBackground(new Color(173, 216, 230));
							buttonSelected = source;
							fId = Integer.parseInt(source.getActionCommand());
							highlightEnemy(Integer.parseInt(source.getActionCommand()));
						}
					}
				});
				map[i][j] = b;
			}
		}
		JPanel[] listOfPanes = new JPanel[map.length];
		for (int i = 0; i < map.length; i++) {
			listOfPanes[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

			mainPanel.add(listOfPanes[i]);
			for (int j = 0; j < map[i].length; j++) {
				listOfPanes[i].add(map[i][j]);
			}
		}
		SwingUtilities.updateComponentTreeUI(this);
	}

	// Highlights the surrounding territories that you are capable of attacking
	private void highlightEnemy(int id) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j].getActionCommand().equals(Integer.toString(id))) {
					for (int neighbourId : m.territoryMap[i][j].getListOfNeighbourId()) {
						for (JButton[] jButtons : map) {
							for (JButton jButton : jButtons) {
								if (jButton.getActionCommand().equals(Integer.toString(neighbourId))
										&& !jButton.getBackground().equals(currentPlayerColour)) {
									jButton.setBorder(neighbourBorder);
								}
							}
						}
					}
				}
			}
		}
	}

	// Fills the bottom panel of the game with the list of players
	public void fillBottomPanel(Player[] players) {
		Image img;
		playerList = new JLabel[players.length];
		for (int i = 0; i < players.length; i++) {
			JLabel l = new JLabel();
			l.setText("" + players[i].getTerritories().size());
			switch (players[i].getId()) {
			case 1:
				l.setBackground(colour1);
				break;
			case 2:
				l.setBackground(colour2);
				break;
			case 3:
				l.setBackground(colour3);
				break;
			case 4:
				l.setBackground(colour4);
				break;
			case 5:
				l.setBackground(colour5);
				break;
			case 6:
				l.setBackground(colour6);
				break;
			}
			try {
				img = ImageIO.read(new File("territory2.png"));
				l.setIcon(new ImageIcon(img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
			} catch (Exception ex) {
				System.out.println(ex);
			}
			l.setPreferredSize(new Dimension(50, 50));
			l.setOpaque(true);
			l.setHorizontalTextPosition(JButton.CENTER);
			l.setVerticalTextPosition(JButton.CENTER);
			l.setFont(new Font("Arial", Font.BOLD, 25));
			l.setForeground(Color.WHITE);
			playerList[i] = l;
			bottomPanel.add(l);
			bottomPanel.setBackground(new Color(105, 105, 105));
			bottomPanel.setPreferredSize(new Dimension(380, 300));
		}
		JButton endTurn = new JButton("End turn");
		endTurn.addActionListener(this);
		bottomPanel.add(endTurn);
		SwingUtilities.updateComponentTreeUI(this);
	}

	// simply states that the first player it is the first player in the list's turn
	private void initGame() {
		playerList[0].setBorder(new LineBorder(Color.RED, 4));
		SwingUtilities.updateComponentTreeUI(this);
	}

	// Contains the logic for when a player has ended their turn
	private void endTurnLogic() {
		int next = 0;
		for (int i = 0; i < playerList.length; i++) {
			if (playerList[i].getBorder() != null) {
				playerList[i].setBorder(null);
				next = i + 1;
				if (next == g.getlistOfPlayers().length) {
					next = 0;
				}
			}
		}
		playerList[next].setBorder(new LineBorder(Color.RED, 4));
	}

	// Handles the buttons and logic relating to remaking the map at the start
	private void remakeMap() {
		JButton yes, no;
		yes = new JButton("Yes");
		yes.addActionListener(this);
		no = new JButton("No");
		no.addActionListener(this);
		bottomPanel.add(new JLabel("Are you happy with this map?"));
		bottomPanel.add(yes);
		bottomPanel.add(no);
		bottomPanel.setBackground(new Color(105, 105, 105));
		bottomPanel.setPreferredSize(new Dimension(380, 300));
	}

	// Code to run after a number of players has been chosen
	private void playerSelectAction(ActionEvent e) {
		System.out.println(e.getActionCommand() + " players chosen");
		playerCount = Integer.parseInt(e.getActionCommand());
		m = new Map(Integer.parseInt(e.getActionCommand()));
		g = new Game(Integer.parseInt(e.getActionCommand()), m);
		this.displayMap(m);
		this.remove(choosePlayerPane);
		this.remakeMap();
	}

	// ActionListener for every button that isn't part of the actual game
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("2")) {
			this.playerSelectAction(e);
		} else if (e.getActionCommand().equals("3")) {
			this.playerSelectAction(e);
		} else if (e.getActionCommand().equals("4")) {
			this.playerSelectAction(e);
		} else if (e.getActionCommand().equals("5")) {
			this.playerSelectAction(e);
		} else if (e.getActionCommand().equals("6")) {
			this.playerSelectAction(e);
		} else if (e.getActionCommand().equals("Yes")) {
			this.bottomPanel.removeAll();
			this.fillBottomPanel(g.getlistOfPlayers());
			this.initGame();
		} else if (e.getActionCommand().equals("No")) {
			System.out.println("\n # RERENDER");
			g.getlistOfPlayers()[0].setAtomic(0);
			m.territoryMap[0][0].setAtomic(0);
			this.m = new Map(playerCount);
			this.g = new Game(playerCount, this.m);
			this.mainPanel.removeAll();
			this.displayMap(this.m);
		} else if (e.getActionCommand().equals("End turn")) {
			for (int j = 0; j < playerList.length; j++) {
				if (playerList[j].getBorder() != null) {
					g.getlistOfPlayers()[j].endTurn();
				}
			}
			mainPanel.removeAll();
			this.displayMap(m);
			this.endTurnLogic();
			SwingUtilities.updateComponentTreeUI(this);
		}
	}
}
