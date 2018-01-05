public class Assignment extends Statement implements Visitable {
	private String name;
	private Expression expression;

	public Assignment(String n, Expression e) {
		name=n;
		expression=e;
	}

	public String getName() {
		return name;
	}

	public Expression getExpression() {
		return expression;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
