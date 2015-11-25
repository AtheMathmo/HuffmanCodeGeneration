package utilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tools {
	
	public static String[] findMinimumSetInMap(Map<String, Double> Alphabet, int base) {
		// o(n*base).
		String[] minimumKeys = new String[base];
		
		double[] minimumProbs = new double[base];
		Arrays.fill(minimumProbs, 1);
		
		int i = 0;
		for (String key: Alphabet.keySet()) {
			double probability = Alphabet.get(key).doubleValue();

			if (i < base) {
				minimumKeys[i] = key;
				minimumProbs[i] = probability;
				i++;
				continue;
			}
			//System.out.println("pre : "+Arrays.deepToString(minimumKeys));
			
			replaceMinimumValues(base, probability, key, minimumKeys, minimumProbs);
			//System.out.println(Arrays.deepToString(minimumKeys));
		}
		return minimumKeys;
	}
	
	private static void replaceMinimumValues(int base, double probability, String key, String[] minimumKeys, double[] minimumProbs) {
		double[] probDifferences = new double[base];
		
		for (int k = 0; k < base; k++) {
			probDifferences[k] = minimumProbs[k] - probability;
		}
		
		int replaceIndex = 0;
		for (int k = 1; k < base; k++) {
			replaceIndex = (probDifferences[k] >= probDifferences[replaceIndex] ? k:replaceIndex);
		}
		if (probDifferences[replaceIndex] > 0) {
			minimumKeys[replaceIndex] = key;
			minimumProbs[replaceIndex] = probability;
		}
	}

	public static void fillCharacterCounts(char[] rawCharacters, Map<String, Integer> AlphabetCount) {
		for (char character : rawCharacters) {
			String charStr = Character.toString(character);
			if (AlphabetCount.containsKey(charStr)) {
				int currentCount = AlphabetCount.get(charStr);
				AlphabetCount.replace(charStr, ++currentCount);
			}
			else {
				AlphabetCount.put(charStr,1);
			}
		}
	}
	
	public static Map<String,Double> ConvertCountsToProbs(Map<String, Integer> AlphabetCount) {
		int totalCount = 0;
		Map<String,Double> probabilityAlphabet = new HashMap<String, Double>();
		
		for (Integer value : AlphabetCount.values()) {
			totalCount += value.intValue();
		}
		
		for (String key : AlphabetCount.keySet()) {
			probabilityAlphabet.put(key,1.0*AlphabetCount.get(key)/totalCount);
		}
		return probabilityAlphabet;
	}
	
	public static boolean checkArrayEqual(String[] array1, String[] array2) {
		boolean equalCheck = true;
		for (String ele1 : array1) {
			boolean ele1In = false;
			for (String ele2 : array2) {
				if (ele2.equals(ele1))
					ele1In = true;
			}
			equalCheck = equalCheck && ele1In;
		}
		return equalCheck;
	}
	
	public static String GenerateOutputString(Map<String,String> hCode) {
		String outputString = "";
		
		for (String key : hCode.keySet()) {
			outputString += key + " : " + hCode.get(key) + "\n";
		}
		return outputString;
	}
}
