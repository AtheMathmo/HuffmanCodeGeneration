package generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.Tools;

public class Generator {
	
	private int baseD = 2;
	
	public int getBaseD() {
		return baseD;
	}


	public void setBaseD(int baseD) {
		this.baseD = baseD;
	}


	public Map<String, Double> generateAlphabet(String inputData) {
		Map<String, Integer> AlphabetCount = new HashMap<String, Integer>();
		
		char[] rawCharacters = inputData.toCharArray();
		
		Tools.fillCharacterCounts(rawCharacters, AlphabetCount);
		Map<String, Double> alphabet = Tools.ConvertCountsToProbs(AlphabetCount);
		
		if (baseD > 2) {
			int alphabetSize = alphabet.size();
			int i = 0;
			while (alphabetSize % (baseD-1) != 1) {
				alphabet.put("filler"+i,0.0);
				alphabetSize = alphabet.size();
				i++;
			}
		}
		return alphabet;
			
	}
	
private String[] compressAlphabet(Map<String, Double> alphabet) {
		
		String[] minimumSet = Tools.findMinimumSetInMap(alphabet, baseD);
		
		double newProb = 0;
		String newEntry = "";
		for (String minValueKey : minimumSet) {
			newProb += alphabet.get(minValueKey);
			newEntry += minValueKey;
			
			alphabet.remove(minValueKey);
		}
		alphabet.put(newEntry, newProb);

		
		return minimumSet;
	}

/* BINARY BACKUP
	private Map<String, String> createHCodeLayer(Map<String, Double> alphabet, Map<String, String> currentHCode) throws Exception {
		Map<String, String> hCode = new HashMap<String, String>();
		
		// There will be D elements in Alphabet.keys that aren't in currentHCode.keys (as we combine D at a time)		
		List<String> unmappedKeys = new ArrayList<String>();
		
		// Copy over the unchanged keys, and find the changed ones.
		for (String key : alphabet.keySet()) {
			if (currentHCode.containsKey(key)) {
				hCode.put(key, currentHCode.get(key));
			}
			else {
				unmappedKeys.add(key);
			}
		}
		// Check if there has been a problem somewhere.
		if (unmappedKeys.size() > 2) {
			throw new Exception("Map not built correctly between layers of alphabets.");
		}
		// Need both orientations, this is easier then filtering to find the correct one by probability weights.
		String oldKey0 = unmappedKeys.get(0) + unmappedKeys.get(1);
		String oldKey1 = unmappedKeys.get(1) + unmappedKeys.get(0);
		
		for (int i = 0; i < unmappedKeys.size(); i++) {
			String prefix = (currentHCode.get(oldKey0) == null ? "":currentHCode.get(oldKey0)) + (currentHCode.get(oldKey1) == null ? "":currentHCode.get(oldKey1));
			hCode.put(unmappedKeys.get(i), prefix + Integer.toString(i));
		}
		return hCode;
	}
	*/
	
	private Map<String, String> createHCodeLayerInD(Map<String, Double> alphabet, Map<String, String> currentHCode, String[] orderedCondensedKeys) throws Exception {
		
		Map<String, String> hCode = new HashMap<String, String>();
		
		// There will be two elements in Alphabet.keys that aren't in currentHCode.keys (as we combine 2 at a time)		
		List<String> unmappedKeys = new ArrayList<String>();
		
		/*
		//Printing to check everything is mapped properly.
		System.out.println("alphabet:");
		for (String key: alphabet.keySet()) {
			System.out.print(key+",");
		}
		System.out.println(Arrays.deepToString(orderedCondensedKeys));
		
		System.out.println("hCode:");
		for (String key: currentHCode.keySet()) {			
			System.out.print(key+":"+currentHCode.get(key)+",");
		}
		System.out.println();
		*/
		
		
		
		// Copy over the unchanged keys, and find the changed ones.
		for (String key : alphabet.keySet()) {
			if (currentHCode.containsKey(key)) {
				hCode.put(key, currentHCode.get(key));
			}
			else {
				unmappedKeys.add(key);
			}
		}
		
		// Check if there has been a problem somewhere.
		if (unmappedKeys.size() != baseD || unmappedKeys.size() != orderedCondensedKeys.length) {
			throw new Exception("Map not built correctly between layers of alphabets.");
		}
		
		// Construct the previous key using the record of the keys which were combined.
		String oldKey = "";
		for (String key : orderedCondensedKeys) {
			oldKey += key;
		}
		
		// Build the Huffman code.
		for (int i = 0; i < orderedCondensedKeys.length; i++) {
			String prefix = (currentHCode.get(oldKey) == null ? "":currentHCode.get(oldKey));
			hCode.put(unmappedKeys.get(i), prefix + Integer.toString(i));
		}
		return hCode;
	}
	
	public Map<String,String> createHuffmanCode(Map<String, Double> alphabet) {
		int alphabetSize = alphabet.size();
		
		Map<String, String> hCode = new HashMap<String, String>();	
		List<Map<String, Double>> alphabetStages = new ArrayList<Map<String, Double>>();
		List<String[]> orderedCondensedKeys = new ArrayList<String[]>();
		
		// Initialise
		alphabetStages.add(new HashMap<String, Double>(alphabet));	
		
		// Crunches down the alphabet until it contains only D elements.
		while (alphabetSize > baseD) {;
			String[] keySet = compressAlphabet(alphabet);
			orderedCondensedKeys.add(keySet);
			
			alphabetStages.add(new HashMap<String, Double>(alphabet));
			alphabetSize = alphabet.size();
		}
		
		int stageCount = alphabetStages.size();
		
		//Place holder to line up indices (the last entry has no meaning, but the others must match up).
		orderedCondensedKeys.add(new String[baseD]);
		
		for (int i = stageCount-1; i >= 0; i--) {
			try {
				hCode = createHCodeLayerInD(alphabetStages.get(i), hCode, orderedCondensedKeys.get(i));
				alphabetStages.get(i).clear();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return hCode;
	}
	
}
