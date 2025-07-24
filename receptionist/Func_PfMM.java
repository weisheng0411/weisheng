package receptionist;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author SCSM11
 */
public class Func_PfMM {
    public static void save(
        JTextField RecepId,  //cannot edit
        JTextField Ename,
        JTextField Eusername,
        JTextField Epassword,
        JTextField Eemail,
        JTextField ECN,
        JFrame parentFrame
        ){
        
        String userId = RecepId.getText().trim();
        String edit_name = Ename.getText().trim();
        String edit_username = Eusername.getText().trim();
        String edit_password = Epassword.getText().trim();
        String edit_email = Eemail.getText().trim();
        String edit_CN = ECN.getText().trim();
        
        if (edit_name.isEmpty() && edit_username.isEmpty() &&  edit_password.isEmpty() &&  edit_email.isEmpty() &&  edit_CN.isEmpty()){
            JOptionPane.showMessageDialog(parentFrame,"Please edit at least one field");
            return;
        }
        
        /*check email format
         *ensure there is new email entered or else still check email first then contact number
         */
        if (!edit_email.isEmpty() && (!edit_email.contains("@") || !edit_email.contains(".com"))){      
           JOptionPane.showMessageDialog(parentFrame,"Email should be xxxx@xxx.com");                   
           Eemail.setText("");
           return;
        }
        
         //check contact number format
        try{
            if (!edit_CN.isEmpty() && edit_CN.length() != 10 && edit_CN.length() != 11){
                JOptionPane.showMessageDialog(parentFrame,"Contact Numeber should be 10 or 11 number.");
                
                Long.parseLong(edit_CN);
                return;
            }
            
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(parentFrame,"Contact number should be numbers.");
            ECN.setText("");
            return;
        }
        
        //using temporary file method 
        try(
            BufferedReader br = new BufferedReader (new FileReader("Receptionist.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("Receptionist_temp.txt"))
            ){
            
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");

                if (rowData[0].equals("User Id")){          //write header
                    bw.write(line);
                    bw.newLine();
                    continue;
                }

                if (rowData[0].equals(userId)){             //update edited infor
                    if (! edit_name.isEmpty()){
                        rowData[1] = edit_name;
                    }
                    if (! edit_username.isEmpty()){
                        rowData[2] = edit_username;
                    }
                    if (! edit_password.isEmpty()){
                        rowData[3] = edit_password;
                    }
                    if (! edit_email.isEmpty()){
                        rowData[4] = edit_email;
                    }
                    if (! edit_CN.isEmpty()){
                        rowData[5] = edit_CN;
                    }
                }
                String new_infor = String.join(",",rowData);
                bw.write(new_infor);
                bw.newLine();
            } 
            
            br.close();
            bw.close();
            File originalFile = new File("Receptionist.txt");
            File tempFile = new File("Receptionist_temp.txt");
            
            if(originalFile.delete()){              //rename
                tempFile.renameTo(originalFile);
            }

            clear(Ename,Eusername,Epassword,Eemail,ECN);
            JOptionPane.showMessageDialog(parentFrame, "Save Successfully");

        }catch (Exception e){
        JOptionPane.showMessageDialog(parentFrame,"Error: )" + e.getMessage());
        } 
    }
     
    public static void clear(
        JTextField Ename,
        JTextField Eusername,
        JTextField Epassword,
        JTextField Eemail,
        JTextField ECN){
        
        Ename.setEditable(false);
        Eusername.setEditable(false);
        Epassword.setEditable(false);
        Eemail.setEditable(false);
        ECN.setEditable(false);

        Ename.setBackground(new Color(204,204,204));
        Eusername.setBackground(new Color(204,204,204));
        Epassword.setBackground(new Color(204,204,204));
        Eemail.setBackground(new Color(204,204,204));
        ECN.setBackground(new Color(204,204,204));

        Ename.setText("");
        Eusername.setText("");
        Epassword.setText("");
        Eemail.setText("");
        ECN.setText("");
    }
}  

