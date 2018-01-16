public class Binary extends Expression implements Visitable {
	private Expression lhs;
	private Binop operator;
	private Expression rhs;

	public Binary(Expression l, Binop o, Expression r) {
		lhs=l;
		rhs=r;
		operator=o;
	}

	public int firstLevelPriority() {
		switch(operator) {
			case Minus: return 5;
			case Plus: return 5;
			case MultiplicationOperator: return 6;
			case DivisionOperator: return 6;
			case Modulo: return 6;
		}
		return -1;
	}

	public Expression getLhs() {
		return lhs;
	}

	public Binop getOperator() {
		return operator;
	}

	public Expression getRhs() {
		return rhs;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
