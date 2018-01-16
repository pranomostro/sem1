public class Expression implements Visitable {
	public void accept(Visitor v) {
		v.visit(this);
	}

	public int firstLevelPriority() {
		return 65536;
	}
}
