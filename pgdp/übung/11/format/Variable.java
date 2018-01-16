public class Variable extends Expression implements Visitable {
	private String name;

	public Variable(String n) {
		name=n;
	}

	public String getName() {
		return name;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public String toString() {
		return name;
	}
}
