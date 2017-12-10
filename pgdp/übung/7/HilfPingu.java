public class HilfPingu extends PenguinPen {
	private final static int width=15;
	private final static int height=10;
	private static int px=1, py=0;
	private static int count=0;
	private static final int[][] penguinPen = generatePenguinPen(width, height);
	private static final boolean[][] done=new boolean[width][height];
	private static final int[][] wechsstate=new int[width][height];

	private final static int NOVAL=-1;
	private final static int GOINGRIGHT=0;
	private final static int RHR_UP=1;
	private final static int RHR_RIGHT=2;
	private final static int RHR_LEFT=3;
	private final static int RHR_DOWN=4;

	private final static int[][] neigh={
		{ -1, -1 },
		{ 0, -1 },
		{ 1, -1 },
		{ 1, 0 },
		{ 1, 1 },
		{ 0, 1 },
		{ -1, 1 },
		{ -1, 0 }
	};

	private final static int[][] movedir={
		{ -1, 0 },
		{ 1, 0 },
		{ 0, -1 },
		{ 0, 1 },
		{ 0, 0 }
	};

	public static void move(int direction) {
		if(count==0)
			for(int i=0; i<width; i++)
				for(int j=0; j<height; j++)
					if(penguinPen[i][j]==PENGUIN_OIO)
						wechsstate[i][j]=0;

		for(int i=0; i<neigh.length; i++)
			if(py+neigh[i][1]>=0&&isPeng(px+neigh[i][0], py+neigh[i][1])) {
				MiniJava.writeConsole("(" + (px+neigh[i][0]) + ", " + (py+neigh[i][1]) + ") wird frei -- Pinguin ist satt.\n");
				penguinPen[px+neigh[i][0]][py+neigh[i][1]]=FREE;
			}

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
						MiniJava.writeConsole("(" + j + ", " + i + ") ==> (" + (j+ystep) + ", " + (i+xstep) + ") -- Pinguin flüchtet\n");
						penguinPen[i][j]=FREE;
						penguinPen[i+xstep][j+ystep]=PENGUIN_OOI;
						done[i+xstep][j+ystep]=true;
					}
					break;
				case PENGUIN_OIO:
					if(penguinPen[i+1][j]==WALL&&wechsstate[i][j]==GOINGRIGHT)
						wechsstate[i][j]=RHR_UP;
					if(wechsstate[i][j]==GOINGRIGHT&&penguinPen[i+1][j]==FREE) {
						MiniJava.writeConsole("(" + j + ", " + i + ") ==> (" + j + ", " + (i+1) + ") -- Pinguin flüchtet\n");
						penguinPen[i][j]=FREE;
						penguinPen[i+1][j]=PENGUIN_OIO;
						done[i+1][j]=true;
						wechsstate[i][j]=NOVAL;
						wechsstate[i+1][j]=GOINGRIGHT;
					} else if(wechsstate[i][j]!=GOINGRIGHT&&wechsstate[i][j]!=NOVAL) {
						rhr(i, j);
					}
					break;
				case PENGUIN_OII:
					int nx, ny;
					nx=ny=0;
					while(penguinPen[nx][ny]!=FREE) {
						nx=(int)(width*Math.random());
						ny=(int)(height*Math.random());
					}
					MiniJava.writeConsole("(" + j + ", " + i + ") ==> (" + ny + ", " + nx + ") -- Pinguin flüchtet\n");
					penguinPen[nx][ny]=PENGUIN_OII;
					penguinPen[i][j]=FREE;
					done[nx][ny]=true;
					break;
				case PENGUIN_IOO:
					int idx=maxdistIndex(i, j);
					MiniJava.writeConsole("(" + j + ", " + i + ") ==> (" + (j+movedir[idx][1]) + ", " + (i+movedir[idx][0]) + ") -- Pinguin flüchtet\n");
					penguinPen[i][j]=FREE;
					penguinPen[i+movedir[idx][0]][j+movedir[idx][1]]=PENGUIN_IOO;
					done[i+movedir[idx][0]][j+movedir[idx][1]]=true;
					break;
				}
			}

		if(py==0&&direction==2)
			return;

		if(penguinPen[px+movedir[direction][0]][py+movedir[direction][1]]==FREE) {
			MiniJava.writeConsole("(" + py + ", " + px + ") ==> (" + (py+movedir[direction][1]) + ", " + (px+movedir[direction][0]) + ") -- Spielschritt.\n");
			px+=movedir[direction][0];
			py+=movedir[direction][1];
		}

		penguinPen[px][py]=ZOOKEEPER;

		draw(penguinPen);
		count++;
	}

	private static int maxdistIndex(int x, int y) {
		int maxdist=0;
		int maxindex=0;
		for(int i=0; i<movedir.length-1; i++)
			if(penguinPen[x+movedir[i][0]][y+movedir[i][1]]==FREE&&
			  (Math.abs((x+movedir[i][0])-px)+Math.abs((y+movedir[i][1])-py))>maxdist&&
			  (y+movedir[i][1]>0)) {
				maxdist=Math.abs((x+movedir[i][0])-px)+Math.abs((y+movedir[i][1])-py);
				maxindex=i;
			}
		if(Math.abs(x-px)+Math.abs(y-py)>maxdist)
			maxindex=movedir.length-1;
		return maxindex;
	}

	private static void rhr(int i, int j) {
		if(j==0&&wechsstate[i][j]==RHR_UP) {
			wechsstate[i][j]=RHR_DOWN;
			rhr(i, j);
		}

		if(wechsstate[i][j]==RHR_UP&&penguinPen[i+1][j]==WALL||
			wechsstate[i][j]==RHR_RIGHT&&penguinPen[i][j+1]==WALL||
			wechsstate[i][j]==RHR_DOWN&&penguinPen[i-1][j]==WALL||
			wechsstate[i][j]==RHR_LEFT&&penguinPen[i][j-1]==WALL) {
			if(wechsstate[i][j]==RHR_UP&&penguinPen[i][j-1]==WALL) {
				wechsstate[i][j]=RHR_LEFT;
				rhr(i, j);
			} else if(wechsstate[i][j]==RHR_RIGHT&&penguinPen[i+1][j]==WALL) {
				wechsstate[i][j]=RHR_UP;
				rhr(i, j);
			} else if(wechsstate[i][j]==RHR_DOWN&&penguinPen[i][j+1]==WALL) {
				wechsstate[i][j]=RHR_RIGHT;
				rhr(i, j);
			} else if(wechsstate[i][j]==RHR_LEFT&&penguinPen[i-1][j]==WALL) {
				wechsstate[i][j]=RHR_DOWN;
				rhr(i, j);
			} else {
				switch(wechsstate[i][j]) {
				case RHR_UP:
					if(penguinPen[i][j-1]==FREE) {
						MiniJava.writeConsole("(" + j + ", " + i + ") ==> (" + (j-1) + ", " + i + ") -- Pinguin flüchtet\n");
						penguinPen[i][j]=FREE;
						penguinPen[i][j-1]=PENGUIN_OIO;
						wechsstate[i][j]=NOVAL;
						wechsstate[i][j-1]=RHR_UP;
						done[i][j-1]=true;
						return;
					}
					break;
				case RHR_RIGHT:
					if(penguinPen[i+1][j]==FREE) {
						MiniJava.writeConsole("(" + j + ", " + i + ") ==> (" + j + ", " + (i+1) + ") -- Pinguin flüchtet\n");
						penguinPen[i][j]=FREE;
						penguinPen[i+1][j]=PENGUIN_OIO;
						wechsstate[i][j]=NOVAL;
						wechsstate[i+1][j]=RHR_RIGHT;
						done[i+1][j]=true;
						return;
					}
					break;
				case RHR_DOWN:
					if(penguinPen[i][j+1]==FREE) {
						MiniJava.writeConsole("(" + j + ", " + i + ") ==> (" + (j+1) + ", " + i + ") -- Pinguin flüchtet\n");
						penguinPen[i][j]=FREE;
						penguinPen[i][j+1]=PENGUIN_OIO;
						wechsstate[i][j]=NOVAL;
						wechsstate[i][j+1]=RHR_DOWN;
						done[i][j+1]=true;
						return;
					}
					break;
				case RHR_LEFT:
					if(penguinPen[i-1][j]==FREE) {
						MiniJava.writeConsole("(" + j + ", " + i + ") ==> (" + j + ", " + (i-1) + ") -- Pinguin flüchtet\n");
						penguinPen[i][j]=FREE;
						penguinPen[i-1][j]=PENGUIN_OIO;
						wechsstate[i][j]=NOVAL;
						wechsstate[i-1][j]=RHR_LEFT;
						done[i-1][j]=true;
						return;
					}
					break;
				}
			}
		} else {
			switch(wechsstate[i][j]) {
			case RHR_LEFT:
				if(penguinPen[i][j-1]==FREE) {
					MiniJava.writeConsole("(" + j + ", " + i + ") ==> (" + (j-1) + ", " + i + ") -- Pinguin flüchtet\n");
					penguinPen[i][j]=FREE;
					penguinPen[i][j-1]=PENGUIN_OIO;
					wechsstate[i][j]=NOVAL;
					wechsstate[i][j-1]=RHR_UP;
					done[i][j-1]=true;
					return;
				}
				break;
			case RHR_UP:
				if(penguinPen[i+1][j]==FREE) {
					MiniJava.writeConsole("(" + j + ", " + i + ") ==> (" + j + ", " + (i+1) + ") -- Pinguin flüchtet\n");
					penguinPen[i][j]=FREE;
					penguinPen[i+1][j]=PENGUIN_OIO;
					wechsstate[i][j]=NOVAL;
					wechsstate[i+1][j]=RHR_RIGHT;
					done[i+1][j]=true;
					return;
				}
				break;
			case RHR_RIGHT:
				if(penguinPen[i][j+1]==FREE) {
					MiniJava.writeConsole("(" + j + ", " + i + ") ==> (" + (j+1) + ", " + i + ") -- Pinguin flüchtet\n");
					penguinPen[i][j]=FREE;
					penguinPen[i][j+1]=PENGUIN_OIO;
					wechsstate[i][j]=NOVAL;
					wechsstate[i][j+1]=RHR_DOWN;
					done[i][j+1]=true;
					return;
				}
				break;
			case RHR_DOWN:
				if(penguinPen[i-1][j]==FREE) {
					MiniJava.writeConsole("(" + j + ", " + i + ") ==> (" + j + ", " + (i-1) + ") -- Pinguin flüchtet\n");
					penguinPen[i][j]=FREE;
					penguinPen[i-1][j]=PENGUIN_OIO;
					wechsstate[i][j]=NOVAL;
					wechsstate[i-1][j]=RHR_LEFT;
					done[i-1][j]=true;
					return;
				}
				break;
			}
		}
	}

	private static boolean isPeng(int x, int y) {
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
