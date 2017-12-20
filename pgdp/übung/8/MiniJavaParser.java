import java.util.Scanner;
import java.util.ArrayList;

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
		ArrayList<String> tokens=new ArrayList<>();

		for(int i=0; i<program.length();) {
			if(program.charAt(i)=='('||program.charAt(i)==')'||
			   program.charAt(i)=='{'||program.charAt(i)=='}'||
			   program.charAt(i)==','||program.charAt(i)=='-'||
			   program.charAt(i)=='+'||program.charAt(i)=='/'||
			   program.charAt(i)=='*'||program.charAt(i)==';') {
				tokens.add(""+program.charAt(i));
				i++;
				continue;
			}
			if(program.charAt(i)=='='||program.charAt(i)=='<'||
			   program.charAt(i)=='>') {
			}
			i++;
		}

		String[] res=new String[tokens.size()];
		for(int i=0; i<tokens.size(); i++)
			res[i]=tokens.get(i);
		return res;
	}

	public static int parseNumber(String[] program, int from) {
		return -1;
	}

	public static int parseName(String[] program, int from) {
		return -1;
	}

	public static int parseType(String[] program, int from) {
		return -1;
	}

	public static int parseDecl(String[] program, int from) {
		return -1;
	}

	public static int parseUnop(String[] program, int from) {
		return -1;
	}

	public static int parseBinop(String[] program, int from) {
		return -1;
	}

	public static int parseComp(String[] program, int from) {
		return -1;
	}

	public static int parseExpression(String[] program, int from) {
		return -1;
	}

	public static int parseBbinop(String[] program, int from) {
		return -1;
	}

	public static int parseBunop(String[] program, int from) {
		return -1;
	}

	public static int parseCondition(String[] program, int from) {
		return -1;
	}

	public static int parseStatement(String[] program, int from) {
		return -1;
	}

	public static int parseProgram(String[] program) {
		return -1;
	}

	public static void main(String[] args) {
		String tp="int sum, n, i;\n"+
"n=read();\n"+
"while(n<0) {"+
"\tn=read();"+
"}";
		String[] res=lex(tp);
		for(String s: res)
			MiniJava.writeConsole(s + "\n");
	}
}
