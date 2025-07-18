/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Receptionist.EMM;

import File_Mng.SaveFile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SCSM11
 */
public class Func_RnM_Stu {

    public static void register(
        JTextField in_name,
        JTextField in_IC,
        JTextField in_email,
        JTextField in_CN,
        JTextField in_address,
        JComboBox<String> in_EL,
        JComboBox<String> in_Sub1,
        JComboBox<String> in_tu1,
        JComboBox<String> in_Sub2,
        JComboBox<String> in_tu2,
        JComboBox<String> in_Sub3,
        JComboBox<String> in_tu3,
        JComboBox<String> in_MOE,
        JTable table,
        JFrame parentFrame){
        
        String name = in_name.getText();
        String IC = in_IC.getText();
        String email = in_email.getText();
        String CN = in_CN.getText();
        String address = in_address.getText();
        String EL = (String) in_EL.getSelectedItem();
        String subject1 = (String)in_Sub1.getSelectedItem();
        String subject2 = (String)in_Sub2.getSelectedItem();
        String subject3 = (String)in_Sub3.getSelectedItem();
        String tutor1 = (String)in_tu1.getSelectedItem();
        String tutor2 = (String)in_tu2.getSelectedItem();
        String tutor3 = (String)in_tu3.getSelectedItem();
        
        String MOE = (String) in_MOE.getSelectedItem();
        
        if (name.isEmpty() || IC.isEmpty()|| email.isEmpty()|| CN.isEmpty()|| address.isEmpty()){
        JOptionPane.showMessageDialog(parentFrame,
                "Please enter all fields",
                "Try Again",
                JOptionPane.ERROR_MESSAGE);
                return;
        } 
        
        //check IC
        try{
            Long.parseLong(IC);
             if (IC.length() != 12){
                 JOptionPane.showMessageDialog(parentFrame,"IC should be 12 number.");
                 return;
             }
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(parentFrame,"IC should be numbers.");
            in_IC.setText("");
            return;
        }
        
        //check email
        if (!email.contains("@") || !email.contains(".com")){
           JOptionPane.showMessageDialog(parentFrame,"Email should be xxxx@xxx.com");
           return;
        }
        
         //check contact number
        try{
            Long.parseLong(CN);
            
            if (CN.length() != 10 && CN.length() != 11){
                JOptionPane.showMessageDialog(parentFrame,"Contact Numeber should be 10 or 11 number.");
                return;
            }
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(parentFrame,"Contact number should be numbers.");
            in_CN.setText("");
            return;
        }

        //check are education level, subjects,tutor and MOE empty?
        if (in_EL.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(parentFrame,"Select one education level");
            return;
        }

        if (in_Sub1.getSelectedIndex() == 0 ||
            in_Sub2.getSelectedIndex() == 0 ||
            in_Sub3.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(parentFrame,"Select any subject");
            return;
        }
        
        if (in_tu1.getSelectedIndex() == 0 ||
            in_tu2.getSelectedIndex() == 0 ||
            in_tu3.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(parentFrame,"Select any tutor");
            return;
        }

        if (in_MOE.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(parentFrame,"Select any month");
            return;
        }

        // add to table
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]
        {name,IC,email,CN,address,EL,subject1+ " (" + tutor1 +")",subject2+ " (" + tutor2 +")",subject3+ " (" + tutor3 +")",MOE});

        SaveFile.stu_enrollFile(table,parentFrame);
        JOptionPane.showMessageDialog(parentFrame,"Register successfully.");

        in_name.setText("");
        in_IC.setText("");
        in_email.setText("");
        in_CN.setText("");
        in_address.setText("");
        in_EL.setSelectedIndex(0);
        in_Sub1.setSelectedIndex(0);
        in_Sub2.setSelectedIndex(0);
        in_Sub3.setSelectedIndex(0);
        in_tu1.setSelectedIndex(0);
        in_tu2.setSelectedIndex(0);
        in_tu3.setSelectedIndex(0);
        in_MOE.setSelectedIndex(0);
    }
    
    
    public static void delete(JTable table,JFrame parentFrame){
        int row = table.getSelectedRow();
        
        if (row < 0){
            JOptionPane.showMessageDialog(parentFrame,
                    "No row is selected!!",
                    "Please select one row",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(row);
        }
        
        SaveFile.stu_enrollFile(table,parentFrame);
        JOptionPane.showMessageDialog(parentFrame,"Delete Successfully.");
    }
    
    
    public static void search(JTable table,JFrame parentFrame,JTextField in_SN){
        String SN = in_SN.getText().trim();

        if (SN.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Please enter a Student Name.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // clear all data
        
        try(BufferedReader br = new BufferedReader(new FileReader("student_enroll.txt"))){
            String line = br.readLine();
            
            while ((line = br.readLine()) != null){
                String[] rowData = line.split(",",-1);
                
                if (rowData[0].toLowerCase().contains(SN.toLowerCase())){
                    Vector<Object> row = new Vector<>();
                    for (int k = 0; k < rowData.length;k++){
                        
                        //6,8,10 are subject, combine subject and tutor in format "subject (tutor)"
                        if (k == 6 || k == 8 || k == 10){
                            row.add(rowData[k] +" (" + rowData[k+1] + ")");
                        } 
                        
                        // skip the tutor
                        else if (k == 7 || k == 9 || k == 11){
                            continue;
                        }else {
                        row.add(rowData[k]);
                        }
                    }
                    model.addRow(row);
                }
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(parentFrame,"Error: " + e.getMessage());
        }
        in_SN.setText("");
    }
}
