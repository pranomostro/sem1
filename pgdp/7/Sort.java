public class Sort {
	public static void main(String[] args) {
		int[] testarr={8, 4, 10, 5, 9, 6, 7, 1, 3, 2, };

		printArr(testarr);
		evenSlowerSort(testarr);
		printArr(testarr);
	}

	private static void printArr(int[] a) {
		for(int i=0; i<a.length; i++)
			MiniJava.writeConsole(a[i] + " ");
		MiniJava.writeConsole("\n");
	}

	private static void slowSort(int[] a) {
		slowSortI(a, 0, a.length-1);
	}

	private static void evenSlowerSort(int[] a) {
		evenSlowerSortI(a, 0, a.length-1);
	}

	private static void slowSortI(int[] a, int b, int e) {
		if(e-b<=0)
			return;
		int mid=b+((e-b)/2);
		slowSortI(a, b, mid);
		slowSortI(a, mid+1, e);
		if(a[mid]>a[e]) {
			int tmp=a[mid];
			a[mid]=a[e];
			a[e]=tmp;
		}
		slowSortI(a, b, e-1);
	}

	private static void evenSlowerSortI(int[] a, int b, int e) {
		if(e-b<=0)
			return;
		int mid1=b+((e-b)/3);
		int mid2=b+((e-b)/3*2);
		evenSlowerSortI(a, b, mid1);
		evenSlowerSortI(a, mid1+1, mid2);
		evenSlowerSortI(a, mid2+1, e);
		if(a[mid1]>a[e]) {
			int tmp=a[mid1];
			a[mid1]=a[e];
			a[e]=tmp;
		}
		if(a[mid2]>a[e]) {
			int tmp=a[mid2];
			a[mid2]=a[e];
			a[e]=tmp;
		}
		evenSlowerSortI(a, b, e-1);
	}
}
