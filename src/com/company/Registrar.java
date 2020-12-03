package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class Registrar extends JFrame {

    // Needed for serialisation WHAT THIS MEANS?
    private static final long serialVersionUID = 1L;

    // declaring the variables
    private JFrame frame;
    private Container mainContainer;
    private JPanel buttonPanel;
    private Button student;
    private Button entry;
    /*private Button modules;
    private Button degreeCourses;*/
    private final int width = 800;
    private final int height = 600;

    public Registrar(String title){

        //initializing the variables
        frame = new JFrame(title);
        mainContainer = new Container();
        buttonPanel = new JPanel();
        student = new Button("Students Registration");
        entry = new Button("Module Check");
       /* modules = new Button("Modules");
        degreeCourses = new Button("Degree Courses");*/

        student.setPreferredSize(new Dimension(100, 150));
        entry.setPreferredSize(new Dimension(100, 150));

        mainContainer.setLayout(new BorderLayout());

        buttonPanel.setLayout(new GridLayout(2,1, 10, 4));
        buttonPanel.setBorder(new EmptyBorder(100, 200, 100, 200));

        buttonPanel.add(student);
        buttonPanel.add(entry);
        /*buttonPanel.add(modules);
        buttonPanel.add(degreeCourses);*/

        //action listener for clicking the "Login" button
        student.addActionListener(ae -> {

            try {
                RegisterStudent student = new RegisterStudent("Students");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //JTableButtonTest table = new JTableButtonTest();
            frame.dispose();

        });

       /* entry.addActionListener(ae -> {

            try {
                ModuleEntry entry = new ModuleEntry("ModuleEntry");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //JTableButtonTest table = new JTableButtonTest();
            frame.dispose();

        });
        */


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
