package receptionist;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SCSM11
 */
public class LoadFile {
    // general load data to table function
    public static void loadFile(JTable table,JFrame parentFrame,String File){
        try{
            FileReader fr = new FileReader(File);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine(); // skip the header
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);   //clear the all row in the table, avoid repeating data when clicking load (>1) 
            
            int expectedColumns = model.getColumnCount();
            
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",",-1); //unlimited number of split, if 1, split 1 time(a[0]); if 2, split 2 time (a[0],a[1])
                
                if (rowData.length == expectedColumns) {
                    Vector<Object> row = new Vector<>();
                    for (int k = 0; k < rowData.length;k++){
                        row.add(rowData[k]);
                    }
                    model.addRow(row);
                } else {
                    JOptionPane.showMessageDialog(parentFrame,"Number of columns does not match");
                    return;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentFrame, "Failed to load data:\n" + e.getMessage());
        }
    }
    
    // combine subject & tutor in the table
    public static void stu_enrolFile(JTable table,JFrame parentFrame,String File){
        try{
            FileReader fr = new FileReader(File);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine(); // skip the header
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);   //clear the all row in the table, avoid repeating data when clicking load (>1) 
            
            int expectedColumns = model.getColumnCount(); //13
            
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",",-1); //unlimited number of split, if 1, split 1 time(a[0]); if 2, split 2 time (a[0],a[1])
                if ((rowData.length -3) == expectedColumns) {   //3 columns tutor are not included
                    Vector<Object> row = new Vector<>();
                    
                    
                    for (int k = 0; k < rowData.length;k++){
                        
                        //9,11,13 are subject, combine subject and tutor in format "subject (tutor)"
                        if (k == 9 || k == 11 || k == 13){
                            row.add(rowData[k] +" (" + rowData[k+1] + ")");
                        } 
                        
                        // skip the tutor
                        else if (k == 10 || k == 12 || k == 14){
                            continue;
                        }else {
                        row.add(rowData[k]);
                        }
                        
                    }
                    
                    //add to table
                    model.addRow(row);
                } else {
                    JOptionPane.showMessageDialog(parentFrame,"Number of columns does not match");
                    return;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentFrame, "Failed to load data:\n" + e.getMessage());
        }
    }
    
    //load file to JTextField
    public static void RepFile(
        String parameter_RecepId,
        JTextField in_userId,
        JTextField in_name,
        JTextField in_username,
        JTextField in_password,
        JTextField in_email,
        JTextField in_CN,
        JFrame parentFrame){
        
        try(BufferedReader br = new BufferedReader (new FileReader("Receptionist.txt"))){
            String line = br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                
                if (rowData[0].equals(parameter_RecepId)){
                    in_userId.setText(rowData[0]);
                    in_name.setText(rowData[1]);
                    in_username.setText(rowData[2]);
                    in_password.setText(rowData[3]);
                    in_email.setText(rowData[4]);
                    in_CN.setText(rowData[5]);
                }
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(parentFrame,"Error: )" + e.getMessage());
        }
    }
}
