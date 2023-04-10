package comprehensive;

/**
 * This class creates a Grammar object from a given .g file, and generates a
 * given number of randomly generated phrases from that Grammar.
 * 
 * @author Caden Erickson, Chandler Copyak
 * @version April 26, 2022
 */
public class RandomPhraseGenerator
{
	/**
	 * Application entry point
	 * 
	 * @param args filepath of grammar file to be used, and number of random phrases to
	 *             generate
	 */
	public static void main(String[] args)
	{
		// Filename, first argument
		String fileLoc = args[0];
		// # of sentences, second argument
		int sentences = Integer.parseInt(args[1]);
		
		Grammar grammar = new Grammar(fileLoc);
		
		// for loop for # argument
		for (int i = 0; i < sentences; i++)
		{
			grammar.generatePhrase();
		}
	}
}