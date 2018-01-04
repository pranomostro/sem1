import java.util.LinkedList;
import java.util.List;

public abstract class MultiObject extends Weihnachtsobjekt {

	protected List<SingleObject> parts;
	protected int breite;

	public MultiObject(int x, int y, int breite) {
		super(x, y);
		this.breite = breite;
		this.parts = new LinkedList<>();
	}

	public boolean moveDown(boolean[][] staticObjects, boolean issingle) {
		if(this.y>=staticObjects[0].length-2) {
			for(Weihnachtsobjekt w: parts)
				staticObjects[w.x][w.y]=true;
			return false;
		}
		for(Weihnachtsobjekt wo: parts)
			if(staticObjects[wo.x][wo.y+1]==true) {
				for(Weihnachtsobjekt fix: parts)
					staticObjects[fix.x][fix.y]=true;
				return false;
			}
		for(Weihnachtsobjekt wo: parts)
			wo.y++;
		this.y++;
		return true;
	}

	public void addObjektToSpielfeld(int[][] spielfeld) {
		for(Weihnachtsobjekt wo: parts)
			wo.addObjektToSpielfeld(spielfeld);
	}

	public int moveLeft(boolean[][] staticObjects) {
		if(this.x<=1) {
			this.markedForDeath=true;
			return 0;
		}
		if(staticObjects[this.x-1][this.y]==true) {
			return 0;
		}
		if(staticObjects[this.x][this.y]==true) {
			return 0;
		}
		for(Weihnachtsobjekt w: parts)
			w.moveLeft(staticObjects);
		this.x--;
		return 1;
	}

	public int moveRight(boolean[][] staticObjects) {
		if((this.x+(2*breite)+3)>=staticObjects[this.x].length-2) {
			this.markedForDeath=true;
			return 0;
		}
		if(staticObjects[this.x+(2*breite)+2][this.y]==true) {
			return 0;
		}
		if(staticObjects[this.x][this.y]==true) {
			return 0;
		}
		for(Weihnachtsobjekt w: parts)
			w.moveRight(staticObjects);
		this.x++;
		return 1;
	}

	@Override
	public String toString() {
		return "MultiObject{" +
				"parts=" + parts +
				", breite=" + breite +
				'}';
	}
}
