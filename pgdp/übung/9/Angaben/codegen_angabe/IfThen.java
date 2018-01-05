public class IfThen extends Statement implements Visitable {
	private Condition condition;
	private Statement thenbranch;

	public IfThen(Condition c, Statement t) {
		condition=c;
		thenbranch=t;
	}

	public Condition getCondition() {
		return condition;
	}

	public Statement getThenBranch() {
		return thenbranch;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
