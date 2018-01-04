public class Baumstamm extends MultiObject {
	public Baumstamm(int x, int y, int breite) {
		super(x, y, breite);

		if(breite==0) {
			StammMitte sm=new StammMitte(x, y, WeihnachtsElfen.BACKGROUND_TRUNK_MIDDLE, WeihnachtsElfen.FOREGROUND_EMPTY);
			parts.add(sm);
			sm=new StammMitte(x+1, y, WeihnachtsElfen.BACKGROUND_TRUNK_MIDDLE, WeihnachtsElfen.FOREGROUND_EMPTY);
			parts.add(sm);
			return;
		}

		StammLinks sl=new StammLinks(x, y, WeihnachtsElfen.BACKGROUND_TRUNK_LEFT, WeihnachtsElfen.FOREGROUND_EMPTY);
		StammMitte sm;
		parts.add(sl);
		for(int i=0; i<2*breite; i++) {
			sm=new StammMitte(x+i+1, y, WeihnachtsElfen.BACKGROUND_TRUNK_MIDDLE, WeihnachtsElfen.FOREGROUND_EMPTY);
			parts.add(sm);
		}
		StammRechts sr=new StammRechts(x+(2*breite)+1, y, WeihnachtsElfen.BACKGROUND_TRUNK_RIGHT, WeihnachtsElfen.FOREGROUND_EMPTY);
		parts.add(sr);
	}
}
