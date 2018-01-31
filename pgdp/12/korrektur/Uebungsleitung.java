import java.util.Random;

public class Uebungsleitung extends Thread {
	private static Random random=new Random();
	private Buffer in, out;
	private static Count count=null;

	public Uebungsleitung(Buffer bin, Buffer bout) {
		in=bin;
		out=bout;

		if(count==null) {
			count=new Count(1);
		}
	}

	public void run() {
		while(count.getn(0)<Helper.NEXAMS) {
			try {
				count.inc(0);
				if(count.getn(0)>Helper.NEXAMS)
					break;
				Klausur k=in.consume();
				k.setNote(Korrekturschema.note(k.getGesamtpunktzahl()));
				int[] p=k.getPunkte();
				if(random.nextInt(10)==0) {
					int cn=random.nextInt(Helper.NTASKS);
					for(int i=0; i<Helper.NTASKS; i++) {
						if(i==cn)
							switch(random.nextInt(2)) {
							case 0: if(k.getPunkte()[i]>0) k.setZweitkorrektur(i, p[i]-1);
								break;
							case 1: if(k.getPunkte()[i]<7) k.setZweitkorrektur(i, p[i]+1);
								break;
							}
						else
							k.setZweitkorrektur(i, p[i]);
					}
				} else {
					for(int i=0; i<Helper.NTASKS; i++)
						k.setZweitkorrektur(i, p[i]);
				}
				out.produce(k);
			}
			catch(InterruptedException e) {
				System.exit(2);
			}
		}
	}
}
