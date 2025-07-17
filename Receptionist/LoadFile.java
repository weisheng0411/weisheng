/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Receptionist.EMM;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SCSM11
 */
public class LoadFile {
    public static void list_stuFile(JTable table,JFrame parentFrame){
        try{
            FileReader fr = new FileReader("list_stu.txt");
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
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Failed to load data:\n" + e.getMessage());
        }
    }
    
        
    public static void SERFile(JTable table,JFrame parentFrame){
        try (BufferedReader br = new BufferedReader (new FileReader ("SER.txt"))){
            String line = br.readLine();
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            
            int expectedColumns = model.getColumnCount(); //make sure the number of column same
            
            while((line = br.readLine()) != null){
                String[] rowData = line.split(",",-1);
                
                if (rowData.length == expectedColumns){
                    Vector<Object> row = new Vector<>();
                    for (int k =0;k < rowData.length;k++){
                        row.add(rowData[k]);
                    }
                    model.addRow(row);
                } else{
                    JOptionPane.showMessageDialog(parentFrame,"Number of columns does not match");
                    return;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame,"Failed to load data: " + e.getMessage());
        }
    }
}
