public class Comparison extends Condition implements Visitable {
	private Expression lhs;
	private Comp operator;
	private Expression rhs;

	public Comparison(Expression l, Comp o, Expression r) {
		lhs=l;
		operator=o;
		rhs=r;
	}

	public int firstLevelPriority() {
		switch(operator) {
			case Equals: return 4;
			case NotEquals: return 4;
			case LessEqual: return 3;
			case Less: return 3;
			case GreaterEqual: return 3;
			case Greater: return 3;
		}
		return -1;
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
