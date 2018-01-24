import java.util.HashSet;
import java.util.Random;

public class SuchtbaumTest {
	private static Random random = new Random();

	public static void testContains() throws InterruptedException {
		HashSet<Integer> testSet = new HashSet<>();
		int n = 100000;
		for (int i = 0; i < n; i++)
			testSet.add(random.nextInt(20*n));
		Suchtbaum<Integer> suchti = new Suchtbaum<>();
		for(Integer i : testSet)
			suchti.insert(i);
		for (int i = 0; i < n; i++)
			if(testSet.contains(i)!=suchti.contains(i)) {
				 MiniJava.writeConsole("should contain " + i + "\n");
				System.exit(1);
			}
	}

	public static void testContainsRemove() throws InterruptedException {
		HashSet<Integer> testSet = new HashSet<>();
		int n = 100000;
		for (int i = 0; i < n; i++)
			testSet.add(random.nextInt(20*n));
		Suchtbaum<Integer> suchti = new Suchtbaum<>();
		for(Integer i : testSet)
			suchti.insert(i);
		for (int i = 0; i < n; i++) {
			int next = random.nextInt(20*n);
			if(testSet.contains(next)) {
				suchti.remove(next);
				testSet.remove(next);
			}
		}
		for (int i = 0; i < n; i++)
			if(testSet.contains(i)!=suchti.contains(i)) {
				MiniJava.writeConsole("should contain " + i + "\n");
				System.exit(1);
			}
	}

	public static void main(String[] args) {
		try {
			testContains();
			testContainsRemove();
		} catch(InterruptedException e) {
			MiniJava.writeConsole("was interrupted\n");
		}
	}
}
