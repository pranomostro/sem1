public class DreiUndSieben extends MiniJava {
	public static void main(String args[]) {
		int i, j, acc;

		i=readInt();

		while(i<0) {
			write("Bitte eine positive Zahl eingeben");
			i=readInt();
		}

		acc=0;
		j=1;

		while(j<i) {
			if(j%3==0||j%7==0)
				acc+=j;
			j++;
		}

		write(acc);
	}
}
