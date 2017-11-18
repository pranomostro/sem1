public class Palina extends MiniJava {
	public static void main(String args[]) {
		int i, j, save;

		i=readInt();

		while(i<1) {
			write("Bitte eine positive Zahl eingeben");
			i=readInt();
		}

		int[] a=new int[9]; /* 32 bit, daher höchstens 9 Stelle */

		j=0;
		save=i;

		while(i>0) {
			a[j]=i%10;
			i=(i-(i%10))/10;
			j++;
		}

		i=0;

		while(i<j&&a[i]==a[j-i-1])
			i++;

		if(i==j)
			write(save + " ist ein Palindrom");
		else
			write(save + " ist kein Palidrom");
	}
}
