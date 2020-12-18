import java.util.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Map {
	public Territory[][] territoryMap;
	public List<Point> validTerritories = new ArrayList<Point>();

	// Constructor uses methods below to initialise the map
	public Map(int numOfPlayers) {
		this.setMapSize(numOfPlayers);
		this.fillMap();
		this.findNeighbours();
		this.getListOfTerritories();
	}

	// Changes the map size depending on the number of players
	private void setMapSize(int numOfPlayers) {
		int size = 0;
		switch (numOfPlayers) {
		case 2:
			size = (int) (numOfPlayers * 3);
			break;
		case 3:
			size = (int) (numOfPlayers * 2.5);
			break;
		case 4:
			size = (int) (numOfPlayers * 1.75);
			break;
		case 5:
			size = (int) (numOfPlayers * 1.5);
			break;
		case 6:
			size = (int) (numOfPlayers * 1.25);
			break;
		default:
			System.out.println("By default the number of players has been set to 2");
			size = (int) (numOfPlayers * 3);
		}

		this.territoryMap = new Territory[size][size];
		System.out.println(" - Map size set");
	}

	// Iterates through the 2D-array to fill it with territories
	// Second part of this code adds random gaps inbetween the territories (GAPS
	// CONTAIN TERRITORIES WHO'S ID == NULL)
	private void fillMap() {
		for (int row = 0; row < this.territoryMap.length; ++row) {
			for (int col = 0; col < this.territoryMap[row].length; ++col) {
				this.territoryMap[row][col] = new Territory();
			}
		}
		for (int i = 0; i < 10; ++i) {
			final Random rand = new Random();
			this.territoryMap[rand.nextInt(this.territoryMap.length)][rand.nextInt(this.territoryMap.length)]
					.setId((Integer) null);
		}
		System.out.println(" - Map filled with territories");
	}

	// Goes through the 2D-array and finds all touching territories in the radius
	private void findNeighbours() {
		final List<Integer> tempList = new ArrayList<Integer>();
		for (int row = 0; row < this.territoryMap.length; ++row) {
			for (int col = 0; col < this.territoryMap[row].length; ++col) {
				try {
					if (this.territoryMap[row + 1][col].getId() != 0) {
						tempList.add(this.territoryMap[row + 1][col].getId());
					}
				} catch (Exception ex) {
				}
				try {
					if (this.territoryMap[row][col + 1].getId() != 0) {
						tempList.add(this.territoryMap[row][col + 1].getId());
					}
				} catch (Exception ex2) {
				}
				try {
					if (this.territoryMap[row + 1][col + 1].getId() != 0) {
						tempList.add(this.territoryMap[row + 1][col + 1].getId());
					}
				} catch (Exception ex3) {
				}
				try {
					if (this.territoryMap[row - 1][col].getId() != 0) {
						tempList.add(this.territoryMap[row - 1][col].getId());
					}
				} catch (Exception ex4) {
				}
				try {
					if (this.territoryMap[row][col - 1].getId() != 0) {
						tempList.add(this.territoryMap[row][col - 1].getId());
					}
				} catch (Exception ex5) {
				}
				try {
					if (this.territoryMap[row - 1][col - 1].getId() != 0) {
						tempList.add(this.territoryMap[row - 1][col - 1].getId());
					}
				} catch (Exception ex6) {
				}
				try {
					if (this.territoryMap[row + 1][col - 1].getId() != 0) {
						tempList.add(this.territoryMap[row + 1][col - 1].getId());
					}
				} catch (Exception ex7) {
				}
				try {
					if (this.territoryMap[row - 1][col + 1].getId() != 0) {
						tempList.add(this.territoryMap[row - 1][col + 1].getId());
					}
				} catch (Exception ex8) {
				}
				List<Integer> copy = List.copyOf(tempList);
				this.territoryMap[row][col].setListOfNeighbourId((List<Integer>) copy);
				tempList.clear();
			}
		}
		System.out.println(" - Neighbours have been found and listed");
	}

	// Gets list of all non-null territories
	private void getListOfTerritories() {
		for (int row = 0; row < this.territoryMap.length; ++row) {
			for (int col = 0; col < this.territoryMap[row].length; ++col) {
				if (this.territoryMap[row][col].getId() != null) {
					validTerritories.add(new Point(row, col));
				}
			}
		}
	}
}