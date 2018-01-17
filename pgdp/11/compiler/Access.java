public class Access extends Expression {
	private Variable var;
	private Expression index;

	public Access(Variable v, Expression e) {
		var=v;
		index=e;
	}

	public Variable getName() {
		return var;
	}

	public Expression getIndex() {
		return index;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
