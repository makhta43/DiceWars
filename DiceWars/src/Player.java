import java.awt.Point;
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

	// Method is parsed two territoryID's. One friendly, one enemy
	public Point attackTerritory(int playerTerr, int enemyTerr, Map m, Player[] ls) {
		Point p = new Point();
		List<Integer> territoryId = new ArrayList<Integer>();
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
								if (m.territoryMap[row][col].getId() != 0) {
									if (m.territoryMap[row][col].getId() == enemyTerr) {
										int youRoll = rollDice(this.territories.get(i).getStrength());
										int theyRolled = rollDice(m.territoryMap[row][col].getStrength());
										p.x=youRoll;
										p.y=theyRolled;
										if (youRoll > theyRolled) {
											for (Player player : ls) {
												if (player.getId() == m.territoryMap[row][col].getPlayerId()) {
													for (int j = 0; j < player.territories.size(); j++) {
														if (player.territories.get(j)
																.getId() == m.territoryMap[row][col].getId()) {
															player.territories.remove(j);
														}
													}
												}
											}
											m.territoryMap[row][col].setPlayerId(this.id);
											m.territoryMap[row][col]
													.setStrength(this.territories.get(i).getStrength() - 1);
											this.territories.add(m.territoryMap[row][col]);
											this.territories.get(i).setStrength(1);
											return p;
										} else {
											this.territories.get(i).setStrength(1);
											return p;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public void endTurn() {
		Random r = new Random();
		boolean hasInc;
		int randomTerritory;
		int numOfTerritories = this.getTerritories().size();
		for (int i = 0; i < numOfTerritories; i++) {
			if (this.totalStrength() < (this.getTerritories().size() * 8)) {
				hasInc = false;
				while (!hasInc) {
					randomTerritory = r.nextInt(numOfTerritories);
					if ((territories.get(randomTerritory).getStrength() + 1) <= 8) {
						this.territories.get(randomTerritory)
								.setStrength(territories.get(randomTerritory).getStrength() + 1);
						hasInc = true;
					}
				}
			}
		}
	}

	public int totalStrength() {
		int total = 0;
		for (Territory territory : territories) {
			total = total + territory.getStrength();
		}
		return total;
	}

	private int rollDice(int strength) {
		int outcome = 0;
		int randNum;
		Random r = new Random();
		for (int i = 0; i < strength; i++) {
			randNum = r.nextInt(6) + 1;
			outcome = outcome + randNum;
		}

		return outcome;
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
	public void setAtomic(int i) {
		Player.count.set(i);
	}
}