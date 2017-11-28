public class LatexBase {
	public static void main(String[] args) {
		int[] a=readNumber();
		int[] b=readNumber();
		MiniJava.writeConsole(toString(add(a, b, 16)));
	}

	public static int[] readNumber() {
		String s;
		int[] res;

		res=new int[MiniJava.readInt("Please enter length of the number")];

		for(int i=res.length-1; i>=0;) {
			s=MiniJava.readString();
			if(s.length()==1&&(s.charAt(0)>='0'&&s.charAt(0)<='9')) {
				res[i]=s.charAt(0)-48;
				i--;
			}
			else if(s.length()==1&&(s.charAt(0)>='A'&&s.charAt(0)<='Z')) {
				res[i]=s.charAt(0)-55;
				i--;
			}
		}

		return res;
	}

	private static String toString(int[] number) {
		String s="";

		for(int i=0; i<number.length; i++) {
			if(number[i]>=10)
				s=s+(char)(number[i]+55);
			else
				s=s+(char)(number[i]+48);
		}
		return s;
	}

	private static int[] add(int[] a, int[] b, int base) {
		int[] res;
		int minlen, maxlen;

		if(a.length>b.length) {
			minlen=b.length;
			maxlen=a.length;
		} else {
			minlen=a.length;
			maxlen=b.length;
		}

		res=new int[maxlen+1];

		for(int i=0; i<maxlen+1; i++)
			res[i]=0;

		for(int i=0; i<maxlen; i++) {
			res[i+1]=(res[i]+a[i]+b[i])/base;
			res[i]=(res[i]+a[i]+b[i])%base;
		}

		return res;
	}
}
