import java.util.HashSet;
import java.util.Random;

public class SuchtbaumTest implements Runnable {
	private static int tn=0;
	private static Suchtbaum<Integer> s;

	public void run() {
		int n=20000, t, v;
		boolean res;

		synchronized(this) {
			t=tn;
			tn++;
		}

		MiniJava.writeConsole("starting thread " + t + "\n");

		for(int i=0; i<n; i++) {
			switch(random.nextInt(3)) {
				case 0:
					v=random.nextInt(n*20);
					try { res=s.contains(v); } catch(InterruptedException e) { System.err.println(e.toString()); break; }
					if(res)
						MiniJava.writeConsole("thread " + t + ": contains " + v + "\n");
					else
						MiniJava.writeConsole("thread " + t + ": doesn't contain " + v + "\n");
					break;
				case 1:
					v=random.nextInt(n*20);
					try { res=s.contains(v); } catch(InterruptedException e) { System.err.println(e.toString()); break; }
					if(res) {
						MiniJava.writeConsole("thread " + t + ": not inserting " + v + ", already present\n");
						break;
					} else {
						MiniJava.writeConsole("thread " + t + ": inserting " + v + "\n");
						try { s.insert(v); } catch(InterruptedException e) { System.err.println(e.toString()); break; }
					}
					break;
				case 2:
					v=random.nextInt(n*20);
					MiniJava.writeConsole("thread " + t + ": removing " + v + "\n");
					try { s.remove(v); } catch(InterruptedException e) { System.err.println(e.toString()); break; }
					break;
				case 3:
					break;
			}
		}
	}

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
		s=new Suchtbaum<>();
		(new Thread(new SuchtbaumTest())).start();
		(new Thread(new SuchtbaumTest())).start();
		(new Thread(new SuchtbaumTest())).start();
		(new Thread(new SuchtbaumTest())).start();
	}
}
