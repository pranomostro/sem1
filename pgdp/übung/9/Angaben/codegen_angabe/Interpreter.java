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
	public static final int EQ=9;
	public static final int LT=10;
	public static final int LE=11;
	public static final int IN=12;
	public static final int OUT=13;
	public static final int CALL=14;
	public static final int RETURN=15;
	public static final int HALT=16;
	public static final int ALLOC=17;
	public static final int DIV=18;
	public static final int AND=19;
	public static final int OR=20;
	public static final int NOT=21;

	private static int ic;
	private static int sp, fp;
	private static int[] stack;
	private static String[] lines;

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

	public static String programToString(int[] program) {
		String res="";
		for(int i=0; i<program.length; i++) {
			res+=i + ": ";
			int immediate=program[i]&0xFFFF;
			if(immediate<0)
				immediate|=0xFFFF<<16;
			switch(program[i]>>16) {
				case NOP: res+="NOP\n"; break;
				case ADD: res+="ADD\n"; break;
				case SUB: res+="SUB\n"; break;
				case MUL: res+="MUL\n"; break;
				case MOD: res+="MOD\n"; break;
				case LDI: res+="LDI " + (program[i]&0xFFFF) + "\n"; break;
				case LDS: res+="LDS " + (program[i]&0xFFFF) + "\n"; break;
				case STS: res+="STS " + (program[i]&0xFFFF) + "\n"; break;
				case JUMP: res+="JUMP " + (program[i]&0xFFFF) + "\n"; break;
				case EQ: res+="EQ\n"; break;
				case LT: res+="LT\n"; break;
				case LE: res+="LE\n"; break;
				case IN: res+="IN\n"; break;
				case OUT: res+="OUT\n"; break;
				case CALL: res+="CALL " + (program[i]&0xFFFF) + "\n"; break;
				case RETURN: res+="RETURN " + (program[i]&0xFFFF) + "\n"; break;
				case HALT: res+="HALT\n"; break;
				case ALLOC: res+="ALLOC " + (program[i]&0xFFFF) + "\n"; break;
				case DIV: res+="DIV\n"; break;
				case AND: res+="AND\n"; break;
				case OR: res+="OR\n"; break;
				case NOT: res+="NOT\n"; break;
			}
		}
		return res;
	}

	public static int[] parse(String textProgram) {
		short immediate;
		int i=0;
		ArrayList<Integer> il=new ArrayList<>();
		lines=textProgram.split("\n");

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
				il.add(0);
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
				try {
					immediate=Short.parseShort(words[1]);
					il.add(5<<16|immediate&0xFFFF);
				} catch(Exception e) {
					error("Failed to parse argument for LDI\n");
				}
				break;
			case "LDS":
				if(words.length!=2)
					error("LDS needs one argument\n");
				try {
					immediate=Short.parseShort(words[1]);
					il.add(6<<16|immediate&0xFFFF);
				} catch(Exception e) {
					error("Failed to parse argument for LDS\n");
				}
				break;
			case "STS":
				if(words.length!=2)
					error("STS needs one argument\n");
				try {
					immediate=Short.parseShort(words[1]);
					il.add(7<<16|immediate&0xFFFF);
				} catch(Exception e) {
					error("Failed to parse argument for STS\n");
				}
				break;
			case "JUMP":
				if(words.length!=2)
					error("JUMP needs one argument\n");
				try {
					il.add(8<<16|Integer.parseInt(words[1]));
				} catch(Exception e) {
					error("Failed to parse argument for JUMP\n");
				}
				break;
			case "EQ":
				il.add(9<<16);
				break;
			case "LT":
				il.add(10<<16);
				break;
			case "LE":
				il.add(11<<16);
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
				try {
					il.add(14<<16|Integer.parseInt(words[1]));
				} catch(Exception e) {
					error("Failed to parse argument for CALL\n");
				}
				break;
			case "RETURN":
				if(words.length!=2)
					error("RETURN needs one argument\n");
				try {
					il.add(15<<16|Integer.parseInt(words[1]));
				} catch(Exception e) {
					error("Failed to parse argument for RETURN\n");
				}
				break;
			case "HALT":
				il.add(16<<16);
				break;
			case "ALLOC":
				if(words.length!=2)
					error("ALLOC needs one argument\n");
				try {
					il.add(17<<16|Integer.parseInt(words[1]));
				} catch(Exception e) {
					error("Failed to parse argument for ALLOC\n");
				}
				break;
			case "DIV":
				il.add(18<<16);
				break;
			case "AND":
				il.add(19<<16);
				break;
			case "OR":
				il.add(20<<16);
				break;
			case "NOT":
				il.add(21<<16);
				break;
			default:
				error("Unknown instruction\n");
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

	public static int execute(int[] program) {
		int o1, o2, narg, val;
		for(int ic=0; ic<program.length; ic++) {
			if(ic<0)
				error("Jumped to negative address\n");
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
			case MOD:
				o1=pop();
				o2=pop();
				if(o2==0)
					error("Modulo by zero not allowed\n");
				push(o1%o2);
				break;
			case LDI:
				val=program[ic]&0xFFFF;
				if(val<0)
					val|=0xFFFF<<16;
				push(val);
				break;
			case LDS:
				push(stack[fp+program[ic]&0xFFFF]);
				break;
			case STS:
				stack[fp+program[ic]&0xFFFF]=pop();
				break;
			case JUMP:
				o1=pop();
				push(o1);
				if(o1==-1)
					ic=(program[ic]&0xFFFF)-1;
				break;
			case EQ:
				o1=pop();
				o2=pop();
				if(o1==o2)
					push(-1);
				else
					push(0);
				break;
			case LT:
				o1=pop();
				o2=pop();
				if(o1<o2)
					push(-1);
				else
					push(0);
				break;
			case LE:
				o1=pop();
				o2=pop();
				if(o1<=o2)
					push(-1);
				else
					push(0);
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
				int addr=pop();
				narg=program[ic]&0xFFFF;
				int args[]=new int[narg];
				for(int j=0; j<narg; j++)
					args[j]=pop();
				push(fp);
				push(ic);
				for(int j=args.length-1; j>=0; j--)
					push(args[j]);
				fp=sp-1;
				ic=addr-1;
				break;
			case RETURN:
				int res=pop();
				narg=program[ic]&0xFFFF;
				for(int j=0; j<narg; j++)
					pop();
				ic=pop();
				fp=pop();
				push(res);
				break;
			case HALT:
				System.exit(0);
				break;
			case ALLOC:
				val=program[ic]&0xFFFF;
				sp+=val;
				break;
			case DIV:
				push(pop()/pop());
				break;
			case AND:
				push(pop()&pop());
				break;
			case OR:
				push(pop()|pop());
				break;
			case NOT:
				push(~pop());
				break;
			default:
				error("Unknown opcode " + (program[ic]>>16) + "\n");
			}
		}
		int res=pop();
		push(res);
		return res;
	}

	public static void main(String[] args) {
		sp=0;
		fp=0;
		stack=new int[128];
		execute(parse(readProgramConsole()));
	}
}
