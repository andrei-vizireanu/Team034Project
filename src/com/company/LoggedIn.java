package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoggedIn extends JFrame{

    // Constructor with frame title
    public LoggedIn(String title) throws HeadlessException {

        //creating instances for Frame and Panel
        final JFrame frame = new JFrame(title);

        final int width = 500;
        final int height = 500;

        centreWindow(frame, width, height);

        //setting the width and the height
        frame.setSize(width,height);

        //making the frame visible
        frame.setVisible(true);

    }

    public static void centreWindow(Window frame, int width, int height) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2) - width/2;
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2) - height/2;
        frame.setLocation(x, y);
    }

}
