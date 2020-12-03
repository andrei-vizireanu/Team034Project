package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class Student extends JFrame {

    // Needed for serialisation WHAT THIS MEANS?
    private static final long serialVersionUID = 1L;

    // declaring the variables
    private JFrame frame;
    private Container mainContainer;
    private JPanel buttonPanel;
    private Button results;
    private final int width = 800;
    private final int height = 600;


    public Student(String title){
        //initializing the variables
        frame = new JFrame(title);
        mainContainer = new Container();
        buttonPanel = new JPanel();
        results = new Button("My Results");

        results.setPreferredSize(new Dimension(100, 50));

        mainContainer.setLayout(new BorderLayout());

        buttonPanel.setLayout(new GridLayout(4,1, 10, 4));
        buttonPanel.setBorder(new EmptyBorder(100, 200, 100, 200));

        buttonPanel.add(results);

        //action listener for clicking the "Login" button
        results.addActionListener(ae -> {

            try {
                StudentFeatures result = new StudentFeatures("My Results");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            //JTableButtonTest table = new JTableButtonTest();
            frame.dispose();

        });


        //initializing the variables
        frame = new JFrame(title);

        //center the window
        MyFrame.centreWindow(frame, width, height);

        //adding the main container to the frame
        //frame.getContentPane().add(mainContainer);
        //setting the width and the height of the frame
        frame.setSize(width,height);

        //don't allow the frame to be resized
        frame.setResizable(false);

        //making the frame visible
        frame.setVisible(true);

    }

}

