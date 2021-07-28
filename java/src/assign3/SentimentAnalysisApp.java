package assign3;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 *
    @author metsis
    @author tesic
    @author wen
    @author webber
 */
public class SentimentAnalysisApp {

    public static void main(String [] args) {
        // Create object
        ReviewHandler rh = new ReviewHandler();
        // Load database if it exists.
        File databaseFile = new File(ReviewHandler.DATA_FILE_NAME);
        if (databaseFile.exists()) {
            rh.loadSerialDB();
        }

        String welcomeMessage = "\nDatabase size: " + rh.database.size() +
                "\nChoose one of the following functions:\n\n"
                + "\t 0. Exit program.\n"
                + "\t 1. Load new movie review collection (given a folder or a file path).\n"
                + "\t 2. Delete movie review from database (given its id).\n"
                + "\t 3. Search movie reviews in database by id or by matching a substring.\n";

        //Output welcome message
        System.out.println(welcomeMessage);
        String selection = CONSOLE_INPUT.nextLine();

        while (!selection.equals("0")) {
            switch (selection) {
                case "1":
                    // 1. Load new movie review collection (given a folder or a file path).
                    System.out.println("Please input the path of file or folder.");
                    // ./Data/Movie-reviews/neg
                    String path = CONSOLE_INPUT.nextLine();
                    System.out.println("Please input real class (0, 1, 2).");
                    System.out.println("0 = negative, 1 = positive, 2 = unknown.");
                    String realClass = CONSOLE_INPUT.nextLine();
                    
                    if (realClass.equals("0")) {
                        rh.loadReviews(path, 0);
                    } else if (realClass.equals("1")) {
                        rh.loadReviews(path, 1);
                    } else if (realClass.equals("2")) {
                        rh.loadReviews(path, 2);
                    } else {
                        System.out.println("Illegal input.");
                    }

                    System.out.println("Database size: " + rh.database.size());
                    break;
                case "2":
                    // 2. Delete movie review from database (given its id).
                    System.out.println("Please input review ID.");
                    String idStr = CONSOLE_INPUT.nextLine();

                    if (!idStr.matches("-?(0|[1-9]\\d*)")) {
                        // Input is not an integer
                        System.out.println("Illegal input.");
                    } else {
                        int id = Integer.parseInt(idStr);
                        rh.deleteReview(id);
                    }

                    break;

                case "3":
                    // 3. Search movie reviews in database by id or by matching a substring.
                    System.out.println("Please input your command (1, 2).");
                    System.out.println("1 = search by ID.");
                    System.out.println("2 = search by substring");
                    String command = CONSOLE_INPUT.nextLine();
                    if (command.equals("1")) {
                        System.out.println("Please input review ID.");
                        idStr = CONSOLE_INPUT.nextLine();
                        if (!idStr.matches("-?(0|[1-9]\\d*)")) {
                            // Input is not an integer
                            System.out.println("Illegal input.");
                        } else {
                            int id = Integer.parseInt(idStr);
                            MovieReview mr = rh.searchById(id);
                            if (mr != null) {
                                printTableHead();
                                printTableContent(mr);
                            } else {
                                System.out.println("Review not found.");
                            }
                        }

                    } else if (command.equals("2")) {
                        System.out.println("Please input substring.");
                        String substring = CONSOLE_INPUT.nextLine();
                        List<MovieReview> reviewList = rh.searchBySubstring(substring);
                        if (reviewList != null) {
                            printTableHead();
                            for (MovieReview mr : reviewList) {
                                printTableContent(mr);
                            }
                            System.out.println(reviewList.size() + " reviews found.");

                        } else {
                            System.out.println("Review not found.");
                        }
                    } else {
                        System.out.println("Illegal input.");
                    }
                    break;
                case "h":
                    System.out.println(welcomeMessage);
                    break;
                default:
                    System.out.println("That is not a recognized command. Please enter another command or 'h' to list the commands.");
                    break;
            }

            System.out.println("Please enter another command or 'h' to list the commands.\n");
            selection = CONSOLE_INPUT.nextLine();
        }

        // Save the database when exit.
        rh.saveSerialDB();
        System.out.println("See you!");
    }

    // Used to read from System's standard input
    static final Scanner CONSOLE_INPUT = new Scanner(System.in);

    public static void printTableHead() {
        String line = "------------------------------------------------------------------------------------------";
        String information = "| ";
        information = information + String.format("%4s", "ID") + " | ";
        information = information + String.format("%53s", "Text") + " | ";
        information = information + String.format("%10s", "Predicted") + " | ";
        information = information + String.format("%10s", "Real") + " |";

        System.out.println(line);
        System.out.println(information);
        System.out.println(line);
    }

    public static void printTableContent(MovieReview mr) {
        String line = "------------------------------------------------------------------------------------------";
        String information = "| ";
        information = information + String.format("%4s", mr.getId()) + " | ";
        information = information + String.format("%53s", mr.getText().substring(0, 50)+"..." ) + " | ";
        information = information + String.format("%10s", (mr.getPredictedPolarity()==0) ? "Negative" : "Positive") + " | ";
        information = information + String.format("%10s", (mr.getPredictedPolarity()==0) ? "Negative" : (mr.getPredictedPolarity()==1) ? "Positive" : "Unknown") + " |";

        System.out.println(information);
        System.out.println(line);
    }

}
