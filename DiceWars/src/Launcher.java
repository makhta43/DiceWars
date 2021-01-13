import java.io.File;

public class Launcher {
	public static void main(final String[] args) {

		GUI gui = new GUI();
//		gui.getNumPlayers();
		
		/**
		* The code below was used for the in-terminal, text based build of the game
		* Simply uncomment the code and then comment the code above to run the old version of the game.
		* You may need to surround the CSV constructor with a try-catch to make it work
		* @variable numOfPlayers is used to change the number of Players
		* @Constructor Map has two lines below, one generates a random map, the other generates it based on a CSV file
		*/
//		int numOfPlayers = 4;
//		final Map m = new Map(new File("map.csv")); // Creates map using CSV file
//		final Map m = new Map(numOfPlayers); // Creates a random map
//		Game g = new Game(numOfPlayers, m);
//		g.runGame(m);
		
	}
}