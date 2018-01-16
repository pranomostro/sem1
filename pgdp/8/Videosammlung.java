import java.util.ArrayList;

public class Videosammlung {
	private Video[] videos;
	private int verbleibende;

	public Videosammlung(int n) {
		verbleibende=n;
		videos=new Video[n];
	}

	int addVideo(Video v) {
		int i;
		if(verbleibende==0)
			return -1;
		for(i=0; i<videos.length; i++)
			if(videos[i]==null) {
				videos[i]=v;
				break;
			}
		verbleibende--;
		return i;
	}

	public Video verkaufen(int index) {
		if(index>=videos.length||videos[index]==null)
			return null;

		Video res=videos[index];
		videos[index]=null;
		verbleibende++;

		return res;
	}

	public Video verkaufen(String titel) {
		for(int i=0; i<videos.length; i++)
			if(videos[i]!=null&&titel.equals(videos[i].getTitel()))
				return verkaufen(i);
		return null;
	}

	public Video[] getVideos() {
		return videos;
	}

	public int getVerbleibende() {
		return verbleibende;
	}

	public String[] videosInGenre(String genre) {
		String [] genres;
		String [] titles;
		ArrayList<String> res=new ArrayList<>();

		for(int i=0; i<videos.length; i++)
			if(videos[i]!=null) {
				genres=videos[i].getGenres();
				for(int j=0; j<genres.length; j++)
					if(genres[j]!=null&&genres[j].equals(genre))
						res.add(videos[i].getTitel());
			}

		titles=new String[res.size()];
		for(int i=0; i<res.size(); i++)
			titles[i]=res.get(i);

		for(int i=0; i<titles.length; i++)
			MiniJava.writeConsole(titles[i] + "\n");

		return titles;
	}
}
