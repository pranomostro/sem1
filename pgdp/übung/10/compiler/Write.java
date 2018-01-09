public class Write extends Statement implements Visitable {
	private Expression expression;

	public Write(Expression e) {
		expression=e;
	}

	public Expression getExpression() {
		return expression;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
