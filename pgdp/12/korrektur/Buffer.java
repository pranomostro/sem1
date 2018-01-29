public class Buffer {
	private int cap, first, last;
	private Sema free, occupied;
	private Klausur[] a;
	public Buffer(int n) {
		cap=n;
		first=last=0;
		a=new Klausur[n];
		free=new Sema(n);
		occupied=new Sema(0);
		MiniJava.writeConsole("New buffer with capacity " + n + "\n");
	}

	public void produce(Klausur k) throws InterruptedException {
		free.down();
		synchronized(this) {
			a[last]=k;
			last=(last+1)%cap;
		}
		occupied.up();
	}

	public Klausur consume() throws InterruptedException {
		Klausur result;
		occupied.down();
		synchronized(this) {
			result=a[first];
			first=(first+1)%cap;
		}
		free.up();
		return result;
	}
}
