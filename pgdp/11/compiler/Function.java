public class Function implements Visitable {
	private Type rettype;
	private String name;
	private Type[] paramtypes;
	private String[] parameters;
	private Declaration[] declarations;
	private Statement[] statements;

	public Function(Type rt, String n, Type[] pt, String[] p, Declaration[] d, Statement[] s) {
		rettype=rt;
		name=n;
		paramtypes=pt;
		parameters=p;
		declarations=d;
		statements=s;
	}

	public Type getReturnType() {
		return rettype;
	}

	public String getName() {
		return name;
	}

	public Type[] getParamTypes() {
		return paramtypes;
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
