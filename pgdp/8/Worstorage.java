public class Worstorage {
	Penguin[] store;
	int[] count;

	public Worstorage() {
		store=new Penguin[1];
		count=new int[1];
	}

	public void insert(Penguin penguin) {
		int topos=1;
		int layer=0;
		while(store[topos-1]!=null) {
			if(penguin.compareTo(store[topos-1])==0)
				return;
			else if(penguin.compareTo(store[topos-1])<0)
					topos*=2;
			else
					topos=(topos*2)+1;
			layer++;

			if(layer>=count.length) {
				int[] newcount=new int[layer+1];
				Penguin[] newstore=new Penguin[2*store.length+1];
				for(int i=0; i<count.length; i++)
					newcount[i]=count[i];
				for(int i=0; i<store.length; i++)
					newstore[i]=store[i];
				count=newcount;
				store=newstore;
			}
		}
		store[topos-1]=penguin;
		count[layer]++;
	}

	public boolean find(Penguin penguin) {
		return findRec(penguin, 1)>=0;
	}

	public int findRec(Penguin penguin, int pos) {
		if(pos-1>=store.length||store[pos-1]==null)
			return -1;
		else if(penguin.compareTo(store[pos-1])==0)
			return pos-1;
		else if(penguin.compareTo(store[pos-1])<0)
			return findRec(penguin, pos*2);
		else if(penguin.compareTo(store[pos-1])>0)
			return findRec(penguin, pos*2+1);
		return -1;
	}

	public void remove(Penguin penguin) {
		int i=findRec(penguin, 1);
		if(i<0)
			return;
		pullUp(i+1);
		reBuild();
	}

	public void pullUp(int pos) {
		int left=2*pos;
		int right=2*pos+1;

		if(pos-1>=store.length)
			return;

		if(left-1>=store.length||(store[left-1]==null&&store[right-1]==null)) {
			store[pos-1]=null;
			return;
		}

		if(store[right-1]==null) {
			store[pos-1]=store[left-1];
			store[left-1]=null;
			pullUp(left);
		} else if(store[left-1]==null) {
			store[pos-1]=store[right-1];
			store[right-1]=null;
			pullUp(right);
		} else if(store[pos-1].compareTo(store[right-1])<=0) {
			store[pos-1]=store[left-1];
			store[left-1]=null;
			pullUp(left);
		} else {
			store[pos-1]=store[right-1];
			store[right-1]=null;
			pullUp(right);
		}
	}

	public void reBuild() {
		int i, j;
		int[] newcount;
		Penguin[] newstore;

		for(i=0; i<count.length; i++)
			count[i]=0;

		reBuildCount(0, 1);

		for(i=0; i<count.length; i++)
			if(count[i]==0)
				break;

		newcount=new int[i];

		for(j=0; j<newcount.length; j++)
			newcount[j]=count[j];

		newstore=new Penguin[(int)Math.pow(2, i)-1];

		for(j=0; j<newstore.length; j++)
			newstore[j]=store[j];

		count=newcount;
		store=newstore;
	}

	public void reBuildCount(int layer, int pos) {
		if(pos-1>=store.length||store[pos-1]==null)
			return;

		count[layer]++;

		int left=2*pos;
		int right=2*pos+1;

		reBuildCount(layer+1, left);
		reBuildCount(layer+1, right);
	}

	@Override
	public String toString() {
		String res="";
		for(int i=0; i<store.length; i++) {
			if(store[i]!=null)
				res+=store[i].getCuddliness();
			if(i<store.length-1)
				res+=",";
		}
		return res;
	}

	// Zum Testen (Code sollte außerhalb der zu testenden Klasse liegen)
	public static void main(String[] args) {
		Test.main();
	}
}

class Penguin implements Comparable<Penguin> {

	private int cuddliness;

	public Penguin(int cuddliness) {
		this.cuddliness = cuddliness;
	}

	public int getCuddliness() {
		return this.cuddliness;
	}

	public void setCuddliness(int cuddliness) {
		this.cuddliness = cuddliness;
	}

	// Note: this class has a natural ordering that is inconsistent with equals.
	public int compareTo(Penguin other) {
		int oc = other.cuddliness;
		if (cuddliness < oc)
			return -1;
		if (cuddliness > oc)
			return 1;
		return 0;
	}
}

class Test {
	public static void main() {
		Worstorage w=new Worstorage();
		w.insert(new Penguin(3));
		MiniJava.writeConsole(w.toString()+"\n");
		w.insert(new Penguin(2));
		MiniJava.writeConsole(w.toString()+"\n");
		w.insert(new Penguin(1));
		MiniJava.writeConsole(w.toString()+"\n");
		w.insert(new Penguin(5));
		MiniJava.writeConsole(w.toString()+"\n");
		w.remove(new Penguin(3));
		MiniJava.writeConsole(w.toString()+"\n");
		w.insert(new Penguin(4));
		MiniJava.writeConsole(w.toString()+"\n");
		w.insert(new Penguin(8));
		MiniJava.writeConsole(w.toString()+"\n");
		w.insert(new Penguin(3));
		MiniJava.writeConsole(w.toString()+"\n");
		w.insert(new Penguin(4));
		MiniJava.writeConsole(w.toString()+"\n");
		w.remove(new Penguin(5));
		MiniJava.writeConsole(w.toString()+"\n");
		w.insert(new Penguin(7));
		MiniJava.writeConsole(w.toString()+"\n");
	}
}
