package dijkstra_angabe;

public class Eisscholle {

	public final static int UNBEKANNT = 0;
	public final static int VERMUTET = 1;
	public final static int BEKANNT = 2;

	private int distance;
	private Eisscholle vorgaenger;
	private final String name;
	private int state = UNBEKANNT;

	public Eisscholle(String n) {
		name=n;
	}

	public String getName() {
		return name;
	}

	public void setVorgaenger(Eisscholle e) {
		vorgaenger=e;
	}

	public Eisscholle getVorgaenger() {
		return vorgaenger;
	}

	public void setDistance(int i) {
		if(i<0)
			distance=Integer.MAX_VALUE;
		else
			distance=i;
	}

	public int getDistance() {
		return distance;
	}

	public void setState(int n) {
		if(n<0||n>2)
			return;
		state=n;
	}

	public int getState() {
		return state;
	}
}
