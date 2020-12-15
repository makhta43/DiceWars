import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Player {
	private static final AtomicInteger count;
	private int id;
	public List<Territory> territories;

	static {
		count = new AtomicInteger(0);
	}

	public Player() {
		this.id = Player.count.incrementAndGet();
	}

	public void attackTerritory(final int enemyId) {
		System.out.println("Attack Territory" + enemyId);
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