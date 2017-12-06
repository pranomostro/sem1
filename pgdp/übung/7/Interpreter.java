import java.util.Scanner;
import java.util.ArrayList;

public class Interpreter extends MiniJava  {
	public static final int NOP=0;
	public static final int ADD=1;
	public static final int SUB=2;
	public static final int MUL=3;
	public static final int MOD=4;
	public static final int LDI=5;
	public static final int LDS=6;
	public static final int STS=7;
	public static final int JUMP=8;
	public static final int JE=9;
	public static final int JNE=10;
	public static final int JLT=11;
	public static final int IN=12;
	public static final int OUT=13;
	public static final int CALL=14;
	public static final int RETURN=15;
	public static final int HALT=16;
	public static final int ALLOC=17;

	private static int[] stack;

	static void error(String message) {
		throw new RuntimeException(message);
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

	public static int[] parse(String textProgram) {
		int i=0;
		ArrayList<Integer> il=new ArrayList<>();
		String[] lines=textProgram.split("\n");

		for(String s: lines) {
			MiniJava.writeConsole(s + "\n");
		}

		/* replace label with instruction number in every line */
		for(i=0; i<lines.length; i++) {
			if(lines[i].matches("[A-Za-z]+:")) {
				String labelname=lines[i].replace(":", "");
				for(int j=0; j<lines.length; j++)
					if(j!=i)
						lines[j]=lines[j].replace(labelname, "" + i);
				lines[i]="NOP";
			}
		}

		MiniJava.writeConsole("-----------------\n");

		for(String s: lines) {
			MiniJava.writeConsole(s + "\n");
		}

		i=0;

		for(String s: lines) {
			String[] words=s.split(" ");
			switch(words[0]) {
			case "NOP":
				il.add(0<<16);
				break;
			case "ADD":
				il.add(1<<16);
				break;
			case "SUB":
				il.add(2<<16);
				break;
			case "MUL":
				il.add(3<<16);
				break;
			case "MOD":
				il.add(4<<16);
				break;
			case "LDI":
				il.add(5<<16);
				if(words.length!=2)
					error("LDI needs an argument");
				il.add(il.get(i)&Integer.parseInt(words[1]));
				break;
			case "LDS":
				il.add(6<<16);
				break;
			case "STS":
				il.add(7<<16);
				break;
			case "JUMP":
				il.add(8<<16);
				break;
			case "JE":
				il.add(9<<16);
				break;
			case "JNE":
				il.add(10<<16);
				break;
			case "JLT":
				il.add(11<<16);
				break;
			case "IN":
			il.add(12<<16);
			break;
			case "OUT":
				il.add(13<<16);
				break;
			case "CALL":
				il.add(14<<16);
				break;
			case "RETURN":
				il.add(15<<16);
				break;
			case "HALT":
				il.add(16<<16);
				break;
			case "ALLOC":
				il.add(17<<16);
				break;
			default:
				break;
			}
			i++;
		}

		for(i=0; i<il.length(); i++)
			MiniJava.writeConsole("n

		return il.stream().mapToInt(Integer::intValue).toArray();
	}

	public static void main(String[] args) {
		stack=new int[128];
		parse("IN\nLDI fak\nCALL 1\nOUT\nHALT\nfak:\nALLOC 1\n");
	}
}
