public class FeldVerarbeitung extends MiniJava {
	public static void main(String args[]) {
		int i, j, acc, max, secmax;

		i=readInt();

		while(i<2) {
			write("Bitte eine Zahl größer als 1 eingeben");
			i=readInt();
		}

		int[] a=new int[i];

		j=0;

		while(j<i) {
			a[j]=readInt();
			j++;
		}

		j=acc=0;

		while(j<i) {
			if(j%2==0)
				acc=acc+a[j];
			else
				acc=acc-a[j];
			j++;
		}

		write("Abwechselnde Summe: " + acc);

		j=0;

		if(a[0]>a[1]) {
			max=a[0];
			secmax=a[1];
		} else {
			max=a[1];
			secmax=a[0];
		}

		while(j<i) {
			if(a[j]>max) {
				secmax=max;
				max=a[j];
			}
			j++;
		}

		write("Zweitgrößtes Element: " + secmax);

		j=0;

		while(j<(i-1)) {
			a[j]+=a[j+1];
			j+=2;
		}

		j=0;

		while(j<i) {
			writeConsole(a[j]+ " ");
			j++;
		}

		writeConsole("\n");
	}
}
