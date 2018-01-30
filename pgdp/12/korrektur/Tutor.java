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
	}

	public void run() {
		while(count.getn(task)<Helper.NEXAMS) {
			try {
				count.inc(task);
				Klausur k=in.consume();
				int bp=k.getGesamtpunktzahl();
				int np=Korrekturschema.punkte(task, k.getAntwort(task));
				k.setGesamtpunktzahl(k.getGesamtpunktzahl()+Korrekturschema.punkte(task, k.getAntwort(task)));
				out.produce(k);
			}
			catch(InterruptedException e) {
				System.exit(2);
			}
		}
	}
}
