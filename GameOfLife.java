import java.util.Scanner;
import java.util.Arrays;
import java.lang.Thread;

class GameOfLife {
  int stateIter;
  int size;
  boolean[][] state;

  public GameOfLife() {
		this.stateIter = 0;
		this.size = 10;
		this.state = new boolean[this.size][this.size];
  }

  public void initGol() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Do you want a custom set up ? (y/N): ");
		String response = sc.nextLine();
		if (response.equals("Y") || response.equals("y") || response.equals("Yes") || response.equals("yes")) {
			System.out.print("Enter a size for the board [1, 30]: ");
			this.size = sc.nextInt();
			while (this.size <= 0 || 30 < this.size) {
				System.out.print("Error: size must be in range [1, 30]\n");
				System.out.print("Enter a size for the board: ");
				this.size = sc.nextInt();
			}
			this.state = new boolean[this.size][this.size];
			for (int i = 0; i < this.size; ++i) {
				Arrays.fill(this.state[i], false);
			}
			sc.close();
		}
  }
  public void  runGol() {
		this.setRandomState();
		// this.state[5][3] = true;
		// this.state[5][4] = true;
		// this.state[5][5] = true;
		this.printState();
		while (true) {
			this.setNextState();
			this.printState();
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				System.out.println("Exception: " + e);
				System.exit(1);
			}
		}
  }

  void  setRandomState() {
		for (int y = 0; y < this.size; ++y) {
			for (int x = 0; x < this.size; ++x) {
				double rand = Math.floor(Math.random() * 15) + 1;
				// System.out.println(rand);
				if (rand == 15) {
					this.setRandomNeighbors(y, x);
				}
			}
		}
  }

  void  setRandomNeighbors(int y0, int x0) {
		double rand;
		for (int y = y0 - 1; y <= y0 + 1; ++y) {
			for (int x = x0 - 1; x <= x0 + 1; ++x) {
				if ((0 <= y && y < this.size)
				&& (0 <= x && x < this.size)
				&& (y != y0 || x != x0)) {
					rand = Math.floor(Math.random() * 3) + 1;
					if (rand == 3) {
						this.state[y][x] = true;
					}
				}
			}
		}
  }
  
  void setNextState() {
		boolean[][] newState = new boolean[this.size][];
		int nbrNeighbors = 0;
		for (int i = 0; i < this.size; ++i) {
			newState[i] = this.state[i].clone();
		}
		for (int y = 0; y < this.size; ++y) {
			for (int x = 0; x < this.size; ++x) {
				nbrNeighbors = this.getNbrNeighbors(y, x);
				// If the cell is alive
				if (this.state[y][x]) {
					if (nbrNeighbors < 2 || 3 < nbrNeighbors) {
						newState[y][x] = false;
					} 
				} else if (nbrNeighbors == 3) {
					newState[y][x] = true;
				}
			}
		}
		this.state = newState;
  }

  int getNbrNeighbors(int y0, int x0) {
		int nbrNeighbors = 0;
		for (int y = y0 - 1; y <= y0 + 1; ++y) {
			for (int x = x0 - 1; x <= x0 + 1; ++x) {
				if ((0 <= y && y < this.size) && (0 <= x && x < this.size) && (y != y0 || x != x0)) {
					if (this.state[y][x]) {
						++nbrNeighbors;
					}
				}
			}
		}
		return nbrNeighbors;
  }

  void printState() {
		for (int i = 0; i < this.size; ++i) {
			for (int j = 0; j < this.size; ++j) {
				if (this.state[i][j]) {
					System.out.print("O ");
				} else {
					System.out.print(". ");
				}
			}
			System.out.print("\n");
		}
		System.out.println("**----------**");
	}
}