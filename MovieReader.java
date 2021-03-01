import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MovieReader {

	public static void init(String path) {
		Movie.movies = new ArrayList<Movie>();
		String[] text = read(path).split("\n");
		for (int i = 0; i < text.length; i++) {
			String[] textSplit = text[i].split(",");
			new Movie(textSplit[0], Integer.parseInt(textSplit[1]), textSplit[2], textSplit[3], textSplit[4]);
		}
	}
	public static String read(String path) {
		System.out.println("Reading file " + path);
		StringBuilder sb = new StringBuilder();
		try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line = reader.readLine();
			sb.append(line);
			sb.append("\n");
			while (line != null) {
				line = reader.readLine();
				if (line != null) {
					sb.append(line);
					sb.append("\n");
				}
			}
			System.out.println("Finished reading file " + path);
			reader.close();
		} catch (IOException e) {
			System.out.println("File Not Found");
			sb.append("404 for " + path);
			e.printStackTrace();
		}
		return sb.toString();
	}
}
