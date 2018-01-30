public class Count {
	private int count[];

	public Count(int n) {
		count=new int[n];
	}

	public synchronized void inc(int i) {
		count[i]++;
	}

	public int getn(int i) {
		return count[i];
	}
}
