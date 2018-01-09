package dijkstra_angabe;

public class Eisscholle {
	private int distance;
	private Eisscholle vorgaenger;
	private final String name;

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
}
