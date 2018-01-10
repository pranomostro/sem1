public class ExprDeriv {
	private Binop b;
	private Expression e;
	private ExprDeriv ed;

	public ExprDeriv(Binop bo, Expression expr, ExprDeriv exprd) {
		b=bo;
		e=expr;
		ed=expr;
	}

	public Binop getBinop() {
		return b;
	}

	public Expression getExpr() {
		return e;
	}

	public ExprDeriv getExprDeriv() {
		return ed;
	}
}
