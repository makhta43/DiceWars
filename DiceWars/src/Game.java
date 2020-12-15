import java.util.List;

public class Game {
	private int numOfPlayers;
	private List<Player> listOfPlayers;

	public Game(final int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	public int getnumOfPlayers() {
		return this.numOfPlayers;
	}
}