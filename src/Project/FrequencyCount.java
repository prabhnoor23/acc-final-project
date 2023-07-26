package Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class FrequencyCount {
	
	/**
	  * Checks whether the string is alphanumeric or not
	  * @param s String.
	  */
	
	public static boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }

	/**
	 * @param args
	 */
	public void frqcount() {
		
		StringBuilder contentBuilder = new StringBuilder();
		//File Reading
		try {
		    BufferedReader in = new BufferedReader(new FileReader("WebPages/1099TaxFormsStaples.txt"));
		    String str;
		    while ((str = in.readLine()) != null) {
		        contentBuilder.append(str);
		    }
		    in.close();
		} catch (IOException e) {
			System.out.println("Unable to read file");
		}
		
		// Converting file content to string
		String content = contentBuilder.toString();
		StringTokenizer st = new StringTokenizer(content," ");
	
		Hashtable<String, Integer> frequencyTable = new Hashtable<String, Integer>(); // java default hashtable
		String word;
		//iterating the string words
	     while (st.hasMoreTokens()) {
	    	 word = st.nextToken();
	    	 if(isAlphaNumeric(word)) {
	    		 word = word.toLowerCase(); // case ignoring
	    		 if (frequencyTable.containsKey(word)) {
	    		        frequencyTable.put(word, frequencyTable.get(word) + 1); // increasing word counter if already present in hash table
	    		    } else {
	    		        frequencyTable.put(word, 1);
	    		    }
	    	 }
	     }

	}

}
