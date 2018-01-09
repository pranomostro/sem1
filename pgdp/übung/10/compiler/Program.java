public class Program implements Visitable {
	private Function[] functions;

	public Program(Function[] f) {
		functions=f;
	}

	public Function[] getFunctions() {
		return functions;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
