public class SingleObject extends Weihnachtsobjekt {
		protected int background;
		protected int foreground;

	public SingleObject(int x, int y, int background, int foreground) {
		super(x, y);
		this.background = background;
		this.foreground = foreground;
	}

	public void addObjektToSpielfeld(int[][] spielfeld) {
		spielfeld[x][y]+=foreground+background;
	}

	@Override
	public String toString() {
		return "" + this.getClass().getName() + "{" +
			"x=" + x +
			", y=" + y +
			", background=" + background +
			", foreground=" + foreground +
			", markedForDeath=" + markedForDeath +
			"} ";
	}
}
