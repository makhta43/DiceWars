import java.util.Arrays;

public class Launcher {
	public static void main(final String[] args) {
		final Map m = new Map(5, 5);
		System.out.println("/////////////////////////////////////////////\nThe Whole Map:\n"
				+ Arrays.deepToString(m.territoryMap).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
	}
}