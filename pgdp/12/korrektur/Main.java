public class Main {
	public static void main(String args[]) {
		Buffer begin, end;
		Buffer[] mid;
		Klausur[] exams;
		Tutor[] tutors;

		begin=new Buffer(Helper.NEXAMS);
		end=new Buffer(Helper.NEXAMS);

		mid=new Buffer[Helper.NTASKS];

		for(int i=0; i<Helper.NTASKS; i++)
			mid[i]=new Buffer(Helper.BUFCAP);

		exams=new Klausur[Helper.NEXAMS];

		for(int i=0; i<Helper.NEXAMS; i++) {
			exams[i]=new Klausur();
			try {
				begin.produce(exams[i]);
			} catch(InterruptedException e) {
				System.exit(1);
			}
		}

		tutors=new Tutor[Helper.NTUTORS];

		for(int i=0; i<Helper.NTUTORS; i++) {
			if(i%(Helper.NTASKS)==0) {
				//MiniJava.writeConsole("Using the first buffer, writing to " + i%(Helper.NTASKS) + "\n");
				tutors[i]=new Tutor(i%(Helper.NTASKS), begin, mid[i%(Helper.NTASKS)]);
			} else {
				//MiniJava.writeConsole("Adding tutor consuming task " + (i%(Helper.NTASKS)-1) + "\n");
				tutors[i]=new Tutor(i%(Helper.NTASKS), mid[i%(Helper.NTASKS)-1], mid[i%(Helper.NTASKS)]);
			}
		}

		Uebungsleitung u1, u2;
		u1=new Uebungsleitung(mid[Helper.NTASKS-1], end);
		u2=new Uebungsleitung(mid[Helper.NTASKS-1], end);

		for(int i=0; i<Helper.NTUTORS; i++)
			tutors[i].start();

		u1.start();
		u2.start();

		for(int i=0; i<Helper.NEXAMS; i++) {
			try { MiniJava.writeConsole(i + ": " + end.consume().toString() + "\n"); }
			catch(InterruptedException ie) { System.exit(3); }
		}
	}
}
