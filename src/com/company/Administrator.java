package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class Administrator extends JFrame {

    // Needed for serialisation
    private static final long serialVersionUID = 1L;

    // declaring the variables
    private JFrame frame;
    private Container mainContainer;
    private JPanel buttonPanel;
    private JPanel logOutPanel;
    private Button accounts;
    private Button departments;
    private Button modules;
    private Button degreeCourses;
    private Button logOut;
    private Database database;
    private final int width = 800;
    private final int height = 600;

    public Administrator(String title){

        //initializing the variables
        frame = new JFrame(title);
        mainContainer = new Container();
        buttonPanel = new JPanel();
        logOutPanel = new JPanel();
        accounts = new Button("User Accounts");
        departments = new Button("University Departments");
        modules = new Button("Modules");
        degreeCourses = new Button("Degree Courses");
        logOut = new Button("<- LOG OUT");
        FlowLayout experimentLayout = new FlowLayout();
        logOutPanel.setLayout(experimentLayout);
        logOutPanel.add(logOut);
        database = new Database();

        accounts.setPreferredSize(new Dimension(100, 50));
        departments.setPreferredSize(new Dimension(100, 50));

        mainContainer.setLayout(new BorderLayout());

        buttonPanel.setLayout(new GridLayout(4,1, 10, 4));
        buttonPanel.setBorder(new EmptyBorder(100, 200, 100, 200));

        buttonPanel.add(accounts);
        buttonPanel.add(departments);
        buttonPanel.add(degreeCourses);
        buttonPanel.add(modules);

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

        //action listener for clicking the "User Accounts" button
        accounts.addActionListener(ae -> {

            try {
                AdminUserAccounts users = new AdminUserAccounts("User Accounts Workbench");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //JTableButtonTest table = new JTableButtonTest();
            frame.dispose();

        });

        //action listener for clicking the "University Departments" button
        departments.addActionListener(ae -> {

            try {
                AdminUniDepartments departments = new AdminUniDepartments("University Departments Workbench");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            frame.dispose();

        });

        //action listener for clicking the "Modules" button
        degreeCourses.addActionListener(ae -> {

            try {
                AdminDegreeCourses departments = new AdminDegreeCourses("Degree Courses Workbench");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            frame.dispose();

        });

        //action listener for clicking the "Modules" button
        modules.addActionListener(ae -> {

            try {
                AdminModules departments = new AdminModules("Modules Workbench");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            frame.dispose();

        });

        //center the window
        MyFrame.centreWindow(frame, width, height);

        //adding the panels to the main Container
        mainContainer.add(logOutPanel, BorderLayout.NORTH);
        mainContainer.add(buttonPanel, BorderLayout.CENTER);

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
