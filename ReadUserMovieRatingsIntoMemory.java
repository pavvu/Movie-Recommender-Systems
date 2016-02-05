import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class ReadUserMovieRatingsIntoMemory 
{
	public static HashMap<String, HashMap<String, Integer>> readUserMovieRatings() throws Exception
	{
		String csvFile = "C:\\Users\\HP PC\\workspace\\ML\\Training.txt";
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		String line="", cvsSplitBy=",";
		String[] userRatings;
		String pid, mid ;
		Integer rating;
		// Data structure to store user movie ratings
		HashMap<String, HashMap<String, Integer>> user_movie_rating = new HashMap<>();
		HashMap<String, Integer> temp = new HashMap<>();
		line=br.readLine();
		while ((line = br.readLine()) != null) 
		{
		 //System.out.println(line);
		 userRatings= line.split(cvsSplitBy);
		 pid=userRatings[0];
		 mid = userRatings[1];
		 rating = Integer.parseInt(userRatings[2]);
		 //populating user movie ratings data structure
		 //If the user is seen for the first time in Training data
		 if(!user_movie_rating.containsKey(pid))
		 {
			 temp = new HashMap<>();
			 temp.put(mid, rating);
			 user_movie_rating.put(pid,temp);
		 }
		 //Existing user is seen; 
		 else
		 {
			 user_movie_rating.get(pid).put(mid, rating);
		 }
	    }
		return user_movie_rating;
	}

}
