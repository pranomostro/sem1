public class Weihnachtskugel extends SingleObject {
	public Weihnachtskugel(int x, int y, int background, int foreground) {
		super(x, y, background, foreground);
	}

	public boolean moveDown(boolean[][] staticObjects, boolean single) {
		return super.moveDown(staticObjects, true);
	}
}
