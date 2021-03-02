import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MovieReader {
	public static void init(String path, Backend backend) {
		String[] text = read(path).split("\n");
		ArrayList<String> info = new ArrayList<String>();
		for (int i = 1; i < text.length; i++) {
			info = parseMovie(text[i]);
			backend.allMovies.add(new Movie(info.get(0), Integer.parseInt(info.get(2)), info.get(7), info.get(11), info.get(12), info.get(3).split(",")));
		}
	}

	public static ArrayList<String> parseMovie(String e) {
		String[] text = e.split(",");
		ArrayList<String> toReturn = new ArrayList<String>();
		for (int i = 0; i < text.length; i++) {

			if (text[i].startsWith("\"")) { //Parsing for commas within quotes
				String x = text[i].substring(1) + ",";
				for (int a = i+1; a < text.length; a++) {
					if (text[a].endsWith("\"")) {
						i = a;
						if (text[a].startsWith(" ")) {
							x += text[a].substring(1, text[a].length()-1);
						}
						else {
							x+= text[a].substring(0, text[a].length()-1);
						}
						break;
					}
					else if (text[a].startsWith(" ")) {
						x+= text[a].substring(1) + ",";
					}
					else {
						x+= text[a] + ",";
					}
				}
				toReturn.add(x);
			}
			else {
				toReturn.add(text[i]);
			}

		}

		return toReturn;
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
