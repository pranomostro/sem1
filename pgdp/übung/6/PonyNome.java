public class PonyNome {
	public static void main(String[] args) {
		double firstzero;
		double[] nextzeros;
		double[] interval;
		double[] newcoeffs;

		double[] coeff=new double[4];
		coeff[0]=0.5;
		coeff[1]=5;
		coeff[2]=-33.5;
		coeff[3]=-308;

		coeff[0]=MiniJava.readDouble();
		coeff[1]=MiniJava.readDouble();
		coeff[2]=MiniJava.readDouble();
		coeff[3]=MiniJava.readDouble();

		interval=findIntervalRecursive(coeff, -2, 2, 10);
		firstzero=findRootRecursive(coeff, (int)interval[0], (int)interval[1]);

		newcoeffs=hornerSchema(coeff, firstzero);
		nextzeros=quadraticFormula(newcoeffs);

		MiniJava.writeConsole("number of zeros: " + (1+nextzeros.length) + "\n");
		MiniJava.writeConsole(firstzero + "\n");
		for(int i=0; i<nextzeros.length; i++)
			MiniJava.writeConsole(nextzeros[i] + "\n");
	}

	/* ✔ */

	private static double calculateY(double[] coeff, double x) {
		double res=0;

		for(int i=0; i<coeff.length; i++)
			res=res+coeff[i]*Math.pow(x, coeff.length-i-1);

		return res;
	}

	/* ✔ */

	public static double[] findIntervalRecursive(double[] coeff, double a, double b, double factor) {
		if(samesign(a, b, coeff))
			return findIntervalRecursive(coeff, a*factor, b*factor, factor);

		double[] res=new double[2];
		res[0]=a;
		res[1]=b;
		return res;
	}

	/* ✔ */

	private static boolean samesign(double d, double e, double[] coeff) {
		return calculateY(coeff, d)*calculateY(coeff, e)>=0;
	}

	/* ✔ */

	private static double findRootRecursive(double[] coeff, int a, int b) {
		double m=(a+b)/2;
		double ares=calculateY(coeff, a);
		double bres=calculateY(coeff, b);
		double mres=calculateY(coeff, m);

		if(ares!=0&&bres!=0&&mres!=0) {
			if(ares*mres<0)
				return findRootRecursive(coeff, a, (int)m);
			else
				return findRootRecursive(coeff, (int)m, b);
		}

		if(ares==0)
			return a;
		else if(bres==0)
			return b;
		else
			return m;
	}

	/* ✔ */

	public static double[] hornerSchema(double[] coeff, double x0) {
		double[] res=new double[3];

		res[0]=coeff[0];
		res[1]=coeff[1]+x0*res[0];
		res[2]=coeff[2]+x0*res[1];

		return res;
	}

	/* ✔ */

	private static double[] quadraticFormula(double[] coeff) {
		double[] res;
		double delta=Math.pow(coeff[1], 2)-(4*coeff[0]*coeff[2]);
		if(delta<0)
			res=new double[0];
		else if(delta==0) {
			res=new double[1];
			res[0]=(-coeff[1])/(2*coeff[0]);
		} else {
			res=new double[2];
			res[0]=((-coeff[1])+Math.sqrt(delta))/(2*coeff[0]);
			res[1]=((-coeff[1])-Math.sqrt(delta))/(2*coeff[0]);
		}
		return res;
	}
}
