package main.java;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * Class to handle the Fuzzy Duck game logic.
 * 
 * @author Sarah.Johnston
 *
 */
public class FuzzyDuck {

	/**
	 * Acceptable user input values.
	 */
	private static String[] acceptedInputs = { "fuzzy duck", "ducky fuzz",
			"does he?", "he does!", "go" };

	/**
	 * Amount of time to go to sleep for.
	 */
	private static final int SLEEPYTIME = 200;

	private static String lastValue;
	private static String lastFuzzyDuck;
	private static boolean firstValue = true;
	private List<String> validInputs = new ArrayList<String>();
	
	/**
	 * FuzzyDuck constructor.
	 */
	public FuzzyDuck() {
		
	}
	
	/**
	 * Added this in for unit tests (probably a terrible way to do it).
	 * @param value Last fuzzy duck value.
	 */
	public FuzzyDuck(final String value) {
		FuzzyDuck.lastFuzzyDuck = value;
	}
	
	/**
	 * Added this in for unit tests (probably a terrible way to do it).
	 * @param value Last fuzzy duck value.
	 * @param last Last value.
	 */
	public FuzzyDuck(final String value, final String last) {
		FuzzyDuck.lastFuzzyDuck = value;
		FuzzyDuck.lastValue = last;
	}
	
	/**
	 * The main Fuzzy Duck runner.
	 * 
	 * @param args Arguments.
	 * @throws IOException Might throw one of these. 
	 * @throws InterruptedException Might throw one of these.
	 */
	public static void main(final String... args)
			throws InterruptedException, IOException {

		FuzzyDuck fd = new FuzzyDuck();
		try (Scanner input = new Scanner(System.in, "UTF-8")) {
			System.out.println("Enter 'start' to start the game.");
			
			String value = input.nextLine().toLowerCase(Locale.ENGLISH);
			if (value.equals("start")) {
				
				List<BufferedWriter> writers = new ArrayList<BufferedWriter>();
				
				writers.add(new BufferedWriter(
						new OutputStreamWriter(System.out, "UTF-8")));

				fd.writeToAll("Suggested input: " 
						+ fd.suggestStartingValue(), writers);
				
				while (true) {
					value = input.nextLine().toLowerCase(Locale.ENGLISH);
					fd.playFuzzyDuck(input, writers);
				}

			} else {
				System.out.println("Enter 'start' to start the game.");
			}
		}
		
	}

	/**
	 * Play Fuzzy Duck. Checks for acceptable user input.
	 * Suggests valid values with the 'go' command.
	 * @param input Scanner for console user input.
	 * @param writers Places to write to.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void playFuzzyDuck(final Scanner input, 
			final List<BufferedWriter> writers) 
					throws IOException, InterruptedException {
		String value = null;

		while (true) {
			value = input.nextLine().toLowerCase(Locale.ENGLISH);

			// check that we've got an acceptable input
			if (Arrays.asList(acceptedInputs).contains(value)) {

				// implement suggestions (actually we probably only want to write to console here!)
				if (value.equals("go")) {
					if (firstValue) {
						writeToAll("Suggested input: " 
								+ suggestStartingValue(), writers);
					} else {
						writeToAll("Suggested input: " + suggestNextValue(), 
								writers);
					}
				}

				// Handle user input 
				if (firstValue) {
					handleFirstValue(value, writers);
				} else {
					handleInputValue(value, writers);
				}
			} else {
				writeToAll("Accepted inputs: " 
						+ Arrays.toString(acceptedInputs), writers);
			}
		}
	}
	
	/**
	 * Play Fuzzy Duck. Checks for acceptable user input.
	 * Suggests valid values with the 'go' command.
	 * @param input Scanner for console user input.
	 * @param writers Places to write to.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void playGame(final String input, 
			final List<BufferedWriter> writers) 
					throws IOException, InterruptedException {
		// check that we've got an acceptable input
		if (Arrays.asList(acceptedInputs).contains(input)) {

		// implement suggestions (actually we probably only want to write to console here!)
			if (input.equals("go")) {
				if (firstValue) {
					writeToAll("Suggested input: " 
							+ suggestStartingValue(), writers);
				} else {
					writeToAll("Suggested input: " + suggestNextValue(), 
							writers);
				}
			}
			// Handle user input 
			if (firstValue) {
				handleFirstValue(input, writers);
			} else {
				handleInputValue(input, writers);
			}
		} else {
			writeToAll("Accepted inputs: " 
					+ Arrays.toString(acceptedInputs), writers);
		}
	}

	/**
	 * Calculate the values that are valid for the next round of user input.
	 * 
	 * @param input
	 *            String input for this round.
	 * @return List of valid values (Strings).
	 */
	public List<String> nextValidValues(final String input) {
		List<String> values = new ArrayList<String>();

		if (input.equals("fuzzy duck") || input.equals("ducky fuzz")) {
			values.add(input);
			values.add("does he?");
		}

		if (input.equals("does he?")) {
			values.add("he does!");
			if (lastFuzzyDuck.equals("fuzzy duck")) {
				values.add("ducky fuzz");
			} else {
				values.add("fuzzy duck");
			}
		}

		if (input.equals("he does!")) {
			values.add(lastFuzzyDuck);
		}
		return values;
	}

	/**
	 * Suggests a value to start the game with (either Fuzzy Duck or Ducky
	 * Fuzz).
	 * 
	 * @return String suggestion.
	 */
	public String suggestStartingValue() {
		Random randomNum = new Random();
		int result = randomNum.nextInt(2);
		if (result == 1) {
			return "fuzzy duck";
		} else {
			return "ducky fuzz";
		}
	}

	/**
	 * Suggests a valid value that the user could input next.
	 * 
	 * @return Suggestion for the next value (String).
	 */
	public String suggestNextValue() {

		List<String> options = nextValidValues(lastValue);

		// make it more likely to get one of these!
		if (options.contains("fuzzy duck")) {
			options.add("fuzzy duck");
		}
		if (options.contains("ducky fuzz")) {
			options.add("ducky fuzz");
		}

		Random randomNum = new Random();
		int index = randomNum.nextInt(options.size());

		return options.get(index);
	}

	/**
	 * Handle the first user input value.
	 * @param value Input value.
	 * @param writers Places to write to.
	 * @return True if the value was valid, false otherwise.
	 * @throws IOException Might throw this.
	 * @throws InterruptedException Might throw this.
	 */
	public boolean handleFirstValue(
			final String value, final List<BufferedWriter> writers)
					throws IOException, InterruptedException {
		if (value.equals("fuzzy duck") 
				|| value.equals("ducky fuzz")) {
		lastFuzzyDuck = value;
		lastValue = value;
		firstValue = false;
		validInputs = nextValidValues(value);
		return true;
		} else {
			writeToAll("Your first input must be 'fuzzy duck'"
					+ "or 'ducky fuzz'", writers);
			return false;
		}
	}
	
	/**
	 * Handle all other user input.
	 * @param value Input value.
	 * @param writers places to write to.
	 * @return True if input valid, false otherwise.
	 * @throws IOException Might throw this.
	 * @throws InterruptedException Might throw this.
	 */
	public boolean handleInputValue(
			final String value,	final List<BufferedWriter> writers) 
					throws IOException, InterruptedException {
		if (validInputs.contains(value)) {						
			if (value.equals("fuzzy duck") 
					|| value.equals("ducky fuzz")) {
				lastFuzzyDuck = value;
				lastValue = value;
			}
			validInputs = nextValidValues(value);
			return true;
		} else {
			writeToAll("Invalid input (try again)", writers);
			return false;
		}
	}
	
	/**
	 * Write out some stuff to all the writers.
	 * @param value Thing to write.
	 * @param writers things to write to.
	 * @throws IOException Might throw this.
	 * @throws InterruptedException Might throw this.
	 */
	public void writeToAll(final String value,
			final List<BufferedWriter> writers)
			throws IOException, InterruptedException {
		for (BufferedWriter writer : writers) {
			writer.write(value);
			writer.newLine();
			writer.flush();
			Thread.sleep(SLEEPYTIME);
		}
	}
}