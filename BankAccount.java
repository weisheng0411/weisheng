/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tutorialweek7;

/**
 *
 * @author SCSM11
 */
import java.util.Scanner;

public class BankAccount {
    private int accountNumber, balance;
    
    BankAccount(int account_num, int balance){
        accountNumber = account_num;
        this.balance = balance;
    }
    
    void deposit(int money){
        balance += money;
        System.out.printf("deposit %d successfully\n",money);
        System.out.printf("Balance: %d\n",balance);
    }
    
    void withdraw(int money){
        if (balance >= money){
            balance -= money;
            System.out.printf("withdraw %d seccessfully\n",money);
            System.out.printf("Balance: %d\n",balance);
        } else {
            System.out.printf("no enough balance... (Balance = %d)\n",balance);
        }
    }
    
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        BankAccount person1 = new BankAccount(1234,1000);
        
        while (true){
            System.out.println("Deposit(D) / Withdraw(W)/ Exit(E):");
            String respond = sc.nextLine();

            if (respond.equalsIgnoreCase("d")){
                System.out.println("Enter the deposit:");
                int money_in = sc.nextInt();
                sc.nextLine();
                person1.deposit(money_in);
                
            } else if (respond.equalsIgnoreCase("w")){
                System.out.println("Enter the money u want to withdraw:");
                int money_out = sc.nextInt();
                sc.nextLine();
                person1.withdraw(money_out);
                
            } else if (respond.equalsIgnoreCase("e")){
                System.out.println("Thanks for using...");
                break;
            } else{
                System.out.println("Invalid number...");
            }
        }
    }
}
