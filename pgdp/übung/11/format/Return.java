public class Return extends Statement implements Visitable {
	private Expression expression;

	public Return(Expression e) {
		expression=e;
	}

	public Expression getExpression() {
		return expression;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
