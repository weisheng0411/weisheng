package receptionist;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SCSM11
 */
public class SaveFile {
    
    public static void stu_enrollFile(JTable table,JFrame parentFrame){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("student_enroll.txt"))){
            
            bw.write("User ID,Name,Username,Password,Email,Contact Number,Address,Education Level,IC,Subject 1,Tutor 1,Subject 2,Tutor 2,Subject 3,Tutor 3,Month of Enrollment\n");

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0;i < model.getRowCount(); i++){
                for (int j = 0;j < model.getColumnCount(); j++){
                    Object value = model.getValueAt(i, j);
                    String cell = value != null? value.toString().trim(): ""; // conditions ? True : False
                    
                    // check "()" and split it to subject and tutor
                    if (cell.contains("(") && cell.contains(")")){
                        int start = cell.indexOf("(");   //get index of "("
                        int end = cell.indexOf(")");
                        String subject = cell.substring(0,start).trim();    //Hello.substring(3), returns "lo"
                        String tutor = cell.substring(start + 1,end).trim();
                        bw.write(subject+","+tutor);
                    } else{
                        bw.write(cell);
                    }
                    
                    if (j < model.getColumnCount() -1) bw.write(",");
                }
                bw.newLine();
            }
        }  catch (Exception e){
            JOptionPane.showMessageDialog(parentFrame,"Saving list_stu file error: " + e.getMessage());
        }
    }
    
    
    public static void SERFile(JTable table,JFrame parentFrame){
        try (BufferedWriter bw = new BufferedWriter (new FileWriter ("SER.txt"))){
            bw.write("Name,Old Subject,New Subject,Status");
            bw.newLine();
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount();i++){
                for (int j = 0; j < model.getColumnCount();j++){
                    Object value = model.getValueAt(i, j);
                    String cell = value != null? value.toString() : "";
                    bw.write(cell);
                    if (j < model.getColumnCount() - 1 ) bw.write(",");
                }
                bw.newLine();
            } 
        } catch (Exception e){
            JOptionPane.showMessageDialog(parentFrame,"Saving SER file error: " + e.getMessage());
        }
    }

    
    public static void py_statusFile(JTable table,JFrame parentFrame){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("py_status.txt"))){
            bw.write("Name,Amount Payable,Total Collected,Status");
            bw.newLine();
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0;i < model.getRowCount();i++){
                for (int j = 0;j < model.getColumnCount();j++){
                    Object value = model.getValueAt(i, j);
                    String cell = value != null? value.toString() : "";
                    bw.write(cell);
                    if (j < model.getColumnCount() -1) bw.write(",");
                }
                bw.newLine();
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(parentFrame,"Saving py_status file error: " + e.getMessage());
        }
    }
    
    
}
