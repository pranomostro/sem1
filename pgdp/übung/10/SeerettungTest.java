import dijkstra_angabe.*;
import java.util.LinkedList;
import java.util.List;

public class SeerettungTest {
	static Eisscholle[] eisschollen;
	static List<Seeweg> seewege;

	public static void setUp() throws Exception {
		eisschollen = new Eisscholle[6];
		seewege = new LinkedList<>();

		for (char c = 'A'; c <= 'F'; c++) {
			eisschollen[c - 'A'] = new Eisscholle("" + c);
		}
	}

	public static void findeWegBeispiel() throws Exception {
		seewege.add(new Seeweg(10, eisschollen[0], eisschollen[1]));
		seewege.add(new Seeweg(12, eisschollen[1], eisschollen[3]));
		seewege.add(new Seeweg(15, eisschollen[0], eisschollen[2]));
		seewege.add(new Seeweg(10, eisschollen[2], eisschollen[4]));
		seewege.add(new Seeweg(1, eisschollen[3], eisschollen[5]));
		seewege.add(new Seeweg(5, eisschollen[5], eisschollen[4]));
		seewege.add(new Seeweg(2, eisschollen[3], eisschollen[4]));
		seewege.add(new Seeweg(15, eisschollen[1], eisschollen[5]));

		List<Eisscholle> schollen = Seerettung.findeWeg(eisschollen, seewege, 0, 4);

		if(4!=schollen.size()) {
			System.out.println("falsche anzahl von schollen im ergebnis");
			return;
		}
		if(eisschollen[0]!=schollen.get(0)) {
			System.out.println("falsche erste scholle");
			return;
		}
		if(eisschollen[1]!=schollen.get(1)) {
			System.out.println("falsche zweite scholle");
			return;
		}
		if(eisschollen[3]!=schollen.get(2)) {
			System.out.println("falsche dritte scholle");
			return;
		}
		if(eisschollen[4]!=schollen.get(3)) {
			System.out.println("falsche vierte scholle");
			return;
		}
	}

	public static void main(String[] args) {
		try {
			setUp();
			findeWegBeispiel();
		} catch(Exception e) {
			System.out.println("exception wurde geworfen");
		}
	}
}
