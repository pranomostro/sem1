public class MyTest {
	/* Possible TODO: Fix usage of negative numbers */
	/* Possible TODO: Use stack of hashtables for locals (for recursive calls) */

	public static void main(String[] args) {
		Visitor c=new Visitor();
		Declaration d=new Declaration(new String[]{"a", "b"});
		d.accept(c);
		Assignment a=new Assignment("a", new Binary(new Number(42), Binop.Plus, new Number(-5)));
		a.accept(c);
		Binary b=new Binary(new Variable("a"), Binop.Modulo, new Binary(new Number(15), Binop.Minus, new Number(7)));
		b.accept(c);
		int[] p=c.getProgram();
		MiniJava.writeConsole(Interpreter.programToString(p) + "\n");
	}
}
