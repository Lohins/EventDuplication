package simAlgorithm;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

public class LCSSimilarity {

	public LCSSimilarity() {
		// TODO Auto-generated constructor stub
	}
	
	
	/*
	 * func to generat tokens with google tokenized tool.
	 * */
	public List<String> getTokens(Splitter splitter , String text){
		
		List<String> result = new ArrayList<String>();
		Iterable<String> splits = splitter.split(text);
		for (String string : splits) {
			result.add(string.toLowerCase());
		}
		
		return result;
		
	}
	
	/*
	 * fucn to get the the longest common subsequence. Use dynamic programing
	 * */ 
	private ArrayList<String> getLCS(List<String> tokens1 , List<String> tokens2){
		
		int[][] index = new int[tokens1.size() + 1][ tokens2.size() + 1];
		
		// set default value to zero.
		for(int i = 0; i < (tokens1.size() + 1); i++ ){
			index[i][0] = 0;
		}
		for(int i = 0; i < (tokens1.size() + 1); i++ ){
			index[0][i] = 0;
		}
		
		// dynamic programing to get the array
		for (int i = 1; i < (tokens1.size() + 1); i++){
			for (int k = 1; k < (tokens2.size() + 1); k++){
				if(tokens1.get(i - 1).equals(tokens2.get(k - 1))){
					index[i][k] = index[i - 1][k - 1] + 1;
				}
				else if(index[i - 1][k] >= index[i][k - 1]){
					index[i][k] = index[i - 1][k];
				}
				else{
					index[i][k] = index[i][k - 1];
				}
			}
		}
		
		// find the longest common subsequence.
		ArrayList<String> result = new ArrayList<String>();
		int i = tokens1.size();
		int k = tokens2.size();
		while(i > 0 && k > 0){
			if(tokens1.get(i - 1).equals(tokens2.get(k - 1))){
				result.add(tokens1.get(i - 1));
				i--;
				k--;
			}
			else if(index[i - 1][k] >= index[i][k - 1]){
				i--;
			}
			else{
				k--;
			}
		}
		
		return result;
	}
	
	
	/*
	 * func to calculate similarity.
	 * */
	public double similarity(String text1, String text2){
		
		// 1. tokenization
		Splitter splitter = Splitter.on(CharMatcher.anyOf(" .,;!?:(){}")).trimResults().omitEmptyStrings();
		
		List<String> tokens1 = getTokens(splitter, text1);
		List<String> tokens2 = getTokens(splitter, text2);
		
		// 2. get LCS
		ArrayList<String> lcs = getLCS(tokens1, tokens2);
		
		for (String string : lcs) {
			System.out.println(string);
		}
		
		double sim = (double)lcs.size() / (tokens1.size() + tokens2.size()) * 2;
		return sim;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String text1 = "My name is sitong, I like apple";
		String text2 = "My name IS Jin, I like apple";
		
		LCSSimilarity lcsSimilarity = new LCSSimilarity();           
		double sim = lcsSimilarity.similarity(text1, text2);
		System.out.println("Similarity is " + sim);
	}

}
