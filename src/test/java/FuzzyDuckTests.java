package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import main.java.FuzzyDuck;

/**
 * Test class for the Fuzzy Duck class.
 * @author Sarah.Johnston
 *
 */
public class FuzzyDuckTests {

	/**
	 * Test the starting suggestion is either Fuzzy Duck or Ducky Fuzz.
	 */
	@Test
	public void testStartingValue() {
		List<String> expectedValues = Arrays.asList("fuzzy duck", "ducky fuzz");
		assertTrue(expectedValues.contains(FuzzyDuck.suggestStartingValue()));
	}

	/**
	 * Test the nextvalidValues function, where the current input is 
	 * 'fuzzy duck' and the last fuzzy duck/duck fuzz value was 'fuzzy duck'.
	 */
	@Test
	public void testNextValueFuzzyDuck() {
		List<String> expectedValues = Arrays.asList("fuzzy duck", "does he?");
		assertEquals(expectedValues, 
				FuzzyDuck.nextValidValues("fuzzy duck", "fuzzy duck"));
	}
	
	/**
	 * Test the nextvalidValues function, where the current input is 
	 * 'ducky fuzz' and the last fuzzy duck/duck fuzz value was 'ducky fuzz'.
	 */
	@Test
	public void testNextValueDuckyFuzz() {
		List<String> expectedValues = Arrays.asList("ducky fuzz", "does he?");
		assertEquals(expectedValues, 
				FuzzyDuck.nextValidValues("ducky fuzz", "ducky fuzz"));
	}
	
	/**
	 * Test the nextvalidValues function, where the current input is 
	 * 'does he?' and the last fuzzy duck/duck fuzz value was 'fuzzy duck'.
	 */
	@Test
	public void testNextValueFuzzyDuckDoesHe() {
		List<String> expectedValues = Arrays.asList("he does!", "ducky fuzz");
		assertEquals(expectedValues, 
				FuzzyDuck.nextValidValues("does he?", "fuzzy duck"));
	}

	/**
	 * Test the nextvalidValues function, where the current input is 
	 * 'does he?' and the last fuzzy duck/duck fuzz value was 'ducky fuzz'.
	 */
	@Test
	public void testNextValueDuckyFuzzDoesHe() {
		List<String> expectedValues = Arrays.asList("he does!", "fuzzy duck");
		assertEquals(expectedValues, 
				FuzzyDuck.nextValidValues("does he?", "ducky fuzz"));
	}

	/**
	 * Test the nextvalidValues function, where the current input is 
	 * 'he does!' and the last fuzzy duck/duck fuzz value was 'fuzzy duck'.
	 */
	@Test
	public void testFuzzyDuckHeDoes() {
		List<String> expectedValues = Arrays.asList("fuzzy duck");
		assertEquals(expectedValues, 
				FuzzyDuck.nextValidValues("he does!", "fuzzy duck"));
	}

	/**
	 * Test the nextvalidValues function, where the current input is 
	 * 'he does!' and the last fuzzy duck/duck fuzz value was 'ducky fuzz'.
	 */
	@Test
	public void testDuckyFuzzHeDoes() {
		List<String> expectedValues = Arrays.asList("ducky fuzz");
		assertEquals(expectedValues, 
				FuzzyDuck.nextValidValues("he does!", "ducky fuzz"));
	}

	// Testing the suggestions

	/**
	 * Test that the 'go' suggestion function produces a correct value, where 
	 * the current input is 'fuzzy duck' and the last fuzzy duck/duck fuzz 
	 * value was 'fuzzy duck'.
	 */
	@Test
	public void testSuggestionFuzzyDuck() {
		List<String> expectedValues = Arrays.asList("fuzzy duck", "does he?");
		String suggestion = 
				FuzzyDuck.suggestNextValue("fuzzy duck", "fuzzy duck");
		assertTrue(expectedValues.contains(suggestion));
	}

	/**
	 * Test that the 'go' suggestion function produces a correct value, where 
	 * the current input is 'ducky fuzz' and the last fuzzy duck/duck fuzz 
	 * value was 'ducky fuzz'.
	 */
	@Test
	public void testSuggestionDuckyFuzz() {
		List<String> expectedValues = Arrays.asList("ducky fuzz", "does he?");
		String suggestion = 
				FuzzyDuck.suggestNextValue("ducky fuzz", "ducky fuzz");
		assertTrue(expectedValues.contains(suggestion));
	}
	
	/**
	 * Test that the 'go' suggestion function produces a correct value, where 
	 * the current input is 'does he?' and the last fuzzy duck/duck fuzz 
	 * value was 'fuzzy duck'.
	 */
	@Test
	public void testSuggestionFuzzyDuckDoesHe() {
		List<String> expectedValues = Arrays.asList("he does!", "ducky fuzz");
		String suggestion = 
				FuzzyDuck.suggestNextValue("does he?", "fuzzy duck");
		assertTrue(expectedValues.contains(suggestion));
	}

	/**
	 * Test that the 'go' suggestion function produces a correct value, where 
	 * the current input is 'does he?' and the last fuzzy duck/duck fuzz 
	 * value was 'ducky fuzz'.
	 */
	@Test
	public void testSuggestionDuckyFuzzDoesHe() {
		//
		List<String> expectedValues = Arrays.asList("he does!", "fuzzy duck");
		String suggestion = 
				FuzzyDuck.suggestNextValue("does he?", "ducky fuzz");
		assertTrue(expectedValues.contains(suggestion));
	}

	/**
	 * Test that the 'go' suggestion function produces a correct value, where 
	 * the current input is 'he does!' and the last fuzzy duck/duck fuzz 
	 * value was 'fuzzy duck'.
	 */
	@Test
	public void testSuggestionFuzzyDuckHeDoes() {
		List<String> expectedValues = Arrays.asList("fuzzy duck");
		String suggestion = 
				FuzzyDuck.suggestNextValue("he does!", "fuzzy duck");
		assertTrue(expectedValues.contains(suggestion));
	}

	/**
	 * Test that the 'go' suggestion function produces a correct value, where 
	 * the current input is 'he does!' and the last fuzzy duck/duck fuzz 
	 * value was 'ducky fuzz'.
	 */
	@Test
	public void testSuggestionDuckyFuzzHeDoes() {
		List<String> expectedValues = Arrays.asList("ducky fuzz");
		String suggestion = 
				FuzzyDuck.suggestNextValue("he does!", "ducky fuzz");
		assertTrue(expectedValues.contains(suggestion));
	}
}
