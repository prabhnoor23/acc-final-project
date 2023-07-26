package Project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.net.URLConnection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;

public class WebCrawler {
	// Variable Intialization
	private static final int maxPageLimit = 100; // variable to check only 100 pages are crawled 
	private static final int maximumDepth = 3;	// variable defined the depth of fetching url
	private static HashSet<String> urlList;	
	
	/**
	  * Constructor
	  */
	public WebCrawler() {
		urlList = new HashSet<String>();
	}
	
	/**
	  * Function to fetch URLs from given website
	  * @param url String.
	  */
	public static Elements fetchUrlsFromWebsite(String url) throws IOException {
		Elements listOfURLExtracted;
		Document document;
		document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").referrer("http://www.google.com").timeout(8000).ignoreHttpErrors(true).get();
		listOfURLExtracted = document.select("a[href]");
		crawlPages(url);
		return listOfURLExtracted;
	}

	/**
	  * Function to crawl website
	  * @param HashSet<String>.
	  */
	public HashSet<String> crawlWebsite(String url,int depth)
	{
		if(((!url.isEmpty()) &&(!urlList.contains(url)) && (depth <= maximumDepth))) // condition to check if url is empty and maximum depth has been reached
		{
			try
			{
				urlList.add(url); // add fetched urls is list array
				if(urlList.size() >= maxPageLimit) // Condition to check whether maximum page fetch limit is reached
				return urlList;
				Elements urlsFromWebPage = fetchUrlsFromWebsite(url);
				
				depth = depth+1;
				
				for(Element webpage: urlsFromWebPage)
				{
					crawlWebsite(webpage.attr("abs:href"), depth); // get list of navigation link from webpage and crawl website in recurssion
				}			
			}
			catch(Exception e)
			{
				System.out.println("Error Occured");
			}		
		}
		return urlList;
	}
	
	/**
	  * Function to create Text File
	  */
	public static void createTextFile(String title,String text,String url) {
		try {
			String[] titlesplit = title.split("\\|");
			String newTitle = "";
			for(String s : titlesplit) {
				newTitle = newTitle+" "+s;
			}
			if(newTitle == "") {
				newTitle = "homepage";
			}
			newTitle = newTitle.replaceAll("[^A-Za-z0-9]", ""); // Regex to remove special characters from file name
			File f = new File("WebPages//"+newTitle+".txt");
			f.createNewFile();			
			PrintWriter pw = new PrintWriter(f);
			pw.println(url);
			pw.println(text);
			pw.close();
			
		} catch (Exception e) {
			System.out.println("Error occured");
		}
		
	}
	
	/**
	  * Function to covert html document to text document
	  * @param websiteURL String.
	  */
	public static String HTMLtoText(String websiteUrl) {
		try {
			Document document = Jsoup.connect(websiteUrl).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").referrer("http://www.google.com").timeout(8000).ignoreHttpErrors(true).get();
			String text = document.body().text();
			return text;
		}
		catch(Exception e) {
			return "Error while converting file";
		}
	}
	
	/**
	  * Function o convert url to html
	  * @param url String.
	  */
	public static String urlToHTML(String url){
		try {
			URL url1 = new URL(url);
			URLConnection conn = url1.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			Scanner sc = new Scanner(conn.getInputStream());
			StringBuffer sb = new StringBuffer();
			while(sc.hasNext()) {
				sb.append(" "+sc.next());
			}
			
			String result = sb.toString();
			sc.close();
			return result;
		}
		catch(Exception e) {
			System.out.println("Error Occured");
		} 
		return url;
	}
	
	/**
	  * Function to get products from website
	  * @param url String.
	  */
	public static void geWebsiteDetailsPage(String url) {
		try {
			Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
		               .referrer("http://www.google.com").timeout(1000*8).ignoreHttpErrors(true).get();
			
			Elements repositories = doc.getElementsByClass("standard-tile__title");
			
			
			Element firstProduct = repositories.first();
			Elements finalProduct = firstProduct.getElementsByTag("a");
			
			System.out.println("Best deal we could find is :"+finalProduct.text());
			System.out.println("You can find the above product on : https://www.staples.com" +finalProduct.attr("href"));
			
		}
		catch(Exception e){
			System.out.println("Error");
		}
	}
	
	/**
	  * Function to crawl webpages
	  * @param url String depth integer.
	  */
	public static String crawl(String url, int depth) {	
		String html = urlToHTML(url); // convert url to HTML
		Document document = Jsoup.parse(html);  
		String text = document.text();
		String title = document.title();
		createTextFile(title,text,url);
		
		Elements e = document.select("a");
		String urls = "";
		
		for(Element webpage : e) {
			String href = webpage.attr("abs:href");
			if(href.length()>3 && (depth <= maximumDepth))
			{
				urls = urls+"\n"+href;
				depth = depth+1;
			}
		}
		return urls;
	}
	
	/**
	  * Function to crawl pages recurssively
	  * @param fetched URLs String.
	  */
	public static void crawlPages(String fetchedUrls) {
		try {
			File f = new File("CrawledPages.txt");
			f.createNewFile();
			FileWriter fwt = new FileWriter(f);
			fwt.close();
						
			String urls1 = "";
			for(String url: fetchedUrls.split("\n")) {				
				urls1 = urls1 + crawl(url, 6);
					
				FileWriter fw = new FileWriter(f,true);
				fw.write(url + "\n");
				fw.close();
				
			}
			
			String urls2 = "";
			for(String url: urls1.split("\n")) {
				In in = new In(f);
				String urlsRead = in.readAll();
				if(!urlsRead.contains(url) ) {
					urls2 = urls2 + crawl(url, 6);
					
					FileWriter fw = new FileWriter(f,true);
					fw.write(url + "\n");
					fw.close();
				}
			}
			
			for(String url: urls2.split("\n")) {
				In in = new In(f);
				String urlsRead = in.readAll();
				if(!urlsRead.contains(url)  ) {
					crawl(url, 3);
					
					FileWriter fw = new FileWriter(f,true);
					fw.write(url + "\n");
					fw.close();
				}			
				
			}
		
		}
		catch(Exception e) {
			System.out.println("Error Occured");
		}
	}
}
