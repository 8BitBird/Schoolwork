package assign2;

 

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sentiment.Sentiment;
import java.util.Random;


/**
 * CS3354 Spring 2019 Review Handler Abstract Class specification
    @author metsis
    @author tesic
    @author wen
    @author webber
 */

 //////////////////////////////////////////////////////////////////////////
 public class ReviewHandler extends AbstractReviewHandler
 {
   /**
    * Loads reviews from a given path. If the given path is a .txt file, then
    * a single review is loaded. Otherwise, if the path is a folder, all reviews
    * in it are loaded.
    * @param filePath The path to the file (or folder) containing the review(sentimentModel).
    * @param realClass The real class of the review (0 = Negative, 1 = Positive
    * 2 = Unknown).
    * @return A list of reviews as objects.
    */
   @Override
   public void loadReviews(String filePath, int realClass) throws IOException
   {
     MovieReview tempReview = readReview(filePath,realClass);
     int thisID = tempReview.getID;
     database.put(thisID, tempReview);
     this.display(tempReview);
   }

   /**
    * Reads a single review file and returns it as a MovieReview object.
    * This method also calls the method classifyReview to predict the polarity
    * of the review.
    * @param reviewFilePath A path to a .txt file containing a review.
    * @param realClass The real class entered by the user.
    * @return a MovieReview object.
    * @throws IOException if specified file cannot be openned.
    */
   public MovieReview readReview(String reviewFilePath, int realClass) throws IOException
   {
     int newID = genID();
     File path = new File(reviewFilePath);
     String text = readfile(path);
     MovieReview newReview = new MovieReview(newID, text, realClass);
     newReview.setPredictedPolarity(classifyReview(newReview));
     return newReview;
   }
 ////////SEARCH BY ID////////////////////
   public MovieReview searchById(int id)
   {
     if (database.containsKey(id))
       return database.get(id);
     else
     {
       return null;
     }
   }
   ///////SEARCH BY VALUE/////////////////
 public MovieReview searchBySubstring(String value)
 {
   if (database.containsValue(value))
     return database.getValue(value);
   else
   {
     return null;
   }
 }
 /////////////DISPLAY MOV REV OBJECT/////////////////////////////////
   public void display(MovieReview m)
   {
     String textSubst = m.getText().substring(0, 49);
     //System.out.println(String.format("%15s %15s %60s %15s %15s %15s", "Review ID", "|", "Review Text", "|", "Predicted Class", "|", "Real Class"));
     System.out.println(String.format("%s", "----------------------------------------------------------------------------------------------------------------"));
     System.out.println(String.format("%15s %15s %60s %15s %15s %15s", m.getId(), "|", textSubst, "|", m.getPredictedPolarity(), "|", m.getRealPolarity()));
   }

 ///////GENERATE ID////////////////
 public int genID()
 {
   int random = (int)(Math.random() * 5000 + 1);
   if (database.containsKey(random))
       genID();
   else
     return random;
 }
 ////////DELETE REVIEW///////////////////////
   public void deleteReview(int id);
   {
     if (database.containsKey(id))
     {
       database.remove(id);
       System.out.println("Review has been removed.\n");
     }
     else
       System.out.println("ID not found in database.\n");
   }
 }
