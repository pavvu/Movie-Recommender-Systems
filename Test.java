import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Test 
{
  public static void main(String[] args) throws Exception 
  {
	//C:\Users\sankar sattari\workspace\ML
	String csvFile = "Training.txt";
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
	
	PrintWriter writer = new PrintWriter("out.txt");
	 
	 // iterating over each person
	 for (String person : user_movie_rating.keySet()) 
	 {
		 //iterating over each movie of a person
		for (String movie : user_movie_rating.get(person).keySet()) 
		{
			rating = user_movie_rating.get(person).get(movie);
			//System.out.println(person + " " + movie + " " + rating);
			writer.println(person + " " + movie + " " + rating) ;
		}
	 }
	writer.close();
	MatchingUsers.MatchUsers(user_movie_rating);
	System.out.println("done");
  }
}
