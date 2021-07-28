package assign5;

import java.io.*;
import java.util.*;

public class ReviewHandler extends AbstractReviewHandler {

    private static int ID = 0;

    /**
     * Loads reviews from a given path. If the given path is a .txt file, then
     * a single review is loaded. Otherwise, if the path is a folder, all reviews
     * in it are loaded.
     * @param filePath The path to the file (or folder) containing the review(sentimentModel).
     * @param realClass The real class of the review (0 = Negative, 1 = Positive
     * 2 = Unknown).
     */
    @Override
    public void loadReviews(String filePath, int realClass) {
        File fileOrFolder = new File(filePath);
        try {
            if (fileOrFolder.isFile()) {
                // File
                if (filePath.endsWith(".txt")) {
                    // Import review
                    MovieReview review = readReview(filePath, realClass);
                    // Add to database
                    database.put(review.getId(), review);
                    //Output result: single file
                    System.out.println("Review imported.");
                    System.out.println("ID: " + review.getId());
                    System.out.println("Text: " + review.getText());
                    System.out.println("Real Class: " + review.getRealPolarity());
                    System.out.println("Classification result: " + review.getPredictedPolarity());
                    if (realClass == 2) {
                        System.out.println("Real class unknown.");
                    } else if (realClass == review.getPredictedPolarity()) {
                        System.out.println("Correctly classified.");
                    } else {
                        System.out.println("Misclassified.");
                    }
                    System.out.println();

                } else {
                    // Cannot import non-txt files
                    System.out.println("Input file path is neither a txt file nor folder.");
                }
            } else {
                // Folder
                String[] files = fileOrFolder.list();
                String fileSeparatorChar = System.getProperty("file.separator");
                int counter = 0;
                for (String fileName : files) {
                    if (fileName.endsWith(".txt")) {
                        // Only import txt files
                        // Import review
                        MovieReview review = readReview(filePath + fileSeparatorChar + fileName, realClass);
                        // Add to database
                        database.put(review.getId(), review);
                        // Count correct classified reviews, only real class is known
                        if (realClass != 2 && review.getRealPolarity() == review.getPredictedPolarity()) {
                            counter++;
                        }
                    } else {
                        //Do nothing
                    }
                }
                // Output result: folder
                System.out.println("Folder imported.");
                System.out.println("Number of entries: " + files.length);

                // Only output accuracy if real class is known
                if (realClass != 2) {
                    System.out.println("Correctly classified: " + counter);
                    System.out.println("Misclassified: " + (files.length - counter));
                    System.out.println("Accuracy: " + ((double)counter / (double)files.length * 100) + "%");
                }
            }
        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }

    }

    /**
     * Reads a single review file and returns it as a MovieReview object.
     * This method also calls the method classifyReview to predict the polarity
     * of the review.
     * @param reviewFilePath A path to a .txt file containing a review.
     * @param realClass The real class entered by the user.
     * @return a MovieReview object.
     * @throws IOException if specified file cannot be opened.
     */
    @Override
    public MovieReview readReview(String reviewFilePath, int realClass) throws IOException {
        // Read file for text
        Scanner inFile = new Scanner(new FileReader(reviewFilePath));
        String text = "";
        while (inFile.hasNextLine()) {
            text += inFile.nextLine();
        }
        // Remove the <br /> occurences in the text and replace them with a space
        text = text.replaceAll("<br />"," ");

        // Create review object, assigning ID and real class
        MovieReview review = new MovieReview(ID, text, realClass);
        // Update ID
        ID++;
        // Classify review
        classifyReview(review);

        return review;
    }

    /**
     * Deletes a review from the database, given its id.
     * @param id The id value of the review.
     */
    @Override
    public void deleteReview(int id) {

        if (!database.containsKey(id)) {
            // Review with given ID does not exist
            System.out.println("ID " + id + " does not exist.");
        } else {
            database.remove(id);
            System.out.println("Review with ID " + id + " deleted.");
        }
    }

    /**
     * Loads review database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void loadSerialDB() {
        System.out.print("Reading database...");
        // serialize the database
        InputStream file = null;
        InputStream buffer = null;
        ObjectInput input = null;
        try {
            file = new FileInputStream(DATA_FILE_NAME);
            buffer = new BufferedInputStream(file);
            input = new ObjectInputStream(buffer);

            database = (Map<Integer, MovieReview>)input.readObject();
            System.out.println(database.size() + " entry(s) loaded.");

            // Find the current maximum ID
            int currMaxId = Collections.max(database.keySet());
            ID = currMaxId + 1;

            input.close();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println(e.toString());
            e.printStackTrace();
        } finally {
            close(file);
        }
        System.out.println("Done.");
        /////////ADD BUTTON w OK
    }

    /**
     * Searches the review database by id.
     * @param id The id to search for.
     * @return The review that matches the given id or null if the id does not
     * exist in the database.
     */
    @Override
    public MovieReview searchById(int id) {
        if (database.containsKey(id)) {
            return database.get(id);
        }
        return null;
    }

    /**
     * Searches the review database for reviews matching a given substring.
     * @param substring The substring to search for.
     * @return A list of review objects matching the search criterion.
     */
    @Override
    public List<MovieReview> searchBySubstring(String substring) {
        List<MovieReview> tempList = new ArrayList<MovieReview>();

        for (Map.Entry<Integer, MovieReview> entry : database.entrySet()){
            if (entry.getValue().getText().contains(substring)) {
                tempList.add(entry.getValue());
            }
        }

        if (!tempList.isEmpty()) {
            return tempList;
        } else {
            // No review has given substring
            return null;
        }

    }
}
