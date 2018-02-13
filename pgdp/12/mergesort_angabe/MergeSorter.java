public class MergeSorter extends Thread {
	private int[] arr;
	private int lo, hi;
	private SharedLock sl;

	public MergeSorter(SharedLock s, int[] arra, int low, int high) {
		lo=low;
		hi=high;
		arr=arra;
		sl=s;
	}

	public void run() {
		NormalMergeSort.mergeSort(arr, lo, hi);
		sl.dec();
	}
}
