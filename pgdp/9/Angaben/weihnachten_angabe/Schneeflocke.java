public class Schneeflocke extends SingleObject {
	public Schneeflocke(int x, int y, int background, int foreground) {
		super(x, y, background, foreground);
	}

	public int moveLeft(boolean[][] staticObjects) {
		return 0;
	}

	public int moveRight(boolean[][] staticObjects) {
		return 0;
	}

	public boolean moveDown(boolean[][] staticObjects, boolean single) {
		return super.moveDown(staticObjects, true);
	}
}
