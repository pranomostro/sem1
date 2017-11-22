public class GrausGauß extends MiniJava {
	private static int lines;

	public static void main(String[] args) {
		int i;
		int[] matrix, results;

		matrix=readMatrix();

		printMatrix(matrix);
		writeConsole("\n");

		results=rowEchelonToResult(matrix);

		for(i=0; i<results.length; i++)
			writeConsole(results[i] + " ");

		writeConsole("\n");
	}

	private static int[] solveSystem(int[] matrix) {
		int i;

		for(i=0; i<lines; i++) {
			searchSwap(matrix, i);

			/* set element before diagonal in next line to 0 */
		}

		return rowEchelonToResult(matrix);
	}

	/* TODO: change before giving back */

	private static int[] readMatrix() {
		/*
		int i;
		int[] matrix;
		lines=readInt("Please enter the number of equations");
		matrix=new int[lines*(lines+1)];

		for(i=0; i<lines*(lines+1); i++)
			matrix[i]=readInt();

		return matrix;
		*/

		lines=3;
		return (new int[] {6, 2, 3, 2, 4, 2, 2, 4, 3, 3, 1, 0});
	}

	private static void printMatrix(int[] matrix) {
		int i, j;

		for(i=0; i<matrix.length; i++) {
			writeConsole(matrix[i]);
			if((i%(lines+1))==lines)
				writeConsole("\n");
			else
				writeConsole(" ");
		}
	}

	private static int get(int[] matrix, int line, int column) {
		return matrix[line*(lines+1)+column];
	}

	private static void set(int[] matrix, int line, int column, int val) {
		matrix[line*(lines+1)+column]=val;
	}

	private static void multLine(int[] matrix, int line, int factor) {
		int i;

		for(i=0; i<lines+1; i++)
			set(matrix, line, i, get(matrix, line, i)*factor);
	}

	private static void multAddLine(int[] matrix, int line1, int line2, int factor) {
		int i;

		for(i=0; i<lines+1; i++)
			set(matrix, line1, i, get(matrix, line1, i)+get(matrix, line2, i)*factor);
	}

	private static void swap(int[] matrix, int line1, int line2) {
		int i, tmp;

		for(i=0; i<lines+1; i++) {
			tmp=get(matrix, line1, i);
			set(matrix, line1, i, get(matrix, line2, i));
			set(matrix, line2, i, tmp);
		}
	}

	private static void searchSwap(int[] matrix, int fromline) {
		int i;

		if(get(matrix, fromline, fromline)==0)
			for(i=fromline+1; i<lines; i++)
				if(get(matrix, i, fromline)!=0) {
					swap(matrix, i, fromline);
					return;
				}
	}

	private static int kgv(int a, int b) {
		int min, max, i;

		if(a>=b) {
			min=b; max=a;
		} else {
			min=a; max=b;
		}

		for(i=1; i<=max; i++)
			if((i*min)%max==0)
				return i*min;

		return a*b;
	}

	private static int[] rowEchelonToResult(int[] matrix) {
		int i, j, eqres, curval, xval;
		int[] results=new int[lines];

		for(i=lines-1; i>=0; i--) {
			for(j=lines-1; j>i; j--) {
				eqres=get(matrix, i, lines);
				curval=get(matrix, i, j);
				xval=results[j];
				set(matrix, i, lines, eqres-(curval*xval));
			}

			results[i]=get(matrix, i, lines)/get(matrix, i, j);
		}

		return results;
	}
}
