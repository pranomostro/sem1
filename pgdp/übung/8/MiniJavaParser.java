import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.*;

public class MiniJavaParser {
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

	/* A function gets: program and from,
		from points to the position in the program that
		should be parsed. from can never be outside the program.
	A function returns: the position in the program after the last
		parsed position, which can be after the last element
		in the token array.
	*/

	/* ✔ */

	public static int parseName(String[] program, int from) {
		//MiniJava.writeConsole("parseName called with from: " + from + "\n");
		if(!program[from].matches("[a-zA-Z][a-zA-Z0-9]*"))
			return -1;
		return from+1;
	}

	/* ✔ */

	public static int parseNumber(String[] program, int from) {
		//MiniJava.writeConsole("parseNumber called with from: " + from + "\n");
		if(!program[from].matches("[0-9]+"))
			return -1;
		return from+1;
	}

	/* ✔ */

	public static int parseType(String[] program, int from) {
		//MiniJava.writeConsole("parseType called with from: " + from + "\n");

		if(!program[from].equals("int"))
			return -1;
		return from+1;
	}

	/* ✔ */

	public static int parseDecl(String[] program, int from) {
		//MiniJava.writeConsole("parseDecl called with from: " + from + "\n");

		int np=parseType(program, from);
		if(np>=program.length||np<0)
			return -1;
		from=np;

		np=parseName(program, from);
		if(np>=program.length||np<0)
			return -1;

		from=np;

		while(program[from].equals(",")) {
			from++;
			if(from>=program.length)
				return -1;

			np=parseName(program, from);
			if(np>=program.length||np<0)
				return -1;
			from=np;
		}

		if(!program[from].equals(";"))
			return -1;

		return from+1;
	}

	/* ✔ */

	public static int parseUnop(String[] program, int from) {
		//MiniJava.writeConsole("parseUnop called with from: " + from + "\n");

		if(!program[from].equals("-"))
			return -1;
		return from+1;
	}

	/* ✔ */

	public static int parseBinop(String[] program, int from) {
		//MiniJava.writeConsole("parseBinop called with from: " + from + "\n");

		if(!program[from].equals("-")&&!program[from].equals("+")&&
		   !program[from].equals("*")&&!program[from].equals("/")&&
		   !program[from].equals("%"))
			return -1;
		return from+1;
	}

	/* ✔ */

	public static int parseComp(String[] program, int from) {
		//MiniJava.writeConsole("parseComp called with from: " + from + "\n");

		if(!program[from].equals("==")&&!program[from].equals("!=")&&
		   !program[from].equals("<=")&&!program[from].equals(">=")&&
		   !program[from].equals("<")&&!program[from].equals(">"))
			return -1;
		return from+1;
	}

	/* ✔ */

	public static int parseExpr(String[] program, int from) {
		//MiniJava.writeConsole("parseExpr called with from: " + from + "\n");

		int np;

		np=parseNumber(program, from);
		if(np>=0) {
			if(np>=program.length)
				return -1;
			from=np;
			np=parseExprDeriv(program, from);
			if(np<0||np>=program.length)
				return -1;

			return np;
		}

		np=parseName(program, from);
		if(np>=0) {
			if(np>=program.length)
				return -1;
			from=np;
			np=parseExprDeriv(program, from);
			if(np<0||np>=program.length)
				return -1;

			return np;
		}

		if(program[from].equals("(")) {
			from++;
			if(from>=program.length)
				return -1;

			np=parseExpr(program, from);
			if(np>=program.length||np<0)
				return -1;
			from=np;
			if(!program[from].equals(")"))
				return -1;
			from++;
			np=parseExprDeriv(program, from);
			if(np<0||np>=program.length)
				return -1;

			return np;
		}

		np=parseUnop(program, from);
		if(np>=0) {
			if(np>=program.length)
				return -1;
			from=np;
			np=parseExpr(program, from);
			if(np<0||np>=program.length)
				return -1;
			from=np;
			np=parseExprDeriv(program, from);
			if(np<0||np>=program.length)
				return -1;

			return np;
		}

		return -1;
	}

	/* ✔ */

	public static int parseExprDeriv(String[] program, int from) {
		//MiniJava.writeConsole("parseExprDeriv called with from: " + from + "\n");

		int np;

		np=parseBinop(program, from);
		if(np>=0) {
			if(np>=program.length)
				return -1;
			from=np;

			np=parseExpr(program, from);
			if(np<0||np>=program.length)
				return -1;
			from=np;

			np=parseExprDeriv(program, from);
			if(np<0||np>=program.length)
				return -1;

			return np;
		}
		return from;
	}

	/* ✔ */

	public static int parseBbinop(String[] program, int from) {
		//MiniJava.writeConsole("parseBbinop called with from: " + from + "\n");

		if(!program[from].equals("&&")&&!program[from].equals("||"))
			return -1;
		return from+1;
	}

	/* ✔ */

	public static int parseBunop(String[] program, int from) {
		//MiniJava.writeConsole("parseBunop called with from: " + from + "\n");

		if(!program[from].equals("!"))
			return -1;
		return from+1;
	}

	/* ✔ */

	public static int parseCond(String[] program, int from) {
		//MiniJava.writeConsole("parseCond called with from: " + from + "\n");

		int np;

		if(program[from].equals("true")) {
			from++;
			if(from>=program.length)
				return -1;
			np=parseCondDeriv(program, from);
			if(np<0||np>=program.length)
				return -1;

			return np;
		}

		if(program[from].equals("false")) {
			from++;
			if(from>=program.length)
				return -1;
			np=parseCondDeriv(program, from);
			if(np<0||np>=program.length)
				return -1;

			return np;
		}

		if(program[from].equals("(")) {
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
			np=parseCondDeriv(program, from);
			if(np<0||np>=program.length)
				return -1;

			return np;
		}

		np=parseExpr(program, from);
		if(np>=0) {
			if(np>=program.length)
				return -1;
			from=np;

			np=parseComp(program, from);
			if(np<0||np>=program.length)
				return -1;
			from=np;

			np=parseExpr(program, from);
			if(np<0||np>=program.length)
				return -1;
			from=np;

			np=parseCondDeriv(program, from);
			if(np<0||np>=program.length)
				return -1;

			return np;
		}

		np=parseBunop(program, from);
		if(np>=0) {
			if(np>=program.length)
				return -1;
			from=np;

			if(!program[from].equals("("))
				return -1;
			from++;

			np=parseCond(program, from);
			if(np<0||np>=program.length)
				return -1;
			from=np;

			if(!program[from].equals(")"))
				return -1;
			from++;

			np=parseCondDeriv(program, from);
			if(np<0||np>=program.length)
				return -1;

			return np;
		}

		return -1;
	}

	/* ✔ */

	public static int parseCondDeriv(String[] program, int from) {
		//MiniJava.writeConsole("parseCondDeriv called with from: " + from + "\n");

		int np;

		np=parseBbinop(program, from);
		if(np>=0) {
			if(np>=program.length)
				return -1;
			from=np;

			np=parseCond(program, from);
			if(np<0||np>=program.length)
				return -1;
			from=np;

			np=parseCondDeriv(program, from);
			if(np<0||np>=program.length)
				return -1;

			return np;
		}
		return from;
	}

	/* ✔ */

	public static int parseStmt(String[] program, int from) {
		//MiniJava.writeConsole("parseStmt called with from: " + from + "\n");

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

	public static int parseProgram(String[] program) {
		int pos=0;

		int np=parseDecl(program, pos);
		if(np>=program.length)
			return 0;

		while(np!=-1) {
			pos=np;
			np=parseDecl(program, pos);
			if(np>=program.length)
				return 0;
		}

		//MiniJava.writeConsole("parsed declarations\n");

		np=parseStmt(program, pos);
		if(np>=program.length)
			return 0;

		while(np!=-1) {
			pos=np;
			np=parseStmt(program, pos);
			if(np>=program.length)
				return 0;
		}

		return -1;
	}

	public static void main(String[] args) {
		MiniJava.writeConsole("Please enter a MiniJava program.\n");
		if(parseProgram(lex(readProgramConsole()))==0)
			MiniJava.writeConsole("program is syntactically valid\n");
		else
			MiniJava.writeConsole("program is syntactically invalid\n");
	}
}
