public class CondDeriv {
	private Bbinop b;
	private Condition c;
	private CondDeriv cd;

	public CondDeriv(Bbinop bb, Condition cond, CondDeriv condd) {
		b=bb;
		c=cond;
		cd=condd;
	}

	public Bbinop getBbinop() {
		return b;
	}

	public Condition getCond() {
		return c;
	}

	public CondDeriv getCondDeriv() {
		return cd;
	}
}
