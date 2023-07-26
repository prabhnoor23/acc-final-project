package Project;
import java.util.HashMap;
import java.util.Map;

class SearchFrequency {
	
	static HashMap<String, Integer> searchFrequencyMap = new HashMap<>();
	
	public static int searchFreq(String word) {
		if(searchFrequencyMap.containsKey(word)) {
			int previousFrequency = searchFrequencyMap.get(word);
			searchFrequencyMap.put(word,previousFrequency+1);
			return previousFrequency;
		} else {
			searchFrequencyMap.put(word, 1);
			return 0;
		}
	}
	
	public static void main(String[] args)
	{
		
	}
}