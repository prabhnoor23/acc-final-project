package Project;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.*;
public class Main {

	/**
	  * Entry Point of the Program
	  */
	public static void main(String args[]) throws IOException {

		System.out.println("Welcome to Office Supply Scout for finding the best deals on office supply");
		
		
		
		boolean findDeals = true;
		Scanner sc = new Scanner(System.in);
		
		// Intializing variable
				SpellChecking spellCheck = new SpellChecking();
				SearchFrequency searchFrequency = new SearchFrequency();
				PageRanking pageRank = new PageRanking();
				MergeSortReviewSortingAlgorithm mergesort=new MergeSortReviewSortingAlgorithm();
		
				// Started Crawling website
				WebCrawler webCrawler = new WebCrawler();
				while (findDeals) {
					InvertedIndex invertedIndex = new InvertedIndex();
					
				
					System.out.println("Please Enter the item for which you want to find best deal for:");
					String searchItem = sc.next(); // getting input from user
					pageRank.PageRank(searchItem);
					
					if(Utilities.isAplhaOnly(searchItem)) {
						SpellCheckReturnObject spellCheckerObj = spellCheck.spellCheck(searchItem); // checking spelling before finding product
						
						if (spellCheckerObj.wordFoundInDictionary) {
							
							int numberOfTimeSearched = searchFrequency.searchFreq(searchItem); // search Frequency
							System.out.println("This word was previously searched " + numberOfTimeSearched + " times");
							
							System.out.println("Searching for the Best Deal.Please be patient, it may take some time.......");
							System.out.println("Crawling staples.com");
							HashSet<String> crawledstaples=webCrawler.crawlWebsite("https://www.staples.com/" +searchItem+ "/directory_" +searchItem, 1);
							
//							System.out.println("Crawling Walmart.com");
//							HashSet<String> crawledWalmart= webCrawler.crawlWebsite("https://www.walmart.ca/search?q="+searchItem, 1);
							
							String text=webCrawler.HTMLtoText("https://www.staples.com/" +searchItem+ "/directory_" +searchItem);
							String savedfile=searchItem;
							
							
							try {
								String filePath = "WebPages\\" + searchItem +"_staples.txt";
								FileWriter fileWriter = new FileWriter(filePath); // Create a FileWriter
					            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
					            bufferedWriter.write(text);
					            bufferedWriter.close();
							}
						
						catch(IOException e) {
							e.printStackTrace();
						}
							
							try {
								String filePath="CrawledPages.txt";
								FileWriter fileWriter = new FileWriter(filePath); // Create a FileWriter
					            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
					            for(String link:crawledstaples) {
					            	bufferedWriter.write(link);
					            	bufferedWriter.newLine();
					            }
					            
//					            for(String link:crawledWalmart) {
//					            	bufferedWriter.write(link);
//					            	bufferedWriter.newLine();
//					            }
					            bufferedWriter.close();
					            
								
							}
							catch(IOException e) {
								e.printStackTrace();
							}

							Map<String, Integer> pageRankForInput = pageRank.PageRank(searchItem); // getting page rank
							pageRankForInput.forEach((key, value) -> System.out.println(searchItem +" word was found "+value+" times in file " + key));
						
							System.out.println("Searching for the Best Deal....");
							mergesort.findItemWithMostReviews(savedfile);
//							File folderDirectory = new File("C:/Users/Jasha/Downloads/ACC-FinalProject/ACC-FinalProject/WebPages");
							File folderDirectory = new File("WebPages");
							invertedIndex.readFile();
							InvertedIndex.createFilesArrayList(folderDirectory);
							System.out.println("Do you want to continue? Please enter 0 to exit and 1 to search another word.");
							int continueInput = sc.nextInt();
							boolean correctInput = true;
							
							// Loop check whether user enter correct value
							while(correctInput) {
								if(continueInput == 0 || continueInput == 1) {
									if(continueInput == 0) {
										findDeals = false;
									} else {
										findDeals = true;
									}
									correctInput = false;
								} else {
									System.out.println("Wrong Input! Please enter 0 to exit and 1 to search another word.");
									continueInput = sc.nextInt();
									correctInput = true;
								}
							}
						} else {
							System.out.println("Sorry we are not able to find the entered word.");
							System.out.println("Suggested words for search are: " + spellCheckerObj.alternativeWordSuggestions);
						}
					}else {
						System.out.println("Input Invalid");
					}

				}
				
		
		

		

		System.out.println("Thanks for using Tech Right system for finding best deals.");
	}
}
