 package assign5;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;


/**
 * This class creates a table of MovieReview objects from a list in a JFrame.
 *
 * @author webber
 * @author metsis
 * @version 4/19/19
 * @params (MovieReview object List)
 */

public class CreateTable extends JFrame
{
    public static void MakeTable(List<MovieReview> mrl) 
    {
        JTable table;
        JFrame frame; 
        frame = new JFrame(); 
        frame.setTitle("Movie Reviews"); 
        String[] titles = {"ID", "Text", "Predicted", "Real"};
        int row = 0;
        String[][] data = new String[mrl.size()][4];
        for (MovieReview mr : mrl) {
            String predic = (mr.getPredictedPolarity()==0) ? "Negative" : "Positive";
            String real = (mr.getPredictedPolarity()==0) ? "Negative" : (mr.getPredictedPolarity()==1) ? "Positive" : "Unknown";
            String strID = Integer.toString(mr.getId());
            String subst = String.format("%53s", mr.getText().substring(0, 50)+"..." );
            data[row][0] = strID;
            data[row][1] = subst;
            data[row][2] = predic;
            data[row][3] = real;
            row++;
         }
    table = new JTable(data, titles);
    table.getColumnModel().getColumn(0).setPreferredWidth(5);
    JScrollPane scroll = new JScrollPane(table); 
    frame.add(scroll); 
    frame.setSize(800, 200);
    frame.setVisible(true); 
    table.setAutoCreateRowSorter(true);
    table.setShowGrid(true);
    table.setEnabled(false);
     }  
     
    public static void main(String[] args) 
    { 
        new CreateTable(); 
    } 
     
}