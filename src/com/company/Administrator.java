package com.company;

import javax.swing.*;

public class Administrator extends JFrame {

    // Needed for serialisation WHAT THIS MEANS?
    private static final long serialVersionUID = 1L;

    // declaring the variables
    private static JFrame frame;
    private final int width = 1000;
    private final int height = 750;

    public Administrator(String title){

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
