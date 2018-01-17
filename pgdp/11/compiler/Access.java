public class Access extends Expression {
	private String name;
	private Expression index;

	public Access(String s, Expression e) {
		name=s;
		index=e;
	}

	public String getName() {
		return name;
	}

	public Expression getIndex() {
		return index;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
