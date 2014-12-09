import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class tracks the time to know how long you have been playing it writes you time to the file that holds the scores for the game. and reads those scores back when the game has been won.   */
public class Scoring 
{
	 long startTime;
	 long totalTime;  
	public int totalMin;
	 public int totalSec;
	 ArrayList<String> topScores;
	
	 /**
	  * constructor for scoring class.*/
	public Scoring()
	{
		startTime =System.currentTimeMillis();
		totalTime=0;
		
	}
	
	/**
	 * this method sets the total time(milliseconds) and the total minutes and seconds in game. */
	public void setTotalTime()
	{
		totalTime=System.currentTimeMillis()-startTime;
		totalMin =Math.round((totalTime/1000)/60);
		totalSec= Math.round((totalTime/1000)%60);
	}

	/**returns the total time spent in game in milliseconds
	 * @return double **/
	public double getTotalTime()
	{
		return totalTime;
	}
	
	/**
	 * reads the scores from the file.*/
	public void readFromFile()//@@needs lots of work
	{
		topScores = new ArrayList<String>();
		
		try(Scanner fromFile = new Scanner(new File("Scores.csv")))
		{
			while(fromFile.hasNext())
			{
				String st = fromFile.nextLine();
				topScores.add(st);
			}
		}
		catch(Exception ex)
		{
			System.out.println("could not print to file");
			ex.printStackTrace();
		}
	}
	
	/**
	 * this method writes your score to the file that has a list of scores
	 * @param String*/
	public void writeToFile(String str)
	{
		  try
		  {
	            FileWriter fstream = new FileWriter("Scores.csv",true);
	            BufferedWriter fbw = new BufferedWriter(fstream);
	            fbw.write(str);
	            fbw.newLine();
	            fbw.close();
	            
	      }
		  catch (Exception ex) 
		  {
	            System.out.println("write to file didn't work: " + ex.getMessage());
	      }
	}
	
}
