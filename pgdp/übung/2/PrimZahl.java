public class PrimZahl extends MiniJava {
	public static void main(String args[]) {
		int i, j, prim;

		i=readInt();

		while(i<1) {
			write("Bitte eine Zahl größer als 0 eingeben");
			i=readInt();
		}

		prim=0;
		j=3;

		if(i==1||i%2==0&&i>2) {
			prim=1;
		} else {
			/* hier die Wurzel von i und nicht i/2 als limit zu benutzen wäre besser,
			aber die können wir noch nicht */
			while(j<=i/2&&prim==0) {
				if(i%j==0)
					prim=1;
				j+=2;
			}
		}

		if(prim==0)
			write(i + " ist prim");
		else
			write(i + " ist nicht prim");
	}
}
