package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

/**
 * Class to handle the Fuzzy Duck game logic.
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
	 * The main Fuzzy Duck runner (possibly obsolete now).
	 * @param args Arguments.
	 */
	public static void main(final String... args) {

		Scanner input = new Scanner(System.in, "UTF-8");
		System.out.println("Enter 'start' to start the game.");

		startGame(input);
		playFuzzyDuck(input);

		input.close();
	}
	
	//maybe we should be inputting a list of writers? or something along those lines.
	// actually just make it so it always writes & reads own server

	/**
	 * Start the Fuzzy Duck game.
	 * @param input Scanner for console user input.
	 */
	public static void startGame(final Scanner input) {
		boolean running = true;

		while (running) {
			String value = input.nextLine();
			if (value.toLowerCase(Locale.ENGLISH).equals("start")) {
				System.out
						.println("Suggested input: " + suggestStartingValue());
				running = false;
			} else {
				System.out.println("Enter 'start' to start the game.");
			}
		}
	}

	/**
	 * Play Fuzzy Duck. Checks for acceptable user input.
	 * Suggests valid values with the 'go' command.
	 * @param input Scanner for console user input.
	 */
	public static void playFuzzyDuck(final Scanner input) {
		String lastFuzzyDuck = null;
		String value = null;
		String lastValue = null;
		List<String> validInputs = null;
		boolean firstValue = true;

		while (true) {
			value = input.nextLine().toLowerCase(Locale.ENGLISH);

			// check that we've got an acceptable input
			if (Arrays.asList(acceptedInputs).contains(value)) {

				// implement suggestions
				if (value.equals("go")) {
					if (firstValue) {
						System.out.println(
								"Suggested input: " + suggestStartingValue());
					} else {
						System.out.println("Suggested input: "
								+ suggestNextValue(lastValue, lastFuzzyDuck));
					}
				}

				// now check that we're following the game rules
				if (value.equals("fuzzy duck") || value.equals("ducky fuzz")) {
					if (firstValue || validInputs.contains(value)) {
						lastFuzzyDuck = value;
						lastValue = value;
						firstValue = false;
						validInputs = nextValidValues(value, lastFuzzyDuck);
					} else {
						System.out.println("Invalid input (try again)");
					}
				}
			} else {
				System.out.println(
						"Accepted inputs: " + Arrays.toString(acceptedInputs));
			}
		}
	}

	/**
	 * Calculate the values that are valid for the next round of user input.
	 * @param input String input for this round.
	 * @param lastFuzzyDuck The last Fuzzy Duck/Ducky Fuzz value used.
	 * @return List of valid values (Strings).
	 */
	public static List<String> nextValidValues(final String input,
			final String lastFuzzyDuck) {
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
	 * Suggests a value to start the game with (either Fuzzy Duck or 
	 * Ducky Fuzz).
	 * @return String suggestion.
	 */
	public static String suggestStartingValue() {
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
	 * @param input String user input.
	 * @param lastFuzzyDuck String the last fuzzy duck/ducky fuzz value.
	 * @return Suggestion for the next value (String).
	 */
	public static String suggestNextValue(final String input, 
			final String lastFuzzyDuck) {

		List<String> options = nextValidValues(input, lastFuzzyDuck);

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

}