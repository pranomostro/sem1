public class Read extends Statement implements Visitable {
	private String name;

	public Read(String n) {
		name=n;
	}

	public String getName() {
		return name;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
