/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Receptionist.EMM;

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
public class Func_SubMng {
    
    public static void search(JTable table,JFrame parentFrame,JTextField in_SN){
        String SN = in_SN.getText().trim();
        
        if (SN.isEmpty()){
            JOptionPane.showMessageDialog(parentFrame,"Please enter a Student Name.");
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        try (BufferedReader br = new BufferedReader (new FileReader("SER.txt"))){
            String line = br.readLine();
            
            while ((line = br.readLine()) != null){
                String [] rowData = line.split(",",-1);
                
                if (rowData[0].toLowerCase().contains(SN.toLowerCase())){
                    Vector<Object> row = new Vector<>();
                    for (int k = 0; k < rowData.length;k++){
                        row.add(rowData[k]);
                    }
                    model.addRow(row);
                }
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(parentFrame,"Error: " + e.getMessage());
        }
        
        in_SN.setText("");
    }
    
    
    public static void save(
        JTextField in_name,
        JTextField in_old_subject,
        JComboBox<String> in_new_subject,
        JComboBox<String> in_status,
        JTable table,
        JFrame parentFrame){
        
            String name = in_name.getText();
            String old_subject = in_old_subject.getText();
            String new_subject = (String) in_new_subject.getSelectedItem();
            String status = (String) in_status.getSelectedItem();

            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1){
                JOptionPane.showMessageDialog(parentFrame,"Please select a row to update");
                return;
            }

            if (in_new_subject.getSelectedIndex() == 0 || in_status.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(parentFrame,"Please select New Subject and update Status");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(selectedRow);

            model.addRow(new Object[] {
                name,old_subject,new_subject,status
            });

            SaveFile.SERFile(table,parentFrame);

            in_name.setText("");
            in_old_subject.setText("");
            in_new_subject.setSelectedIndex(0);
            in_status.setSelectedIndex(0);
            
            JOptionPane.showMessageDialog(parentFrame,"Save Successfully");
    }
    
}
