public class Number extends Expression implements Visitable {
	private int value;

	public Number(int v) {
		value=v;
	}

	public int getValue() {
		return value;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public String toString() {
		return ("" + value);
	}
}
