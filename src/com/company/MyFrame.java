package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.AttributedCharacterIterator;

public class MyFrame extends JFrame {

    // Needed for serialisation WHAT THIS MEANS?
    private static final long serialVersionUID = 1L;

    // declaring the variables
    private static JFrame frame;
    private static JPanel topPanel;
    private static JPanel centerPanel;
    private static JLabel titleLabel;
    private static JLabel userLabel;
    private static JLabel passwordLabel;
    private static JTextField userField;
    private static JPasswordField passwordField;
    private static JButton login;
    private static Container mainContainer;
    private static GridBagConstraints gbc;
    private final int width = 500;
    private final int height = 500;
    private static Statement stmt = null;

    // Constructor with frame title
    public MyFrame(String title) throws HeadlessException {

        //initializing the variables
        frame = new JFrame(title);
        topPanel = new JPanel(new GridBagLayout());
        centerPanel = new JPanel(new GridBagLayout());
        gbc = new GridBagConstraints();
        mainContainer = this.getContentPane();
        titleLabel = new JLabel("Present Your Details");
        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        userField = new JTextField(20);
        passwordField = new JPasswordField(20);
        login = new JButton("Login");

        //initializing the indexes for rows and columns of the GridBag Constraint
        gbc.gridx = 0;
        gbc.gridy = 0;

        //setting the layout of the main Container and adding to it the top and center Panels in the specified positions
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(centerPanel, BorderLayout.CENTER);
        mainContainer.add(topPanel, BorderLayout.NORTH);

        //setting the borders for top and center Panels
        topPanel.setBorder(new EmptyBorder(100, 0, 0, 0));
        centerPanel.setBorder(new EmptyBorder(0, 0, 150, 0));

        //setting the font for title Label, then added to the top Panel
        titleLabel.setFont (titleLabel.getFont ().deriveFont (30.0f));
        topPanel.add(titleLabel);

        //adding the labels and the fields in the center Panel
        //changing the coordinates accordingly to the desired position
        gbc.gridy++;
        centerPanel.add(userLabel, gbc);
        gbc.gridy++;
        centerPanel.add(passwordLabel, gbc);
        gbc.gridx++;
        gbc.gridy = 1;
        centerPanel.add(userField, gbc);
        gbc.gridy++;
        centerPanel.add(passwordField, gbc);
        gbc.gridy ++;
        gbc.gridx = 1;

        //adding the button under the fields
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(login,gbc);

        //action listener for clicking the "Login" button
        login.addActionListener(ae -> {


//            try {
//                System.out.println(stmt);
//                stmt = Main.getConnection();
//
//                String sql = ("SELECT * FROM User;");
//                ResultSet rs = stmt.executeQuery(sql);
//                while(rs.next()) {
//                    int id = rs.getInt("UserID");
//                    String str1 = rs.getString("UserName");
//
//                    System.out.println(id + " " + str1);
//                }
//
//                System.out.println(rs.toString());
//
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }



            System.out.println("Login clicked");
            frame.dispose();
            LoggedIn loggedIn = new LoggedIn("Logged In");

        });

        //center the window
        centreWindow(frame, width, height);

        //adding the main container to the frame
        frame.getContentPane().add(mainContainer);

        //setting the width and the height of the frame
        frame.setSize(width,height);

        //don't allow the frame to be resized
        frame.setResizable(false);

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
