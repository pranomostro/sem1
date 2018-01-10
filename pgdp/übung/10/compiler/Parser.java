import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.*;

public class Parser {
	private String[] program;
	private int from;

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

	public static Expression expressionize(Expression e, ExprDeriv ed) {
		if(ed.getExprDeriv()==null)
			return new Binary(e, ed.getBinop()(), ed.getExpr());

		return new Binary(e, ed.getBinop(), expressionize(ed.getExpr(), ed.getExprDeriv());
	}

	/* ✔ */

	public static Condition conditionize(Condition c, CondDeriv cd) {
		if(cd.getCondDeriv()==null)
			return new Comparison(c, cd.getBbinop(), cd.getCond());

		return new Comparison(c, cd.getBbinop(), conditionize(cd.getCond(), cd.getCondDeriv());
	}

	/*
		A function gets: nothing.
		A function returns: a new object or null (if parsing was not possible)
		from: when no parsing was possible, from is not updated. if the parsing
		was possible, the new position is after the parsed tokens.
	*/

	/* ✔ */

	public static Variable parseName() {
		MiniJava.writeConsole("parseName called with from: " + from + "\n");

		if(!program[from].matches("[a-zA-Z][a-zA-Z0-9]*"))
			return null;
		Variable v=new Variable(program[from]);
		from++;
		return v;
	}

	/* ✔ */

	public static Number parseNumber(String[] program, int from) {
		MiniJava.writeConsole("parseNumber called with from: " + from + "\n");

		if(!program[from].matches("[0-9]+"))
			return null;
		try {
			Number n=new Number(Integer.parseInt(program[from]);
		} catch(Exception e) {
			/* can't be reached, since we arealdy checked whether it's a number representation */
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

		Variable v=parseName();

		if(from>=program.length||v==null) {
			from=save;
			return null;
		}

		while(program[from].equals(",")) {
			vars.add(v);
			from++;

			if(from>=program.length) {
				from=save;
				return null;
			}

			v=parseName();

			if(from>=program.length||v==null) {
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
		for(int i=0; i<vars.size(); i++)
			names[i]=vars.get(i).getName();

		return new Declaration(names);
	}

	/* ✔ */

	public static Unop parseUnop() {
		MiniJava.writeConsole("parseUnop called with from: " + from + "\n");

		if(!program[from].equals("-"))
			return null;
		from++;
		return Unop.Minus;
	}

	/* ✔ */

	public static Binop parseBinop() {
		MiniJava.writeConsole("parseBinop called with from: " + from + "\n");

		from++;

		switch(program[from]) {
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

		switch(program[from]) {
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

		Name nm=parseName(program, from);

		if(nm!=null) {
			ExpressionDeriv ed=parseExprDeriv();

			if(ed==null)
				return nm;
			else
				return expressionize(nm, ed);
		}

		from=save;

		if(program[from].equals("(")) {
			from++;
			if(from>=program.length) {
				from=save;
				return null;
			}

			Expr e=parseExpr();
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
		from=save;
		return null;
	}

	/* ✔ */

	public static Bbinop parseBbinop() {
		MiniJava.writeConsole("parseBbinop called with from: " + from + "\n");

		from++;

		switch(program[from]) {
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

			if(from>=program.length||c==null) {
				from=save;
				return null;
			}

			Expression e1=parseExpr();
			if(from>=program.length||e1==null) {
				from=save;
				return null;
			}

			CondDeriv cd=parseCondDeriv();
			if(cd==null)
				return new Comparison(e, comp, e1);
			else
				return conditionize(new Condition(e, c, e1), cd);
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

	public static int parseStmt(String[] program, int from) {
		MiniJava.writeConsole("parseStmt called with from: " + from + "\n");

		int np;

		if(program[from].equals(";")) {
			return from+1;
		}

		if(program[from].equals("{")) {
			from++;
			if(from>=program.length)
				return -1;

			np=parseStmt(program, from);
			if(np>=program.length)
				return -1;

			while(np!=-1) {
				from=np;
				np=parseStmt(program, from);
				if(np>=program.length)
					return -1;
			}
			if(!program[from].equals("}"))
				return -1;

			return from+1;
		}

		if(program[from].equals("write")) {
			from++;
			if(from>=program.length)
				return -1;

			if(!program[from].equals("("))
				return -1;
			from++;
			if(from>=program.length)
				return -1;

			np=parseExpr(program, from);
			if(np<0||np>=program.length)
				return -1;
			from=np;

			if(!program[from].equals(")"))
				return -1;

			return from+1;
		}

		if(program[from].equals("while")) {
			from++;
			if(from>=program.length)
				return -1;

			if(!program[from].equals("("))
				return -1;
			from++;
			if(from>=program.length)
				return -1;

			np=parseCond(program, from);
			if(np<0||np>=program.length)
				return -1;
			from=np;

			if(!program[from].equals(")"))
				return -1;
			from++;
			if(from>=program.length)
				return -1;

			np=parseStmt(program, from);
			if(np<0)
				return -1;

			return np;
		}

		if(program[from].equals("if")) {
			from++;
			if(from>=program.length)
				return -1;

			if(!program[from].equals("("))
				return -1;
			from++;
			if(from>=program.length)
				return -1;

			np=parseCond(program, from);
			if(np<0||np>=program.length)
				return -1;
			from=np;

			if(!program[from].equals(")"))
				return -1;
			from++;
			if(from>=program.length)
				return -1;

			np=parseStmt(program, from);
			if(np<0)
				return -1;
			from=np;

			if(program[from].equals("else")) {
				from++;
				if(np>=program.length)
					return -1;

				np=parseStmt(program, from);
				if(np>=0)
					from=np;
			}

			return from;
		}

		np=parseName(program, from);
		if(np>=0) {
			if(np>=program.length)
				return -1;
			from=np;

			if(!program[from].equals("="))
				return -1;
			from++;
			if(from>=program.length)
				return -1;

			if(program[from].equals("read")) {
				from++;
				if(from>=program.length)
					return -1;

				if(!program[from].equals("("))
					return -1;
				from++;
				if(from>=program.length)
					return -1;

				if(!program[from].equals(")"))
					return -1;
				from++;
				if(from>=program.length)
					return -1;
			} else {
				np=parseExpr(program, from);
				if(np<0||np>=program.length)
					return -1;
				from=np;
			}

			if(!program[from].equals(";"))
				return -1;
			from++;

			return from;
		}

		return -1;
	}

	/* ✔ */

	public static Program parseProgram() {
		ArrayList<Declaration> decls=new ArrayList<>();
		ArrayList<Statement> stmts=new ArrayList<>();

		Declaration d=parseDecl();

		if(from>=program.length)
			return 0;

		while(d!=null) {
			decls.add(d);
			d=parseDecl(program, pos);
			if(from>=program.length)
				return 0;
		}

		Statement s=parseStmt();

		if(from>=program.length)
			return 0;

		while(s!=null) {
			stmts.add(s);
			s=parseStmt();
			if(from>=program.length)
				return 0;
		}

		Declaration[] d=new Declaration[decls.size()];
		d=decls.toArray(d);
		Statement[] s=new Statement[stmts.size()];
		s=stmts.toArray(s);

		return new Program(d, s);
	}

	public static void main(String[] args) {
		MiniJava.writeConsole("Please enter a MiniJava program.\n");
		program=lex(readProgramConsole());
		from=0;
		if(parse())!=null)
			MiniJava.writeConsole("program is syntactically valid\n");
		else
			MiniJava.writeConsole("program is syntactically invalid\n");
	}
}
