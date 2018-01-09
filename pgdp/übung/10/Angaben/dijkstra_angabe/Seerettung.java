package dijkstra_angabe;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Seerettung {
	private static PriorityQueue<Eisscholle> nachbarschollen;

	public static List<Eisscholle> findeWeg
	 (Eisscholle[] eisschollen,
		List<Seeweg> seewege,
		int startIndex,
		int endIndex)
	{
		int dn;
		Eisscholle e0, last, cur;
		List<Seeweg> nf;
		ArrayList<Eisscholle> res;
		HashSet<Eisscholle> bekannt=new HashSet<>();

		nachbarschollen = new PriorityQueue<>(new EisschollenComparator());

		e0=eisschollen[startIndex];
		last=eisschollen[endIndex];
		e0.setDistance(0);

		for(int i=1; i<eisschollen.length; i++) {
			eisschollen[i].setDistance(-1);
			eisschollen[i].setVorgaenger(null);
		}

		nachbarschollen.add(e0);

		while(nachbarschollen.size()>0) {
			cur=nachbarschollen.poll();

			System.out.println("smallest element in the queue (now known) was " + cur.getName());

			if(cur==last)
				break;

			bekannt.add(cur);

			nf=findSeewegeFromEisscholle(seewege, cur);

			for(Seeweg s: nf) {
				dn=s.getFrom().getDistance()+s.getDistance();
				if(dn<s.getTo().getDistance()) {
					s.getTo().setDistance(dn);
					s.getTo().setVorgaenger(s.getFrom());
				}
				if(!bekannt.contains(s.getTo()))
					nachbarschollen.add(s.getTo());
			}
		}

		res=new ArrayList<>();

		for(cur=last; cur!=null; cur=cur.getVorgaenger()) {
			System.out.println("smallest path contains " + cur.getName());
			res.add(0, cur);
		}

		return res;
	}

	private static List<Seeweg> findSeewegeFromEisscholle(List<Seeweg> seewege, Eisscholle eisscholle) {
		return seewege.stream()
				.filter(o -> o.getFrom().equals(eisscholle))
				.collect(Collectors.toList());
	}
}
