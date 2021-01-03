import java.util.Scanner;

public class Launcher {
	public static void main(final String[] args) throws Exception {
		boolean tryToCatch = false;
		int numOfPlayers = 0;
		Scanner in = new Scanner(System.in);

		// This do while only allows inputs between 2 and 6
		do {
			System.out.println("Enter number of players (2 to 6): ");
			if (in.hasNextInt()) {
				tryToCatch = true;
				numOfPlayers = in.nextInt();
			} else {
				in.nextLine();
			}
		} while (tryToCatch == false || numOfPlayers < 2 || numOfPlayers > 6);

//		final Map m = new Map(new File("map.csv")); // Creates map using CSV file
		final Map m = new Map(numOfPlayers);		// Creates a random map
		Game g = new Game(numOfPlayers, m);
		System.out.println("\n=====================================\n");
		g.runGame(m);
		in.close();
	}
}