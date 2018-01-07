import java.util.ArrayList;
import java.util.Stack;
import java.util.Hashtable;

public class Visitor {
	private ArrayList<Integer> res;
	private Stack<Hashtable<String, Integer>> locals;
	private Hashtable<String, Integer> functions;
	private Hashtable<Integer, String> calls;

	public Visitor() {
		res=new ArrayList<>();
		locals=new Stack<>();
		functions=new Hashtable<>();
		calls=new Hashtable<>();
	}

	public int[] getProgram() {
		MiniJava.writeConsole("returning Program\n");

		for(Integer i: calls.keySet()) {
			String s=calls.get(i);
			Integer fl=functions.get(s);
			if(fl==null)
				throw new RuntimeException("unknown function " + s + "\n");
			res.set(i, 5<<16|((int)fl)&0xFFFF);
		}

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

	public void visit(Statement s) {
		MiniJava.writeConsole("visiting Statement\n");
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

		c.getLhs().accept(this);
		c.getRhs().accept(this);

		switch(c.getOperator()) {
			case Equals: res.add(9<<16); break;
			case NotEquals: res.add(9<<16); res.add(21<<16); break;
			case LessEqual: res.add(11<<21); break;
			case Less: res.add(10<<16); break;
			case GreaterEqual: res.add(10<<16); res.add(21<<16); break;
			case Greater: res.add(11<<16); res.add(21<<16); break;
			default: throw new RuntimeException("unknown comparison\n");
		}
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
		for(int i=0; i<d.getNames().length; i++) {
			if(locals.peek().get(i)!=null)
				locals.peek().put(d.getNames()[i], locals.peek().size());
			else
				locals.peek().put(d.getNames()[i], i);
		}
	}

	public void visit(Assignment a) {
		MiniJava.writeConsole("visiting Assignment\n");

		a.getExpression().accept(this);
		res.add(7<<16|(findVar(a.getName())&0xFFFF));
	}

	public void visit(IfThen i) {
		MiniJava.writeConsole("visiting IfThen\n");

		i.getCondition().accept(this);
		res.add(21<<16);
		int jp=res.size();
		res.add(8<<16);
		i.getThenBranch().accept(this);
		res.set(jp, 8<<16|res.size()&0xFFFF);
	}

	/*
		CMP
		NOT
		JUMP else
		#then
		LDI -1
		JUMP end
		else:
		#else
		end:
	*/

	public void visit(IfThenElse i) {
		MiniJava.writeConsole("visiting IfThenElse\n");

		i.getCondition().accept(this);
		res.add(21<<16);
		int jp1=res.size();
		res.add(8<<16);

		i.getThenBranch().accept(this);
		res.add(5<<16|((-1)&0xFFFF));
		int jp2=res.size();
		res.add(8<<16);

		res.set(jp1, 8<<16|(res.size()&0xFFFF));

		i.getElseBranch().accept(this);

		res.set(jp2, 8<<16|(res.size()&0xFFFF));
	}

	public void visit(Composite c) {
		MiniJava.writeConsole("visiting Composite\n");

		for(Statement s: c.getStatements())
			s.accept(this);
	}

	public void visit(Read r) {
		MiniJava.writeConsole("visiting Read\n");

		res.add(12<<16);
		res.add(7<<16|findVar(r.getName())&0xFFFF);
	}

	public void visit(Write w) {
		MiniJava.writeConsole("visiting Write\n");

		w.getExpression().accept(this);
		res.add(13<<16);
	}

	/*
		begin:
		CMP
		NOT
		JUMP end
		#body
		LDI -1
		JUMP begin
		end:
	*/

	public void visit(While w) {
		MiniJava.writeConsole("visiting While\n");

		int begin=res.size();
		w.getCond().accept(this);
		res.add(21<<16);
		int jp=res.size();
		res.add(8<<16);
		w.getBody().accept(this);
		res.add(5<<16|((-1)&0xFFFF));
		res.add(8<<16|(begin&0xFFFF));
		res.set(jp, 8<<16|(res.size()&0xFFFF));
	}

	public void visit(True t) {
		MiniJava.writeConsole("visiting True\n");

		res.add(5<<16|((-1)&0xFFFF));
	}

	public void visit(False f) {
		MiniJava.writeConsole("visiting False\n");

		res.add(5<<16|(0&0xFFFF));
	}

	public void visit(Function f) {
		MiniJava.writeConsole("visiting Function\n");

		locals.push(new Hashtable<>());

		functions.put(f.getName(), res.size());
		for(int i=0; i<f.getParameters().length; i++)
			locals.peek().put(f.getParameters()[i], (-i)-1);

		for(Declaration d: f.getDeclarations())
			d.accept(this);

		for(Statement s: f.getStatements())
			s.accept(this);
	}

	public void visit(Call c) {
		MiniJava.writeConsole("visiting Call\n");

		for(Expression e: c.getArguments())
			e.accept(this);

		calls.put(res.size(), c.getFunctionName());
		res.add(5<<16);
		res.add(14<<16|c.getArguments().length&0xFFFF);
	}

	public void visit(Return r) {
		MiniJava.writeConsole("visiting Return\n");

		int l=locals.peek().size();
		r.getExpression().accept(this);
		res.add(15<<16|l&0xFFFF);

		locals.pop();
	}
}
