package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class Registrar extends JFrame {

    // Needed for serialisation
    private static final long serialVersionUID = 1L;

    // declaring the variables
    private JFrame frame;
    private Container mainContainer;
    private JPanel buttonPanel;
    private Button student;
    private Button entry;
    private Database database;
    private Button logOut;
    private JPanel logOutPanel;
    private final int width = 800;
    private final int height = 600;

    public Registrar(String title){

        //initializing the variables
        frame = new JFrame(title);
        mainContainer = new Container();
        buttonPanel = new JPanel();
        logOutPanel = new JPanel();
        student = new Button("Students Registration");
        entry = new Button("Module Check");
        logOut = new Button("<- LOG OUT");
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

        student.setPreferredSize(new Dimension(100, 150));
        entry.setPreferredSize(new Dimension(100, 150));

        mainContainer.setLayout(new BorderLayout());

        buttonPanel.setLayout(new GridLayout(2,1, 10, 4));
        buttonPanel.setBorder(new EmptyBorder(100, 200, 100, 200));

        buttonPanel.add(student);
        buttonPanel.add(entry);

        //action listener for clicking the "Login" button
        student.addActionListener(ae -> {

            RegisterStudent student = new RegisterStudent("Students");

            //JTableButtonTest table = new JTableButtonTest();
            frame.dispose();

        });

        mainContainer.add(buttonPanel, BorderLayout.CENTER);
        mainContainer.add(logOutPanel, BorderLayout.NORTH);

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
