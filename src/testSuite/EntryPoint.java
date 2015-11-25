package testSuite;
import java.util.HashMap;
import java.util.Map;

import generator.Generator;

public class EntryPoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String test = "Enter a sample using the alphabet to be converted here.";
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
		
		Generator generator = new Generator();
		generator.setBaseD(2);
		Map<String, Double> testAlpha2 = generator.generateAlphabet(test);
		Map<String,String> hCode = generator.createHuffmanCode(testAlpha2);
		
		for (String key : hCode.keySet()) {
			System.out.println(key + " - " + hCode.get(key));
		}
	}

}
