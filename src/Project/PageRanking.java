package Project;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PageRanking {
	/*
	 * This function will read the text from the file and return the text of given
	 * file which we access from the W3C Web Pages Folder
	 */
	public static String readTextOfFile(String fileName) throws IOException {
		// Set the folder path to access content
		String text = readFile("WebPages/" + fileName, StandardCharsets.US_ASCII);
		return text;
	}

	// This function will return the list of all files of given folder which
	// contains HTML files
	public static String[] getTheNameOfFile(String folderName) {

		// Set Folder path
		File folderPath = new File(folderName);

		// Read and return files name from the given folder name
		String fileName[] = folderPath.list();
		return fileName;

	}

	// Read the file from the given path and return the text
	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	// This function will match the pattern and return web page name along with
	// total matches per web page
	public static Hashtable<String, Integer> matchPattern(String pattern) throws IOException {
		Hashtable<String, Integer> page_rank = new Hashtable<String, Integer>();

		String text = "";

		// To get the list of all available files
		String htmlFileList[] = getTheNameOfFile("WebPages/");
		for (int i = 0; i < htmlFileList.length; i++) {
			String fileName = htmlFileList[i];

			// Read the text from the file
			text = readTextOfFile(fileName);
			// Create a Pattern object using Pattern Class for compiling pattern
			Pattern p1 = Pattern.compile(pattern);
			// Now create matcher object using Matcher Class
			Matcher m1 = p1.matcher(text);
			// loop for validating matches if found then increment matches per page
			while (m1.find())
				page_rank.merge(fileName, 1, Integer::sum);
		}

		return page_rank;// return matches of the web page along with web page name
	}

	// Main execution Function
	public static Map<String, Integer>  PageRank(String input) throws IOException {
//		Scanner sc = new Scanner(System.in);
//		System.out.print("Enter word: ");
//		String input = sc.nextLine();
//
//		// converting input in lowercase
//		input = input.toLowerCase();
//
//		sc.close();
		Map<String, Integer> sort = null;
		Hashtable<String, Integer> pageMatch = new Hashtable<>(500);
		String [] result;
		// match the KeyWord in web page using matchPattern function

		pageMatch = matchPattern(input);

		// if match not found in any web page then print not found
		if (pageMatch.size() == 0)
		{
			
		}
			//System.out.println("Not found");
		else {
			// else print total matching or the frequency of the web pages and number of
			// matching with keyword
			int totalMatch = 0;
			for (int match = 0; match < pageMatch.size(); match++)
				totalMatch += match;

//			System.out.println("Matches\t\t  Pages");

			// Listing and give the rank to the top 1 best match page

		 sort = pageMatch.entrySet().stream().limit(1)
					.sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).collect(Collectors
							.toMap(Map.Entry::getKey, Map.Entry::getValue, (m1, m2) -> m1, LinkedHashMap::new));
		}
		
		return sort;

	}

}
