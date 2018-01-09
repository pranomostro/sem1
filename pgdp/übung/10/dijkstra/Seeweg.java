package dijkstra_angabe;

public class Seeweg {
	private int distance;
	private Eisscholle from;
	private Eisscholle to;

	public Seeweg(int d, Eisscholle f, Eisscholle t) {
		distance=d;
		from=f;
		to=t;
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

	public void setFrom(Eisscholle e) {
		from=e;
	}

	public Eisscholle getFrom() {
		return from;
	}

	public void setTo(Eisscholle e) {
		to=e;
	}

	public Eisscholle getTo() {
		return to;
	}
}
