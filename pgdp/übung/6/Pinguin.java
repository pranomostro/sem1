public class Pinguin extends Maze {
	private static int maze[][];

	public static void main(String[] args) {
		int i, j;
		maze=generateStandardPenguinMaze(30, 30);

		for(i=0; i<30; i++)
			for(j=0; j<30; j++)
				System.out.println(maze[i][j] + "");

		draw(maze);
	}
}
