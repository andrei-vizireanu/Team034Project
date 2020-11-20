package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {

    // Needed for serialisation WHAT THIS MEANS?
    private static final long serialVersionUID = 1L;

    // Constructor with frame title
    public MyFrame(String title) throws HeadlessException {

        //creating instances for Frame and Panel
        final JFrame frame = new JFrame(title);

        final JPanel panel = new JPanel(new GridBagLayout());

        final int width = 500;
        final int height = 500;

        //setting the layout of the page
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());

        //initiating the labels and fields
        //final JLabel textLabel = new JLabel("Present Your Details");

        final JLabel userLabel = new JLabel("Username:");
        final JLabel passwordLabel = new JLabel("Password:");

        final JTextField userField = new JTextField(20);
        final JPasswordField passwordField = new JPasswordField(20);
        //, new ImageIcon("images.jpg")
        JButton login = new JButton("Login");
        login.setIcon(new ImageIcon("images.jpg"));

        //center the window
        centreWindow(frame, width, height);

        //adding the labels and the fields on the panel
        gbc.fill = GridBagConstraints.NORTH;

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(userLabel, gbc);
        gbc.gridy++;
        panel.add(passwordLabel, gbc);
        gbc.gridx++;

        gbc.gridy = 1;
        panel.add(userField, gbc);
        gbc.gridy++;
        panel.add(passwordField, gbc);
        gbc.gridy ++;
        gbc.gridx = 1;

        //adding the button under the fields
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(login,gbc);

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Login clicked");
                frame.dispose();
                LoggedIn loggedIn = new LoggedIn("Logged In");
                //loggedIn.setVisible(true);
            }
        });

        //adding the panel to the frame
        frame.getContentPane().add(panel);

        //setting the width and the height
        frame.setSize(width,height);

        //making the frame visible
        frame.setVisible(true);

    }


    public static void main(String[] args) {
        // launching code goes in here

    }

    public static void centreWindow(Window frame, int width, int height) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2) - width/2;
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2) - height/2;
        frame.setLocation(x, y);
    }

}
