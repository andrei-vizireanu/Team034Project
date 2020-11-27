package com.company;

import javax.swing.*;

public class Teacher extends JFrame {
    private static final long serialVersionUID = 1L;

    // declaring the variables
    private static JFrame frame;
    private final int width = 1000;
    private final int height = 750;

    public Teacher(String title){

        //initializing the variables
        frame = new JFrame(title);

        //centering the window
        MyFrame.centreWindow(frame, width, height);

        //setting the width and the height of the frame
        frame.setSize(width, height);

        //the frame is not allowed to be resizable
        frame.setResizable(false);

        //making the frame visible
        frame.setVisible(true);

    }

}
