// --== CS400 File Header Information ==--
// Name: Michael Corbishley
// Email: mcorbishley@wisc.edu
// Team:  Red Team
// Group: DD
// TA: dkiel2@wisc.edu
// Lecturer: Florian
// Notes to Grader: N/A
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.DataFormatException;

public class Backend implements BackendInterface{
	private HashTableMap<String, List<Movie>> genres; //hash table using genres as keys
	private HashTableMap<String, List<Movie>> ratings; //hash table using ratings as keys
	private MovieDataReader dataReader; //the reader we will use to pull our info from the file
	private List<MovieInterface> allMovies; //all movies in the incoming data file
	private List<String> setGenres; //all genres that are currently set in the hash table
	private List<String> setRatings; //all ratings that are currently set in the hash table

	public Backend(String[]args) {
		genres = new HashTableMap(); //initialize
		ratings = new HashTableMap(); //initialize
		setRatings = new ArrayList<String>(); //initialize
		setGenres = new ArrayList<String>(); //initialize
		try {
			File data = new File(args[0]); //file that we're working with
			FileReader fileReader = new FileReader(data); //fileReader for that file
			dataReader = new MovieDataReader(); //MovieDataReader initialized
			allMovies = dataReader.readDataSet(fileReader); //list of all movies with their attributes
		}
		catch(FileNotFoundException e) {
			System.out.println("File Not Found"); //if file isn't found when used in constructor
		}
		catch(IOException e) {
			System.out.println("Error Importing file"); //if there's an error importing a file
		}

		catch(DataFormatException e) {
			System.out.println("Invalid format for file"); //if the data format is incorrect
		}
	}

	public Backend(Reader s) {
		genres = new HashTableMap(); //initialize
		ratings = new HashTableMap(); //initialize
		List<String> setRatings = new ArrayList<String>(); //initialize
		List<String> setGenres = new ArrayList<String>(); //initialize
		try {
			dataReader = new MovieDataReader();
			allMovies = dataReader.readDataSet(s);
		}
		catch(FileNotFoundException e) {
			System.out.println("File Not Found"); //if file isn't found when used in constructor
		}
		catch(IOException e) {
			System.out.println("Error Importing file"); //if there's an error importing a file
		}

		catch(DataFormatException e) {
			System.out.println("Invalid format for file"); //if the data format is incorrect
		}
	}

	@Override
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
	@Override
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
	@Override
	public void removeGenre(String genre) {
		genres.remove(genre);
		setGenres.remove(genre);
	}

	/**
	 * removeGenre removes a set rating from the hash table
	 * and all the values associated with it. It is also
	 * removed from the setRatings ArrayList.
	 */
	@Override
	public void removeAvgRating(String rating) {
		ratings.remove(rating);
		setRatings.remove(rating);
	}

	/**
	 * @returns the list of genres currently set in the hash table
	 */
	@Override
	public List<String> getGenres() {
		return setGenres;
	}

	/**
	 * @returns the list of avgRatings currently set in the hash table
	 */
	@Override
	public List<String> getAvgRatings() {
		return setRatings;
	}

	/**
	 * @returns the total number of movies in the resulting
	 * set (any movies with genres or avg ratings set)
	 */
	@Override
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
	@Override
	public List<MovieInterface> getThreeMovies(int startingIndex) {
		List<MovieInterface> threeMovies = new ArrayList<MovieInterface>(); //list of 3 movies to be returned
		List<MovieInterface> withinParam = new ArrayList<MovieInterface>(); //list of movies that fit the parameters
		List<MovieInterface> withinGenre = new ArrayList<MovieInterface>(); //all movies that match at least 1 set genre
		for(int i=0;i<allMovies.size();i++) { //loop through the set of all movies
			List<String> movieIGenres = allMovies.get(i).getGenres(); //all genres associated with the movie at location i
			for(int w=0;w<allMovies.get(i).getGenres().size();w++) { //loop through the list of genres for movie at location i
				if(setGenres.contains(movieIGenres.get(w))) { //if the setGenres contains at least 1 of the genres for movie at location i
					withinGenre.add(allMovies.get(i)); //the movie is then added to within genre
					break; //and we break out of the inside loop
				}
			if(withinGenre.contains(allMovies.get(i))&&setRatings.contains(Float.toString((allMovies.get(i).getAvgVote())))) {
				withinParam.add(allMovies.get(i)); //if movie at location i is in the withinGenres list and 
				//the rating for movie i is in setRatings, it is then added to the withinParam list
				}
			}
		}
		if(withinParam.isEmpty()) //Empty list returned if within
			return withinParam;

		threeMovies.add(withinParam.get(startingIndex));
		threeMovies.add(withinParam.get(startingIndex+1));
		threeMovies.add(withinParam.get(startingIndex+2));
		
		Collections.sort(threeMovies, Collections.reverseOrder());
		return threeMovies; //3 movies that fit the parameter, in order from greatest avgRating to least
	}

	/**
	 * getAllGenres loops through the allMovies array list
	 * and if the allGenres array list doesn't contain
	 * one of the movies genres, then it is added to the list that
	 * is returned at the end of the method.
	 * @returns a list of all genres across all the movies
	 */
	@Override
	public List<String> getAllGenres() {
		List<String> allGenres = new ArrayList<String>(); //list of all the genres to be returned at the end
		for(int i=0;i<allMovies.size();i++) {
			for(String genre : allMovies.get(i).getGenres()) {
				if(!allGenres.contains(genre))
					allGenres.add(genre);
			}
		}
		return allGenres; //list of all the genres
	}

}