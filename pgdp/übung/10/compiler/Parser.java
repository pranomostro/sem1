import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.*;

public class Parser {
	private static String[] program;
	private static int from;

	public Parser(String p) {
		program=lex(p);

		for(int i=0; i<program.length; i++)
			MiniJava.writeConsole("" + i + ": " + program[i] + "\n");
	}

	public static String readProgramConsole() {
		@SuppressWarnings("resource")
		Scanner sin=new Scanner(System.in);
		StringBuilder builder=new StringBuilder();
		while(true) {
			String nextLine=sin.nextLine();
			if(nextLine.equals("")) {
				nextLine=sin.nextLine();
				if(nextLine.equals(""))
					break;
			}
			if(nextLine.startsWith("//"))
				continue;
			builder.append(nextLine);
			builder.append('\n');
		}
		return builder.toString();
	}

	public static String[] lex(String program) {
		int j;
		ArrayList<String> tokens=new ArrayList<>();

		for(int i=0; i<program.length();) {
			if(Character.isSpace(program.charAt(i))) {
				i++;
				continue;
			}
			if(program.charAt(i)=='('||program.charAt(i)==')'||
			   program.charAt(i)=='{'||program.charAt(i)=='}'||
			   program.charAt(i)==','||program.charAt(i)=='-'||
			   program.charAt(i)=='+'||program.charAt(i)=='/'||
			   program.charAt(i)=='*'||program.charAt(i)==';'||
			   program.charAt(i)=='%') {
				tokens.add(""+program.charAt(i));
				i++;
				continue;
			}
			if(program.charAt(i)=='='||program.charAt(i)=='<'||
			   program.charAt(i)=='>') {
				if(i>=program.length()-1||program.charAt(i+1)!='=') {
					tokens.add(""+program.charAt(i));
					i++;
					continue;
				} else {
					tokens.add(program.substring(i, i+2));
					i+=2;
					continue;
				}
			}
			if(program.charAt(i)=='!') {
				if(i>=program.length()-1||program.charAt(i+1)!='=') {
					tokens.add(""+program.charAt(i));
					i++;
					continue;
				} else {
					tokens.add(program.substring(i, i+2));
					i+=2;
					continue;
				}
			}
			String toend=program.substring(i);
			if(toend.startsWith("&&")||toend.startsWith("||")) {
				tokens.add(program.substring(i, i+2));
				i+=2;
				continue;
			}
			Matcher isname=Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*").matcher(toend);
			if(isname.find()) {
				j=isname.end();
				tokens.add(toend.substring(0, j));
				i+=j;
				continue;
			}
			Matcher isnum=Pattern.compile("^[0-9][0-9]*").matcher(toend);
			if(isnum.find()) {
				j=isnum.end();
				tokens.add(toend.substring(0, j));
				i+=j;
				continue;
			}
			MiniJava.writeConsole("Found no matching token.\n");
			MiniJava.writeConsole(program.substring(i, i+6)+"\n");
			System.exit(1);
		}

		String[] res=new String[tokens.size()];
		for(int i=0; i<tokens.size(); i++)
			res[i]=tokens.get(i);
		return res;
	}

	/* ✔ */

	public static Binary expressionize(Expression e, ExprDeriv ed) {
		MiniJava.writeConsole("expressionize called\n");

		if(ed.getExprDeriv()==null)
			return new Binary(e, ed.getBinop(), ed.getExpr());

		return new Binary(e, ed.getBinop(), expressionize(ed.getExpr(), ed.getExprDeriv()));
	}

	/* ✔ */

	public static BinaryCondition conditionize(Condition c, CondDeriv cd) {
		MiniJava.writeConsole("conditionize called\n");

		if(cd.getCondDeriv()==null)
			return new BinaryCondition(c, cd.getBbinop(), cd.getCond());

		return new BinaryCondition(c, cd.getBbinop(), conditionize(cd.getCond(), cd.getCondDeriv()));
	}

	/*
		A function gets: nothing.
		A function returns: a new object or null (if parsing was not possible)
		from: when no parsing was possible, from is not updated. if the parsing
		was possible, the new position is after the parsed tokens.
	*/

	/* ✔ */

	public static Variable parseVar() {
		MiniJava.writeConsole("parseVar called with from: " + from + "\n");

		if(!program[from].matches("[a-zA-Z][a-zA-Z0-9]*"))
			return null;
		Variable v=new Variable(program[from]);
		from++;
		return v;
	}

	/* ✔ */

	public static Number parseNumber() {
		MiniJava.writeConsole("parseNumber called with from: " + from + "\n");
		Number n;

		if(!program[from].matches("[0-9]+"))
			return null;
		try {
			n=new Number(Integer.parseInt(program[from]));
		} catch(Exception e) {
			/* can't be reached, since we arealdy checked whether it's a number representation */
			return null;
		}
		from++;
		return n;
	}

	/* ✔ */

	public static Object parseType() {
		MiniJava.writeConsole("parseType called with from: " + from + "\n");

		if(!program[from].equals("int"))
			return null;
		from++;
		return new Object();
	}

	/* ✔ */

	public static Declaration parseDecl() {
		MiniJava.writeConsole("parseDecl called with from: " + from + "\n");

		int save=from;

		ArrayList<Variable> vars=new ArrayList<>();

		if(parseType()==null) {
			from=save;
			return null;
		}

		if(from>=program.length) {
			from=save;
			return null;
		}

		Variable v=parseVar();
		if(from>=program.length||v==null) {
			from=save;
			return null;
		}

		while(v!=null) {
			vars.add(v);
			if(program[from].equals(";"))
				break;
			if(!program[from].equals(",")) {
				from=save;
				return null;
			}
			from++;
			v=parseVar();
			if(from>=program.length-1) {
				from=save;
				return null;
			}
		}

		if(!program[from].equals(";")) {
			from=save;
			return null;
		}

		from++;

		String[] names=new String[vars.size()];
		for(int i=0; i<vars.size(); i++) {
			names[i]=vars.get(i).getName();
		}

		return new Declaration(names);
	}

	/* ✔ */

	public static Unop parseUnop() {
		MiniJava.writeConsole("parseUnop called with from: " + from + "\n");

		if(!program[from-1].equals("-"))
			return null;
		from++;
		return Unop.Minus;
	}

	/* ✔ */

	public static Binop parseBinop() {
		MiniJava.writeConsole("parseBinop called with from: " + from + "\n");

		from++;

		switch(program[from-1]) {
			case "-": return Binop.Minus;
			case "+": return Binop.Plus;
			case "*": return Binop.MultiplicationOperator;
			case "/": return Binop.DivisionOperator;
			case "%": return Binop.Modulo;
			default: from--; return null;
		}
	}

	/* ✔ */

	public static Comp parseComp() {
		MiniJava.writeConsole("parseComp called with from: " + from + "\n");

		from++;

		switch(program[from-1]) {
			case "==": return Comp.Equals;
			case "!=": return Comp.NotEquals;
			case "<=": return Comp.LessEqual;
			case ">=": return Comp.GreaterEqual;
			case "<": return Comp.Less;
			case ">": return Comp.Greater;
			default: from--; return null;
		}
	}

	/* ✔ */

	public static Expression parseExpr() {
		MiniJava.writeConsole("parseExpr called with from: " + from + "\n");

		int save=from;

		Number n=parseNumber();
		if(n!=null) {
			ExprDeriv ed=parseExprDeriv();

			if(ed==null)
				return n;
			else
				return expressionize(n, ed);
		}

		from=save;

		Variable v=parseVar();
		if(v!=null) {
			if(program[from].equals("(")) {
				MiniJava.writeConsole("found a function call\n");
				from++;
				ArrayList<Expression> args=new ArrayList<>();

				if(from>=program.length) {
					from=save;
					return null;
				}

				if(program[from].equals(")")) {
					from++;
					if(from>=program.length) {
						from=save;
						return null;
					}
					return new Call(v.getName(), new Expression[] {});
				}

				Expression e=parseExpr();

				MiniJava.writeConsole("made it behind the first argument\n");

				while(e!=null) {
					args.add(e);
					if(program[from].equals(")"))
						break;
					if(!program[from].equals(",")) {
						from=save;
						return null;
					}
					from++;

					MiniJava.writeConsole("parsing argument list, at position from: " + from + "\n");

					e=parseExpr();
					if(from>=program.length-1) {
						from=save;
						return null;
					}
				}

				if(from>=program.length-1||!program[from].equals(")")) {
					from=save;
					return null;
				}
				from++;

				MiniJava.writeConsole("finished parsing arguments, from: " + from + "\n");

				Expression[] es=new Expression[args.size()];
				es=args.toArray(es);

				ExprDeriv ed=parseExprDeriv();
				if(ed==null)
					return new Call(v.getName(), es);
				else
					return expressionize(new Call(v.getName(), es), ed);
			}

			MiniJava.writeConsole("didn't find a function call, using variable instead\n");

			ExprDeriv ed=parseExprDeriv();
			if(ed==null) {
				MiniJava.writeConsole("returning the variable itself, after receiving null from parseExprDeriv()\n");
				return v;
			} else {
				MiniJava.writeConsole("calling expressionize\n");
				return expressionize(v, ed);
			}
		}

		from=save;

		if(program[from].equals("(")) {
			from++;
			if(from>=program.length) {
				from=save;
				return null;
			}

			Expression e=parseExpr();
			if(from>=program.length||e==null) {
				from=save;
				return null;
			}

			if(!program[from].equals(")")) {
				from=save;
				return null;
			}

			from++;

			ExprDeriv ed=parseExprDeriv();

			if(ed==null)
				return e;
			else
				return expressionize(e, ed);
		}

		from=save;

		Unop u=parseUnop();
		if(u!=null) {
			if(from>=program.length) {
				from=save;
				return null;
			}

			Expression e=parseExpr();
			if(from>=program.length||e==null) {
				from=save;
				return null;
			}

			ExprDeriv ed=parseExprDeriv();

			if(ed==null)
				return e;
			else
				return expressionize(e, ed);
		}

		return null;
	}

	/* ✔ */

	public static ExprDeriv parseExprDeriv() {
		MiniJava.writeConsole("parseExprDeriv called with from: " + from + "\n");

		int save=from;

		Binop b=parseBinop();
		if(b!=null) {
			if(from>=program.length) {
				from=save;
				return null;
			}

			Expression e=parseExpr();
			if(from>=program.length||e==null) {
				from=save;
				return null;
			}

			ExprDeriv ed=parseExprDeriv();
			if(from>=program.length) {
				from=save;
				return null;
			}

			return new ExprDeriv(b, e, ed);
		}
		MiniJava.writeConsole("no ExprDeriv found\n");
		from=save;
		return null;
	}

	/* ✔ */

	public static Bbinop parseBbinop() {
		MiniJava.writeConsole("parseBbinop called with from: " + from + "\n");

		from++;

		switch(program[from-1]) {
			case "&&": return Bbinop.And;
			case "||": return Bbinop.Or;
			default: from--; return null;
		}
	}

	/* ✔ */

	public static Bunop parseBunop() {
		MiniJava.writeConsole("parseBunop called with from: " + from + "\n");

		from++;

		switch(program[from]) {
			case "!": return Bunop.Not;
			default: from--; return null;
		}
	}

	/* ✔ */

	public static Condition parseCond() {
		MiniJava.writeConsole("parseCond called with from: " + from + "\n");

		int save=from;

		if(program[from].equals("true")) {
			from++;
			if(from>=program.length) {
				from=save;
				return null;
			}

			CondDeriv cd=parseCondDeriv();

			if(cd==null)
				return new True();
			else
				return conditionize(new True(), cd);
		}

		if(program[from].equals("false")) {
			from++;
			if(from>=program.length) {
				from=save;
				return null;
			}

			CondDeriv cd=parseCondDeriv();

			if(cd==null)
				return new False();
			else
				return conditionize(new False(), cd);
		}

		if(program[from].equals("(")) {
			from++;
			if(from>=program.length) {
				from=save;
				return null;
			}

			Condition c=parseCond();
			if(from>=program.length||c==null) {
				from=save;
				return null;
			}

			if(!program[from].equals(")")) {
				from=save;
				return null;
			}

			from++;

			CondDeriv cd=parseCondDeriv();

			if(cd==null)
				return c;
			else
				return conditionize(c, cd);
		}

		Expression e=parseExpr();
		if(e!=null) {
			if(from>=program.length) {
				from=save;
				return null;
			}

			Comp c=parseComp();
			if(from>=program.length||c==null) {				from=save;
				return null;
			}

			Expression e1=parseExpr();
			if(from>=program.length||e1==null) {
				from=save;
				return null;
			}

			CondDeriv cd=parseCondDeriv();
			if(cd==null)
				return new Comparison(e, c, e1);
			else
				return conditionize(new Comparison(e, c, e1), cd);
		}

		from=save;

		Bunop b=parseBunop();
		if(b!=null) {
			if(from>=program.length) {
				from=save;
				return null;
			}

			if(!program[from].equals("(")) {
				from=save;
				return null;
			}

			from++;

			Condition c=parseCond();
			if(from>=program.length||c==null) {
				from=save;
				return null;
			}

			if(!program[from].equals(")")) {
				from=save;
				return null;
			}

			from++;

			CondDeriv cd=parseCondDeriv();
			if(cd==null)
				return new UnaryCondition(b, c);
			else
				return new UnaryCondition(b, conditionize(c, cd));
		}

		from=save;
		return null;
	}

	/* ✔ */

	public static CondDeriv parseCondDeriv() {
		MiniJava.writeConsole("parseCondDeriv called with from: " + from + "\n");

		int save=from;

		Bbinop b=parseBbinop();
		if(b!=null) {
			if(from>=program.length) {
				from=save;
				return null;
			}

			Condition c=parseCond();
			if(from>=program.length||c==null) {
				from=save;
				return null;
			}

			CondDeriv cd=parseCondDeriv();
			if(from>=program.length) {
				from=save;
				return null;
			}

			return new CondDeriv(b, c, cd);
		}
		from=save;
		return null;
	}

	/* ✔ */

	public static Statement parseStmt() {
		MiniJava.writeConsole("parseStmt called with from: " + from + "\n");

		int save=from;

		if(program[from].equals(";")) {
			from++;
			return new Statement();
		}

		if(program[from].equals("{")) {
			ArrayList<Statement> stmts=new ArrayList<>();

			from++;
			if(from>=program.length) {
				from=save;
				return null;
			}

			Statement s=parseStmt();
			if(from>=program.length) {
				from=save;
				return null;
			}

			while(s!=null) {
				stmts.add(s);
				s=parseStmt();
				if(from>=program.length) {
					from=save;
					return null;
				}
			}

			if(!program[from].equals("}")) {
				from=save;
				return null;
			}
			from++;

			Statement[] st=new Statement[stmts.size()];
			return new Composite(stmts.toArray(st));
		}

		if(program[from].equals("write")) {
			from++;
			if(from>=program.length||!program[from].equals("(")) {
				from=save;
				return null;
			}

			from++;

			if(from>=program.length) {
				from=save;
				return null;
			}

			Expression e=parseExpr();
			if(from>=program.length||e==null) {
				from=save;
				return null;
			}

			if(!program[from].equals(")")) {
				from=save;
				return null;
			}
			from++;
			return new Write(e);
		}

		if(program[from].equals("return")) {
			from++;
			if(from>=program.length) {
				from=save;
				return null;
			}

			Expression e=parseExpr();
			if(from>=program.length||e==null) {
				from=save;
				return null;
			}

			if(!program[from].equals(";")) {
				from=save;
				return null;
			}
			from++;
			return new Return(e);
		}

		if(program[from].equals("while")) {
			from++;
			if(from>=program.length||!program[from].equals("(")) {
				from=save;
				return null;
			}

			from++;

			if(from>=program.length) {
				from=save;
				return null;
			}

			Condition c=parseCond();
			if(from>=program.length||c==null) {
				from=save;
				return null;
			}

			if(!program[from].equals(")")) {
				from=save;
				return null;
			}
			from++;
			if(from>=program.length) {
				from=save;
				return null;
			}

			Statement s=parseStmt();
			if(from>=program.length||s==null) {
				from=save;
				return null;
			}

			return new While(c, s);
		}

		if(program[from].equals("if")) {
			from++;
			if(from>=program.length||!program[from].equals("(")) {
				from=save;
				return null;
			}

			from++;

			if(from>=program.length) {
				from=save;
				return null;
			}

			Condition c=parseCond();
			if(from>=program.length||c==null) {
				from=save;
				return null;
			}

			if(!program[from].equals(")")) {
				from=save;
				return null;
			}
			from++;
			if(from>=program.length) {
				from=save;
				return null;
			}

			Statement s=parseStmt();
			if(from>=program.length||s==null) {
				from=save;
				return null;
			}

			if(program[from].equals("else")) {
				from++;
				if(from>=program.length) {
					from=save;
					return null;
				}

				Statement e=parseStmt();
				if(from>=program.length||s==null) {
					from=save;
					return null;
				}
				return new IfThenElse(c, s, e);
			} else
				return new IfThen(c, s);
		}

		Variable v=parseVar();
		if(v!=null) {
			if(from>=program.length||!program[from].equals("=")) {
				from=save;
				return null;
			}

			from++;
			if(from>=program.length) {
				from=save;
				return null;
			}

			if(program[from].equals("read")) {
				from++;
				if(from>=program.length||!program[from].equals("(")) {
					from=save;
					return null;
				}
				from++;
				if(from>=program.length||!program[from].equals(")")) {
					from=save;
					return null;
				}
				from++;
				if(from>=program.length||!program[from].equals(";")) {
					from=save;
					return null;
				}
				return new Read(v.getName());
			} else {
				Expression e=parseExpr();
				if(from>=program.length||e==null) {
					from=save;
					return null;
				}
				if(from>=program.length||!program[from].equals(";")) {
					from=save;
					return null;
				}
				from++;
				return new Assignment(v.getName(), e);
			}
		}

		return null;
	}

	public static String[] parseParams() {
		MiniJava.writeConsole("parseParams called with from: " + from + "\n");

		ArrayList<String> params=new ArrayList<>();

		if(program[from].equals(")"))
			return new String[] {};

		int save=from;

		Object o=parseType();

		if(from>=program.length||o==null) {
			from=save;
			return null;
		}

		Variable v=parseVar();

		if(from>=program.length) {
			from=save;
			return null;
		}

		while(v!=null) {
			params.add(v.getName());
			if(program[from].equals(")"))
				break;
			if(!program[from].equals(",")) {
				from=save;
				return null;
			}
			from++;

			o=parseType();
			if(from>=program.length) {
				from=save;
				return null;
			}
			if(o==null)
				break;

			v=parseVar();
			if(from>=program.length) {
				from=save;
				return null;
			}
		}

		String[] ps=new String[params.size()];
		ps=params.toArray(ps);
		return ps;
	}

	/* ✔ */

	public static Function parseFunction() {
		MiniJava.writeConsole("parseFunction called with from: " + from + "\n");

		ArrayList<Declaration> decls=new ArrayList<>();
		ArrayList<Statement> stmts=new ArrayList<>();
		String[] ps;

		int save=from;

		Object o=parseType();
		if(from>=program.length||o==null) {
			from=save;
			return null;
		}

		Variable v=parseVar();
		if(from>=program.length||v==null||!program[from].equals("(")) {
			from=save;
			return null;
		}
		from++;

		if(from>=program.length) {
			from=save;
			return null;
		}

		ps=parseParams();

		if(from>=program.length||ps==null) {
			from=save;
			return null;
		}

		if(from>=program.length-2||!program[from].equals(")")&&!program[from+1].equals("{")) {
			from=save;
			return null;
		}

		from+=2;

		Declaration d=parseDecl();
		if(from>=program.length) {
			from=save;
			return null;
		}

		while(d!=null) {
			decls.add(d);
			d=parseDecl();
			if(from>=program.length) {
				from=save;
				return null;
			}
		}

		Statement s=parseStmt();
		if(from>=program.length) {
			from=save;
			return null;
		}

		while(s!=null) {
			stmts.add(s);
			s=parseStmt();
			if(from>=program.length) {
				from=save;
				return null;
			}
		}

		if(!program[from].equals("}")) {
			from=save;
			return null;
		}

		from++;

		Declaration[] ds=new Declaration[decls.size()];
		ds=decls.toArray(ds);
		Statement[] ss=new Statement[stmts.size()];
		ss=stmts.toArray(ss);

		return new Function(v.getName(), ps, ds, ss);
	}

	/* ✔ */

	public static Program parse() {
		ArrayList<Function> funcs=new ArrayList<>();

		from=0;

		Function f=parseFunction();

		if(from>=program.length) {
			funcs.add(f);
			f=null;
		}

		while(f!=null) {
			funcs.add(f);
			if(from>=program.length)
				break;
			f=parseFunction();
		}

		if(from<program.length) {
			MiniJava.writeConsole("program was not completely parsed\n");
			return null;
		}

		Function[] fs=new Function[funcs.size()];
		fs=funcs.toArray(fs);

		return new Program(fs);
	}
}
