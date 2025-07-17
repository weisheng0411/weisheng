/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package File_Mng;

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
    
    public static void list_stuFile(JTable table,JFrame parentFrame){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("list_stu.txt"))){
            
            bw.write("Name,IC,Email,Contact Number,Address,Education Level,Subject 1,Subject 2,Subject 3,Month of Enrollment\n");
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0;i < model.getRowCount(); i++){
                for (int j = 0;j < model.getColumnCount(); j++){
                    Object value = model.getValueAt(i, j);
                    String cell = value != null? value.toString(): ""; // conditions ? True : False
                    bw.write(cell);
                    if (j < model.getColumnCount() -1) bw.write(",");
                }
                bw.newLine();
            }
        }  catch (Exception e){
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame,"Saving py_status file error: " + e.getMessage());
        }
    }
}
