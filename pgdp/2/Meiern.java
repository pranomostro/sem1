public class Meiern extends MiniJava {
	public static void main(String args[]) {
		// State: 0 für Spieler, 1 für Computer

		int a, b, res, last, state;

		last=state=0;
		res=1;

		while(res!=21&&res>last)
		{
			last=res;

			a=dice();
			b=dice();

			if(a>=b)
				res=10*a+b;
			else
				res=10*b+a;

			if(state==0) {
				write("Spieler: " + res);
				state=1;
			} else {
				write("Computer: " + res);
				state=0;
			}

			if(a==b)
				res*=10;
		}

		if(state==1)
			write("Computer hat gewonnen");
		else
			write("Spieler hat gewonnen");
	}
}
