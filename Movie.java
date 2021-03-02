import java.util.ArrayList;
import java.util.List;

public class Movie implements MovieInterface {
	String title;
	int year;
	List<String> genre;
	String director;
	float avgVote;
	String description;

	public Movie(String title, int year, String director, String description, String rating, String... genre) {
		this.title = title;
		this.year = year;
		this.director = director;
		this.description = description;
		this.avgVote = Float.parseFloat(rating);
		this.genre = new ArrayList<String>();
		for (int i = 0; i < genre.length; i++)
			this.genre.add(genre[i]);
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public int getYear() {
		return this.year;
	}

	@Override
	public List<String> getGenres() {
		return this.genre;
	}

	@Override
	public String getDirector() {
		return this.director;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public Float getAvgVote() {
		return avgVote;
	}

	@Override
	public int compareTo(MovieInterface e) {
		if (this.avgVote > e.getAvgVote()) {
			return 1;
		}
		else if (this.avgVote < e.getAvgVote()) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
