package Project;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import Project.HtmlToTextParser;

public class StaplesCrawling {
    public static void main(String[] args) {
        String searchItem = "pen";
        String staplesUrl = "https://www.staples.com/" + searchItem + "/directory_" + searchItem;
        String query = "pen"; // Adjust the search query as needed
        String searchUrl = staplesUrl + "?q=" + query;

        try {
            // Fetch the HTML content of the Staples search results page
            Document document = Jsoup.connect(staplesUrl).get();
            HtmlToTextParser htmltotextparser=new HtmlToTextParser();
       //     htmltotextparser.HTMLtoText(document,searchItem);
            System.out.println(document);
            Elements spanElements = document.select(".money.pre-money");

            // Iterate over the selected elements and print the text content
            for (Element element : spanElements) {
                String price = element.text();
                System.out.println("Price: " + price);
            }

            // Extract the product elements from the search results
            Elements products = document.select(".product-details");

            // Iterate over each product and extract the pen name and price
            for (Element product : products) {
                // Extract the pen name
                Element nameElement = product.select(".product-title-link").first();
                String penName = nameElement.text();

                // Extract the pen price
                Element priceElement = product.select(".price").first();
                String penPrice = priceElement.text();

                // Output the pen name and price
                System.out.println("Pen Name: " + penName);
                System.out.println("Pen Price: " + penPrice);
                System.out.println("--------------------------------");
            }

            // Save the document to a text file
            saveDocumentToFile(document, "output.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDocumentToFile(Document doc, String fileName) throws IOException {
        // Get the HTML content of the document
        String html = doc.html();

        // Write the HTML content to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(html);
        }
    }
}
