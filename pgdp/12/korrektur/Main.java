public class Main {
	public static void main(String args[]) {
		Buffer begin, end;
		Buffer[] mid;
		Klausur[] exams;
		Tutor[] tutors;

		begin=new Buffer(Helper.NEXAMS);
		end=new Buffer(Helper.NEXAMS);

		mid=new Buffer[Helper.NTASKS-1];

		for(int i=0; i<Helper.NTASKS-1; i++)
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
			} else if(i%(Helper.NTASKS)==Helper.NTASKS-1) {
				//MiniJava.writeConsole("Using the last buffer, reading from " + (i%(Helper.NTASKS)-1) + "\n");
				tutors[i]=new Tutor(i%(Helper.NTASKS), mid[i%(Helper.NTASKS)-1], end);
			} else {
				//MiniJava.writeConsole("Adding tutor consuming task " + (i%(Helper.NTASKS)-1) + "\n");
				tutors[i]=new Tutor(i%(Helper.NTASKS), mid[i%(Helper.NTASKS)-1], mid[i%(Helper.NTASKS)]);
			}
		}

		for(int i=0; i<Helper.NTUTORS; i++)
			tutors[i].start();

		for(int i=0; i<Helper.NEXAMS; i++) {
			try { MiniJava.writeConsole(i + ": " + end.consume().toString() + "\n"); }
			catch(InterruptedException ie) { System.exit(3); }
		}
	}
}
