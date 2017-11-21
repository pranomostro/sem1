class Mensch extends Aerger {
	private static int[] startpoints={0, 10, 20, 30};
	private static int[] endpoints={9, 19, 29, 39};
	private static String[] colornames={"yellow", "blue", "red", "green"};
	private static String selmsg1="player 1: yellow(1)/blue(2)/red(3)/green(4)";
	private static String selmsg2="player 2 (other): yellow(1)/blue(2)/red(3)/green(4)";
	private static int[][] positions={
		{-1, -1, -1, -1},
		{-1, -1, -1, -1},
		{-1, -1, -1, -1},
		{-1, -1, -1, -1},
	};

	public static void main(String args[]) {
		int p1, p2; /* always between 0<=p.<=3, except when printing */

		Aerger.paintField(positions[0], positions[1], positions[2], positions[3]);

		for(p1=readInt(selmsg1); !(p1>=1&&p1<=4); p1=readInt(selmsg1))
			;

		for(p2=readInt(selmsg2); p2==p1||!(p2>=1&&p2<=4); p2=readInt(selmsg2))
			;

		p1--;
		p2--;

		while(true) {
			Aerger.paintField(positions[0], positions[1], positions[2], positions[3]);
			move(p1, p2, "player 1");
		}
	}

	private static void move(int mover, int other, String msg) {
		int amount, figure;

		amount=dice();

		String onmove=msg + " (" + colornames[mover] + "): " + amount + " field(s), figure: ";

		for(figure=readInt(onmove); !(figure>=1&&figure<=4)||!validmove(mover, figure-1, amount); figure=readInt(onmove))
			;

		figure--;

		if(positions[mover][figure]==-1)
			positions[mover][figure]=startpoints[mover]+amount;
		else {
			positions[mover][figure]+=amount;
			positions[mover][figure]%=40;
		}
	}

	private static boolean validmove(int player, int figure, int amount) {
		int newpos, i;

		newpos=positions[player][figure];

		if(positions[player][figure]==-1)
			newpos=startpoints[player]+amount;
		else {
			newpos+=amount;
			newpos%=40;
		}

		for(i=0; i<4; i++)
			if(positions[player][i]==newpos&&i!=figure)
				return false;
		return true;
	}
}
