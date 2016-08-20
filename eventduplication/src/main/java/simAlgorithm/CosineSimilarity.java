package simAlgorithm;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;

import java.math.*;

import org.apache.commons.collections.*;

import com.google.common.base.*;

public class CosineSimilarity {
	
	private String text1 = "";
	private String text2 = "";
			

	public CosineSimilarity() {
		// TODO Auto-generated constructor stub
	}
	
	public CosineSimilarity(String text1 , String text2) {
		this.text1 = text1;
		this.text2 = text2;
	}
	
	/*
	 * fucntion to tokenize the text
	 * */
	
	private List<String> getTokens( Splitter splitter , String text){
		
		Iterable<String> tokenList = splitter.split(text);
		List<String> list = new ArrayList<String>();
		for (String string : tokenList) {
			list.add(string.toLowerCase());
			System.out.println(string);
		}
		return list;
	}
	
	private HashMap<String, Double> getVector(List<String> list){
		
		int length = list.size();
		
		HashMap<String, Integer> CountMap = new HashMap<String, Integer>();
		for (String string : list) {
			
			if (CountMap.containsKey(string)) {
				CountMap.put(string, CountMap.get(string) + 1);
			}
			else{
				CountMap.put(string, 1);
			}
		}
		
		HashMap<String, Double> result = new HashMap<String, Double>();
		for (Entry<String, Integer> entry : CountMap.entrySet()) {
			double percent = (double)entry.getValue() / length;
			result.put(entry.getKey(), percent);
			
			System.out.println(entry.getKey() + "  " + entry.getValue());
		}
		
		return result;
	}
	
	/*
	 * calculate the length of vector
	 * */
	
	private double getVectorLength(HashMap<String, Double> vector){
		double all = 0.0;
		for (Entry<String, Double> entry : vector.entrySet()) {
			all += entry.getValue() * entry.getValue();
		}
		
		return Math.sqrt(all);
		
	}
	
	/*
	 * get the multiple of intersection
	 * */
	private double getNumerator(HashMap<String, Double> map1, HashMap<String, Double> map2, Collection<String> intersection){
		System.out.println("intersection---------------------------");
		double all = 0.0;
		for (String string : intersection) {
			System.out.println(string);
			all += map1.get(string) * map2.get(string);
		}
		return all;
		
	}
	
	public double similarity() {
		
		// Tokenization
		// build a splitter
		Splitter splitter = Splitter.on(CharMatcher.anyOf(" .,;!?:(){}")).trimResults().omitEmptyStrings();
		
		List<String> tokens1 = getTokens(splitter, this.text1);
		List<String> tokens2 = getTokens(splitter, this.text2);
		
		
		
		HashMap<String, Double> map1 = getVector(tokens1);
		HashMap<String, Double> map2 = getVector(tokens2);
		
		// get intersection
		Collection<String> intersection = CollectionUtils.intersection(map1.keySet(), map2.keySet());
		
		
		double denominator = getVectorLength(map1) * getVectorLength(map2);
		System.out.println("denomitor:" + denominator);
		
		if(denominator == 0.0){
			return 0.0;
		}
		
		double numerator = getNumerator(map1, map2, intersection);
		System.out.println("numerator:" + numerator);
	
		return numerator / denominator;
	}
	
	public static void main(String[] args) {
		
		String text1 = "My name is sitong, today is my birthday.";
		String text2 = "My name was lili.";
		String text3 = "My name is sitong, today is my birthday.";
		
		CosineSimilarity cosineSimilarity = new CosineSimilarity(text1, text3);
		
		double sim = cosineSimilarity.similarity();
		System.out.println(sim);
		
	}

}
