package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class Administrator extends JFrame {

    // Needed for serialisation WHAT THIS MEANS?
    private static final long serialVersionUID = 1L;

    // declaring the variables
    private JFrame frame;
    private Container mainContainer;
    private JPanel buttonPanel;
    private Button accounts;
    private Button departments;
    private Button modules;
    private Button degreeCourses;
    private final int width = 800;
    private final int height = 600;

    public Administrator(String title){

        //initializing the variables
        frame = new JFrame(title);
        mainContainer = new Container();
        buttonPanel = new JPanel();
        accounts = new Button("User Accounts");
        departments = new Button("University Departments");
        modules = new Button("Modules");
        degreeCourses = new Button("Degree Courses");

        accounts.setPreferredSize(new Dimension(100, 50));
        departments.setPreferredSize(new Dimension(100, 50));

        mainContainer.setLayout(new BorderLayout());

        buttonPanel.setLayout(new GridLayout(4,1, 10, 4));
        buttonPanel.setBorder(new EmptyBorder(100, 200, 100, 200));

        buttonPanel.add(accounts);
        buttonPanel.add(departments);
        buttonPanel.add(modules);
        buttonPanel.add(degreeCourses);

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
            //JTableButtonTest table = new JTableButtonTest();
            frame.dispose();

        });


        //mainContainer.add(buttonPanel, BorderLayout.CENTER);


        //center the window
        MyFrame.centreWindow(frame, width, height);

        //adding the main container to the frame
        frame.getContentPane().add(buttonPanel);
        //setting the width and the height of the frame
        frame.setSize(width,height);

        //don't allow the frame to be resized
        frame.setResizable(false);

        //making the frame visible
        frame.setVisible(true);

    }

}
