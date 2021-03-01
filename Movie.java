import java.util.ArrayList;
import java.util.List;

public class Movie implements MovieInterface {
	public static ArrayList<Movie> movies = new ArrayList<Movie>();
	String title;
	int year;
	List<String> genre;
	String director;
	double avgVote;
	String description;

	public Movie(String title, int year, String director, String description, String genre) {
		this.title = title;
		this.year = year;
		this.director = director;
		this.description = description;
		this.avgVote = 0.0;
		this.genre = new ArrayList<String>();
		this.genre.add(genre);
		movies.add(this);
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
	public double getAvgVote() {
		return avgVote;
	}

	@Override
	public int compareTo(MovieInterface e) {
		//todo
		return 0;
	}

}
