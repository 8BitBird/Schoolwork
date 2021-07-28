package assign2;



import java.io.*;
import java.util.*;


/**

   @author metsis
   @author tesic
   @author wen
   @author webber

   @param args the command line arguments
*/
public class SentimentAnalysisApp {
 static int total_pos_reviews = 0;    //counter for actual total pos reviews
 static int total_neg_reviews = 0;    //counter for actual total neg reviews

 ////CREATE ARRAY FROM FOLDER, LIST OF FILENAMES
 public static List<String> createArray(String args)
 {
     String folderPath = args;
     File folder = new File(folderPath);

     // Get a list of all the files in the folder
     String[] files = folder.list();

     // Get the file separator character for this operating system
     //("/" for Linux and Mac, "\" for Windows).
     String fileSeparatorChar = System.getProperty("file.separator");

     List<String> allTxts = new ArrayList<>();
     for (String fileName : files)
     {
         if (fileName.endsWith(".txt"));
         {
           allTxts.add(folderPath + fileSeparatorChar + fileName);
         }
     }

     return allTxts;   //returns array of filenames in folder
 }

 ////PASS A TXT THROUGH, IT RETURNS ARRAY OF ONE FILE
 public static String FileToArray(String fileName) throws IOException
 {
     Scanner inFile = new Scanner(new FileReader(fileName));

     String text = "";
     while (inFile.hasNextLine())
     {
         text += inFile.nextLine();
     }

     // Remove the <br /> occurences in the text and
     //replace them with a space
     text = text.replaceAll("<br />"," ");

     //Remove punctuation marks and replace them with spaces.
     text = text.replaceAll("\\p{Punct}"," ");
     // Alternative way to remove punctuation marks
     //text = text.replaceAll("[!\"#$%&'()*+,./:;<=>?@\\[\\]^_`{|}~]"," ");

     // Convert everything to lowercase
     text = text.toLowerCase();

     // Split the text into tokens using white spaces
     //as the separator character.
     //String[] tokens = text.split("\\s+");

     //return tokens;      ///returns array of words in one file
     return text;
 }

   /////////////////////////MAIN////////////////////////////////////////////////
   public static void main(String [] args) throws IOException
   {
       ReviewHandler rh = new ReviewHandler();
       SentimentAnalysisApp review = new SentimentAnalysisApp();
       rh.loadSerialDB();
       int realClass = 0;
       rh.loadReviews(args[0], realClass);
       ///////////////
       System.out.println("This program analyzes movie reviews.\n");
       System.out.println("Press '0' to exit.\n");
       System.out.println("Press '1' to load a new movie collection.\n");
       System.out.println("Press '2' to delete a review from the database.\n");
       System.out.println("Press '3' to search the database.\n");
       System.out.println("Please enter your choice: ");
       Scanner user_input = new Scanner(System.in);
       int choice = Integer.parseInt(user_input.next());
       while (choice != 0)
       {
          if (choice == 1)
            {
               System.out.println("Please enter a folder path or a file path.\n");
               String contentToLoad = user_input.next();
               System.out.println("Please enter the real class for your file or folder. " +
               "(0 = Negative, 1 = Positive 2 = Unknown).\n");
               int actualClass = Integer.parseInt(user_input.next());
               rh.loadReviews(contentToLoad, actualClass);
               System.out.println("Loaded: \n\n");
               if (contentToLoad.endsWith(".txt"))
               {
                 System.out.println(String.format("%15s %15s %60s %15s %15s %15s", "Review ID", "|", "Review Text", "|", "Predicted Class", "|", "Real Class"));
                 rh.loadReviews(contentToLoad, actualClass);
               }
               else
                 {
                   System.out.println(String.format("%15s %15s %60s %15s %15s %15s", "Review ID", "|", "Review Text", "|", "Predicted Class", "|", "Real Class"));
                   List<String>fileList = review.createArray(contentToLoad); // /goodreviews/file1.txt, file2.txt
                   for (int i = 0; i < fileList.size(); i++)
                   {
                     rh.loadReviews(contentToLoad, actualClass);
                   }
                 }
                 System.out.println("Input your next action (0,1,2,3).\n");
                 choice = Integer.parseInt(user_input.next());
               }
               /*///Load new movie review collection (given a folder or a file path)

               The program should also ask the user to provide the real class of the review
               collection (if known). The user can choose from the options: 0. Negative, 1.
               Positive, and 2. Unknown.Upon loading each review, your program should assign a unique ID to each
               review, which should not conflict with existing ones, and it should also assign the
               value of the real class (as provided by the user).
               Then the program should automatically classify each review, using the external
               library “sentiment.jar�? and assign a value to the “predictedClass�? field of
               each review. The overall classification accuracy should also be reported, if the
               real class is known.
               Finally, the newly loaded reviews should be added to the permanent database. */


             //////////////////DONE////////////////////////////////////////////////////////
           else if (choice == 2)
               { /*Delete movie review from database (given its id).
                 When the user selects option “3�?, the results should be printed in a formatted manner.
                 The printed information should be a table with each row showing: review ID, first 50
                 characters of review text, predicted class, real class. */
                //display(Map data);
                 System.out.println("Input the ID of the review you would like to remove.");
                 int ID = Integer.parseInt(user_input.next());
                 MovieReview rev = searchById(ID);
                 if (rev == null)
                     System.out.println("Cannot delete nonexstent review.\n");
                 else
                 {
                   rh.display(rev);   //display review result
                   rh.deleteReview(ID);
                 }
                 System.out.println("Input your next action (0,1,2,3).\n");
                 choice = Integer.parseInt(user_input.next());
               }

             else if (choice == 3)
             {
               System.out.println("Input k if you would like to search by key or t to search by text.\n");
               /*/Search movie reviews in database by id or by matching a substring.
               When the user selects option “3�?, the results should be printed in a formatted manner.
               The printed information should be a table with each row showing: review ID, first 50
               characters of review text, predicted class, real class. */
               String typeSearch = user_input.next();
               typeSearch = typeSearch.toLowerCase();
               if (typeSearch == "k")
               {
                 System.out.println("\nInput the key: ");
                 String searchfor = user_input.next();
                 Set<Integer> values = rh.database.get(searchfor);
                 System.out.println("\nFound:\n ");
                 MovieReview foundRev = rh.searchById(values);
                 System.out.println(String.format("%15s %15s %60s %15s %15s %15s", "Review ID", "|", "Review Text", "|", "Predicted Class", "|", "Real Class"));
                 rh.display(foundRev);
               }
               else if (typeSearch == "t")
              {
                System.out.println("\nInput the text to search for: ");
                String value = user_input.next();
                value = value.toLowerCase();
                MovieReview foundText = searchBySubstring(value);
                if (foundText == null)
                    System.out.println("Review does not exist.\n");
                else
                  rh.display(foundText);   //display review result
              }
              else
                System.out.println("Input invalid.\n");
               System.out.println("Input your next action (0,1,2,3).\n");
               choice = Integer.parseInt(user_input.next());
             }
           else
           {
             System.out.println("Please input a valid choice.\n");
              choice = Integer.parseInt(user_input.next());
            }
            //close program
            rh.saveSerialDB();
            rh.close(rh.database);
       }
       System.out.println("Now exiting program.\n");

     }
}
