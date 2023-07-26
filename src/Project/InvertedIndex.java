package Project;

import java.util.*;
import java.io.*;

public class InvertedIndex {
	public String str;

	static void createFilesArrayList(File folderDirectory) throws IOException {

		for (File fileEntry : folderDirectory.listFiles()) {
			Docs.add(fileEntry.getName());
		}
	}

	// Array of documents
	static ArrayList<String> Docs = new ArrayList<String>();

	public static int getCount(String word, HashMap<String, Integer> frequencyData) {
		if (frequencyData.containsKey(word)) { // The word has occurred before, so get its count from the map
			return frequencyData.get(word); // Auto-unboxed
		} else { // No occurrences of this word
			return 0;
		}
	}

	public static void readFile() throws IOException {
		HashMap<String, Integer> frequencyData = new HashMap<>();
//		Scanner wordFile;
//		String word; // A word read from the file
//		Integer count; // The number of occurrences of the word
		System.out.println("-----------------------------------------------");
		
		File folderDirectory = new File("WebPages/");
		createFilesArrayList(folderDirectory);
		// Specify the file path and name where you want to store the data
		String filePath = "invertedindex.txt";
		// FOR LOOP TO READ THE DOCUMENTS


		// Create a FileWriter and BufferedWriter to write to the file
		try (FileWriter fileWriter = new FileWriter(filePath);
		     BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

		    // Iterate over the frequencyData map and write the data to the file
		    for (int x = 0; x < Docs.size(); x++) {
		        try {
		            Scanner wordFile = new Scanner(new FileReader("WebPages/" + Docs.get(x)));

		            while (wordFile.hasNext()) {
		                // Read the next word and get rid of the end-of-line marker if needed:
		                String word = wordFile.next();
		                wordFile.useDelimiter("[^a-zA-Z]+");
		                // This makes the word lowercase.
		                word = word.toLowerCase();

		                // Get the current count of this word, add one, and then store the new count:
		                int count = getCount(word, frequencyData) + 1;
		                frequencyData.put(word, count);
		            }

		            // Write the data to the file for the current document
		            for (String word1 : frequencyData.keySet()) {
		                bufferedWriter.write(String.format("%s   %15d   %s\n", Docs.get(x), frequencyData.get(word1), word1));
		            }

		            // Clear the frequencyData map for the next document
		            frequencyData.clear();

		            wordFile.close();
		        } catch (FileNotFoundException e) {
		            System.err.println(e);
		            return;
		        }
		    }

		    // Print a message indicating successful writing to the file
		    System.out.println("Data has been written to the file successfully in inverted index file!");

		} catch (IOException e) {
		    e.printStackTrace();
		}


	}

}
