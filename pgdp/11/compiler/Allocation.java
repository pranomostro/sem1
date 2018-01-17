public class Allocation extends Expression {
	private Expression size;

	public Allocation(Expression s) {
		size=s;
	}

	public Expression getSize() {
		return size;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
