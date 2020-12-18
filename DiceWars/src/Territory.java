import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Territory {
	private static final AtomicInteger count;
	private Integer id;
	private int playerId;
	private int strength;
	private List<Integer> listOfNeighbourId;

	static {
		count = new AtomicInteger(0);
	}

	public Territory() {
		this.id = Territory.count.incrementAndGet();
	}

	public Integer getId() {
		return this.id;
	}

	public void setListOfNeighbourId(final List<Integer> listOfNeighbourId) {
		this.listOfNeighbourId = listOfNeighbourId;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Integer getStrength() {
		return this.strength;
	}

	public void setStrength(final Integer strength) {
		this.strength = strength;
	}
	
	public Integer getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(final Integer playerId) {
		this.playerId = playerId;
	}

	public List<Integer> getListOfNeighbourId() {
		return listOfNeighbourId;
	}

	@Override
	public String toString() {
		return String.valueOf(this.id);
	}
}