public class Expression implements Visitable {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
