public class Call extends Expression implements Visitable {
	private String functionname;
	private Expression[] arguments;

	public Call(String f, Expression[] a) {
		functionname=f;
		arguments=a;
	}

	public String getFunctionName() {
		return functionname;
	}

	public Expression[] getArguments() {
		return arguments;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
