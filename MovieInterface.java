import java.util.List;

public interface MovieInterface extends Comparable<MovieInterface>{

	public String getTitle();
	public int getYear();
	public List<String> getGenres();
	public String getDirector();
	public String getDescription();
	public double getAvgVote();

	public int compareTo(MovieInterface e);
}
