public class Sema {
	private int waiting, permits;
	public Sema(int n) { permits=n; }
	public synchronized void up() {
		permits++;
		if(waiting>0)
			notifyAll();
	}
	public synchronized void down() throws InterruptedException {
		waiting++;
		while(permits<=0)
			wait();
		waiting--;
		permits--;
	}
}
