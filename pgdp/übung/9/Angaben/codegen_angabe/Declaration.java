public class Declaration implements Visitable {
	private String[] names;

	public Declaration(String[] n) {
		names=n;
	}

	public String[] getNames() {
		return names;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
