public class StringRay extends MiniJava {
	public static void main(String args[]) {
		char c, r1, r2;
		int i, j, k, wordbegin;
		String input, replacer;
		char[] replaced, mirrored;
		int[] freq=new int[26];

		input=readString();

		/* Häufigkeit der Buchstaben */

		i=0;
		while(i<26)
			freq[i++]=0;

		i=0;
		while(i<input.length()) {
			c='0';
			if(input.charAt(i)>='a'&&input.charAt(i)<='z')
				c=(char)((int)input.charAt(i)-32);
			if(input.charAt(i)>='A'&&input.charAt(i)<='Z')
				c=input.charAt(i);
			if(c!='0')
				freq[(int)c-65]++;
			i++;
		}

		i=0;
		while(i<26) {
			if(freq[i]>0)
				writeConsole((char)(i+65) + ": " + freq[i] + " ");
			i++;
		}
		writeConsole("\n");

		/* Buchstaben ersetzen */

		replaced=new char[input.length()];

		replacer=readString();

		while(replacer.length()!=2||
			!((replacer.charAt(0)>='A'&&replacer.charAt(0)<='Z')||
				(replacer.charAt(0)>='a'&&replacer.charAt(0)<='z'))||
			!((replacer.charAt(1)>='A'&&replacer.charAt(1)<='Z')||
				(replacer.charAt(1)>='a'&&replacer.charAt(1)<='z'))) {
			write("Bitte String der Länge 2 aus Buchstaben eingeben!");
			replacer=readString();
		}

		r1=(char)((int)replacer.charAt(0)-32);
		r2=(char)((int)replacer.charAt(1)-32);

		i=0;
		while(i<input.length()) {
			if(input.charAt(i)==r1)
				replaced[i]=r2;
			else if(input.charAt(i)==r1+32)
				replaced[i]=(char)((int)r2+32);
			else
				replaced[i]=input.charAt(i);
			i++;
		}

		i=0;
		while(i<input.length()) {
			writeConsole(replaced[i] + "");
			i++;
		}
		writeConsole("\n");

		/* Wortweise spiegeln */

		mirrored=new char[input.length()];

		i=j=k=0;

		while(i<input.length()&&j<input.length()) {
			while(input.charAt(i)==' '&&i<input.length()) {
				mirrored[k]=' ';
				k++;
				i++;
			}

			j=i;

			while(j<input.length()&&input.charAt(j)!=' ')
				j++;

			wordbegin=i;

			while(i<j) {
				mirrored[k]=input.charAt(j-(i-wordbegin)-1);
				k++;
				i++;
			}
		}

		i=0;
		while(i<input.length()) {
			writeConsole(mirrored[i] + "");
			i++;
		}
		writeConsole("\n");
	}
}
