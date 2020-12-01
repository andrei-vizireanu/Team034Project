package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class Teacher extends JFrame {
    private static final long serialVersionUID = 1L;

    // declaring the variables
    private static JFrame frame;
    private final int width = 800;
    private final int height = 600;
    private Container mainContainer;
    private JPanel buttonPanel;
    private Button gradesBtn;
    private Button weightedMeanGradeBtn;
    private Button registerBtn;
    private Button overallDegreeResultBtn;
    private Button studentStatusBtn;

    public Teacher(String title){

        //initializing the variables
        frame = new JFrame(title);
        mainContainer = new Container();
        buttonPanel = new JPanel();
        gradesBtn = new Button("Add or update grades");
        weightedMeanGradeBtn = new Button("Calculate weighted mean grade");
        registerBtn = new Button("Register student for next period of study");
        overallDegreeResultBtn = new Button("Calculate overall degree result");
        studentStatusBtn = new Button("View student's status");

        mainContainer.setLayout(new BorderLayout());

        buttonPanel.setLayout(new GridLayout(5, 1, 10, 4));
        buttonPanel.setBorder(new EmptyBorder(100, 200, 100, 200));

        buttonPanel.add(gradesBtn);
        buttonPanel.add(weightedMeanGradeBtn);
        buttonPanel.add(registerBtn);
        buttonPanel.add(overallDegreeResultBtn);
        buttonPanel.add(studentStatusBtn);


        //action listener for clicking the buttons, in order
        gradesBtn.addActionListener(ae -> {

            try {
                Grades grades = new Grades("Grades for Students");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            frame.dispose();
        });


        //centering the window
        MyFrame.centreWindow(frame, width, height);

        frame.getContentPane().add(buttonPanel);
        //setting the width and the height of the frame
        frame.setSize(width, height);

        //the frame is not allowed to be resizable
        frame.setResizable(false);

        //making the frame visible
        frame.setVisible(true);

    }

}
