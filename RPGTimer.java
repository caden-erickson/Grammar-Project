package comprehensive;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Random;

public class RPGTimer
{
	public static void main(String[] args)
	{
//		System.out.println("Time Non - Terminals");
//		timeNT();
//
//		System.out.println("\nTime Rules");
//		timeRules();
		
		System.out.println("\nTime Phrases");
		timePhrases();
	}
	
	public static void generateFileNTs(int numNTs)
	{
		PrintWriter out = null;
		try
		{
			out = new PrintWriter("src/comprehensive/timing_grammar.g");
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		
		Random rng = new Random();
		
		// generate a list of non-terminals
		String[] ntList = new String[numNTs];
		for (int i = 0; i < numNTs; i++)
		{
			ntList[i] = "" + i + i;
		}
		ntList[0] = "start";
		
		// generate a list of terminals
		String[] tList = new String[100];
		for (int i = 0; i < 100; i++)
		{
			tList[i] = "" + (char)(rng.nextInt(26) + 97) + (char)(rng.nextInt(26) + 97) + (char)(rng.nextInt(26) + 97);
		}
		
		for (int i = 0; i < numNTs; i++)
		{
			out.println("{");
			out.println("<" + ntList[i] + ">");
			
			int numRules = 7;
			for (int j = 1; j <= numRules; j++)
			{
				StringBuilder newRule = new StringBuilder();
				int ruleSize = 5;
				for (int k = 0; k < ruleSize; k++)
				{
					if (k == 2 && j > 1)
					{
						newRule.append("<" + ntList[rng.nextInt(numNTs)] + "> ");
					}
					else
					{
						newRule.append(tList[rng.nextInt(100)] + " ");
					}
				}
				out.println(newRule.toString());
			}
			out.println("}\n\n");
		}
		out.close();
	}
	
	public static void generateFileRules(int numRules)
	{
		PrintWriter out = null;
		try
		{
			out = new PrintWriter("src/comprehensive/timing_grammar.g");
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		
		Random rng = new Random();
		
		// generate a list of non-terminals
		String[] ntList = new String[10];
		for (int i = 0; i < 10; i++)
		{
			ntList[i] = "" + i + i;
		}
		ntList[0] = "start";
		
		// generate a list of terminals
		String[] tList = new String[100];
		for (int i = 0; i < 100; i++)
		{
			tList[i] = "" + (char)(rng.nextInt(26) + 97) + (char)(rng.nextInt(26) + 97) + (char)(rng.nextInt(26) + 97);
		}
		
		for (int i = 0; i < 10; i++)
		{
			out.println("{");
			out.println("<" + ntList[i] + ">");
			
			for (int j = 1; j <= numRules; j++)
			{
				StringBuilder newRule = new StringBuilder();
				int ruleSize = 5;
				for (int k = 0; k < ruleSize; k++)
				{
					if (k == 2 && rng.nextBoolean())
					{
						newRule.append("<" + ntList[rng.nextInt(10)] + "> ");
					}
					else
					{
						newRule.append(tList[rng.nextInt(100)] + " ");
					}
				}
				out.println(newRule.toString());
			}
			out.println("}\n\n");
		}
		out.close();
	}
	
	public static void timeNT()
	{
		int timesToLoop = 10000;
		
		// For each problem size n . . .
		for(int n = 100000; n <= 1000000; n += 100000)
		{
			generateFileNTs(n);
			Grammar g = new Grammar("src/comprehensive/timing_grammar.g");
			
			long startTime, midpointTime, stopTime;
			
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {} // empty block
			
			// Now, run the test
//			String[] toArgs = new String[] {"src/comprehensive/timing_grammar.g", "" + 10000};
			startTime = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++)
			{
//				RandomPhraseGenerator.main(toArgs);
//				Grammar g = new Grammar("src/comprehensive/timing_grammar.g");
				g.generatePhrase();
			}
			
			midpointTime = System.nanoTime();
			
			// Run a loop to capture the cost of running the "timesToLoop" loop
			for(int i = 0; i < timesToLoop; i++) {} // empty block
			
			stopTime = System.nanoTime();
			
			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and doing the lookups
			// Average it over the number of runs
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}
	}

	public static void timeRules()
	{
		int timesToLoop = 10000;
		
		// For each problem size n . . .
		for(int n = 100000; n <= 1000000; n += 100000)
		{
			generateFileRules(n);
			Grammar g = new Grammar("src/comprehensive/timing_grammar.g");
			
			long startTime, midpointTime, stopTime;
			
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {} // empty block
			
			// Now, run the test
//			String[] toArgs = new String[] {"src/comprehensive/timing_grammar.g", "" + 100};
			startTime = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++)
			{
//				RandomPhraseGenerator.main(toArgs);
//				Grammar g = new Grammar("src/comprehensive/timing_grammar.g");
				g.generatePhrase();
			}
			
			midpointTime = System.nanoTime();
			
			// Run a loop to capture the cost of running the "timesToLoop" loop
			for(int i = 0; i < timesToLoop; i++) {} // empty block
			
			stopTime = System.nanoTime();
			
			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and doing the lookups
			// Average it over the number of runs
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}
	}

	public static void timePhrases()
	{
		int timesToLoop = 10;
		
		generateFileNTs(1000);
		
		Grammar g = new Grammar("src/comprehensive/timing_grammar.g");
		// For each problem size n . . .
		for(int n = 100000; n <= 1000000; n += 100000)
		{
			long startTime, midpointTime, stopTime;
			// First, spin computing stuff until one second has gone by
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {} // empty block
			
			// Now, run the test
			startTime = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++)
			{
				for(int j = 0; j < n; j++)
				{
					g.generatePhrase();
				}
			}
			
			midpointTime = System.nanoTime();
			
			// Run a loop to capture the cost of running the "timesToLoop" loop
			for(int i = 0; i < timesToLoop; i++)
			{
				for(int j = 0; j < n; j++)
				{
				}
			} // empty block
			
			stopTime = System.nanoTime();
			
			// Compute the time, subtract the cost of running the loop
			// from the cost of running the loop and doing the lookups
			// Average it over the number of runs
			double averageTime = ((midpointTime - startTime) - (stopTime - midpointTime)) / (double) timesToLoop;
			System.out.println(n + "\t" + averageTime);
		}
	}
}
