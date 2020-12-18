import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Player {
	private static final AtomicInteger count;
	private int id;
	private List<Territory> territories = new ArrayList<Territory>();

	static {
		count = new AtomicInteger(0);
	}

	public Player() {
		this.id = Player.count.incrementAndGet();
	}

	// When this method is run on a player, you must pass it a friendly (owned)
	// territoryID and a *neighbouring* enemyID
	public void attackTerritory(int playerTerr, int enemyTerr) {
		for (Territory territory : this.territories) {
			if (playerTerr == territory.getId()) {
				for (int neighbour : territory.getListOfNeighbourId()) {
					if (enemyTerr == neighbour && neighbour != territory.getId()) { //INCORRECT: Evaluates to True even when the enemyTerr is friendly
						System.out.println("The enemy heathen hath been attacked");
					} else {
						System.out.println("You must pick a neighbouring enemy ID");
					}
				}
			} else {
				System.out.println("You must pick a friendly territories ID");
			}
		}
	}

	public void endTurn() {
		System.out.println("End Turn");
	}
	
	//This method is useless. (It's an attempt at fixing the attackTerritory() method above)
	public boolean isFriendly(int fId, int eId) {
		for (Territory territory : this.territories) {
			if (territory.getId() == fId) {
				for (int neighbour : territory.getListOfNeighbourId()) {
					if (territory.getId() == neighbour) {
						System.out.println("Tis friendly");
					}
				}
			}
		}
		return false;
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
//	@Override
//	public String toString() {
//		return String.valueOf(this.id);
//	}
}