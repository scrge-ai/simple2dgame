import java.util.*;

public class Game {
	private int size;
	private int fences;
	private int enemies;

	public int[][] startgrid;
	public int[][] grid;

	public int[] enemy_x;
	public int[] enemy_y;

	public int player_x;
	public int player_y;

	public int[] fence_x;
	public int[] fence_y;

	public List<int[][]> memory;

	public List<int[]> enemy_memory_x = new ArrayList<int[]>();
	public List<int[]> enemy_memory_y = new ArrayList<int[]>();

	public List<Integer> player_memory_x = new ArrayList<Integer>();
	public List<Integer> player_memory_y = new ArrayList<Integer>();

	public Boolean game_over = false;
	public int winThing = 0;

	public Game(int size, int fences, int enemies) {
		this.fences = fences;
		this.enemies = enemies;
		this.size = size;

		this.startgrid = new int[size][size];
		this.grid = new int[size][size];
		this.enemy_x = new int[enemies];
		this.enemy_y = new int[enemies];
		this.fence_x = new int[fences];
		this.fence_y = new int[fences];

		player_x = (int) (Math.random() * (size - 2) + 1);
		player_y = (int) (Math.random() * (size - 2) + 1);

		grid[player_x][player_y] = 1;

		for (int i = 0; i < size; i++) {
			startgrid[0][i] = 3;
			startgrid[i][0] = 3;
			startgrid[size - 1][i] = 3;
			startgrid[i][size - 1] = 3;
			grid[0][i] = 3;
			grid[i][0] = 3;
			grid[size - 1][i] = 3;
			grid[i][size - 1] = 3;
		}

		for (int i = 0; i < this.enemies; i++) {
			enemy_x[i] = 1 + (int) (Math.random() * (size - 2));
			enemy_y[i] = 1 + (int) (Math.random() * (size - 2));
			// System.out.println(i);
			while (enemy_x[i] == player_x && enemy_y[i] == player_y) {
				enemy_x[i] = 1 + (int) (Math.random() * (size - 2));
				enemy_y[i] = 1 + (int) (Math.random() * (size - 2));
			}
			grid[enemy_x[i]][enemy_y[i]] = 2;
		}

		for (int i = 0; i < this.fences; i++) {
			fence_x[i] = 1 + (int) (Math.random() * (size - 2));
			fence_y[i] = 1 + (int) (Math.random() * (size - 2));
			while (fence_x[i] == player_x && fence_y[i] == player_y) {
				fence_x[i] = 1 + (int) (Math.random() * (size - 2));
				fence_y[i] = 1 + (int) (Math.random() * (size - 2));
			}
			startgrid[fence_x[i]][fence_y[i]] = 3;
			grid[fence_x[i]][fence_y[i]] = 3;
		}

		int[] memx = enemy_x.clone();
		int[] memy = enemy_y.clone();

		this.enemy_memory_x.add(memx);
		this.enemy_memory_y.add(memy);
		this.player_memory_x.add(player_x);
		this.player_memory_y.add(player_y);
	}

	public int getFences() {
		return fences;
	}

	public int setFences() {
		return fences;
	}

	public void Undo() {

		enemy_memory_x.remove(enemy_memory_x.size() - 1);
		enemy_memory_y.remove(enemy_memory_y.size() - 1);
		player_memory_x.remove(player_memory_x.size() - 1);
		player_memory_y.remove(player_memory_y.size() - 1);
		System.out.println("size: " + enemy_memory_x.size());

		enemy_x = enemy_memory_x.get(enemy_memory_x.size() - 1).clone();
		enemy_y = enemy_memory_y.get(enemy_memory_y.size() - 1).clone();
		player_x = player_memory_x.get(player_memory_x.size() - 1);
		player_y = player_memory_y.get(player_memory_y.size() - 1);

		int[][] newgrid = new int[grid.length][grid.length];
		// System.out.println("size: " + 12);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				newgrid[i][j] = startgrid[i][j];
				// System.out.print(newgrid[i][j]);
			}
			// System.out.println();
		}

		for (int i = 0; i < enemy_x.length; i++) {
			if (enemy_x[i] >= 0 && enemy_y[i] >= 0)
				newgrid[enemy_x[i]][enemy_y[i]] = 2;
		}
		newgrid[player_x][player_y] = 1;
		grid = newgrid;
	}

	public void Update(int movex, int movey) {
		player_x += movex;
		player_y += movey;

		int[][] newgrid = new int[grid.length][grid.length];
		// System.out.println("size: " + 12);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				newgrid[i][j] = startgrid[i][j];
				// System.out.print(newgrid[i][j]);
			}
			// System.out.println();
		}

		for (int i = 0; i < enemy_x.length; i++) {
			if (enemy_x[i] != -1 && enemy_y[i] != -1) {
				int xdist = Math.abs(enemy_x[i] - player_x);
				int ydist = Math.abs(enemy_y[i] - player_y);

				if (xdist > ydist) {
					if (enemy_x[i] < player_x)
						enemy_x[i]++;
					else
						enemy_x[i]--;
				} else if (ydist > xdist) {
					if (enemy_y[i] < player_y)
						enemy_y[i]++;
					else
						enemy_y[i]--;
				} else {
					int dx = (enemy_x[i] < player_x) ? 1 : -1;
					int dy = (enemy_y[i] < player_y) ? 1 : -1;
					enemy_x[i] += dx;
					enemy_y[i] += dy;
				}

				if (startgrid[enemy_x[i]][enemy_y[i]] == 3) {
					enemy_x[i] = -1;
					enemy_y[i] = -1;
					// int winThing = winThing +1;

				} else {
					newgrid[enemy_x[i]][enemy_y[i]] = 2;
				}
				if (startgrid == grid) {
					int winThing = 1;

				}
			}
		}

		if (newgrid[player_x][player_y] == 2 || newgrid[player_x][player_y] == 3) {
			// GAME OVER
			game_over = true;
		} else {
			newgrid[player_x][player_y] = 1;
		}

		grid = newgrid;
		int[] memx = enemy_x.clone();
		int[] memy = enemy_y.clone();

		this.enemy_memory_x.add(memx);
		this.enemy_memory_y.add(memy);
		this.player_memory_x.add(player_x);
		this.player_memory_y.add(player_y);
	}

	public void Jump() {
		player_x = (int) (Math.random() * (size - 2) + 1);
		player_y = (int) (Math.random() * (size - 2) + 1);
		Update(0, 0);
	}

	public int[][] DrawMemory(int frame) {
		int[] ex = enemy_memory_x.get(frame);
		int[] ey = enemy_memory_y.get(frame);
		int px = player_memory_x.get(frame);
		int py = player_memory_y.get(frame);
		int ret[][] = new int[grid.length][grid.length];

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				ret[i][j] = startgrid[i][j];
			}
		}

		for (int i = 0; i < ex.length; i++) {
			ret[ex[i]][ey[i]] = 2;
		}

		ret[px][py] = 1;

		return ret;
	}
}