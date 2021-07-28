package assign1;

import java.io.FileReader;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

/**
 * Class that supports Movie Review by word counting Dictionary of positive
 * words and negative words are kept in this class
 *
 * @author metsis
 * @author tesic
 * @author wen
 * @author webber
 */
public class MovieReviewCount {

    /**
     * Constructor from ArrayList of positive and negative words, if any
     *
     * @param fileName path to file containing words
     */
    MovieReviewCount(String pathToPosWords, String pathToNegWords) throws IOException {
        this.readInWords(pathToPosWords, posHashSet);
        this.readInWords(pathToNegWords, negHashSet);
    }

    /**
     * Read positive/negative words in to HashSet
     *
     * @param dictionary word dictionary
     * @throws IOException
     */
    private void readInWords(String fileName, HashSet<String> dictionary) throws IOException {
        Scanner inFile = new Scanner(new FileReader(fileName));
        String text = "";
        while (inFile.hasNextLine()) {
            text = inFile.nextLine();
            if (!text.isEmpty() && !text.startsWith(";") && !text.equals("")) {
                dictionary.add(text);
            }
        }
        System.out.println("Hash set is of size " + dictionary.size());
        inFile.close();
        return;
    }

    /**
     * Reads in all files in the collection and returns the number of positive
     * reviews number of negative reviews is size of collection minus the number
     * of positive reviews
     *
     * @param folderPath path to review folder containing words
     * @param gt what is the ground truth for the collection, 1 for positive 0
     * for negative
     * @return number of positively classificed movie reviews in a collection
     * @throws IOException
     */
    public int classifyCollection(ArrayList<String> fileList, int gt) throws IOException {

        String label = (gt > 0) ? "positive" : "negative";
        int counter = 0;
        int currSent = 0;
        String currStr;
        for (String filePath : fileList) {
            //read the file in the String
            Scanner inFile = new Scanner(new FileReader(filePath));
            currStr = "";
            while (inFile.hasNextLine()) {
                currStr += inFile.nextLine();
            }
            currSent = this.classifyReview(currStr);
            String res = "negative";
            if (currSent > 0) {
                res = "positive";
                counter++;
            }

            System.out.println("File name: " + filePath);
            System.out.println("Ground truth is " + label + ", predicted sentiment is " + res + "\n");
            // Close the file
            inFile.close();
        }
        return counter;
    }//end classifyCollection

    /**
     * Input is raw sting movie rewiew: the method processed the file and
     * computes the sentiment Sentiment is computed by counting positive and
     * negative words
     *
     * @param fileName path to review file
     * @return integer number of positive words - negative words
     * @throws IOException
     *
     */
    public int classifyReview(String currStr) {

        int positive = 0;
        int negative = 0;
        // Remove the <br /> occurences in the text and replace them with a space
        String text = currStr.replaceAll("<br />", " ");
        //Remove punctuation marks and replace them with spaces.
        text = text.replaceAll("\\p{Punct}", " ");
        //call function to format text and classify Review
        text = text.toLowerCase();

        // Split the text into tokens using white spaces as the separator character.
        String[] tokens = text.split("\\s+");
        for (String token : tokens) {
            if (posHashSet.contains(token)) {
                positive++;
            }
            if (negHashSet.contains(token)) {
                negative++;
            }
        }
        return (positive - negative);
    }

    private HashSet<String> posHashSet = new HashSet<String>();
    private HashSet<String> negHashSet = new HashSet<String>();

}
