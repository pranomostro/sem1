public class While extends Statement implements Visitable {
	private Condition cond;
	private Statement body;

	public While(Condition c, Statement b) {
		cond=c;
		body=b;
	}

	public Condition getCond() {
		return cond;
	}

	public Statement getBody() {
		return body;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
