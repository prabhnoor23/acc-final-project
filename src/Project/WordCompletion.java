package Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class WordCompletion {

	private static Trie trie = new Trie();
	
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
	
	
	public static void makeTrieFromDataString(String data) {
		StringTokenizer tokenizer = new StringTokenizer(data, " ");
		while(tokenizer.hasMoreTokens()) {
			String wordToken = tokenizer.nextToken().toLowerCase();
			if(Utilities.isAplhaOnly(wordToken)) {
				Trie.add(wordToken);
			}
		}
	}
	
	public static String wordCompletion(String word) throws IOException {
		if(word == "") 
			return "";
		
		File folderDirectory = new File("WebPages/");
		String dataFromTextFile = dataReadFromTextFile(folderDirectory);
		makeTrieFromDataString(dataFromTextFile);
		
		String result = "";
		TrieNode currNode = trie.root;;
		
		for(int i=0; i< word.length();i++) {
			int charIndex = word.charAt(i) - 'a';
			if(currNode.children[charIndex] != null) {
				result += word.charAt(i);
				currNode = currNode.children[charIndex];
			} else {
				return word;
			}
		}
		
		while(!currNode.isEnd) {
			for(int i=0;i<26;i++) {
				if(currNode.children[i] != null) {
					result += (char)('a' + i);
					currNode = currNode.children[i];	
					break;
				}
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		

		
		System.out.println(wordCompletion("chai"));
		
	}

}
