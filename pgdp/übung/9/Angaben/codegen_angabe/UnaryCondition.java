public class UnaryCondition extends Condition implements Visitable {
	private Bunop operator;
	private Condition operand;

	public UnaryCondition(Bunop o, Condition c) {
		operator=o;
		operand=c;
	}

	public Bunop getOperator() {
		return operator;
	}

	public Condition getOperand() {
		return operand;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
