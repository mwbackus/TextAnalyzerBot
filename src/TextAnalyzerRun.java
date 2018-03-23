import java.util.Scanner;

/** 
 * Text Analyzer Bot
 * Michael Backus
 * CS110 3/20/2018
 */

public class TextAnalyzerRun {
	public static void main(String[] args) 
	{
		//Declarations
		int selected_item = 0;
		String user = System.getProperty("user.name");
		Scanner keyboard = new Scanner(System.in);
		TextAnalyzer text_reader = new TextAnalyzer();
		String user_input = null;
		String restart_bot = null;
		int min_num = 0;
		int max_num = 0;
		boolean end_program = false;
		
		System.out.println("Hello "+user+"! I am the text analyzer bot. I will guide you\n"
				+ "through the steps of analyzing some reddit post data.");
		text_reader.pause_interaction(4); //Wait 4 seconds until continuing.
		
		do {
			try {
				//Analysis options
				System.out.println("\nPlease select one of the following options:\n"
						+ "***** Select a number on your keyboard: *****\n\n"
						+ "1) Search for data on a specific word in the posts...\n"
						+ "2) Search for a combination of words within the posts....\n"
						+ "3) Search for the sentiment on specific words...\n"
						+ "4) Search for posts by a specific author...\n"
						+ "5) Search for posts by comment range...\n"
						+ "6) Get the most popular post...");
			
				selected_item = keyboard.nextInt();
			} catch (Exception ex) {
				System.out.println("Please enter in a number.");
			}

			switch (selected_item) {
		
				case 1: 
					System.out.println("Please enter in a word to receive statistics: ");
					keyboard.nextLine(); //Consume \n leftover
					user_input = keyboard.next();
					text_reader.search_word(user_input.toLowerCase());
					System.out.println("\n");
					break;
			
				case 2: //ISSUE with nextLine() \n consumption
					System.out.println("Please enter in words separated by spaces. Press enter to complete: ");
					keyboard.nextLine(); //Consume \n leftover
					user_input = keyboard.nextLine().toLowerCase();
					text_reader.search_combo(user_input);
					System.out.println("\n");
					break;
			
				case 3:
					System.out.println("Please enter in a word to search for post sentiment: ");
					keyboard.nextLine(); //Consume \n leftover
					user_input = keyboard.next();
					text_reader.search_sentiment(user_input.toLowerCase());
					System.out.println("\n");
					break;
			
				case 4:
					System.out.println("Please enter in a reddit user name: ");
					keyboard.nextLine(); //Consume \n leftover
					user_input = keyboard.next();
					text_reader.get_post_by_author(user_input);
					System.out.println("\n");
					break;
			
				case 5:
					System.out.println("Select a minimum comment amount by post: ");
					keyboard.nextLine(); //Consume \n leftover
					min_num = keyboard.nextInt();
					System.out.println("Select a minimum comment amount by post: ");
					max_num = keyboard.nextInt();
					text_reader.get_posts_by_comments(min_num, max_num);
					System.out.println("\n");
					break;
					
				case 6:
					text_reader.get_most_popular_post();
					System.out.println("\n");
					break;
			}
				//Check if user wants to restart
				System.out.println("Do you want to try another search??");
				keyboard.nextLine(); //Consume \n leftover
				restart_bot = keyboard.nextLine();
				if (text_reader.is_in_array(text_reader.exitWords, restart_bot)) {
					text_reader.good_bye();
				}
		
		} while (end_program == false);
		
		keyboard.close();	
		text_reader.good_bye();
	}
	
	
}
