public class BinaryCondition extends Condition implements Visitable {
	private Condition lhs;
	private Bbinop operator;
	private Condition rhs;

	public BinaryCondition(Condition l, Bbinop o, Condition r) {
		lhs=l;
		operator=o;
		rhs=r;
	}

	public int firstLevelPriority() {
		switch(operator) {
			case And: return 2;
			case Or: return 1;
		}
		return -1;
	}

	public Condition getLhs() {
		return lhs;
	}

	public Bbinop getOperator() {
		return operator;
	}

	public Condition getRhs() {
		return rhs;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
