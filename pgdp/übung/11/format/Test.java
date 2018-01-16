public class Test {
	public static void main(String[] args) {
		FormatVisitor visitor=new FormatVisitor();

		Expression exp = new Binary(new Binary(new Number(99), Binop.Plus, new Number(11)), Binop.Minus, new Binary(new Variable("a"), Binop.Plus, new Number(1)));

		exp.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: 9 + 11 - (a + 1)\n\n");

		visitor.reset();

		exp = new Binary(new Binary(new Number(99), Binop.Plus, new Number(11)), Binop.Plus, new Binary(new Variable("a"), Binop.Plus, new Number(1)));
		exp.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: 9 + 11 + a + 1\n\n");

		visitor.reset();

		exp =  new Binary(new Binary(new Number(99), Binop.Plus, new Number(11)), Binop.Plus, new Binary(new Variable("a"), Binop.MultiplicationOperator, new Number(1)));
		exp.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: 9 + 11 + a * 1\n\n");

		visitor.reset();

		exp = new Binary(new Binary(new Number(99), Binop.MultiplicationOperator, new Number(11)), Binop.MultiplicationOperator, new Binary(new Variable("a"), Binop.Plus, new Number(1)));

		exp.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: 99 * 11 * (a + 1)\n\n");

		visitor.reset();

		exp = new Unary(Unop.Minus, new Binary(new Number(99), Binop.Plus, new Number(11)));
		exp.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: -(99 + 11)\n\n");

		visitor.reset();

		exp = new Unary(Unop.Minus, new Binary(new Number(99), Binop.MultiplicationOperator, new Number(11)));
		exp.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: -99 * 11\n\n");

		visitor.reset();

		exp = new Binary(new Binary(new Number(99), Binop.DivisionOperator, new Number(11)), Binop.DivisionOperator, new Binary(new Variable("a"), Binop.DivisionOperator, new Number(1)));
		exp.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: 99 / 11 / (a / 1)\n\n");

		visitor.reset();

		exp = new Binary(new Binary(new Number(99), Binop.DivisionOperator, new Call("hugo", new Expression[] {new Number(1), new Variable("b")})), Binop.DivisionOperator, new Binary(new Variable("a"), Binop.DivisionOperator, new Number(1)));
		exp.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: 99 / hugo(1, b) / (a / 1)\n\n");

		visitor.reset();

		exp = new Binary(new Binary(new Number(99), Binop.Plus, new Number(11)), Binop.MultiplicationOperator, new Binary(new Variable("a"), Binop.Plus, new Number(1)));
		exp.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: (9 + 11) * (a + 1)\n\n");

		visitor.reset();

		exp = new Binary(new Binary(new Number(99), Binop.MultiplicationOperator, new Number(11)), Binop.MultiplicationOperator, new Binary(new Variable("a"), Binop.Plus, new Number(1)));
		exp.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: 99 * 11 * (a + 1)\n\n");

		visitor.reset();

		exp = new Binary(new Binary(new Number(99), Binop.Minus, new Number(11)), Binop.MultiplicationOperator, new Binary(new Variable("a"), Binop.MultiplicationOperator, new Number(1)));
		exp.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: (9 - 11) * a * 1\n\n");

		visitor.reset();

		Condition cond;

		cond = new BinaryCondition(new True(), Bbinop.And, new BinaryCondition(new False(), Bbinop.And, new True()));
		cond.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: true && false && true\n\n");

		visitor.reset();

		cond = new BinaryCondition(new True(), Bbinop.And, new BinaryCondition(new False(), Bbinop.Or, new True()));
		cond.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: true && (false || true)\n\n");

		visitor.reset();

		cond = new BinaryCondition(new Comparison(new Number(2), Comp.Greater, new Variable("a")), Bbinop.Or, new BinaryCondition(new False(), Bbinop.And, new True()));
		cond.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: 2 > a || false && true\n\n");

		visitor.reset();

		cond = new UnaryCondition(Bunop.Not, new Comparison(new Number(1), Comp.Equals, new Number(2)));
		cond.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: !(1 == 2)\n\n");

		visitor.reset();

		cond = new UnaryCondition(Bunop.Not, new BinaryCondition(new Comparison(new Number(2), Comp.Greater, new Variable("a")), Bbinop.Or, new BinaryCondition(new False(), Bbinop.And, new True())));
		cond.accept(visitor);
		MiniJava.writeConsole(visitor.getResult() + "\n");
		MiniJava.writeConsole("expected: !(2 > a || false && true)\n\n");

		visitor.reset();
	}
}
