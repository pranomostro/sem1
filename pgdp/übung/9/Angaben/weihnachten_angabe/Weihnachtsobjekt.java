public class Weihnachtsobjekt {
	protected int x, y;
	protected boolean fallend = true;
	protected boolean markedForDeath = false;

	public Weihnachtsobjekt(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void addObjektToSpielfeld(int[][] spielfeld) { };
	public boolean moveDown(boolean[][] staticObjects){
		if(staticObjects[x][y-1]==true||y==staticObjects[0].length) {
			return false;
		}
		y--;
		return true;
	};

	public int moveLeft(boolean[][] staticObjects) { return 1; };
	public int moveRight(boolean[][] staticObjects) { return 1; };
}
