public class Video {
	private String titel;
	private String[] genres;
	private static int id=0;
	private int thisid, ng;

	public Video(String titel) {
		ng=0;
		genres=new String[5];
		this.titel=titel;
		thisid=id;
		id++;
	}

	public String getTitel() {
		return titel;
	}

	public int getId() {
		return thisid;
	}

	public String[] getGenres() {
		return genres;
	}

	public int addGenre(String genre) {
		if(ng>=5)
			return -1;
		for(int i=0; i<ng; i++)
			if(genres[i]==genre)
				return -1;
		genres[ng]=genre;
		ng++;
		return ng;
	}
}
