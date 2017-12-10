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

	private static int ic;
	private static int sp;
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
				if(words.length!=2)
					error("LDI needs one argument\n");
				il.add(5<<16|Integer.parseInt(words[1]));
				break;
			case "LDS":
				if(words.length!=2)
					error("LDS needs one argument\n");
				il.add(6<<16|Integer.parseInt(words[1]));
				break;
			case "STS":
				if(words.length!=2)
					error("STS needs one argument\n");
				il.add(7<<16|Integer.parseInt(words[1]));
				break;
			case "JUMP":
				if(words.length!=2)
					error("JUMP needs one argument\n");
				il.add(8<<16|Integer.parseInt(words[1]));
				break;
			case "JE":
				if(words.length!=2)
					error("JE needs one argument\n");
				il.add(9<<16|Integer.parseInt(words[1]));
				break;
			case "JNE":
				if(words.length!=2)
					error("JNE needs one argument\n");
				il.add(10<<16|Integer.parseInt(words[1]));
				break;
			case "JLT":
				if(words.length!=2)
					error("JLT needs one argument\n");
				il.add(11<<16|Integer.parseInt(words[1]));
				break;
			case "IN":
				il.add(12<<16);
				break;
			case "OUT":
				il.add(13<<16);
				break;
			case "CALL":
				if(words.length!=2)
					error("CALL needs one argument\n");
				il.add(14<<16|Integer.parseInt(words[1]));
				break;
			case "RETURN":
				if(words.length!=2)
					error("RETURN needs one argument\n");
				il.add(15<<16|Integer.parseInt(words[1]));
				break;
			case "HALT":
				il.add(16<<16);
				break;
			case "ALLOC":
				if(words.length!=2)
					error("ALLOC needs one argument\n");
				il.add(17<<16|Integer.parseInt(words[1]));
				break;
			default:
				break;
			}
			i++;
		}

		return il.stream().mapToInt(Integer::intValue).toArray();
	}

	private static int pop() {
		if(sp==0)
			error("Not pop()'ing from an empty stack\n");
		sp--;
		return stack[sp];
	}

	private static void push(int val) {
		if(sp>127)
			error("Not push()'ing on a full stack\n");
		stack[sp]=val;
		sp++;
	}

	private static int execute(int[] program) {
		int o1, o2;
		for(int ic=0; ic<program.length; ic++)
			switch(program[ic]>>16) {
			case NOP:
				break;
			case ADD:
				push(pop()+pop());
				break;
			case SUB:
				push(pop()-pop());
				break;
			case MUL:
				push(pop()*pop());
				break;
			case LDI:
				push(program[ic]|0xFFFF);
				break;
			case LDS:
				// TODO
				break;
			case STS:
				// TODO
				break;
			case JUMP:
				ic=(program[ic]|0xFFFF)-1;
				break;
			case JE:
				o1=pop();
				o2=pop();
				push(o2);
				push(o1);
				if(o1==o2)
					ic=(program[ic]|0xFFFF)-1;
				break;
			case JNE:
				o1=pop();
				o2=pop();
				push(o2);
				push(o1);
				if(o1!=o2)
					ic=(program[ic]|0xFFFF)-1;
				break;
			case JLT:
				o1=pop();
				o2=pop();
				push(o2);
				push(o1);
				if(o1<o2)
					ic=(program[ic]|0xFFFF)-1;
				break;
			case IN:
				push(MiniJava.readInt());
				break;
			case OUT:
				int out=pop();
				push(out);
				MiniJava.writeConsole(out + "\n");
				break;
			case CALL:
				// TODO
				break;
			case RETURN:
				// TODO
				break;
			case HALT:
				System.exit(0);
				break;
			case ALLOC:
				// TODO
				break;
			default:
				error("Unknown opcode " + (program[ic]<<16) + "\n");
			}
		int res=pop();
		push(res);
		return res;
	}

	public static void main(String[] args) {
		sp=0;
		stack=new int[128];
		//parse("ALLOC 1\nLDI 0\nSTS 1\nfrom:\nLDS 1\nOUT\nLDS 1\nLDI 1\nADD\nSTS 1\nJUMP from\n");
		execute(parse("IN\nIN\nSUB\nOUT\n"));
	}
}
