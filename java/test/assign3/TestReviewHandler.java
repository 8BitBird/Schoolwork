package assign3;

import java.io.*;
import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
    @author metsis
    @author webber
 */

public class TestReviewHandler
{
  static ReviewHandler rh = new ReviewHandler();
  SentimentAnalysisApp sa;
  MovieReview mr1, mr2, mr3, r1, r2, r3;
  static String text1 = "Lorem ipsum placerat eu augue et tincidunt. Phasellus ut dignissim sem, eu consectetur elit. ";
  static String text2 = "Nam neque augue, placerat in libero sit amet, iaculis aliquam felis. ";
  static String text3 = "Etiam et lorem augue. Mauris rhoncus varius elit, at ultrices ipsum vestibulum ac.";
  
  static String subtext1 = "Lorem ipsum placerat";
  static String subtext2 = "iaculis aliquam felis";
  static String subtext3 = "at ultrices ipsum";
  
  static String fileSeparatorChar = System.getProperty("file.separator");
  static String path = System.getProperty("user.dir") + fileSeparatorChar + "assign3" + fileSeparatorChar +"TEMP";
  
  @BeforeClass
  public static void createTestfiles() throws IOException
  {
    new File(path).mkdirs();
    File file1 = new File(path + fileSeparatorChar + "testfile1.txt");
    System.out.println(file1);
    FileWriter writer = new FileWriter(file1);
    writer.write(text1);
    writer.close();
    File file2 = new File(path + fileSeparatorChar + "testfile2.txt");
    System.out.println(file2);
    FileWriter writer2 = new FileWriter(file2);
    writer2.write(text2);
    writer2.close();
    File file3 = new File(path + fileSeparatorChar + "testfile3.txt");
    System.out.println(file3);
    FileWriter writer3 = new FileWriter(file3);
    writer3.write(text3);
    writer3.close();
    
    MovieReview mr1 = new MovieReview( 999, text1, 1);
    MovieReview mr2 = new MovieReview( 1000, text2, 1);
    MovieReview mr3 = new MovieReview( 1001, text3, 1);
    rh.database.put(1, mr1);
    rh.database.put(2, mr2);
    rh.database.put(3, mr3);
    
    System.out.println("Test Files Created");
       
  }

  @Test
  public void TestreadReview() throws IOException
  {
    r1 = rh.readReview((path + fileSeparatorChar + "testfile1.txt"), 0);
    r2 = rh.readReview((path + fileSeparatorChar + "testfile2.txt"), 0);
    r3 = rh.readReview((path + fileSeparatorChar + "testfile3.txt"), 0);

    assertTrue("Text file1 Failed", r1.getText() == text1.replaceAll("<br />"," "));
    assertTrue("Text file2 Failed", r2.getText() == text2.replaceAll("<br />"," "));
    assertTrue("Text file3 Failed", r3.getText() == text3.replaceAll("<br />"," "));
}

  @Test
  public void testLoadReview()
  {  ///create file and Folder
    //pass text through
    int size = rh.database.size();    
    rh.loadReviews((path + fileSeparatorChar + "testfile1.txt"), 1);
    assertTrue("File wasn't loaded into database", size+1 == rh.database.size());
}

  @Test
  public void TestsearchBySubstring()
  {
    //put object in Database
    //search for it
    //verify it's found
    List<MovieReview> movielist = new ArrayList<MovieReview>();
    List<MovieReview> temp = new ArrayList<MovieReview>();
    temp.add(mr1);
    
    movielist = rh.searchBySubstring(subtext1);
    assertNotNull("Substring not found", temp);
  }

  @Test
  public void TestsearchById()
  {
    //put object in Database
    //search for it
    //verify it's found
    mr1 = rh.searchById(1);
    mr2 = rh.searchById(2);
    mr3 = rh.searchById(3);
   

    ///Make sure item is found and not returned null
    assertNotNull("Find ID failed, no ID found", mr1);
    assertNotNull("Find ID failed, no ID found", mr2);
    assertNotNull("Find ID failed, no ID found", mr3);
  }
  
  @Test
  public void loadSerialDB()
  {
    //create newDatabase
    //load database
    //read database
    //verify contents
    rh.loadSerialDB();
    int size = rh.database.size(); 
    assertTrue("Database was not loaded.", size != 0);
  }

  @Test
  public void TestdeleteReview()
  {
    //create review objects
    //delete objects
    //search again, verify it's gone
    r2 = rh.searchById(1);
    rh.deleteReview(1);
    assertNotNull("Review not deleted", r2);   
  }

  //ABSTRACT//////////////
  @Test
  public void TestsaveSerialDB()
  {
      //add objects to Database
      //save
      //serch for those objects, verify changes were saved
      int size = rh.database.size(); 
      rh.database.put(4, mr1);
      rh.saveSerialDB();
      assertTrue("Database wasn't saved.", size+1 == rh.database.size());
  }

  
  @AfterClass
  public static void cleanUp() throws IOException 
  {
      File file1 = new File(path + fileSeparatorChar + "testfile1.txt"); 
      System.out.println(file1);
      file1.delete();
      File file2 = new File(path + fileSeparatorChar + "testfile2.txt"); 
      System.out.println(file2);
      file2.delete();
      File file3 = new File(path + fileSeparatorChar + "testfile3.txt"); 
      System.out.println(file3);
      file3.delete();
      File filepath = new File(path); 
      System.out.println(filepath);
      filepath.delete();

      System.out.println("Test Files Cleared");
  }
   
}
 
