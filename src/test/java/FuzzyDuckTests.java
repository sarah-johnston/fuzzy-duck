package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import main.java.FuzzyDuck;

public class FuzzyDuckTests {
	
	//Testing the starting suggestion
	@Test
	public void testStartingValue(){
		List<String> expectedValues = Arrays.asList("fuzzy duck", "ducky fuzz");
		assertTrue(expectedValues.contains(FuzzyDuck.suggestStartingValue()));
	}
	
	//Testing the nextValidValues method	
	@Test
	public void testNextValueFuzzyDuck()
	{
		List<String> expectedValues = Arrays.asList("fuzzy duck", "does he?");
		assertEquals(expectedValues, FuzzyDuck.nextValidValues("fuzzy duck", "fuzzy duck"));
	}
	
	@Test
	public void testNextValueDuckyFuzz()
	{
		List<String> expectedValues = Arrays.asList("ducky fuzz", "does he?");
		assertEquals(expectedValues, FuzzyDuck.nextValidValues("ducky fuzz", "ducky fuzz"));
	}
	
	@Test
	public void testNextValueFuzzyDuckDoesHe()
	{
		List<String> expectedValues = Arrays.asList("he does!", "ducky fuzz");
		assertEquals(expectedValues, FuzzyDuck.nextValidValues("does he?", "fuzzy duck"));
	}
	
	@Test
	public void testNextValueDuckyFuzzDoesHe()
	{
		List<String> expectedValues = Arrays.asList("he does!", "fuzzy duck");
		assertEquals(expectedValues, FuzzyDuck.nextValidValues("does he?", "ducky fuzz"));
	}
	
	@Test
	public void testFuzzyDuckHeDoes(){
		List<String> expectedValues = Arrays.asList("fuzzy duck");
		assertEquals(expectedValues, FuzzyDuck.nextValidValues("he does!", "fuzzy duck"));
	}
	
	@Test
	public void testDuckyFuzzHeDoes(){
		List<String> expectedValues = Arrays.asList("ducky fuzz");
		assertEquals(expectedValues, FuzzyDuck.nextValidValues("he does!", "ducky fuzz"));
	}
	
	

	// Testing the suggestions
	
	@Test
	public void testSuggestionFuzzyDuck(){
		List<String> expectedValues = Arrays.asList("fuzzy duck", "does he?");
		String suggestion = FuzzyDuck.suggestNextValue("fuzzy duck", "fuzzy duck");
		assertTrue(expectedValues.contains(suggestion));		
	}
	
	@Test
	public void testSuggestionDuckyFuzz(){
		List<String> expectedValues = Arrays.asList("ducky fuzz", "does he?");
		String suggestion = FuzzyDuck.suggestNextValue("ducky fuzz", "ducky fuzz");
		assertTrue(expectedValues.contains(suggestion));		
	}
	
	@Test
	public void testSuggestionFuzzyDuckDoesHe(){
		List<String> expectedValues = Arrays.asList("he does!", "ducky fuzz");
		String suggestion = FuzzyDuck.suggestNextValue("does he?", "fuzzy duck");
		assertTrue(expectedValues.contains(suggestion));	
	}
	
	@Test
	public void testSuggestionDuckyFuzzDoesHe(){
		//
		List<String> expectedValues = Arrays.asList("he does!", "fuzzy duck");
		String suggestion = FuzzyDuck.suggestNextValue("does he?", "ducky fuzz");
		assertTrue(expectedValues.contains(suggestion));	
	}
	
	@Test
	public void testSuggestionFuzzyDuckHeDoes(){
		List<String> expectedValues = Arrays.asList("fuzzy duck");
		String suggestion = FuzzyDuck.suggestNextValue("he does!", "fuzzy duck");
		assertTrue(expectedValues.contains(suggestion));
	}
	
	@Test
	public void testSuggestionDuckyFuzzHeDoes(){
		List<String> expectedValues = Arrays.asList("ducky fuzz");
		String suggestion = FuzzyDuck.suggestNextValue("he does!", "ducky fuzz");
		assertTrue(expectedValues.contains(suggestion));		
	}
}
