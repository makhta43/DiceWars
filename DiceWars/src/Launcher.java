import java.util.Arrays;
import java.util.Scanner;

public class Launcher {
	public static void main(final String[] args) {
		
		int numOfPlayers = 6;

		final Map m = new Map(numOfPlayers);
		Game g = new Game(numOfPlayers, m);
		System.out.println("\n=====================================\n");

		
		// # The code bellow simply prints out the 2D array that makes up the map. It doesn't change anything but is a useful visual aid
//		System.out.println("/////////////////////////////////////////////\nThe Whole Map:\n"
//				+ Arrays.deepToString(m.territoryMap).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

		Scanner in = new Scanner(System.in);
		System.out.println("This tests the attackTerritory() Method:  ");
		for (Territory t : g.getlistOfPlayers()[1].getTerritories()) {
			System.out.println(t.getId());
		}
		for (Territory t : g.getlistOfPlayers()[1].getTerritories()) {
			System.out.println(t.getListOfNeighbourId());
		}
		System.out.println("Now choose a friendly ID");
		int f = in.nextInt();
		System.out.println("Now choose a neighbouring enemy ID");
		int h = in.nextInt();
		g.getlistOfPlayers()[1].isFriendly(f, h);
//		g.getlistOfPlayers()[1].attackTerritory(f, h);
		in.close();

	}
}