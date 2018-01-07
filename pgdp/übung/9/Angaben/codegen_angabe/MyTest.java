public class MyTest {
	/* Possible TODO: Fix usage of negative numbers */
	/* TODO: add Call, Return and Function */

	public static void main(String[] args) {
		Visitor c=new Visitor();
		/*
		function f(a, b)
			return a+b
		end
		*/
		Function f=new Function("add", new String[] {"a", "b"}, new Declaration[] {}, new Statement[]{ new Return(new Binary(new Variable("a"), Binop.Plus, new Variable("b"))) });
		f.accept(c);
		Call ca=new Call("perf", new Expression[] { new Number(5), new Number(10) });
		ca.accept(c);
		int[] p=c.getProgram();
		MiniJava.writeConsole(Interpreter.programToString(p) + "\n");
	}
}
