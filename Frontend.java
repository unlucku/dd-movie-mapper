import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Frontend {
	private Backend backend;
	private Scanner input;


	/**
	 * Frontend interface for picking movie genres
	 */
	public void genreMode() {
		List<String> allGenres = backend.getAllGenres();

		// Intro / how to use
		System.out.println("----------------------------------------------------------------");
		System.out.println("| To select/unselect a genre, type the number corresponding to  |");
		System.out.println("| the genre you wish to select/deselect.                        |");
		System.out.println("----------------------------------------------------------------");
		System.out.println();


		// input options
		for (int i = 0; i < allGenres.size(); i++) {

			// genre to be printed
			String genre = allGenres.get(i);

			// Prints genre along with corresponding index
			System.out.print(String.valueOf(i+1) + ". ");
			System.out.print(genre);

			if (backend.getGenres().contains(genre))
				System.out.print(" ----> SELECTED");
			else
				System.out.print(" ----> ");
			System.out.println();
		}
		System.out.println();
		System.out.println("To return to the base mode, type the \"x\" key");
		System.out.println();


		while (input.hasNextInt()) {

			try {
				// Determines genre by what number was entered
				String selectedGenre = allGenres.get(input.nextInt()-1);

				if (backend.getGenres().contains(selectedGenre)) {
					// Removes genre if selected
					backend.removeGenre(selectedGenre);
				} else {
					// Adds genre if not selected
					backend.addGenre(selectedGenre);
				}

				// Reloads options
				genreMode();
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("ERROR: Please select a number 1 - " + allGenres.size());
			}
		}

		// if letter is input
		while (input.hasNextLine()) {
			String key = input.nextLine();

			// x key pressed --> return to base mode
			if (key.equals("x")) baseMode(0);
		}

	}

	public void ratingMode() {

		// Intro / how to use
		System.out.println("----------------------------------------------------------------");
		System.out.println("| To select/unselect a rating, type the number corresponding   |");
		System.out.println("| to the range of ratings you wish to select/unselect.         |");
		System.out.println("----------------------------------------------------------------\n");

		// ArrayList of whole numbers
		ArrayList<Integer> wholeNumberRatings = new ArrayList<Integer>();

		// Iterates through list of ratings
		for (int i = 0; i < backend.getAvgRatings().size(); i++) {
			// converts String to float
			float ratingAsFloat = Float.parseFloat(backend.getAvgRatings().get(i));

			// converts float to int and adds it to ArrayList of whole numbers (if not already added)
			wholeNumberRatings.add((int) ratingAsFloat);
		}

		// Input options
		for (int i = 0; i < 10; i++)  {
			// converts i + 1 to String
			String numAsString = String.valueOf(i+1);

			// Prints Input key
			System.out.print(numAsString + ": ");
			// Prints corresponding rating
			System.out.print(numAsString + " - " + String.valueOf(i+1.999f));

			// Indicator based on whether or not rating is selected
			if (wholeNumberRatings.contains(i+1)) System.out.print(" ----> SELECTED");

			System.out.println();
		}
		System.out.println();

		while (input.hasNextInt()) {
			int numberInput = input.nextInt();

			if (numberInput < 10) {

				if (wholeNumberRatings.contains(numberInput)) {

					for (String rating : backend.getAvgRatings()) {

						if (rating.substring(0, 1).equals(String.valueOf(numberInput))) 
							backend.removeAvgRating(rating);

					}

				} else {
					backend.addAvgRating(String.valueOf(numberInput));
				}
				ratingMode();
			}

		}

		while (input.hasNextLine()) {
			String key = input.nextLine();

			if (key.equals("x")) baseMode(0);
		}
	}

	/**
	 * Default interface for movie mapper (think home screen)
	 *
	 * @param startingIndex the rank of the first movie to be shown
	 */
	public void baseMode(int startingIndex) {

		// Welcome message
		System.out.println("----------------------------------------------------------------");
		System.out.println("|               WELCOME TO THE CS400 MOVIE MAPPER!             |");
		System.out.println("----------------------------------------------------------------\n");

		// List of top 3 movies (by average rating)
		List<MovieInterface> movieList = backend.getThreeMovies(startingIndex);

		// Does not loop through list ifempty
		if (movieList.isEmpty()) {
			System.out.println("No movies to display\n");
		} else {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Top 3 Movies: \n");

			// iterates through list of movies
			int rank = 1;
			for (MovieInterface movie : movieList) {
				System.out.print(rank + ". ");
				System.out.print(movie.getTitle() + " (");
				System.out.print(movie.getAvgVote() + ")\n");
				rank++;
			}
			System.out.println("----------------------------------------------------------------\n");
		}

			

		// Mode options
		System.out.println("g: Genre Mode");
		System.out.println("r: Rating Mode");
		System.out.println("x: Exit\n");

		// if input is a number
		while(input.hasNextInt()) {
			int newIndex = input.nextInt() - 1;

			if (newIndex >= movieList.size()) continue;

			// scrolls to given number input
			baseMode(newIndex);
		}

		// if input is a letter
		while (input.hasNextLine()) {
			String key = input.nextLine();

			// x key pressed --> program exits
			if (key.equals("x")) {
				// Exit message
				System.out.println("Exiting...");
				return;
				//System.exit(0);
			};

			// g key pressed --> genre mode
			if (key.equals("g")) genreMode();

			// r key pressed --> rating mode
			if (key.equals("r")) ratingMode();
		}

	}

	public void run(Backend backend) {

		this.backend = backend;
		this.input = new Scanner(System.in);

		baseMode(0);

	}

	public static void main(String[] args) {
		new Frontend().run(new Backend(args));
	}
}
