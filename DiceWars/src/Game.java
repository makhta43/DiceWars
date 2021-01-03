import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
	private int numOfPlayers;
	private Player[] listOfPlayers;

	public Game(int numOfPlayers, Map m) {
		this.numOfPlayers = numOfPlayers;
		this.fillPlayerList();
		this.assignMapSpace(m);
		this.assignDice();
		this.shufflePlayers();
	}

	// Populate player list
	public void fillPlayerList() {
		listOfPlayers = new Player[numOfPlayers];
		for (int i = 0; i < numOfPlayers; i++) {
			listOfPlayers[i] = new Player();
		}
		System.out.println(" - Player list filled");
	}

	// Assign territories to a player
	public void assignMapSpace(Map m) {
		Random rand = new Random();
		int randomPoint, randomPlayer;
		while (m.validTerritories.size() > 0) {
			randomPoint = rand.nextInt(m.validTerritories.size());
			randomPlayer = rand.nextInt(listOfPlayers.length);
			Point p = m.validTerritories.get(randomPoint);

			listOfPlayers[randomPlayer].getTerritories().add(m.territoryMap[p.x][p.y]);
			m.validTerritories.remove(randomPoint);
		}

		for (Player player : listOfPlayers) {
			for (Territory territory : player.getTerritories()) {
				territory.setPlayerId(player.getId());
			}
		}
		System.out.println(" - Player have been assigned territory");
	}

	// returns total strength of a players territories
	public int calcTotalStrength(Player p) {
		int strength = 0;
		for (Territory t : p.getTerritories()) {
			strength = strength + t.getStrength();
		}
		return strength;
	}

	// Assign strength (dice) to all territories
	public void assignDice() {
		Random rand = new Random();
		boolean hasZero;

		for (Player p : listOfPlayers) {
			hasZero = hasZero(p.getTerritories());
			while (hasZero) {
				for (int i = 0; i < p.getTerritories().size(); i++) {
					p.getTerritories().get(i).setStrength(0);
				}
				for (int i = 0; i < 20; i++) {
					int r = rand.nextInt(p.getTerritories().size());
					if ((p.getTerritories().get(r).getStrength() + 1) < 8) {
						p.getTerritories().get(r).setStrength(p.getTerritories().get(r).getStrength() + 1);
					}
				}
				hasZero = hasZero(p.getTerritories());
			}
		}
		System.out.println(" - Territories have been assigned strength");
	}

	// Checks if List of territories contains any zero values
	private boolean hasZero(List<Territory> t) {
		int checkAgainstTer = 0;
		for (int i = 0; i < t.size(); i++) {
			if (t.get(i).getStrength() > 0) {
				checkAgainstTer++;
			}
		}
		if (checkAgainstTer == t.size()) {
			return false;
		}
		return true;
	}

	// Shuffles players for random order
	private void shufflePlayers() {
		List<Player> tempList = Arrays.asList(listOfPlayers);
		Collections.shuffle(tempList);
		tempList.toArray(listOfPlayers);
	}

	// Runs the game in terminal as a text based game. C'est semi fonctionnel
	public void runGame(Map m) {
		Scanner in = new Scanner(System.in);
		List<Territory> enemyNeighbour = null;
		int nullCounter = 0;
		while (nullCounter != this.listOfPlayers.length - 1) {
			for (Player currentPlayer : this.getlistOfPlayers()) {
				if (currentPlayer.getTerritories().size() == 0) {
					System.out.println("========================= Player " + currentPlayer.getId()
							+ " has lost all their territories =========================\nPlayers remaining are: ");
					currentPlayer.setId(0);
					for (Player p : this.getlistOfPlayers()) {
						if (p.getId()!=0) {
							System.out.print(p.getId()+", ");
						}
					}
					System.out.println();
					nullCounter++;
				}
				System.out.println("Current Status of the map: <<[playerID,strength]>>");
				for (Territory[] t : m.territoryMap) {
					for (Territory t1 : t) {
						System.out.print("[" + t1.getPlayerId() + "," + t1.getStrength() + "]");
					}
					System.out.println();
				}
				if (currentPlayer != null) {
					System.out.println("-----------\nPlayer " + currentPlayer.getId()
							+ "'s turn\nWould you like to attack or end your turn?\na = attack\ne = end turn");
					String res = in.next();
					while (!res.equalsIgnoreCase("e")) {
						if (res.equalsIgnoreCase("a")) {
							System.out.println(
									"Enter a friendly Territory from the list below: <<[territoryId,strength]>>\n== Only strength > 1 is shown and territories with neighbouring enemies ==");
							for (Territory t : currentPlayer.getTerritories()) {
								if (t.getStrength() > 1 && t.enemyNeighbours(m).size() > 0) {
									System.out.print("[" + t.getId() + "," + t.getStrength() + "]");
								}
							}
							System.out.println();
							int res1 = in.nextInt();
							System.out.println(
									"Enter a neighbouring enemy Territory from the list below: <<[territoryId,strength]>>");
							enemyNeighbour = enemyNeighbours(res1, m);
							for (int i = 0; i < enemyNeighbour.size(); i++) {
								System.out.print("[" + enemyNeighbour.get(i).getId() + ","
										+ enemyNeighbour.get(i).getStrength() + "]");
							}
							System.out.println();
							int res2 = in.nextInt();
							currentPlayer.attackTerritory(res1, res2, m, listOfPlayers);
							System.out.println("-----------\nPlayer " + currentPlayer.getId()
									+ "'s turn\nWould you like to attack or end your turn?\na = attack\ne=end turn");
							res = in.next();
						}
					}
					currentPlayer.endTurn();
				}
			}
		}
		for (Player p : this.getlistOfPlayers()) {
			if (p!=null) {
				System.out.println("Player "+p.getId()+" WINS!!");
				System.out.println("You won out of " + this.getlistOfPlayers().length + " players");
				System.out.println("Total strength: " + p.totalStrength());
				System.out.println("Total territories captured: " + p.getTerritories().size());
				System.exit(0);
			}
		}

		in.close();

	}

	private List<Territory> enemyNeighbours(int territoryId, Map m) {
		List<Territory> listOfEnemyNeighbours = new ArrayList<Territory>();
		Territory current = null;
		for (Territory[] territoryList : m.territoryMap) {
			for (Territory territory : territoryList) {
				if (territory.getId() == territoryId) {
					current = territory;
				}
			}
		}
		for (Integer neighbourId : current.getListOfNeighbourId()) {
			for (Territory[] territoryList : m.territoryMap) {
				for (Territory territory : territoryList) {
					if (neighbourId == territory.getId() && current.getPlayerId() != territory.getPlayerId()) {
						listOfEnemyNeighbours.add(territory);
					}
				}
			}
		}
		return listOfEnemyNeighbours;
	}

	public int getnumOfPlayers() {
		return this.numOfPlayers;
	}

	public Player[] getlistOfPlayers() {
		return this.listOfPlayers;
	}
}