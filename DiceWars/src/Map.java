import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Map {
	public Territory[][] territoryMap;

	public Map(final int width, final int height) {
		this.territoryMap = new Territory[width][height];
		this.fillMap();
		this.findNeighbours();
	}

	private void fillMap() {
		for (int row = 0; row < this.territoryMap.length; ++row) {
			for (int col = 0; col < this.territoryMap[row].length; ++col) {
				this.territoryMap[row][col] = new Territory(2);
			}
		}
		for (int i = 0; i < 10; ++i) {
			final Random rand = new Random();
			this.territoryMap[rand.nextInt(this.territoryMap.length - 1)][rand.nextInt(this.territoryMap.length - 1)]
					.setId((Integer) null);
		}
	}

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
				this.territoryMap[row][col].setListOfNeighbourId((List<Integer>) tempList);
//				System.out.println(tempList);
				tempList.clear();
			}
		}
	}

	public void assignPlayers() {
	}
}