public class ParallelMergeSort {
	public static int numberOfThreads=8;

	public static void mergeSort(int[] arr) {
		if(arr.length<numberOfThreads) {
			NormalMergeSort.mergeSort(arr);
			return;
		}

		int[] index=new int[numberOfThreads+1];
		int i=0, j=0;
		for(i=0; i<numberOfThreads; i++) {
			index[i]=j;
			j+=arr.length/numberOfThreads;
		}
		index[numberOfThreads]=arr.length;

		SharedLock sl=new SharedLock(numberOfThreads);
		MergeSorter[] ms=new MergeSorter[numberOfThreads];
		for(i=0; i<numberOfThreads; i++)
			ms[i]=new MergeSorter(sl, arr, index[i], index[i+1]-1);

		for(i=0; i<numberOfThreads; i++)
			ms[i].start();

		while(sl.get()>0)
			Thread.yield();

		for(i=1; i<numberOfThreads; i++)
			NormalMergeSort.merge(arr, 0, index[i]-1, index[i+1]-1);
	}
}
