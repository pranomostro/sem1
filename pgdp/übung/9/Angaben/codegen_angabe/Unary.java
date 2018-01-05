public class Unary extends Expression implements Visitable {
	private Unop operator;
	private Expression operand;

	public Unary(Unop o, Expression e) {
		operator=o;
		operand=e;
	}

	public Unop getOperator() {
		return operator;
	}

	public Expression getOperand() {
		return operand;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
