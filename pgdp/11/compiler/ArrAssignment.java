public class ArrAssignment extends Statement {
	private Variable var;
	private Expression index;
	private Expression value;

	public ArrAssignment(Variable v, Expression i, Expression r) {
		var=v;
		index=i;
		value=r;
	}

	public Variable getVar() {
		return var;
	}

	public Expression getIndex() {
		return index;
	}

	public Expression getValue() {
		return value;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
