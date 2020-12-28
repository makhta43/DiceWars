import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Player {
	private static final AtomicInteger count;
	private int id;
	private List<Territory> territories = new ArrayList<Territory>();

	// This code ensures that every Player has a unique ID
	static {
		count = new AtomicInteger(0);
	}

	public Player() {
		this.id = Player.count.incrementAndGet();
	}

	// When this method is run on a player, you must pass it a friendly (owned)
	// params are - territoryID and a *neighbouring* enemyID
	// == WARNING: This method might extend what it's meant to do into the functions
	// of other methods and might need a rewrite ==
	public void attackTerritory(int playerTerr, int enemyTerr, Map m) {
		Random r = new Random();
		List<Integer> territoryId = new ArrayList<Integer>();
		int max1, max2;
		for (Territory t : this.territories) {
			territoryId.add(t.getId());
		}
		for (int i = 0; i < this.territories.size(); i++) {
			if (playerTerr == this.territories.get(i).getId()) {
				for (int neighbour : this.territories.get(i).getListOfNeighbourId()) {
					if (enemyTerr == neighbour && !territoryId.contains(enemyTerr)
							&& this.territories.get(i).getStrength() != 1) {
						for (int row = 0; row < m.territoryMap.length; ++row) {
							for (int col = 0; col < m.territoryMap[row].length; ++col) {
								if (m.territoryMap[row][col].getId() != null) {
									if (m.territoryMap[row][col].getId() == enemyTerr) {
										max1 = this.territories.get(i).getStrength() * 6;
										System.out.println("You have " + this.territories.get(i).getStrength()
												+ " dice. and you rolled: " + (r.nextInt((max1 - 1) + 1) + 1));
										max2 = m.territoryMap[row][col].getStrength() * 6;
										System.out.println("Enemy has " + m.territoryMap[row][col].getStrength()
												+ " dice. and they rolled: " + (r.nextInt((max2 - 1) + 1) + 1));
										if (r.nextInt((max1 - 1) + 1) + 1 > r.nextInt((max2 - 1) + 1) + 1) {
											System.out.println("You win");
											m.territoryMap[row][col].setPlayerId(this.id);
											m.territoryMap[row][col]
													.setStrength(this.territories.get(i).getStrength() - 1);
											this.territories.get(i).setStrength(1);
										} else {
											System.out.println("You lose");
											this.territories.get(i).setStrength(1);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void endTurn() {
		System.out.println("End Turn");
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public List<Territory> getTerritories() {
		return this.territories;
	}

	public void setTerritories(final List<Territory> territories) {
		this.territories = territories;
	}
}