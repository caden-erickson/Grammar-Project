package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.SplittableRandom;

/**
 * This class represents set of grammar rules, as specified in a file whose path
 * is passed to the constructor. Objects of this class have the functionality to
 * output a randomly generated phrase using the grammar rules read in from the
 * file.
 * 
 * @author Caden Erickson, Chandler Copyak
 * @version April 26, 2022
 */
public class Grammar
{
	HashMap<String, ArrayList<String[]>> ntMap;
	
	/**
	 * Per some research we've done, java.util.SplittableRandom is a more efficient
	 * RandomNumberGenerator than java.util.Random, so we've chosen to use that.
	 */
	SplittableRandom rng;
	
	
	/**
	 * Constructor. Constructs a new Grammar object using the rules specified
	 * in the file at the given file location.
	 * 
	 * @param fileLoc location of the grammar file to be used
	 */
	public Grammar(String fileLoc)
	{
		ntMap = new HashMap<String, ArrayList<String[]>>();
		rng = new SplittableRandom();
		Scanner fromFile = null;
		
		try
		{
			fromFile = new Scanner(new File(fileLoc));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("No such file found.");
			System.exit(0);
		}
		
		buildGrammar(fromFile);
	}
	
	/**
	 * Parses the file into non-terminal lists of production rule lists of
	 * individual terminals and non-terminals, using the given Scanner object.
	 * 
	 * @param fromFile the Scanner object to use
	 */
	private void buildGrammar(Scanner fromFile)
	{
		while (fromFile.hasNextLine())
		{
			String nextLine = fromFile.nextLine();

			// Beginning of non-terminal definition
			if (nextLine.startsWith("{"))
			{
				// Next line will be the name of the non-terminal
				nextLine = fromFile.nextLine();
				String ntName = nextLine;
				ArrayList<String[]> newNT = new ArrayList<String[]>();

				// Read and split all subsequent lines until a "}" is found
				// Each line will be a new production rule
				nextLine = fromFile.nextLine();
				while (!nextLine.startsWith("}"))
				{
					newNT.add(nextLine.split("(?=<)|(?<=>)"));	// split before all '<' and after all '>'
					nextLine = fromFile.nextLine();
				}

				// Put the completed ArrayList into the HashMap, keyed to by its non-terminal
				// name from earlier
				ntMap.put(ntName, newNT);
			}
		}
	}
	
	/**
	 * Driver method for generating and then printing a random phrase using the
	 * current Grammar.
	 */
	public void generatePhrase()
	{
		System.out.println(buildPhrase("<start>"));
//		buildPhrase("<start>");
	}
	
	/**
	 * Builds a String from a randomly selected production rule in the non-terminal
	 * specified by the given non-terminal name.
	 * 
	 * @param ntName the non-terminal to expand into a phrase
	 * @return the built phrase
	 */
	private String buildPhrase(String ntName)
	{
		// Get a non-terminal (ArrayList) from the map, and select a random production rule (ArrayList)
		ArrayList<String[]> ntList = ntMap.get(ntName);
		String[] chosen = ntList.get(rng.nextInt(ntList.size()));
		
		StringBuilder segment = new StringBuilder();
		
		for (int i = 0; i < chosen.length; i++)
		{
			String current = chosen[i];
			
			// If the current segment of the production rule starts with "<",
			//	then recursively call this output method with that segment
			if (current.startsWith("<"))
			{
				segment.append(buildPhrase(current));
			}
			// Otherwise it's a terminal, so just append it to the StringBuilder
			else
			{
				segment.append(current);
			}
		}
		return segment.toString();
	}
}
