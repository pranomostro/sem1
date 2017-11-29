public class Pinguin extends Maze {
	private static final int width=30;
	private static final int height=30;
	private static int[][] maze;

	public static void main(String[] args) {
		int x, y;

		maze=generatePenguinMaze(width, height);

		Maze.draw(maze);
		x=1;
		y=0;
		maze[x][y]=OLD_PATH_ACTIVE;
		draw(maze);

		MiniJava.writeConsole(walk(x, y+1, 50) + " penguins saved.\n");

		maze[x][y]=PLAYER;

		draw(maze);
	}

	private static int walk(int x, int y, int maxdist) {
		int penguins=0;

		if(!validfield(x, y)||maxdist<=0)
			return penguins;

		if(maze[x][y]==PENGUIN)
			penguins++;

		maze[x][y]=PLAYER;

		try { Thread.sleep(20); } catch(Exception e) { }

		draw(maze);

		maze[x][y]=OLD_PATH_ACTIVE;

		draw(maze);

		penguins+=walk(x+1, y, maxdist-1);
		penguins+=walk(x, y+1, maxdist-1);
		penguins+=walk(x, y-1, maxdist-1);
		penguins+=walk(x-1, y, maxdist-1);

		//penguins+=walk(x-1, y-1, maxdist-1);
		//penguins+=walk(x-1, y+1, maxdist-1);
		//penguins+=walk(x+1, y+1, maxdist-1);
		//penguins+=walk(x+1, y-1, maxdist-1);

		maze[x][y]=OLD_PATH_DONE;
		draw(maze);

		return penguins;
	}

	private static boolean validfield(int x, int y) {
		if(x<=0||y<=0||x>=width-1||y>=height-1)
			return false;

		if(maze[x][y]==OLD_PATH_DONE||maze[x][y]==OLD_PATH_ACTIVE)
			return false;

		if(!(maze[x+1][y]==WALL||
		     maze[x+1][y+1]==WALL||
		     maze[x][y+1]==WALL||
		     maze[x-1][y+1]==WALL||
		     maze[x-1][y]==WALL||
		     maze[x-1][y-1]==WALL||
		     maze[x][y-1]==WALL||
		     maze[x+1][y-1]==WALL))
			return false;

		if(maze[x][y]==WALL)
			return false;

		return true;
	}
}
