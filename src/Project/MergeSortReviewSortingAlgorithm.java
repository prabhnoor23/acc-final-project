package Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MergeSortReviewSortingAlgorithm {
	static WebCrawler webCrawler = new WebCrawler();

    public static String findItemWithMostReviews(String file) {
    	String filename="WebPages\\" + file + "_staples.txt";
		webCrawler.geWebsiteDetailsPage("https://www.staples.com/" + file + "/directory_" + file+ "?fids=Rating_3A_225_22&sby=1"); 

        List<Item> items = readItemsFromFile(filename);

        // Sort items based on the number of reviews using Merge Sort
        mergeSort(items, 0, items.size() - 1);

        // Return the item with the most reviews
        return items.get(0).getName();
    }

    private static void mergeSort(List<Item> items, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(items, left, mid);
            mergeSort(items, mid + 1, right);
            merge(items, left, mid, right);
        }
    }

    private static void merge(List<Item> items, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Item> leftItems = new ArrayList<>();
        List<Item> rightItems = new ArrayList<>();

        for (int i = 0; i < n1; i++) {
            leftItems.add(items.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightItems.add(items.get(mid + 1 + j));
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (leftItems.get(i).getReviews() >= rightItems.get(j).getReviews()) {
                items.set(k, leftItems.get(i));
                i++;
            } else {
                items.set(k, rightItems.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            items.set(k, leftItems.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            items.set(k, rightItems.get(j));
            j++;
            k++;
        }
    }

    private static List<Item> readItemsFromFile(String filename) {
        List<Item> items = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts.length>1) {
                	String name = parts[0].trim();
                    int price = 0;
                    int reviews = 0;
                    items.add(new Item(name, price, reviews));
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    private static class Item {
        private String name;
        private int price;
        private int reviews;

        public Item(String name, int price, int reviews) {
            this.name = name;
            this.price = price;
            this.reviews = reviews;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public int getReviews() {
            return reviews;
        }
    }

    public static void main(String[] args) {


    }
}

