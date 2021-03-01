// --== CS400 File Header Information ==--
// Name: Michael Corbishley
// Email: mcorbishley@wisc.edu
// Team:  Red Team
// Group: DD
// TA: dkiel2@wisc.edu
// Lecturer: Florian
// Notes to Grader: N/A
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Backend {
	private HashMap<String, List<Movie>> genres; //hash table using genres as keys
	private HashMap<String, List<Movie>> ratings; //hash table using ratings as keys
	private List<MovieInterface> allMovies; //all movies in the incoming data file
	private List<String> setGenres; //all genres that are currently set in the hash table
	private List<String> setRatings; //all ratings that are currently set in the hash table

	public Backend(String[]args) {
		genres = new HashMap<String, List<Movie>>(); //initialize
		ratings = new HashMap<String, List<Movie>>(); //initialize
		setRatings = new ArrayList<String>(); //initialize
		setGenres = new ArrayList<String>(); //initialize
		allMovies = new ArrayList<MovieInterface>();
		MovieReader.init(args[0]);
		allMovies.addAll(Movie.movies);
	}
	public void addGenre(String genre) {
		List<Movie> selectedGenres = new ArrayList<Movie>();
		for(int i=0;i<allMovies.size();i++) {
			if(allMovies.get(i).getGenres().contains(genre))
				selectedGenres.add((Movie)allMovies.get(i));
		}
		genres.put(genre, selectedGenres);
		setGenres.add(genre);
	}

	/**
	 * addAvgRating takes a parameter String rating
	 * and uses it to map all the movies with the given
	 * rating into one group and uses the rating
	 * as the hash key for where the values are stored.
	 * The rating is also added to the setRatings list.
	 */
	public void addAvgRating(String rating) {
		setRatings.add(rating);
		ArrayList<Movie> selectedRatings = new ArrayList<Movie>();
		for(int i=0;i<allMovies.size();i++) {
			String k = Float.toString((allMovies.get(i).getAvgVote()));
			String range = k.substring(0,1);
			if(range.equals(k))
				selectedRatings.add((Movie)allMovies.get(i));
		}
		ratings.put(rating, selectedRatings);

	}

	/**
	 * removeGenre removes a set genre from the hash table
	 * and all the values associated with it. It is also
	 * removed from the setGenres ArrayList.
	 */
	public void removeGenre(String genre) {
		genres.remove(genre);
		setGenres.remove(genre);
	}

	/**
	 * removeGenre removes a set rating from the hash table
	 * and all the values associated with it. It is also
	 * removed from the setRatings ArrayList.
	 */
	public void removeAvgRating(String rating) {
		ratings.remove(rating);
		setRatings.remove(rating);
	}

	/**
	 * @returns the list of genres currently set in the hash table
	 */
	public List<String> getGenres() {
		return setGenres;
	}

	/**
	 * @returns the list of avgRatings currently set in the hash table
	 */
	public List<String> getAvgRatings() {
		return setRatings;
	}

	/**
	 * @returns the total number of movies in the resulting
	 * set (any movies with genres or avg ratings set)
	 */
	public int getNumberOfMovies() {
		int total = 0; //integer used to count total number of movies in the resulting set
		for(int i=0;i<allMovies.size();i++) {
			if(setGenres.contains(allMovies.get(i).getGenres())||setRatings.contains(Float.toString((allMovies.get(i).getAvgVote()))))
				total++;
		}
		return total; //total number of movies in the resulting set
	}

	/**
	 * @returns a list of 3 movies from the resulting
	 * set (any movies with genres or avg ratings set) starting
	 * from the int startingIndex parameter and putting them
	 * in order from greatest to least avgVote.
	 */
	public List<MovieInterface> getThreeMovies(int startingIndex) {
		List<MovieInterface> threeMovies = new ArrayList<MovieInterface>(); //list of 3 movies to be returned
		List<MovieInterface> withinParam = new ArrayList<MovieInterface>(); //list of movies that fit the parameters
		for(int i=0;i<allMovies.size();i++) {
			if(setGenres.contains(allMovies.get(i).getGenres())||setRatings.contains(Float.toString((allMovies.get(i).getAvgVote())))) {
				withinParam.add(allMovies.get(i));
			}
		}

		MovieInterface movie1 = withinParam.get(startingIndex);
		Float vote1 = movie1.getAvgVote();
		MovieInterface movie2 = withinParam.get(startingIndex+1);
		Float vote2 = movie2.getAvgVote();
		MovieInterface movie3 = withinParam.get(startingIndex+2);
		Float vote3 = movie3.getAvgVote();
		Float max = Math.max(Math.min(vote1, vote2), vote3);

		if(max==vote1) {
			threeMovies.add(movie1);
			Float max2 = Math.max(vote2, vote3);
			if(max2==vote2) {
				threeMovies.add(movie2);
				threeMovies.add(movie3);
			}
			else {
				threeMovies.add(movie3);
				threeMovies.add(movie2);
			}
		}

		if(max==vote2) {
			threeMovies.add(movie2);
			Float max3 = Math.max(vote1, vote3);
			if(max3==vote1) {
				threeMovies.add(movie1);
				threeMovies.add(movie3);
			}
			else {
				threeMovies.add(movie3);
				threeMovies.add(movie1);
			}
		}

		if(max==vote3) {
			threeMovies.add(movie3);
			Float max4 = Math.max(vote1, vote2);
			if(max4==vote1) {
				threeMovies.add(movie1);
				threeMovies.add(movie2);
			}
			else {
				threeMovies.add(movie2);
				threeMovies.add(movie1);
			}
		}
		return threeMovies;
	}

	/**
	 * getAllGenres loops through the allMovies array list
	 * and if the allGenres array list doesn't contain
	 * one of the movies genres, then it is added to the list that
	 * is returned at the end of the method.
	 * @returns a list of all genres across all the movies
	 */
	public List<String> getAllGenres() {
		List<String> allGenres = new ArrayList<String>(); //list of all the genres to be returned at the end
		for(int i=0;i<allMovies.size();i++) { //loop through all the movie objects
			if(!allGenres.contains(allMovies.get(i).getGenres())) //if allGenres doesn't contain the genre for the given movie object
				allGenres.addAll(allMovies.get(i).getGenres()); //the genre is then added to the set
		}
		return allGenres; //list of all the genres
	}

}
