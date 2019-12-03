package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Rake {
	public static Map<String, Double> rake(String content){
		Map<String, Map<String, Integer>> graph = new HashMap<>();
		String[] windows = content.split(",");
		for(String window : windows) {
			String[] winWords = window.split(" ");
			for(int i = 0; i < winWords.length; ++i) {
				for(int j = 0; j < winWords.length; ++j) {
					if(!graph.containsKey(winWords[i])) graph.put(winWords[i], new HashMap<>());
					Map<String, Integer> countMap = graph.get(winWords[i]);
					int count = countMap.getOrDefault(winWords[j], 0);
					countMap.put(winWords[j], count+1);
				}
			}
		}
		Map<String, Double> wordScore = new HashMap<>(); // 单词得分
		for(Entry<String, Map<String, Integer>> e : graph.entrySet()) {
			int sum = 0;
			Map<String, Integer> countMap = e.getValue();
			for(int count : countMap.values()) {
				sum += count;
			}
			String word = e.getKey();
			wordScore.put(word, (double)sum/countMap.get(word));
		}
		Map<String, Double> phraseScore = new HashMap<>(); // 短语得分
		for(String window : windows) {
			String[] winWords = window.split(" ");
			double sum = 0;
			for(String word : winWords) {
				sum += wordScore.get(word);
			}
			phraseScore.put(window, sum);
		}
		return phraseScore;
	}
	
	
	public static void main(String[] args) {
		// 测试文本
		String content = "compatibility,systems,linear constraints,set,natural numbers,criteria," + 
				"compatibility,system,linear diophantine equations,strict inequations,nonstrict " + 
				"inequations,upper bounds,components,minimal set,solutions,algorithms," + 
				"minimal generating sets,solutions,systems,criteria,corresponding algorithms," + 
				"constructing,minimal supporting set,solving,systems,systems";
		
		Map<String, Double> phraseScore = rake(content); // 短语得分
		for(Entry<String, Double> e : phraseScore.entrySet()) {
			System.out.println(e.getKey()+"\t"+e.getValue());
		}
	}

}
