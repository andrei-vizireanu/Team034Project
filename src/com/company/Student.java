package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Student extends JFrame {

    // Needed for serialisation
    private static final long serialVersionUID = 1L;

    // declaring the variables
    private JFrame frame;
    private Container mainContainer;
    private JPanel buttonPanel;
    private JPanel logOutPanel;
    private JButton profile;
    private JButton checkModule;
    private JButton logOut;
    private Database database;
    private final int width = 800;
    private final int height = 600;

    public Student(String title){

        //initializing the variables
        frame = new JFrame(title);
        mainContainer = new Container();
        buttonPanel = new JPanel();
        logOutPanel = new JPanel();
        profile = new JButton("View my profile");
        checkModule = new JButton("Check or choose Modules");
        logOut = new JButton("<- LOG OUT");
        FlowLayout experimentLayout = new FlowLayout();
        logOutPanel.setLayout(experimentLayout);
        logOutPanel.add(logOut);
        database = new Database();

        //stop the connection and statement to the database when closing the window
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                database.close(Main.statement, Main.connection);
            }
        });

        //action listener for clicking the "University Departments" button
        logOut.addActionListener(ae -> {

            //log out and open the login page again
            MyFrame departments = new MyFrame("Login Page");

            //close the window
            frame.dispose();

        });

        mainContainer.setLayout(new BorderLayout());

        buttonPanel.setLayout(new GridLayout(2,1, 10, 4));
        buttonPanel.setBorder(new EmptyBorder(100, 200, 100, 200));

        buttonPanel.add(profile);
        buttonPanel.add(checkModule);

        mainContainer.add(logOutPanel, BorderLayout.NORTH);
        mainContainer.add(buttonPanel, BorderLayout.CENTER);

        //initializing the variables
        frame = new JFrame(title);

        //center the window
        MyFrame.centreWindow(frame, width, height);

        //adding the main container to the frame
        frame.getContentPane().add(mainContainer);

        //setting the width and the height of the frame
        frame.setSize(width,height);

        //don't allow the frame to be resized
        frame.setResizable(false);

        //making the frame visible
        frame.setVisible(true);

    }

}

