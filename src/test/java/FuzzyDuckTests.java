package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import main.java.FuzzyDuck;

/**
 * Test class for the Fuzzy Duck class.
 * @author Sarah.Johnston
 *
 */
public class FuzzyDuckTests {

	/**
	 * Fuzzy Duck.
	 */
	
	
	/**
	 * Setup method.
	 */
	@BeforeClass
	public static void setUp() {
		
	}
	
	/**
	 * Test the starting suggestion is either Fuzzy Duck or Ducky Fuzz.
	 */
	@Test
	public void testStartingValue() {
		FuzzyDuck fd = new FuzzyDuck();
		List<String> expectedValues = Arrays.asList("fuzzy duck", "ducky fuzz");
		assertTrue(expectedValues.contains(fd.suggestStartingValue()));
	}

	/**
	 * Test the nextvalidValues function, where the current input is 
	 * 'fuzzy duck' and the last fuzzy duck/duck fuzz value was 'fuzzy duck'.
	 */
	@Test
	public void testNextValueFuzzyDuck() {
		FuzzyDuck fd = new FuzzyDuck("fuzzy duck");
		List<String> expectedValues = Arrays.asList("fuzzy duck", "does he?");
		assertEquals(expectedValues, 
				fd.nextValidValues("fuzzy duck"));
	}
	
	/**
	 * Test the nextvalidValues function, where the current input is 
	 * 'ducky fuzz' and the last fuzzy duck/duck fuzz value was 'ducky fuzz'.
	 */
	@Test
	public void testNextValueDuckyFuzz() {
		FuzzyDuck fd = new FuzzyDuck("fuzzy duck");
		List<String> expectedValues = Arrays.asList("ducky fuzz", "does he?");
		assertEquals(expectedValues, 
				fd.nextValidValues("ducky fuzz"));
	}
	
	/**
	 * Test the nextvalidValues function, where the current input is 
	 * 'does he?' and the last fuzzy duck/duck fuzz value was 'fuzzy duck'.
	 */
	@Test
	public void testNextValueFuzzyDuckDoesHe() {
		FuzzyDuck fd = new FuzzyDuck("fuzzy duck");
		List<String> expectedValues = Arrays.asList("he does!", "ducky fuzz");
		assertEquals(expectedValues, 
				fd.nextValidValues("does he?"));
	}

	/**
	 * Test the nextvalidValues function, where the current input is 
	 * 'does he?' and the last fuzzy duck/duck fuzz value was 'ducky fuzz'.
	 */
	@Test
	public void testNextValueDuckyFuzzDoesHe() {
		FuzzyDuck fd = new FuzzyDuck("ducky fuzz");
		List<String> expectedValues = Arrays.asList("he does!", "fuzzy duck");
		assertEquals(expectedValues, 
				fd.nextValidValues("does he?"));
	}

	/**
	 * Test the nextvalidValues function, where the current input is 
	 * 'he does!' and the last fuzzy duck/duck fuzz value was 'fuzzy duck'.
	 */
	@Test
	public void testFuzzyDuckHeDoes() {
		FuzzyDuck fd = new FuzzyDuck("fuzzy duck");
		List<String> expectedValues = Arrays.asList("fuzzy duck");
		assertEquals(expectedValues, 
				fd.nextValidValues("he does!"));
	}

	/**
	 * Test the nextvalidValues function, where the current input is 
	 * 'he does!' and the last fuzzy duck/duck fuzz value was 'ducky fuzz'.
	 */
	@Test
	public void testDuckyFuzzHeDoes() {
		FuzzyDuck fd = new FuzzyDuck("ducky fuzz");
		List<String> expectedValues = Arrays.asList("ducky fuzz");
		assertEquals(expectedValues, 
				fd.nextValidValues("he does!"));
	}

	// Testing the suggestions

	/**
	 * Test that the 'go' suggestion function produces a correct value, where 
	 * the current input is 'fuzzy duck' and the last fuzzy duck/duck fuzz 
	 * value was 'fuzzy duck'.
	 */
	@Test
	public void testSuggestionFuzzyDuck() {
		FuzzyDuck fd = new FuzzyDuck("fuzzy duck", "fuzzy duck");
		List<String> expectedValues = Arrays.asList("fuzzy duck", "does he?");
		String suggestion = 
				fd.suggestNextValue();
		assertTrue(expectedValues.contains(suggestion));
	}

	/**
	 * Test that the 'go' suggestion function produces a correct value, where 
	 * the current input is 'ducky fuzz' and the last fuzzy duck/duck fuzz 
	 * value was 'ducky fuzz'.
	 */
	@Test
	public void testSuggestionDuckyFuzz() {
		FuzzyDuck fd = new FuzzyDuck("ducky fuzz", "ducky fuzz");
		List<String> expectedValues = Arrays.asList("ducky fuzz", "does he?");
		String suggestion = 
				fd.suggestNextValue();
		assertTrue(expectedValues.contains(suggestion));
	}
	
	/**
	 * Test that the 'go' suggestion function produces a correct value, where 
	 * the current input is 'does he?' and the last fuzzy duck/duck fuzz 
	 * value was 'fuzzy duck'.
	 */
	@Test
	public void testSuggestionFuzzyDuckDoesHe() {
		FuzzyDuck fd = new FuzzyDuck("fuzzy duck", "does he?");
		List<String> expectedValues = Arrays.asList("he does!", "ducky fuzz");
		String suggestion = 
				fd.suggestNextValue();
		assertTrue(expectedValues.contains(suggestion));
	}

	/**
	 * Test that the 'go' suggestion function produces a correct value, where 
	 * the current input is 'does he?' and the last fuzzy duck/duck fuzz 
	 * value was 'ducky fuzz'.
	 */
	@Test
	public void testSuggestionDuckyFuzzDoesHe() {
		FuzzyDuck fd = new FuzzyDuck("ducky fuzz", "does he?");
		List<String> expectedValues = Arrays.asList("he does!", "fuzzy duck");
		String suggestion = 
				fd.suggestNextValue();
		assertTrue(expectedValues.contains(suggestion));
	}

	/**
	 * Test that the 'go' suggestion function produces a correct value, where 
	 * the current input is 'he does!' and the last fuzzy duck/ducky fuzz 
	 * value was 'fuzzy duck'.
	 */
	@Test
	public void testSuggestionFuzzyDuckHeDoes() {
		FuzzyDuck fd = new FuzzyDuck("fuzzy duck", "he does!");
		List<String> expectedValues = Arrays.asList("fuzzy duck");
		String suggestion = 
				fd.suggestNextValue();
		assertTrue(expectedValues.contains(suggestion));
	}

	/**
	 * Test that the 'go' suggestion function produces a correct value, where 
	 * the current input is 'he does!' and the last fuzzy duck/duck fuzz 
	 * value was 'ducky fuzz'.
	 */
	@Test
	public void testSuggestionDuckyFuzzHeDoes() {
		FuzzyDuck fd = new FuzzyDuck("ducky fuzz", "he does!");
		List<String> expectedValues = Arrays.asList("ducky fuzz");
		String suggestion = 
				fd.suggestNextValue();
		assertTrue(expectedValues.contains(suggestion));
	}
	
}
