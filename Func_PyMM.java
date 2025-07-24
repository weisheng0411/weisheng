package receptionist;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


//import File_Mng.SaveFile;
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
public class Func_PyMM {
    
    public static void search(JTextField in_SN,JTable table,JFrame parentFrame){
        String name = in_SN.getText();
        boolean found = false;
        
        if (name.isEmpty()){
            JOptionPane.showMessageDialog(parentFrame,"Please enter a student name.");
            return;
        }
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        try(BufferedReader br = new BufferedReader(new FileReader("py_status.txt"))){
            String line = br.readLine();
            
            while ((line = br.readLine()) != null){
                String[] rowData = line.split(",",-1);
                
                if (rowData[0].toLowerCase().contains(name.toLowerCase())){
                    Vector<Object> row = new Vector<>();
                    found = true;
                    
                    for (int i = 0;i < rowData.length;i++){
                        row.add(rowData[i]);
                    }
                    model.addRow(row);
                }
            }
            if (found == false){
                JOptionPane.showMessageDialog(parentFrame,"Student not found...");
            }
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(parentFrame,"Error: " + e.getMessage());
        }
        in_SN.setText("");
    }
    
    
    public static void update(
        JTextField in_name,
        JTextField in_AP,
        JTextField in_TC,
        JComboBox<String> in_status,
        JTable table,
        JFrame parentFrame,
        String currentuserID
        ){
        
        String name = in_name.getText().trim();
        String amount_payable = in_AP.getText().trim();
        String total_collected = in_TC.getText().trim();
        String status = (String)in_status.getSelectedItem();
        
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1){
            JOptionPane.showMessageDialog(parentFrame,"Please select a row to update");
            return;
        }
        
        //check is any empty?
        if (name.isEmpty() || amount_payable.isEmpty() || total_collected.isEmpty()){
            JOptionPane.showMessageDialog(table,"Please enter all fields");
            return;
        }
        
        if (in_status.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(table,"Please select the status");
            return;
        }
        
        //check is input amount numbers?
        try{
            //remove 
            String cleaned_AP = amount_payable.replaceAll("[^0-9]", "");
            String cleaned_TC = total_collected.replaceAll("[^0-9]", "");
            
            int check_AP = Integer.parseInt(cleaned_AP);
            int check_TC = Integer.parseInt(cleaned_TC);
            
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(parentFrame,"Amounts should be numbers.");
            in_AP.setText("");
            in_TC.setText("");
            return;
        }
        
        //check is exist RM
        if (!amount_payable.startsWith("RM")){
            JOptionPane.showMessageDialog(parentFrame, "Amount should start with 'RM'");
            return;
        }
        //add to table
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.removeRow(selectedRow);
        
        model.addRow(new Object[]{
            name,amount_payable,total_collected,status
        });
        
        SaveFile.py_statusFile(table, parentFrame);
        receipt(status,name,amount_payable,total_collected,currentuserID);

        in_name.setText("");
        in_AP.setText("");
        in_TC.setText("");
        in_status.setSelectedIndex(0);
        
        JOptionPane.showMessageDialog(parentFrame,"Update Successfully");
    }
    
    public static void receipt(String in_status,String in_name,String in_AP,String in_TC,String currentuserID){
        String status = in_status;
        String name = in_name;
        String amount_payable = in_AP;
        String total_collected = in_TC;
        
        if (status.equals("Paid")){
            Page_receipt page = new Page_receipt(name,amount_payable,total_collected,currentuserID);
            page.setVisible(true);
        }   
    }
    
}
