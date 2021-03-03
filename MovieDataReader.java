// --== CS400 File Header Information ==--
// Name: Maaz Amin
// Email: mamin6@wisc.edu
// Team: DD Red
// Role: Data Wrangler
// TA: Dan
// Lecturer: Gary
// Notes to Grader: N/A

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class MovieDataReader implements MovieDataReaderInterface {

	@Override
	public List<MovieInterface> readDataSet(Reader inputFileReader)
			throws FileNotFoundException, IOException, DataFormatException {
		List<MovieInterface> movies = new ArrayList<MovieInterface>();
		String[] text = readInputReader(inputFileReader).split("\n");
		int cutoff = text[0].split(",").length;
		for (int i = 1; i < text.length; i++) {
			ArrayList<String> info = parseMovie(text[i]);
			if (info.size() != cutoff) {
				throw new DataFormatException("Invalid Number of Columns");
			}
			movies.add(new Movie(info.get(0), Integer.parseInt(info.get(2)), info.get(7), info.get(11), info.get(12), info.get(3).split(",")));
		}
		return movies;
	}
	public String readInputReader(Reader r) throws IOException {
		StringBuffer toReturn = new StringBuffer();
		int z = 0;
		while ((z = r.read()) != -1) {
			toReturn.append((char) z);
		}
		return toReturn.toString();
	}
	public ArrayList<String> parseMovie(String e) {
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
}
