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
			staticObjects[x][y]=true;
			return false;
		}
		y--;
		return true;
	};

	public int moveLeft(boolean[][] staticObjects) {
		if(x<=0) {
			markedForDeath=true;
			return 0;
		} else if(staticObjects[x-1][y]==true) {
			return 0;
		}
		x--;
		return 1;
	};

	public int moveRight(boolean[][] staticObjects) {
		if(x>=staticObjects.length-1) {
			markedForDeath=true;
			return 0;
		} else if(staticObjects[x+1][y]==true) {
			return 0;
		}
		x++;
		return 1;
	};
}
