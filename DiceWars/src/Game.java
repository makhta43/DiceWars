import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

	//Populate player list
	public void fillPlayerList() {
		listOfPlayers = new Player[numOfPlayers];
		for (int i = 0; i < numOfPlayers; i++) {
			listOfPlayers[i] = new Player();
		}
		System.out.println(" - Player list filled");
	}

	//Assign territories to a player
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

	//returns total strength of a players territories
	public int calcTotalStrength(Player p) {
		int strength = 0;
		for (Territory t : p.getTerritories()) {
			strength = strength + t.getStrength();
		}
		return strength;
	}

	//Assign strength (dice) to all territories
	public void assignDice() {
		Random rand = new Random();
		boolean hasZero;

		for (Player p : listOfPlayers) {
			hasZero = hasZero(p.getTerritories());
			while (hasZero == true) {
				for (int i = 0; i < p.getTerritories().size(); i++) {
					p.getTerritories().get(i).setStrength(0);
				}
				for (int i = 0; i < 20; i++) {
					int r = rand.nextInt(p.getTerritories().size());
					p.getTerritories().get(r).setStrength(p.getTerritories().get(r).getStrength() + 1);
				}
				hasZero = hasZero(p.getTerritories());
			}
		}
		System.out.println(" - Territories have been assigned strength");
	}
	
	//Shuffles players for random order
	public void shufflePlayers() {
		List<Player> tempList = Arrays.asList(listOfPlayers);
		Collections.shuffle(tempList);
		tempList.toArray(listOfPlayers);
	}

	//Checks if List of territories contains any zero values
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

	public int getnumOfPlayers() {
		return this.numOfPlayers;
	}

	public Player[] getlistOfPlayers() {
		return this.listOfPlayers;
	}

}