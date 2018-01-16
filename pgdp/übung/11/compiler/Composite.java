public class Composite extends Statement implements Visitable {
	private Statement[] statements;

	public Composite(Statement[] s) {
		statements=s;
	}

	public Statement[] getStatements() {
		return statements;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
