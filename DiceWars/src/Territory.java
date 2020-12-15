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

	public Territory(final int strength) {
		this.id = Territory.count.incrementAndGet();
		this.strength = strength;
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

	@Override
	public String toString() {
		return String.valueOf(this.id);
	}
}