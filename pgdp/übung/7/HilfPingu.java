public class HilfPingu extends PenguinPen {
	private final static int width=15;
	private final static int height=10;
	private static int px=1, py=0;
	private static final int[][] penguinPen = generateStandardPenguinPen(width, height);
	private static final boolean[][] done=new boolean[width][height];
	private static final boolean[][] goingright=new boolean[width][height];

	private final static int[][] neigh={
		{ -1, -1 },
		{ 0, -1 },
		{ 1, -1 },
		{ 1, 0 },
		{ 1, 1 },
		{ 0, 1 },
		{ -1, 1 },
		{ -1, 0 },
	};

	public static void move(int direction) {
		if(py==0&&direction==3) {
			penguinPen[px][py]=FREE;
			py++;
			penguinPen[px][py]=ZOOKEEPER;
			draw(penguinPen);
			return;
		} else if(py==0) {
			return;
		}

		System.out.println(direction);

		for(int i=0; i<neigh.length; i++)
			if(isPeng(px+neigh[i][0], py+neigh[i][1]))
				penguinPen[px+neigh[i][0]][py+neigh[i][1]]=FREE;

		penguinPen[px][py]=FREE;

		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++)
				done[i][j]=false;

		for(int i=0; i<width; i++)
			for(int j=0; j<height; j++) {
				if(done[i][j]==true)
					continue;
				done[i][j]=true;
				switch(penguinPen[i][j]) {
				case PENGUIN_OOO:
					break;
				case PENGUIN_OOI:
					int xstep, ystep;
					if((int)(2*Math.random())==1) {
						xstep=0;
						ystep=1-(int)(3*Math.random());
					} else {
						xstep=1-(int)(3*Math.random());
						ystep=0;
					}
					if(penguinPen[i+xstep][j+ystep]==FREE&&j+ystep>=0) { /* no zufullin suicide, please */
						penguinPen[i][j]=FREE;
						penguinPen[i+xstep][j+ystep]=PENGUIN_OOI;
						done[i+xstep][j+ystep]=true;
					}
					break;
				case PENGUIN_OIO:
					break;
				case PENGUIN_OII:
					int nx, ny;
					nx=ny=0;
					while(penguinPen[nx][ny]!=FREE) {
						nx=(int)(width*Math.random());
						ny=(int)(height*Math.random());
					}
					penguinPen[nx][ny]=PENGUIN_OII;
					penguinPen[i][j]=FREE;
					done[nx][ny]=true;
					break;
				case PENGUIN_IOO:
					break;
			}

		switch(direction) {
		case 0:
			if(penguinPen[px-1][py]==FREE)
				px--;
			break;
		case 1:
			if(penguinPen[px+1][py]==FREE)
				px++;
			break;
		case 2:
			if(penguinPen[px][py-1]==FREE)
				py--;
			break;
		case 3:
			if(penguinPen[px][py+1]==FREE)
				py++;
			break;
		default:
			break;
		}

		penguinPen[px][py]=ZOOKEEPER;

		draw(penguinPen);
	}

	public static boolean isPeng(int x, int y) {
		switch(penguinPen[x][y]) {
			case PENGUIN_OOO: return true;
			case PENGUIN_OOI: return true;
			case PENGUIN_OIO: return true;
			case PENGUIN_OII: return true;
			case PENGUIN_IOO: return true;
		}
		return false;
	}

	/*********************************************/
	/* Ab hier soll nichts mehr geändert werden! */
	/*********************************************/

	public static void main(String[] args) {
		draw(penguinPen);
		handleUserInput();
	}

	private static void handleUserInput() {
		while(true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException ie) {
				/* Intentionally left blank */
			}
			int step = nextStep();
			if (step != NO_MOVE) {
				// System.out.print(step+",");
				move(step);
			}
		}
	}
}
