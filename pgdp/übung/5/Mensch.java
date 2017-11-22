class Mensch extends Aerger {
	private static int[] startpoints={0, 10, 20, 30};
	private static int[] endpoints={39, 9, 19, 29};
	private static String[] colornames={"yellow", "blue", "green", "red"};
	private static String selmsg1="player 1: yellow(1)/blue(2)/green(3)/red(4)";
	private static String selmsg2="player 2 (other): yellow(1)/blue(2)/green(3)/red(4)";
	private static int[][] positions={
		{-1, -1, -1, -1},
		{-1, -1, -1, -1},
		{-1, -1, -1, -1},
		{-1, -1, -1, -1},
	};

	public static void main(String args[]) {
		int p1, p2; /* always between 0<=p<=3, except when printing */

		/* green is on position 3 on the board but, when passed to paintField, it */
		/* is the 4th argument */

		paint();

		for(p1=readInt(selmsg1); !(p1>=1&&p1<=4); p1=readInt(selmsg1))
			;

		for(p2=readInt(selmsg2); p2==p1||!(p2>=1&&p2<=4); p2=readInt(selmsg2))
			;

		p1--;
		p2--;

		while(!gameover()) {
			move(p1, p2, "player 1");
			if(gameover())
				break;
			paint();
			move(p2, p1, "player 2");
			paint();
		}
		paint();
		write("Game over");
	}

	private static void paint() {
		Aerger.paintField(positions[0], positions[1], positions[3], positions[2]);
	}

	private static void move(int mover, int other, String msg) {
		int amount, figure;

		amount=dice();

		String onmove=msg + " (" + colornames[mover] + "): " + amount + " field(s), figure: ";

		for(figure=readInt(onmove);
			!(figure>=1&&figure<=4)||!validmove(mover, figure-1, amount);
			figure=readInt(onmove))
			write("Please use other figure\n");

		figure--;

		if(positions[mover][figure]==-1)
			positions[mover][figure]=startpoints[mover]+amount;
		else if(positions[mover][figure]<=endpoints[mover]&&positions[mover][figure]+amount>endpoints[mover])
			positions[mover][figure]=40;
		else {
			positions[mover][figure]+=amount;
			positions[mover][figure]%=40;
		}
		throwout(mover, figure);
	}

	private static void throwout(int player, int figure) {
		int i, j;

		for(i=0; i<4; i++)
			for(j=0; j<4; j++)
				if(i!=player&&positions[i][j]<=39&&positions[i][j]>=0&&
					positions[i][j]==positions[player][figure])
					positions[i][j]=-1;
	}

	private static boolean validmove(int player, int figure, int amount) {
		int newpos, i;

		newpos=positions[player][figure];

		if(newpos==40)
			return false;

		if(positions[player][figure]==-1)
			newpos=startpoints[player]+amount;
		else {
			newpos+=amount;
			newpos%=40;
		}


		for(i=0; i<4; i++)
			if(positions[player][i]==newpos&&i!=figure&&positions[player][i]<40)
				return false;
		return true;
	}

	private static boolean gameover() {
		int i;

		for(i=0; i<4; i++)
			if(positions[i][0]==40&&positions[i][1]==40&&
				positions[i][2]==40&&positions[i][3]==40)
				return true;
		return false;
	}
}
