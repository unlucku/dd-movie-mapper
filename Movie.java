// --== CS400 File Header Information ==--
// Name: Maaz Amin
// Email: mamin6@wisc.edu
// Team: DD Red
// Role: Data Wrangler
// TA: Dan
// Lecturer: Gary
// Notes to Grader: N/A

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
	public Integer getYear() {
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
		return this.avgVote;
	}

	@Override
	public int compareTo(MovieInterface e) {
		if (this.avgVote > e.getAvgVote()) {
			return -1;
		}
		else if (this.avgVote < e.getAvgVote()) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "Name: " + this.title + "\n" +
				"Year: " + this.year + "\n" +
				"Genre: " + String.join(", ", this.genre) + "\n" +
				"Director: " + this.director + "\n" +
				"Description: " + this.description + "\n" +
				"Avg. Vote: " + this.avgVote + "\n";
	}
}
