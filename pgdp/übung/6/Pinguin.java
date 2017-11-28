public class Pinguin extends Maze {
	private static int[][] maze;

	public static void main(String[] args) {
		maze=generateStandardPenguinMaze(10, 10);

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}

		Maze.draw(maze);
	}
}
