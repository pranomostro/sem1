import java.util.ArrayList;

/*
.:*~*:._.:*~*:._.:*~*:._.:*~*:._.:*~*:._.:*~*:.
		.     *																			 .
		.    /.\																			.
		.   /..'\																		 .
		.   /'.'\																		 .
		.  /.''.'\																		.
		.  /.'.'.\																		.
		. /'.''.'.\																	 .
		. ^^^[_]^^^																	 .
		.																						 .
		.																						 .
		.jgs																					.
.:*~*:._.:*~*:._.:*~*:._.:*~*:._.:*~*:._.:*~*:.
*/

public class Weihnachtsbaum extends BitteNichtAbgeben {

	private static final int[][] landscape = generateLandscape(30, 33);
	private static ArrayList<Weihnachtsobjekt> objekte = new ArrayList<>();
	private static boolean[][] staticObjects = new boolean[30][33];
	private static boolean gentree=true;

	public static void keyPressed(int key) {
		switch(key) {
		case WeihnachtsElfen.KEY_LEFT:
			for(Weihnachtsobjekt w: objekte) {
				w.moveLeft(staticObjects);
				w.moveDown(staticObjects);
			}
			WeihnachtsElfen.removeMarkedForDeath(objekte);
			break;
		case WeihnachtsElfen.KEY_RIGHT:
			for(Weihnachtsobjekt w: objekte) {
				w.moveRight(staticObjects);
				w.moveDown(staticObjects);
			}
			WeihnachtsElfen.removeMarkedForDeath(objekte);
			break;
		case WeihnachtsElfen.KEY_UP:
			gentree=!gentree;
			break;
		case WeihnachtsElfen.KEY_DOWN:
			for(Weihnachtsobjekt w: objekte) {
				w.moveDown(staticObjects);
			}
			break;
		}

		for(Weihnachtsobjekt w: objekte)
			w.moveDown(staticObjects);

		Weihnachtsobjekt wo=createRandomObjekt();
		if(wo!=null) {
			objekte.add(wo);
		}

		for(int i=1; i<landscape.length-1; i++)
			for(int j=1; j<landscape[i].length-1; j++)
				landscape[i][j]=WeihnachtsElfen.BACKGROUND_EMPTY+WeihnachtsElfen.FOREGROUND_EMPTY;

		for(Weihnachtsobjekt w: objekte) {
			w.addObjektToSpielfeld(landscape);
		}

		draw(landscape);
	}

	public static Weihnachtsobjekt createRandomObjekt() {
		Weihnachtsobjekt res=null;
		if(gentree) {
			WeihnachtsElfen.newRandomObject();
			switch(WeihnachtsElfen.randomObjects[WeihnachtsElfen.currentRandomObject][0]) {
			case WeihnachtsElfen.FALLING_NONE:
				break;
			case WeihnachtsElfen.FALLING_TRUNK:
				res=new Baumstamm((staticObjects[1].length/2)-(WeihnachtsElfen.randomObjects[WeihnachtsElfen.currentRandomObject][1]/2), 1, WeihnachtsElfen.randomObjects[WeihnachtsElfen.currentRandomObject][1]);
				break;
			case WeihnachtsElfen.FALLING_GREEN:
				res=new Ast((staticObjects[1].length/2)-(WeihnachtsElfen.randomObjects[WeihnachtsElfen.currentRandomObject][1]/2), 1, WeihnachtsElfen.randomObjects[WeihnachtsElfen.currentRandomObject][1]);
				break;
			}
		} else {
		}
		return res;
	}

	/*********************************************/
	/* Ab hier soll nichts mehr geändert werden! */
	/*********************************************/

	public static void main(String[] args) {
		draw(landscape);
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
			if (step != NO_KEY) {
				// System.out.print(step+",");
				keyPressed(step);
			}
		}
	}
}
