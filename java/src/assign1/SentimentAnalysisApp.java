package assign1;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

/**
 * Sentiment Analysis App for Assignment 1
 *
 * @author metsis
 * @author tesic
 * @author wen
 * @author webber
 */
public class SentimentAnalysisApp {

    /**
     * Main method acquires file and folder inputs for sentiment analysis via
     * counting
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            System.out.println("Wrong number of args");
            System.out.println("Example: java SentimentAnalysisApp <path_to_pos_words> <path_to_neg_words> <path_to pos_reviews_folder> <path_to_neg_reviews_folder>");
            return;
        }

        String pathToPosWords = args[0];
        String pathToNegWords = args[1];
        String pathToPosReviewsFolder = args[2];
        String pathToNegReviewsFolder = args[3];

        //check if positive file exists
        File fp = new File(pathToPosWords);
        if (fp.exists() && !fp.isDirectory()) {
            System.out.println("Path to positive words: " + pathToPosWords);
        } else {
            System.out.println("File " + pathToPosWords + " does not exist");
            return;
        }

        //check if negative file exists
        File fn = new File(pathToNegWords);
        if (fn.exists() && !fn.isDirectory()) {
            System.out.println("Path to negative words: " + pathToNegWords);
        } else {
            System.out.println("File " + pathToNegWords + " does not exist");
            return;
        }

        //instantiate new MovieReviewCount object with positive and negative words
        MovieReviewCount movieRev = new MovieReviewCount(pathToPosWords, pathToNegWords);

        //check if positive dir exists, if it does return the list of file names
        String eXt = ".txt";
        ArrayList<String> posFiles = populateFileNames(pathToPosReviewsFolder, eXt);
        ArrayList<String> negFiles = populateFileNames(pathToNegReviewsFolder, eXt);

        //classify file, arraylist of files
        //1 is ground truth for positive
        //0 is ground truth for negative
        //extensible to multiple classification types throug enum
        //NOTE: the code assumes that posCount+negCount = file.size()
        int posCount1 = movieRev.classifyCollection(posFiles, 1);
        int posCount2 = movieRev.classifyCollection(negFiles, 0);

        //NOTE: display results always returns the number of positive classifications
        displayResults(posCount1, posFiles.size(), 1);
        displayResults(posCount2, negFiles.size(), 0);
        return;
    }

    /**
     * Method to get a list of filenames that match the pattern from the folder
     *
     * @param ffolder input folder
     * @param fList ArrayList of strings to populate
     * @return ArrayList<String>
     */
    public static ArrayList<String> populateFileNames(String fFolder, String fExt) {

        ArrayList<String> fList = new ArrayList<String>();
        File fileDir = new File(fFolder);
        if (!fileDir.exists() || !fileDir.isDirectory()) {
            return null;
        }
        String[] files = fileDir.list();
        if (files.length == 0) {
            return null;
        }

         // Get the file separator character for this operating system
        //("/" for Linux and Mac, "\" for Windows).
        String fileSeparatorChar = System.getProperty("file.separator");

        for (int i = 0; i < files.length; i++) {
            if (fExt == null || files[i].endsWith(fExt)) {
                fList.add(fFolder + fileSeparatorChar + files[i]);
            }
        }

        return fList;
    }

    /**
     * Method to display classification results
     *
     * @param posClassify file extension to match
     * @param size file extension to match
     * @param gt what is dataset ground truth, 1 is positive, 0 is negative
     */
    public static void displayResults(int posClassify, int size, int gt) {

        String label = (gt > 0) ? "positive" : "negative";
        System.out.println("Out of " + size + " " + label + " items, counting method classified:");
        System.out.println("positive: " + posClassify + ", negative: " + (size - posClassify));
        double acc = (double) posClassify / size;
        if (gt <= 0) {
            acc = (double) 1.0 - acc;
        }
        System.out.println("Correct classification accuracy is: " + acc);
    }
}
