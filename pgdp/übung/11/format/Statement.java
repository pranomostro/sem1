public class Statement implements Visitable {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
