public class MyTest {
	public static void main(String[] args) {
		Visitor c=new Visitor();

		Statement ggtSwap =
			new IfThen(new Comparison(new Variable("b"), Comp.Greater, new Variable("a")),
				new Composite(new Statement[] {new Assignment("temp", new Variable("a")),
					new Assignment("a", new Variable("b")),
					new Assignment("b", new Variable("temp")),}));
		Statement ggtWhile = new While(new Comparison(new Variable("b"), Comp.NotEquals, new Number(0)),
			new Composite(new Statement[] {new Assignment("temp", new Variable("b")),
				new Assignment("b", new Binary(new Variable("a"), Binop.Modulo, new Variable("b"))),
				new Assignment("a", new Variable("temp"))}));
		Function ggt = new Function("ggt", new String[] {"a", "b"},
			new Declaration[] {new Declaration(new String[] {"temp"})},
			new Statement[] {ggtSwap, ggtWhile, new Return(new Variable("a"))});
		Function mainFunctionGgt =
			new Function("main", new String[] {}, new Declaration[] {}, new Statement[] {
				new Return(new Call("ggt", new Expression[] {new Number(4514), new Number(3528)}))});
		Program ggtProgram = new Program(new Function[] {mainFunctionGgt, ggt});

		ggtProgram.accept(c);

		int[] p=c.getProgram();

		MiniJava.writeConsole(Interpreter.programToString(p) + "\n");
		MiniJava.writeConsole(Interpreter.execute(p) + "\n");
	}
}
