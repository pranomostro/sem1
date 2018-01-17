public class Condition implements Visitable {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
