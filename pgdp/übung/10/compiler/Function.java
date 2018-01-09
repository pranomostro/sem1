public class Function implements Visitable {
	private String name;
	private String[] parameters;
	private Declaration[] declarations;
	private Statement[] statements;

	public Function(String n, String[] p, Declaration[] d, Statement[] s) {
		name=n;
		parameters=p;
		declarations=d;
		statements=s;
	}

	public String getName() {
		return name;
	}

	public String[] getParameters() {
		return parameters;
	}

	public Declaration[] getDeclarations() {
		return declarations;
	}

	public Statement[] getStatements() {
		return statements;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
