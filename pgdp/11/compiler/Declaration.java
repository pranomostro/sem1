public class Declaration implements Visitable {
	private Type type;
	private String[] names;

	public Declaration(Type t, String[] n) {
		type=t;
		names=n;
	}

	public Type getType() {
		return type;
	}

	public String[] getNames() {
		return names;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
