public class AstMitte extends SingleObject {
	public AstMitte(int x, int y, int background, int foreground) {
		super(x, y, background, foreground);
	}

	public boolean moveDown(boolean[][] staticObjects, boolean single) {
		return super.moveDown(staticObjects, false);
	}
}
