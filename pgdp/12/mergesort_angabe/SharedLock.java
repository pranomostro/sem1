public class SharedLock {
	private int cap;

	public SharedLock(int n) {
		cap=n;
	}

	public synchronized void dec() {
		if(cap>0)
			cap--;
	}

	public int get() {
		return cap;
	}
}
