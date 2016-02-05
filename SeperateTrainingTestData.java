import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Random;

public class SeperateTrainingTestData 
{
	public static void main(String[] args) throws Exception
	{
		
		String line="";
		PrintWriter writer_training = new PrintWriter("Training.txt");
		PrintWriter writer_test = new PrintWriter("Test.txt");
		
		Random randomGenerator = new Random();
		HashSet<Integer> rand_line_numbers = new HashSet<>();
		int no_of_records = 500101;
		// generating random numbers to separate test and training data
		while(rand_line_numbers.size() <500)
		{
			rand_line_numbers.add(randomGenerator.nextInt(no_of_records));
		}
		
		String csvFile = "C:\\Users\\HP PC\\Downloads\\ML (2).tar\\ML (2)\\ML\\training_ratings_for_kaggle_comp.csv";
		BufferedReader br = new BufferedReader(new FileReader(csvFile));
		
		// seperating test and training data
		line=br.readLine();
		int line_count=0;
		while((line = br.readLine())!= null)
		{
			if(rand_line_numbers.contains(line_count))
			{
				writer_test.println(line);
			}
			else
			{
				writer_training.println(line);
			}
			line_count+=1;
		}
		writer_test.close();
		writer_training.close();
		br.close();
	System.out.println("done");	
	}
}
