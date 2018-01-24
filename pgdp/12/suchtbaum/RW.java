public class RW {
	/* TODO: add a flag that is set after startWrite() is called,
		causing all startRead()'s to wait until it is unset,
		which is then unset in endRead()
	*/

	private int readers=0;

	public synchronized void startRead() throws InterruptedException {
		while(readers<0) wait();
		readers++;
	}

	public synchronized void endRead() {
		readers--;
		if(readers==0) notifyAll();
	}

	public synchronized void startWrite() throws InterruptedException {
		while(readers!=0) wait();
		readers=-1;
	}

	public synchronized void endWrite() {
		readers=0;
		notifyAll();
	}
}
