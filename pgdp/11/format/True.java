public class True extends Condition implements Visitable {
	public void accept(Visitor v) {
		v.visit(this);
	}

	public String toString() {
		return "true";
	}
}
