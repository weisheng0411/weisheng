//liankai is gay
//....
//lian kai 
gegeggegege

package multipageapp;
rndfbnfpncx;ln xnx;bnx;lb
import java.awt.*;
import java.awt.event.*;

public class MultiPageApp {
    private static Frame currentPage;
    
    public static void main(String[] args) {
        createPage1();
    }

    // first page
    private static void createPage1() {
        currentPage = new Frame("Main Page");
        currentPage.setSize(400, 300);
        currentPage.setLayout(new BorderLayout());

        Label label = new Label("Welcome to the first page", Label.CENTER);
        currentPage.add(label, BorderLayout.CENTER);

        Panel buttonPanel = new Panel();
        Button nextButton = new Button("Next");
        nextButton.addActionListener(e -> {
            currentPage.dispose(); // close the current page
            createPage2();       // open the next page
        });
        buttonPanel.add(nextButton);
        
        currentPage.add(buttonPanel, BorderLayout.SOUTH);
        currentPage.setVisible(true);
    }

    // second page
    private static void createPage2() {
        currentPage = new Frame("Second Page");
        currentPage.setSize(400, 300);
        currentPage.setLayout(new BorderLayout());

        Label label = new Label("Welcome to the second page", Label.CENTER);
        currentPage.add(label, BorderLayout.CENTER);

        Panel buttonPanel = new Panel();
        Button prevButton = new Button("Prev");
        prevButton.addActionListener(e -> {
            currentPage.dispose();
            createPage1();
        });
        
        Button nextButton = new Button("Next");
        nextButton.addActionListener(e -> {
            currentPage.dispose();
            createPage3();
        });
        
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        currentPage.add(buttonPanel, BorderLayout.SOUTH);
        currentPage.setVisible(true);
    }

    // third page
    private static void createPage3() {
        currentPage = new Frame("Third Page");
        currentPage.setSize(400, 300);
        currentPage.setLayout(new BorderLayout());

        Label label = new Label("Welcome to the final page", Label.CENTER);
        currentPage.add(label, BorderLayout.CENTER);

        Panel buttonPanel = new Panel();
        Button prevButton = new Button("Prev");
        prevButton.addActionListener(e -> {
            currentPage.dispose();
            createPage2();
        });
        
        Button exitButton = new Button("Exit");
        exitButton.addActionListener(e -> {
            currentPage.dispose(); //close the curent page
            System.exit(0);       // exit
        });
        
        buttonPanel.add(prevButton);
        buttonPanel.add(exitButton);
        currentPage.add(buttonPanel, BorderLayout.SOUTH);
        currentPage.setVisible(true);
    }
}
