package Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SpellChecking {
	
	static HashSet<String> wordDictionary = new HashSet<String>();
	static int wordSuggestionsCount = 5;
	
	static String dataReadFromTextFile(File folderDirectory) throws IOException {
		StringBuilder contentBuilder = new StringBuilder();
		String data;
		for (File fileEntry : folderDirectory.listFiles()) {
	    	String fileName = fileEntry.getName();
			BufferedReader bufferedReader = new BufferedReader(new FileReader("WebPages/"+fileName));

			while(( data = bufferedReader.readLine())!= null) {
				contentBuilder.append(data);
				contentBuilder.append(" ");
			}
	    }
		return contentBuilder.toString();
	}
	
	static void makeHashSetFromDataString(String data) {
		StringTokenizer tokenizer = new StringTokenizer(data, " ");
		while(tokenizer.hasMoreTokens()) {
			String wordToken = tokenizer.nextToken().toLowerCase();
			wordDictionary.add(wordToken);
		}
	}
	
	static ArrayList<String> findAlternativeWords(String word) {
		ArrayList<String> alternativeWords = new ArrayList<String>();
		TreeMap<Integer, ArrayList<String>> editDistanceValueMap = new TreeMap<Integer,ArrayList<String>>();
		
		Iterator<String> iterator = wordDictionary.iterator();
		while(iterator.hasNext()) {
			String wordFromDictionary = iterator.next();
			int editDistanceValue = editDistance(wordFromDictionary, word);
			ArrayList<String> wordsList;
			if(editDistanceValueMap.containsKey(editDistanceValue)) {
				wordsList = editDistanceValueMap.get(editDistanceValue);
			} else {
				wordsList = new ArrayList<String>();
			}
			wordsList.add(wordFromDictionary);
			editDistanceValueMap.put(editDistanceValue, wordsList);
		}
		
		Collection<ArrayList<String>> values = editDistanceValueMap.values();
		Iterator<ArrayList<String>> it = values.iterator();
		ArrayList<String> valuesList;
		int count = 0;
		while(it.hasNext() && count < 5) {
			valuesList = it.next();
			valuesList.sort(null);
			for(int i=0; i < valuesList.size() && count < 5;i++) {
				alternativeWords.add(valuesList.get(i));
				count++;
			}
		}
		
		return alternativeWords;
	}
	
	static SpellCheckReturnObject spellCheck(String word) throws IOException {
		File folderDirectory = new File("WebPages/");
		String dataFromTextFile = dataReadFromTextFile(folderDirectory);
		makeHashSetFromDataString(dataFromTextFile);
		
		SpellCheckReturnObject result = new SpellCheckReturnObject();
		result.wordFoundInDictionary = wordDictionary.contains(word);
		result.alternativeWordSuggestions = new ArrayList<String>();
		if(!result.wordFoundInDictionary) {
			result.alternativeWordSuggestions = findAlternativeWords(word);
		}
		return result;
	}
	
	static int editDistance(String word1, String word2) {
		int word1Length = word1.length();
		int word2Length = word2.length();
	 
		int[][] editDistDP = new int[word1Length + 1][word2Length + 1];
	 
		for (int i = 0; i <= word1Length; i++) {
			editDistDP[i][0] = i;
		}
	 
		for (int j = 0; j <= word2Length; j++) {
			editDistDP[0][j] = j;
		}
	 
		for (int i = 0; i < word1Length; i++) {
			char char1 = word1.charAt(i);
			for (int j = 0; j < word2Length; j++) {
				char char2 = word2.charAt(j);
	 
				if (char1 == char2) {
					editDistDP[i + 1][j + 1] = editDistDP[i][j];
				} else {
					int replaceDist = editDistDP[i][j] + 1;
					int insertDist = editDistDP[i][j + 1] + 1;
					int deleteDist = editDistDP[i + 1][j] + 1;
					
					int minimumDist = replaceDist > insertDist ? insertDist : replaceDist;
					minimumDist = deleteDist > minimumDist ? minimumDist : deleteDist;
					
					
					editDistDP[i + 1][j + 1] = minimumDist;
				}
			}
		}
	 
		return editDistDP[word1Length][word2Length];
	}
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub



	}
	
	

}
