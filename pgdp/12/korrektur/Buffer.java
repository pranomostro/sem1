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
		MiniJava.writeConsole("creating new buffer with capacity " + n + "\n");
	}

	public void produce(Klausur k) throws InterruptedException {
		free.down();
		synchronized(this) {
			a[last]=k;
			last=(last+1)%cap;
		}
		MiniJava.writeConsole("added value to the buffer\n");
		occupied.up();
	}

	public Klausur consume() throws InterruptedException {
		Klausur result;
		occupied.down();
		synchronized(this) {
			result=a[first];
			first=(first+1)%cap;
		}
		MiniJava.writeConsole("removed value from the buffer\n");
		free.up();
		return result;
	}
}
