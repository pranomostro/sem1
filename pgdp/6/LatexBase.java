public class LatexBase {
	public static void main(String[] args) {
		int base, cols, rl;
		int[] a=readNumber();
		int[] b=readNumber();

		do
			base=MiniJava.readInt("Enter base");
		while(base<0||base>36);

		cols=2+a.length+b.length;

		MiniJava.writeConsole("\\begin{tabular}{");
		for(int i=0; i<cols; i++)
			MiniJava.writeConsole("c");
		MiniJava.writeConsole("}\n");

		MiniJava.writeConsole("& ");
		for(int i=a.length-1; i>=0; i--)
			MiniJava.writeConsole(toChar(a[i]) + " & ");
		MiniJava.writeConsole("$\\ast$");
		for(int i=b.length-1; i>=0; i--)
			MiniJava.writeConsole(" & " + toChar(b[i]));
		MiniJava.writeConsole("\\\\\n");

		MiniJava.writeConsole("\\hline\n");

		for(int i=0; i<b.length; i++) {
			int[] med=mulDigit(a, b[i], i, base);

			MiniJava.writeConsole("+");

			for(rl=med.length-1; rl>0&&med[rl]==0; rl--)
				;

			for(int j=0; j<cols-rl-1; j++)
				MiniJava.writeConsole("&");

			MiniJava.writeConsole(" ");

			for(int j=rl; j>0; j--)
				MiniJava.writeConsole(toChar(med[j]) + "& ");
			MiniJava.writeConsole(med[0] + "\\\\\n");
		}

		MiniJava.writeConsole("\\hline\n");

		int[] res=mul(a, b, base);
		for(rl=res.length-1; rl>0&&res[rl]==0; rl--)
			;

		MiniJava.writeConsole("=");

		for(int j=0; j<cols-rl-1; j++)
			MiniJava.writeConsole("&");

		MiniJava.writeConsole(" ");

		for(int j=rl; j>0; j--)
			MiniJava.writeConsole(toChar(res[j]) + "& ");
		MiniJava.writeConsole(res[0] + "\\\\\n");

		MiniJava.writeConsole("\\end{tabular}\n");
	}

	public static int[] readNumber() {
		String s;
		int i, j;
		int[] res;

		do
			s=MiniJava.readString("Please enter the number");
		while(!isvalid(s));

		res=new int[s.length()];

		for(i=0, j=s.length()-1; i<s.length(); i++) {
			if(s.charAt(i)>=0&&s.charAt(i)<='9') {
				res[j]=s.charAt(i)-48;
				j--;
			 } else {
				res[j]=s.charAt(i)-55;
				j--;
			}
		}

		return res;
	}

	private static String toString(int[] number) {
		String s="";

		for(int i=0; i<number.length; i++)
			s=toChar(number[i])+s;

		return s;
	}

	private static int[] add(int[] a, int[] b, int base) {
		int[] res, longer, shorter;
		int minlen, maxlen;

		if(a.length>b.length) {
			longer=a;
			shorter=b;
		} else {
			longer=b;
			shorter=a;
		}

		res=new int[longer.length+1];
		for(int i=0; i<longer.length; i++)
			res[i]=longer[i];

		for(int i=0; i<shorter.length; i++) {
			res[i+1]+=(res[i]+shorter[i])/base;
			res[i]=(res[i]+shorter[i])%base;
		}

		return res;
	}

	private static int[] mul(int[] a, int[] b, int base) {
		int[] med;
		int[] res=new int[a.length+b.length];

		for(int i=0; i<b.length; i++)
			res=add(res, mulDigit(a, b[i], i, base), base);

		return res;
	}

	private static boolean isvalid(String s) {
		for(int i=0; i<s.length(); i++)
			if(!(Character.isDigit(s.charAt(i))||Character.isUpperCase(s.charAt(i))))
				return false;
		return true;
	}

	private static int[] mulDigit(int[] a, int digit, int shift, int base) {
		int med;
		int[] res=new int[a.length+shift+1];

		for(int i=shift; i<res.length-1; i++) {
			med=res[i]+a[i-shift]*digit;
			res[i+1]+=med/base;
			res[i]=med%base;
		}

		return res;
	}

	private static char toChar(int a) {
		if(a>=0&&a<=9)
			return (char)(a+48);
		else
			return (char)(a+55);
	}
}
