public class IfThenElse extends Statement implements Visitable {
	private Condition condition;
	private Statement thenbranch;
	private Statement elsebranch;

	public IfThenElse(Condition c, Statement t, Statement e) {
		condition=c;
		thenbranch=t;
		elsebranch=e;
	}

	public Condition getCondition() {
		return condition;
	}

	public Statement getThenBranch() {
		return thenbranch;
	}

	public Statement getElseBranch() {
		return elsebranch;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
