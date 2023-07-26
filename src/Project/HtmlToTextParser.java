package Project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlToTextParser {
	
	/**
	  * Function to convert html file to text file
	  * @param fetched URLs String.
	  */
	public static void HTMLtoText(File file1, String FileName) throws IOException {
		Document d = Jsoup.parse(file1, "utf-8");
		String doc = d.text();
		String filepath = FileName + ".txt";
		PrintWriter p = new PrintWriter(filepath);
		p.println(doc);
		p.close();
	}

}
