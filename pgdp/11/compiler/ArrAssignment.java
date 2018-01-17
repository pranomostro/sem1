public class ArrAssignment extends Statement {
	private String name;
	private Expression index;
	private Expression value;

	public ArrAssignment(String s, Expression i, Expression v) {
		name=s;
		index=i;
		value=v;
	}

	public String getName() {
		return name;
	}

	public Expression getIndex() {
		return index;
	}

	public Expression getValue() {
		return value;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
