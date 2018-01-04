public class Ast extends MultiObject{
	public Ast(int x, int y, int breite) {
		super(x, y, breite);

		AstLinks al=new AstLinks(x, y, WeihnachtsElfen.BACKGROUND_GREEN_LEFT, WeihnachtsElfen.FOREGROUND_EMPTY);
		AstMitte am;
		parts.add(al);
		for(int i=0; i<2*breite; i++) {
			am=new AstMitte(x+i+1, y, WeihnachtsElfen.BACKGROUND_GREEN_MIDDLE, WeihnachtsElfen.FOREGROUND_EMPTY);
			parts.add(am);
		}
		AstRechts ar=new AstRechts(x+(2*breite)+1, y, WeihnachtsElfen.BACKGROUND_GREEN_RIGHT, WeihnachtsElfen.FOREGROUND_EMPTY);
		parts.add(ar);
	}
}
