public class RW {
	/* TODO: add a flag that is set after startWrite() is called,
		causing all startRead()'s to wait until it is unset,
		which is then unset in endRead()
	*/

	private boolean writeclaim=false;
	private int readers=0;

	public synchronized void startRead() throws InterruptedException {
		MiniJava.writeConsole("sr\n");
		while(readers<0||writeclaim==true) wait();
		readers++;
	}

	public synchronized void endRead() {
		MiniJava.writeConsole("er\n");
		readers--;
		if(readers==0) notifyAll();
	}

	public synchronized void startWrite() throws InterruptedException {
		MiniJava.writeConsole("sw\n");
		writeclaim=true;
		while(readers!=0) wait();
		readers=-1;
	}

	public synchronized void endWrite() {
		MiniJava.writeConsole("ew\n");
		readers=0;
		writeclaim=false;
		notifyAll();
	}
}
