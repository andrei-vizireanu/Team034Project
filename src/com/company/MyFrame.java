package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MyFrame extends JFrame {

    // Needed for serialisation
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
    private Database database;
    private final int width = 500;
    private final int height = 500;

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
        database = new Database();

        //stop the connection and statement to the database when closing the window
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                database.close(Main.statement, Main.connection);
            }
        });

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

            //taking the data from the username and password fields
            String username = userField.getText();
            String password = passwordField.getText();

            if(username.isEmpty() || password.isEmpty()){

                //custom title, error icon
                JOptionPane.showMessageDialog(frame,
                        "Your need to type both username and password!",
                        "Type username AND password",
                        JOptionPane.ERROR_MESSAGE);

            }
            else{

                //checking the credentials for the data typed in and the role for that user
                boolean credentials = database.checkCredentials(Main.statement, username, password);
                String role = database.getRole(Main.statement, username);

                if(credentials){

                    //check the role in order to open the correct window after login
                    if(role.equals("Student")){
                        //open the Student class
                        Student student = new Student("Student");
                    }
                    else if(role.equals("Teacher")){
                        //open the Teacher class
                        Teacher teacher = new Teacher("Teacher");
                    }
                    else if(role.equals("Administrator")){
                        //open the Administrator class
                        Administrator admin = new Administrator("Admin");
                    }
                    else if(role.equals("Registrar")){
                        //open the Registrar class
                        Registrar admin = new Registrar("Registrar");
                    }
                    else {
                        System.out.println("Error");
                    }

                    //close the current window if credentials are right
                    frame.dispose();

                }
                else {

                    //if credentials are wrong delete the info from the fields and pop up an alert window
                    userField.setText("");
                    passwordField.setText("");
                    //custom title, error icon
                    JOptionPane.showMessageDialog(frame,
                            "Your username or password are wrong!",
                            "Wrong Credentials",
                            JOptionPane.ERROR_MESSAGE);

                }

            }

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

        //close the entire java process by clicking on the "close" button of the window
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void centreWindow(Window frame, int width, int height) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2) - width/2;
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2) - height/2;
        frame.setLocation(x, y);
    }

}
