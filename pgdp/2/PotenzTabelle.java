public class PotenzTabelle extends MiniJava {
	public static void main(String args[]) {
		int limit, i, j, mult;

		limit=readInt();

		while(limit<=0) {
			write("Bitte eine positive Zahl eingeben");
			limit=readInt();
		}

		writeConsole("\\begin{tabular}{");

		i=0;

		while(i<limit) {
			writeConsole("l");
			i++;
		}

		writeConsole("}\n");

		i=0;

		while(i<limit) {
			mult=1;
			j=0;
			while(j<limit) {
				writeConsole(mult);

				if(j==limit-1)
					writeConsole("\\\\");
				else
					writeConsole(" & ");

				mult*=(i+1);
				j++;
			}
			writeConsole("\n");
			i++;
		}

		writeConsole("\\end{tabular}\n");
	}
}
