public class Comparison extends Condition implements Visitable {
	private Expression lhs;
	private Comp operator;
	private Expression rhs;

	public Comparison(Expression l, Comp o, Expression r) {
		lhs=l;
		operator=o;
		rhs=r;
	}

	public Expression getLhs() {
		return lhs;
	}

	public Comp getOperator() {
		return operator;
	}

	public Expression getRhs() {
		return rhs;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
