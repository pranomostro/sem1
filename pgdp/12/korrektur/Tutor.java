public class Tutor extends Thread {
	private int task;
	private Buffer in, out;
	private static Count count=null;

	public Tutor(int taskn, Buffer bin, Buffer bout) {
		task=taskn;
		in=bin;
		out=bout;

		if(count==null) {
			count=new Count(Helper.NTASKS);
		}

		MiniJava.writeConsole("New tutor for task " + task + "\n");
	}

	public void run() {
		MiniJava.writeConsole("Started tutor for task " + task + "\n");
		while(count.getn(task)<Helper.NEXAMS) {
			try {
				count.inc(task);
				Klausur k=in.consume();
				MiniJava.writeConsole("Tutor for task " + task + " consumed exam " + count.getn(task) + "\n");
				int bp=k.getGesamtpunktzahl();
				int np=Korrekturschema.punkte(task, k.getAntwort(task));
				k.setGesamtpunktzahl(k.getGesamtpunktzahl()+Korrekturschema.punkte(task, k.getAntwort(task)));
				MiniJava.writeConsole("attempting to produce " + count.getn(task) + "\n");
				out.produce(k);
				MiniJava.writeConsole("Tutor for task " + task + " produced exam " + count.getn(task) + "\n");
			}
			catch(InterruptedException e) {
				System.exit(2);
			}
		}
		MiniJava.writeConsole("Finished tutor for task " + task + "\n");
	}
}
