import java.util.ArrayList;
import java.util.Stack;
import java.util.Hashtable;

public class Visitor {
	private ArrayList<Integer> res;
	private Stack<Hashtable<String, Integer>> locals;

	public Visitor() {
		res=new ArrayList<>();
		locals=new Stack<>();
	}

	public int[] getProgram() {
		MiniJava.writeConsole("returning Program\n");

		return res.stream().mapToInt(Integer::intValue).toArray();
	}

	public int findVar(String s) {
		Integer idx=locals.peek().get(s);
		if(idx==null)
			throw new RuntimeException("usage of undeclared variable " + s + "\n");
		return idx;
	}

	public void visit(Visitable v) {
	}

	public void visit(Condition c) {
		MiniJava.writeConsole("visiting Condition\n");
	}

	public void visit(UnaryCondition u) {
		MiniJava.writeConsole("visiting UnaryCondition\n");

		u.getOperand().accept(this);
		switch(u.getOperator()) {
		case Not:
			res.add(21<<16);
			break;
		default:
			throw new RuntimeException("unknown unary boolean operation\n");
		}
	}

	public void visit(BinaryCondition b) {
		MiniJava.writeConsole("visiting BinaryCondition\n");

		b.getLhs().accept(this);
		b.getRhs().accept(this);

		switch(b.getOperator()) {
			case And: res.add(19<<16); break;
			case Or: res.add(20<<16); break;
			default: throw new RuntimeException("unknown binary boolean operation\n");
		}
	}

	public void visit(Comparison c) {
		MiniJava.writeConsole("visiting Comparison\n");
	}

	public void visit(Expression e) {
		MiniJava.writeConsole("visiting Expression\n");
	}

	public void visit(Variable v) {
		MiniJava.writeConsole("visiting Name\n");

		res.add(6<<16|findVar(v.getName())&0xFFFF);
	}

	public void visit(Number n) {
		MiniJava.writeConsole("visiting Number\n");

		res.add(5<<16|n.getValue()&0xFFFF);
	}

	public void visit(Binary b) {
		MiniJava.writeConsole("visiting Binary\n");

		b.getLhs().accept(this);
		b.getRhs().accept(this);

		switch(b.getOperator()) {
			case Minus: res.add(2<<16); break;
			case Plus: res.add(1<<16); break;
			case MultiplicationOperator: res.add(3<<16); break;
			case DivisionOperator: res.add(18<<16); break;
			case Modulo: res.add(4<<16); break;
			default: throw new RuntimeException("unknown binary operator\n");
		}
	}

	public void visit(Unary u) {
		MiniJava.writeConsole("visiting Unary\n");

		u.getOperand().accept(this);

		switch(u.getOperator()) {
		case Minus:
			res.add(5<<16|((-1)&0xFFFF));
			res.add(3<<16);
			break;
		default:
			throw new RuntimeException("unknown unary operator\n");
		}
	}

	public void visit(Declaration d) {
		MiniJava.writeConsole("visiting Declaration\n");

		res.add(17<<16|(d.getNames().length&0xFFFF));
		locals.push(new Hashtable<>());
		for(int i=0; i<d.getNames().length; i++)
			locals.peek().put(d.getNames()[i], i);
	}

	public void visit(Assignment a) {
		MiniJava.writeConsole("visiting Assignment\n");

		a.getExpression().accept(this);
		res.add(7<<16|(findVar(a.getName())&0xFFFF));
	}
}
