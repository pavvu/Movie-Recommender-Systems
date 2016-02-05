import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

public class MatchingUsers 
{
   public static void MatchUsers(HashMap<String, HashMap<String, Integer>> user_ratings ) throws Exception
   {
	   double score=0, maxscore;
	   //data sctructure to store matching score between two users
	   HashMap<String, HashMap<String, Double>> all_user_matching_score = new HashMap<>();
	   TreeMap<Double, ArrayList<String>> person2_match_score;
	   HashMap<String, HashMap<String, Double>> top_user_matching_score = new HashMap<>();
	   HashMap<String, Double> temp;
	   ArrayList<String> tempArray;
	   int personcount=0;
	   double time = System.currentTimeMillis();
	   for (String person1 : user_ratings.keySet()) 
		 {
		       personcount+=1;
		       if(personcount%100 ==0)
		       {
		    	   System.out.println("processed " + personcount + " users : time " + (System.currentTimeMillis() - time));
		    	  time = System.currentTimeMillis(); 
		       }
		       
			   person2_match_score = new TreeMap<>(Collections.reverseOrder());
			   for (String person2 : user_ratings.keySet()) 
			   {
				   if(person1.equals(person2))
					   continue;
				   else
				   {
					   // score already calculated for person1,person2
					   if(all_user_matching_score.get(person2)!=null && all_user_matching_score.get(person2).get(person1)!=null)
					   {
					     temp = new HashMap<>();
					     score =all_user_matching_score.get(person2).get(person1);
					     temp.put(person2, score);
					     
					     // if score is seen for the first time
					     if(!person2_match_score.containsKey(score))
						 {   
					    	 tempArray = new ArrayList<>();
					         tempArray.add(person2);
					         person2_match_score.put(score, tempArray);
						 }
						 //same score seen for more than one user
						 else
						 {
							 person2_match_score.get(score).add(person2);
						 }
					   }
					   // score is not calculated for person1, person2
					   else
					   {
						     score = findMatchScore(user_ratings.get(person1), user_ratings.get(person2));
						     
						     if(!all_user_matching_score.containsKey(person1))
							 {   
						    	 temp = new HashMap<>();
						         temp.put(person2, score);
						    	 all_user_matching_score.put(person1, temp);
							 }
							 //Existing user is seen; 
							 else
							 {
								 all_user_matching_score.get(person1).put(person2, score);
							 }
						     
						     // score seen for first time
						     if(!person2_match_score.containsKey(score))
							 {   
						    	 tempArray = new ArrayList<>();
						         tempArray.add(person2);
						         person2_match_score.put(score, tempArray);
							 }
							 //same score seen for more than one user
							 else
							 {
								 person2_match_score.get(score).add(person2);
							 }
					   }
				   }
			   }
			  // Taking top 20 matching user
			  int i=0;
			  for (Double matchScore : person2_match_score.keySet()) 
			  {
				
				if(i>20)
					break;
				// populating top matching users data structure
				if(!top_user_matching_score.containsKey(person1))
				 {
					temp = new HashMap<>();
					for (String user : person2_match_score.get(matchScore)) 
					{
						temp.put(user, matchScore);
						i+=1;
						if(i>20)
							break;
					}
					
					top_user_matching_score.put(person1, temp);
				 }
				 //Existing user is seen; 
				 else
				 {
					 for (String user : person2_match_score.get(matchScore)) 
						{
							//temp.put(user, matchScore);
							top_user_matching_score.get(person1).put(user, matchScore);
							i+=1;
							if(i>20)
								break;
						}
				 }
			  }
		 }
	   printMatchinguser(top_user_matching_score);
	   
	   
   }
   
   public static double findMatchScore(HashMap<String, Integer> user1_rating, HashMap<String, Integer>user2_rating)
   {
	   int commonmoviecount=0;
	   double  matchingscore =0;
	   float rating1, rating2;
	   
	   for (String movie1 : user1_rating.keySet()) 
		{
		   for (String movie2 : user2_rating.keySet())
		   {
			   if(movie1.equals(movie2))
			   {
				   commonmoviecount+=1;
				   rating1=user1_rating.get(movie1);
				   rating2=user2_rating.get(movie2);
				   matchingscore = matchingscore + Math.pow((rating1-rating2), 2);
			   }
		   }
		}
	   if(commonmoviecount>=5)
	   {
		   matchingscore = 1/(1+Math.sqrt(matchingscore));
	   }
	   else
		   matchingscore =0;
	   return matchingscore;
   }
   
   public static void printMatchinguser(HashMap<String, HashMap<String, Double>> matchingUsers) throws FileNotFoundException
   {
	     PrintWriter writer = new PrintWriter("MatchingUsers.txt");
	     double matchingscore;
	     StringBuilder userinfo = new StringBuilder();
		 // iterating over each user
		 for (String user1 : matchingUsers.keySet()) 
		 {
			 userinfo.append(user1+"\t");
			 //iterating over each matching user 
			for (String user2 : matchingUsers.get(user1).keySet()) 
			{
				matchingscore = matchingUsers.get(user1).get(user2);
				userinfo.append(user2+" -> " + matchingscore+"\t");
			}
			writer.println(userinfo) ;
			userinfo.setLength(0);
		 }
		writer.close();
		System.out.println("done");
   }
}


