/*code to predict ratings of a movie by given user */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class PredictRatings 
{
  public static void predictTestRatings(HashMap<String, HashMap<String, Double>> matchingUsers) throws Exception
  {
	  String testFile_matchingUsers = "MatchingUsers.txt";
	  BufferedReader br1 = new BufferedReader(new FileReader(testFile_matchingUsers));
	  String line_matchingUsers = "";
	  String testFile = "C:\\Users\\HP PC\\workspace\\ML\\Test.txt";
	  BufferedReader br = new BufferedReader(new FileReader(testFile));
	  String line = "";
	  String user, movie, actualRating;
	  double expectedRating =0;
	  while((line = br.readLine())!= null)
	  {
		  expectedRating =0;
		  user = line.split(",")[0];
		  movie = line.split(",")[1];
		  actualRating = line.split(",")[2];
		  expectedRating = predictMovieRating(user, movie, matchingUsers.get(user));
	  }
  }
  
  public static double predictMovieRating(String user, String movie, HashMap<String, Double> matching_users) throws Exception
  {
	  double expectedRating =0;
	  double sumOfMatchingScores=0;
	  HashMap<String, HashMap<String, Integer>> userMovieRatingList=ReadUserMovieRatingsIntoMemory.readUserMovieRatings();
	  for (String user1 : matching_users.keySet()) 
	  {
		  for (String movieId : userMovieRatingList.get(user1).keySet()) {
			if(movieId.equals(movie)){
				expectedRating= matching_users.get(user1)*userMovieRatingList.get(user1).get(movieId)+expectedRating;
				sumOfMatchingScores=sumOfMatchingScores+ matching_users.get(user1);
			}
		}  
	  }
	  expectedRating=expectedRating/sumOfMatchingScores;
	  return expectedRating;
  }
}
