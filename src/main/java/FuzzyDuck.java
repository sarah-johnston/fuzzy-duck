package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FuzzyDuck {

	public static String[] acceptedInputs = { "fuzzy duck", "ducky fuzz", "does he?", "he does!", "go" };

	public static void thing(String... args) {

		Scanner input = new Scanner(System.in);
		System.out.println("Enter 'start' to start the game.");

		startGame(input);
		playFuzzyDuck(input);

		input.close();
	}

	public static void startGame(Scanner input) {
		boolean running = true;

		while (running) {
			String value = input.nextLine();
			if (value.toLowerCase().equals("start")) {
				System.out.println("Suggested input: " + suggestStartingValue());
				running = false;
			} else
				System.out.println("Enter 'start' to start the game.");
		}
	}

	public static void playFuzzyDuck(Scanner input) {
		String lastFuzzyDuck = null;
		String value = null;
		String lastValue = null;
		List<String> validInputs = null;
		boolean running = true;
		boolean firstValue = true;

		while (running) {
			value = input.nextLine().toLowerCase();

			// check that we've got an acceptable input
			if (Arrays.asList(acceptedInputs).contains(value)) {

				// implement suggestions
				if (value.equals("go")) {
					if (firstValue)
						System.out.println("Suggested input: " + suggestStartingValue());
					// suggest f or d
					else
						System.out.println("Suggested input: " + suggestNextValue(lastValue, lastFuzzyDuck));
				}

				// now check that we're following the game rules
				if (value.equals("fuzzy duck") || value.equals("ducky fuzz")) {
					if (firstValue || validInputs.contains(value)) {
						lastFuzzyDuck = value;
						lastValue = value;
						firstValue = false;
						validInputs = nextValidValues(value, lastFuzzyDuck);
					} else
						System.out.println("Invalid input (try again)");
				}
			} else
				System.out.println("Accepted inputs: " + Arrays.toString(acceptedInputs));
		}
	}

	public static List<String> nextValidValues(String input, String lastFuzzyDuck) {
		List<String> values = new ArrayList<String>();

		if (input.equals("fuzzy duck") || input.equals("ducky fuzz")) {
			values.add(input);
			values.add("does he?");
		}

		if (input.equals("does he?")) {
			values.add("he does!");
			if (lastFuzzyDuck.equals("fuzzy duck"))
				values.add("ducky fuzz");
			else
				values.add("fuzzy duck");
		}

		if (input.equals("he does!")) {
			values.add(lastFuzzyDuck);
		}

		return values;
	}

	public static String suggestStartingValue() {
		Random randomNum = new Random();
		int result = randomNum.nextInt(2);
		if (result == 1)
			return "fuzzy duck";
		else
			return "ducky fuzz";
	}

	public static String suggestNextValue(String input, String lastFuzzyDuck) {

		List<String> options = nextValidValues(input, lastFuzzyDuck);

		// make it more likely to get one of these!
		if (options.contains("fuzzy duck"))
			options.add("fuzzy duck");
		if (options.contains("ducky fuzz"))
			options.add("ducky fuzz");

		Random randomNum = new Random();
		int index = randomNum.nextInt(options.size());

		return options.get(index);
	}

}