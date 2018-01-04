public class Weihnachtsobjekt {
	protected int x, y;
	protected boolean fallend = true;
	protected boolean markedForDeath = false;

	public Weihnachtsobjekt(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void addObjektToSpielfeld(int[][] spielfeld) { };

	public boolean moveDown(boolean[][] staticObjects, boolean issingle){
		if(staticObjects[x][y]==true)
			return false;
		if(y>=staticObjects[x].length-2) {
			staticObjects[x][y]=true;
			return false;
		}
		if(staticObjects[x][y+1]==true||y==staticObjects[0].length) {
			if(issingle) {
				y++;
			}
			staticObjects[x][y]=true;
			return false;
		}
		y++;
		return true;
	};

	public int moveLeft(boolean[][] staticObjects) {
		if(staticObjects[x][y]==true)
			return 0;
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
		if(staticObjects[x][y]==true)
			return 0;
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
