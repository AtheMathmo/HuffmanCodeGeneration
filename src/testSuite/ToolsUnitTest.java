package testSuite;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import utilities.Tools;

public class ToolsUnitTest {
	
	@Test
	public void testFindMinimumPairBasic() {
		Map<String, Double> testAlpha = new HashMap<String, Double>();
		testAlpha.put("a",0.2);
		testAlpha.put("b",0.6);
		testAlpha.put("c",0.3);
		testAlpha.put("d",0.1);
		
		String[] testResult = Tools.findMinimumSetInMap(testAlpha, 2);
		assertTrue(Tools.checkArrayEqual(testResult,new String[] {"a","d"}));
	}
	
	@Test
	public void testFindMinimumPairBasicSwapped() {
		Map<String, Double> testAlpha = new HashMap<String, Double>();
		testAlpha.put("a",0.1);
		testAlpha.put("b",0.6);
		testAlpha.put("c",0.3);
		testAlpha.put("d",0.2);
		
		String[] testResult = Tools.findMinimumSetInMap(testAlpha, 2);
		assertTrue(Tools.checkArrayEqual(testResult,new String[] {"a", "d"}));
	}
	
	
	@Test
	public void testFindMinimumPairEqualMin() {
		Map<String, Double> testAlpha = new HashMap<String, Double>();
		testAlpha.put("a",0.2);
		testAlpha.put("b",0.6);
		testAlpha.put("c",0.1);
		testAlpha.put("d",0.1);
		
		String[] testResult = Tools.findMinimumSetInMap(testAlpha, 2);
		
		assertTrue(Tools.checkArrayEqual(testResult,new String[] {"d", "c"}));
	}
	
	@Test
	public void testFindMinimumSetBasic() {
		Map<String, Double> testAlpha = new HashMap<String, Double>();
		testAlpha.put("a",0.2);
		testAlpha.put("b",0.6);
		testAlpha.put("c",0.3);
		testAlpha.put("d",0.1);
		
		String[] testResult = Tools.findMinimumSetInMap(testAlpha, 3);
		assertTrue(Tools.checkArrayEqual(testResult,new String[] {"d","a","c"}));
	}
	
	@Test
	public void testFindMinimumSetComplex3() {
		Map<String, Double> testAlpha = new HashMap<String, Double>();
		testAlpha.put("a",0.2);
		testAlpha.put("b",0.2);
		testAlpha.put("c",0.15);
		testAlpha.put("d",0.15);
		testAlpha.put("e",0.1);
		testAlpha.put("f",0.1);
		testAlpha.put("g",0.05);
		testAlpha.put("h",0.05);
		testAlpha.put("filler", 0.0);
		
		String[] testResult = Tools.findMinimumSetInMap(testAlpha, 3);
		assertTrue(Tools.checkArrayEqual(testResult,new String[] {"filler","h","g"}));
	}
	
	@Test
	public void testFindMinimumSetMoreComplex3() {
		Map<String, Double> testAlpha = new HashMap<String, Double>();
		testAlpha.put("a",0.25);
		testAlpha.put("b",0.2);
		testAlpha.put("c",0.005);
		testAlpha.put("d",0.15);
		testAlpha.put("e",0.1);
		testAlpha.put("f",0.4);
		testAlpha.put("g",0.05);
		testAlpha.put("h",0.05);
		testAlpha.put("filler", 0.0);
		
		String[] testResult = Tools.findMinimumSetInMap(testAlpha, 4);
		assertTrue(Tools.checkArrayEqual(testResult,new String[] {"filler","c","g","h"}));
	}
	
	@Test
	public void testFindMinimumSetAllComplex5() {
		Map<String, Double> testAlpha = new HashMap<String, Double>();
		testAlpha.put("a",0.2);
		testAlpha.put("b",0.2);
		testAlpha.put("c",0.15);
		testAlpha.put("d",0.15);
		testAlpha.put("e",0.1);
		testAlpha.put("f",0.1);
		testAlpha.put("g",0.05);
		testAlpha.put("h",0.05);
		testAlpha.put("filler", 0.0);
		
		String[] testResult = Tools.findMinimumSetInMap(testAlpha, 5);
		assertTrue(Tools.checkArrayEqual(testResult,new String[] {"filler","h","g","f","e"}));
	}
	
	@Test
	public void testFindMinimumSetAllEqualWhole() {
		Map<String, Double> testAlpha = new HashMap<String, Double>();
		testAlpha.put("a",0.2);
		testAlpha.put("b",0.2);
		testAlpha.put("c",0.2);
		testAlpha.put("d",0.2);
		testAlpha.put("e",0.2);
		
		String[] testResult = Tools.findMinimumSetInMap(testAlpha, 5);
		assertTrue(Tools.checkArrayEqual(testResult,new String[] {"a","b","c","d","e"}));
	}
	
	@Test
	public void testFillCharacterCountsBasic() {
		Map<String, Integer> AlphabetCount = new HashMap<String, Integer>();
		char[] test = new char[] {'c','c','c','b'};
		Tools.fillCharacterCounts(test, AlphabetCount);
		assertTrue(AlphabetCount.get("c") == 3);
		assertTrue(AlphabetCount.get("b") == 1);
	}
	
	@Test
	public void testConvertCountsToProbsBasic() {
		Map<String, Integer> AlphabetCount = new HashMap<String, Integer>();
		AlphabetCount.put("a",2);
		AlphabetCount.put("b",2);
		AlphabetCount.put("c",2);
		AlphabetCount.put("d",2);
		AlphabetCount.put("e",2);
		
		Map<String, Double> probabilityMap = Tools.ConvertCountsToProbs(AlphabetCount);
		double total = 0;
		for (Double value : probabilityMap.values()) {
			total += value;
		}
		assertTrue(probabilityMap.get("a") == 0.2);
		assertTrue(probabilityMap.get("b") == 0.2);
		assertTrue(probabilityMap.get("c") == 0.2);
		assertTrue(probabilityMap.get("d") == 0.2);
		assertTrue(probabilityMap.get("e") == 0.2);
		assertTrue(total == 1);
	}
	
	@Test
	public void testConvertCountsToProbsComplex() {
		Map<String, Integer> AlphabetCount = new HashMap<String, Integer>();
		AlphabetCount.put("a",3);
		AlphabetCount.put("b",3);
		AlphabetCount.put("c",7);
		AlphabetCount.put("d",2);
		AlphabetCount.put("e",5);
		
		Map<String, Double> probabilityMap = Tools.ConvertCountsToProbs(AlphabetCount);
		double total = 0;
		for (Double value : probabilityMap.values()) {
			total += value.doubleValue();
		}
		
		assertTrue(probabilityMap.get("a") == 0.15);
		assertTrue(probabilityMap.get("b") == 0.15);
		assertTrue(probabilityMap.get("c") == 0.35);
		assertTrue(probabilityMap.get("d") == 0.1);
		assertTrue(probabilityMap.get("e") == 0.25);
		assertTrue(Math.abs(total - 1) < 0.00001);
	}
}
