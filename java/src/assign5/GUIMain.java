package assign5;
                
import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;   // event classes
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Stack;
import java.io.IOException;
import java.util.logging.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
            
            
/**
 * GUI Interface 
*
* @author Laura webber
* @version 4/19/19
*/
public class GUIMain {
    public static void main(String[] args) {

/**
* Create logger
*
*/
     MyGUILogger logger = new MyGUILogger();
     try {
         logger.setup();
     } catch(Exception e){
       e.printStackTrace();
       }
      logger.getLogger().info("Software opened.\n");
/**
* Create Frame, create text area, send welcome to text area
*
*/
       JFrame frame = new JFrame("Review Manager");
       frame.setSize(600, 700);
       JTextArea textArea = new JTextArea(10, 25);
       JScrollPane scrollPane = new JScrollPane(textArea); 
       textArea.append("Welcome to the Review Manager!\n");
                   
/**
* Instantiate objects, open working database
*
*/
        ReviewHandler rh = new ReviewHandler();
        File databaseFile = new File(ReviewHandler.DATA_FILE_NAME);
        if (databaseFile.exists()) {
           rh.loadSerialDB();
           textArea.append("Reading database..." + rh.database.size() + " entry(s) loaded. \nDone.");
         }
                   
/**
* Make button to show all reviews
*
*/
         JLabel label1 = new JLabel("Show current database");
         JButton showButton = new JButton("Display database");
         showButton.setActionCommand("Display");
         showButton.addActionListener( new ActionListener()
         {
           @Override
           public void actionPerformed(ActionEvent e)
           {
             List<MovieReview> all = new ArrayList<MovieReview>(rh.database.values());
             CreateTable.MakeTable(all);
             logger.getLogger().info("All reviews displayed.\n");
                         
           }
         });
           
/**
* Create text area for output messages
*
*/
            textArea.setEditable(false);     
            scrollPane.setVerticalScrollBarPolicy(scrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setPreferredSize(new Dimension(250, 250));
                 
/**
* Create dropdown menu for actions
*
*/
             final String choices[] = { "Choose..", "Load Reviews", "Delete Review", "Search by ID", "Search by Substring"};
             final JComboBox comboChoices = new JComboBox(choices);
             ActionListener cbActionListener = new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                     JComboBox comboChoices =(JComboBox)e.getSource();
                     String actn = (String)comboChoices.getSelectedItem();
                     switch (actn){    
                         case "Load Reviews":
                            logger.getLogger().info("Load review chosen.\n");
                            String path= JOptionPane.showInputDialog("Please enter the file or folder path to be loaded: ");
                            String realClass= JOptionPane.showInputDialog("Please enter rating: 0 = negative, 1 = positive, 2 = unknown ");
                            Path givenpath = Paths.get(path);
                         if (realClass.equals("0") && Files.exists(givenpath)) {
                             rh.loadReviews(path, 0);
                             logger.getLogger().info("Review(s) loaded entered with negative rating.\n");
                         }else if (realClass.equals("1") && Files.exists(givenpath)) {
                             rh.loadReviews(path, 1);
                             logger.getLogger().info("Review(s) loaded entered with positive rating.\n");
                         } else if (realClass.equals("2") && Files.exists(givenpath)) {
                             rh.loadReviews(path, 2);
                             logger.getLogger().info("Review(s) loaded entered with unknown rating.\n");
                         } else {
                              textArea.append("\nIllegal input.");
                              logger.getLogger().warning("Illegal input entered attempting to load.\n");
                         }

                            textArea.append("\nDatabase size: " + rh.database.size());
                            break;
                       case "Search by ID":
                        String idStr= JOptionPane.showInputDialog("Please enter the ID of review to be searched for:");
                            if (!idStr.matches("-?(0|[1-9]\\d*)")) {
                                textArea.append("\nIllegal input.\n");
                                logger.getLogger().warning("Illegal input entered attempting to search by ID.\n");
                            } else {
                                int id = Integer.parseInt(idStr);
                                MovieReview mr = rh.searchById(id);
                                if (mr != null) {
                                    List<MovieReview> searchList = new ArrayList<MovieReview>();
                                    searchList.add(mr);
                                    CreateTable.MakeTable(searchList);
                                    logger.getLogger().info("ID entered for searching.\n");
                                     textArea.append("\nReview with ID: " + idStr+ " found.");
                                 } else {
                                     textArea.append("\nReview with ID: " + idStr+ " not found.");
                                    }
                                }
                                break;
                       case "Delete Review":
                        String delID= JOptionPane.showInputDialog("Please enter the ID of review to be deleted: ");
                        if (!delID.matches("-?(0|[1-9]\\d*)")) {
                            logger.getLogger().warning("Illegal input entered attempting to delete review.\n");
                            textArea.append("\nIllegal input.");
                        } else {
                            int id = Integer.parseInt(delID);
                            MovieReview find_del = rh.searchById(id);
                            rh.deleteReview(id);
                            if (find_del != null){
                                logger.getLogger().info("User entered ID of review to delete.\n");
                                textArea.append("\nReview with ID " + delID + " deleted.");
                                textArea.append("\nDatabase size: " + rh.database.size());
                            } else
                                 textArea.append("\nReview with ID " + delID + " not found.");
                        }
                          break; 
                       case "Search by Substring":
                        String substring= JOptionPane.showInputDialog("Please enter the substring to search for: ");
                            List<MovieReview> reviewList = rh.searchBySubstring(substring);
                            logger.getLogger().info("Substring entered for searching.\n");
                             String[] titles = {"ID", "Text", "Predicted", "Real"};
                             if (reviewList != null) 
                             {
                                 String[][] data = new String[reviewList.size()][4];
                                 int row = 0;
                                 CreateTable.MakeTable(reviewList);
                                 textArea.append("\n" + reviewList.size() + " review(s) found.");
                              }  
                              else
                              { 
                                textArea.append("\nReview containing " + substring + " not found.");
                                logger.getLogger().warning("Illegal input entered attempting to load.\n");
                              }
                          break; 
                   }
                 
                }
            };
                
      comboChoices.addActionListener(cbActionListener);    
     
      
/**
 * Create left panel with display button
 *
 */
      JPanel p1 = new JPanel(); 
       p1.setBorder(BorderFactory.createLineBorder(Color.black));
       p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
       p1.add(Box.createRigidArea(new Dimension(0,10)));
       p1.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
       p1.add(label1);
       p1.add(showButton);
      
/**
 * Create right panel with dropdown menu
 *
 */
       JPanel p = new JPanel(); 
       p.setBorder(BorderFactory.createLineBorder(Color.black));
       p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
       p.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
       p.setPreferredSize(new Dimension(250, 50));
       JLabel comboLabel = new JLabel("Choose action to take:");
       p.add(Box.createRigidArea(new Dimension(0,10)));
       p.add(comboLabel);
       p.add(comboChoices);
       p.add(Box.createRigidArea(new Dimension(0,10)));

/**
 * Build main frame
 *
 */      
      frame.setLayout(new BorderLayout());
      frame.add(p, BorderLayout.EAST);
      frame.add(p1, BorderLayout.WEST);
      frame.add(scrollPane, BorderLayout.SOUTH);

/**
 * Create closing actions, save work
 *
 */  
      frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      frame.addWindowListener(new WindowAdapter() 
      {     @Override
            public void windowClosing(WindowEvent e) 
            {
                if(JOptionPane.showConfirmDialog(frame, "Save database and close?") == JOptionPane.OK_OPTION)
                {
                    frame.setVisible(false);
                     rh.saveSerialDB();
                     logger.getLogger().info("User chose to close application.\n");
                     textArea.append("See you!");
                     frame.dispose();
                }
            }
        });

      frame.pack();
      frame.setVisible(true);
   }
   
   
}