import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/** 
 * Text Analyzer Bot
 * Michael Backus
 * CS110 3/20/2018
 */

public class TextAnalyzer {
	/** Class for creating a TextAnalyzer object */
	
	
	//Bot variable declarations
	String[] exitWords = {"exit", "bye", "good-bye", "goodbye", "good bye", "no", "n"};
	String inputFile = "rposts_thedonald.txt";
	int counted_lines = 0; 
	int num_of_lines = 0;
	int num_comments = 0;
	float percentage = 0;
	int wordIndex = 0;
	int maxIndex = 0;
	int counter = 0;
	String line = null;
	

	//Default Constructor
	TextAnalyzer() {
		inputFile = "rposts_thedonald.txt";
	}
	
	//Constructor with file given
	TextAnalyzer(String file) {
		inputFile = file;
	}

	//Methods of Text Analyzer
	public void search_word (String word) {
		/** Searches for data on a specific word within the text file */
		counted_lines = 0; num_of_lines = 0; line = null;
		try {	
			FileReader myFileReader=new FileReader(inputFile);
			BufferedReader myBufferReader= new BufferedReader(myFileReader);
			while ((line=myBufferReader.readLine())!=null)
			{
				//Print the line if the selected word is found
				String currentLine = line.toLowerCase();
				if (currentLine.contains(word)) {
					counted_lines++;
				}
				num_of_lines++;
			} myBufferReader.close();
		} catch (Exception ex) {
			System.out.println("The file could not be found.");
		} 
		//Print the results
		percentage = (float)counted_lines / num_of_lines * 100;
		System.out.println("Word searched: '" + word + "'.");
		System.out.println("This word appears in " + counted_lines + " posts.");
		System.out.println("Percentage the word appears in post titles: " + percentage + "%.");
	}
	
	public void search_combo (String words) {
		/** Searches for a combination of words and lines that contain them */
		num_of_lines = 0; line = null; counter = 0;
		//Create a new array from the words input
		ArrayList<String> wordArray = new ArrayList<String>();
		for(String item : words.split(" ")) {
			wordArray.add(item);
		}
		try {	
			FileReader myFileReader=new FileReader(inputFile);
			BufferedReader myBufferReader= new BufferedReader(myFileReader);
			while ((line=myBufferReader.readLine())!=null)
			{
				String currentLine = line.toLowerCase();
				counter = 0; //reset counter
				//Iterate through word array, if all exists, print
				for(int i=0; i < wordArray.size(); i++) {
					if (currentLine.contains(wordArray.get(i))) {
						counter ++;
						if (wordArray.size() == counter) {
							System.out.println("Word combination found in post: " + currentLine);
						}
						continue;
					} else {		
						break;
					}
				} 
				num_of_lines++;
			} myBufferReader.close();
		} catch (Exception ex) {
			System.out.println("The file could not be found.");
		} 
	}
	
	public void search_sentiment (String word) {
		/** Searches for the sentiment on a specific word */
		line = null; wordIndex = 0; counter = 0;
		try {	
			FileReader myFileReader=new FileReader(inputFile);
			BufferedReader myBufferReader= new BufferedReader(myFileReader);
			while ((line=myBufferReader.readLine())!=null)
			{
				//Iterate through each line of the file
				String currentLine = line.toLowerCase();
				maxIndex = 5;
				if (currentLine.contains(word)) {
					//Add current line to an array list
					counter ++;
					ArrayList<String> lineArray = new ArrayList<String>();
					for(String item : currentLine.split(" ")) {
					    lineArray.add(item);
					}
					//From the index of the word, print out the next 4 words
					wordIndex = lineArray.indexOf(word); //Start point of word
					System.out.print("Instance #"+counter+": ");
					if (lineArray.size() < wordIndex+5) {
						maxIndex = (lineArray.size() - wordIndex);
					}
					if (wordIndex <= 0) {
						System.out.println(word + "... ");
						continue;
					}
					for (int i=wordIndex; i< (wordIndex + maxIndex); i++) {
						System.out.print(lineArray.get(i)+" ");
					}				
					System.out.println("...");
				}
			} myBufferReader.close();
		} catch (Exception ex) {
			System.out.println("The file could not be found.");
		}	
	}

	public void get_post_by_author(String redditUser) {
		/** Returns the posts by the author's name */
		num_of_lines = 0; line = null; num_comments = 0; 
		ArrayList<String> titleArray = new ArrayList<String>();
		try {	
			FileReader myFileReader=new FileReader(inputFile);
			BufferedReader myBufferReader= new BufferedReader(myFileReader);
			while ((line=myBufferReader.readLine())!=null)
			{
				titleArray.add(line);
			} myBufferReader.close();
		} catch (Exception ex) {
			System.out.println("The file could not be found.");
		} 
		try {	
			FileReader myFileReader=new FileReader("rposts_authors.txt");
			BufferedReader myBufferReader= new BufferedReader(myFileReader);
			while ((line=myBufferReader.readLine())!=null)
			{
				if (redditUser.equals(line)) {
					System.out.println("Author: '"+redditUser+"', "
							+ "Title of Post: "+titleArray.get(num_of_lines));
				}
				num_of_lines++;
			} myBufferReader.close();
		} catch (Exception ex) {
			System.out.println("The file could not be found.");
		} 
	}
	
	public void get_posts_by_comments (int minComments, int maxComments) {
		/** Returns the posts with the most comments */
		num_of_lines = 0; line = null; num_comments = 0; 
		ArrayList<String> titleArray = new ArrayList<String>();
		try {	
			FileReader myFileReader=new FileReader(inputFile);
			BufferedReader myBufferReader= new BufferedReader(myFileReader);
			while ((line=myBufferReader.readLine())!=null)
			{
				titleArray.add(line);
			} myBufferReader.close();
		} catch (Exception ex) {
			System.out.println("The file could not be found.");
		} 
		try {	
			FileReader myFileReader=new FileReader("rposts_comments");
			BufferedReader myBufferReader= new BufferedReader(myFileReader);
			while ((line=myBufferReader.readLine())!=null)
			{
				try {
					String currentLine = line.toLowerCase();
					int currentNumber = Integer.parseInt(currentLine);
					if (currentNumber >= minComments && currentNumber <= maxComments) {
						System.out.println("Post #"+num_of_lines+", Comments: "+currentNumber);
						System.out.println("Title of Post: "+titleArray.get(num_of_lines+1));
					}
				} catch (NumberFormatException ex) {
					continue;
				}
				num_of_lines++;
			} myBufferReader.close();
		} catch (Exception ex) {
			System.out.println("The file could not be found.");
		} 
	}

	public boolean is_in_array(String[] array_name, String user_response) {
		/** Check if the user response is in array_name */
		boolean contains = Stream.of(array_name).anyMatch(x -> x.equals(user_response.toLowerCase())); //Search array
		if (contains) { //A match has been found
			return true;
		} else {
			return false;
		}
	}
	
	public void pause_interaction(int seconds) {
		/** Pauses interaction for seconds input */
		for (int i=seconds; i>0; i--) {
			System.out.print(".");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				continue;
			}
		}
	
	}
	
	public void good_bye() {
		/** Print good bye message */
		String user = System.getProperty("user.name");
		System.out.println("\nHope to talk again! Good bye, "+user+"!");
		System.exit(0); //End the program
	}
	
}