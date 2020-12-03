package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

public class RegisterStudent {
    // declaring the variables
    private JFrame frame;
    private JPanel buttonsPanel;
    private Container mainContainer;
    private JTable table;
    private JButton delete;
    private JButton add;
    private JButton edit;
    private Database database;
    private final int width = 1300;
    private final int height = 700;

    public RegisterStudent(String title) throws SQLException {

        //initializing the variables
        frame = new JFrame(title);
        buttonsPanel = new JPanel();
        mainContainer = new Container();
        delete = new JButton("Delete");
        add = new JButton("Add");
        edit = new JButton("Edit");
        database = new Database();

        // Column Names
        String[] columnNames = { "ID", "Username", "Password", "Title", "Forename", "Surname", "Email", "Role"};
        String[][] rows = database.getStudentUser(Main.statement);

        TableModel tableModel = new DefaultTableModel(rows, columnNames);
        table = new JTable(tableModel);



        JScrollPane scrollpane = new JScrollPane(table);

        //action when clicking the delete button
        delete.addActionListener(ae -> {

            if(table.getSelectedRowCount() == 1){

                String id = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));

                int confirm1 = JOptionPane.showOptionDialog(frame,
                        "Are you sure you want to delete the following User Account ID: " + id + " ?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, null, null);
                if (confirm1 == JOptionPane.YES_OPTION) {

                    try {
                        database.deleteUser(Main.connection, id);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    ((DefaultTableModel) tableModel).removeRow(table.getSelectedRow());

                   /* //resizing the table again
                    fitExactly();*/

                }

            }
            else{
                if(table.getRowCount() == 0){
                    //custom title, error icon
                    JOptionPane.showMessageDialog(frame,
                            "The table is empty so you cannot delete anything!",
                            "Empty Table",
                            JOptionPane.ERROR_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(frame,
                            "You need to select the item first in order to delete it!",
                            "Select an item",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });


        //action when clicking the add button
        add.addActionListener(ae -> {

            //declaring and initiating the objects
            Container editContainer = new Container();
            JFrame dialogFrame = new JFrame();
            JPanel infoPanel = new JPanel();
            JPanel buttonsPanel = new JPanel();
            JDialog addDialog = new JDialog(dialogFrame, "Add Dialog");
            JButton add = new JButton("Add");
            JButton cancel = new JButton("Cancel");
            EmptyBorder borderLabels = new EmptyBorder(15, 0, 0, 0);
            JScrollPane dialogScroll = new JScrollPane(infoPanel);

            //setting the layouts of the container and infoPanel
            editContainer.setLayout(new BorderLayout());
            infoPanel.setLayout(new GridLayout(14, 2, 10, 10));

            //creating the labels objects and setting their text colors and borders
            JLabel usernameLabel = new JLabel("Username");
            usernameLabel.setForeground(Color.BLUE);
            usernameLabel.setBorder(borderLabels);
            JLabel passwordLabel = new JLabel("Password");
            passwordLabel.setForeground(Color.BLUE);
            passwordLabel.setBorder(borderLabels);
            JLabel titleLabel = new JLabel("Title");
            titleLabel.setForeground(Color.BLUE);
            titleLabel.setBorder(borderLabels);
            JLabel forenameLabel = new JLabel("Forename");
            forenameLabel.setForeground(Color.BLUE);
            forenameLabel.setBorder(borderLabels);
            JLabel surnameLabel = new JLabel("Surname");
            surnameLabel.setForeground(Color.BLUE);
            surnameLabel.setBorder(borderLabels);
            JLabel emailLabel = new JLabel("Email");
            emailLabel.setForeground(Color.BLUE);
            emailLabel.setBorder(borderLabels);
            JLabel roleLabel = new JLabel("Role");
            roleLabel.setForeground(Color.BLUE);
            roleLabel.setBorder(borderLabels);

            //creating the fields for each value
            JTextField usernameFiled = new JTextField();
            JTextField passwordField = new JTextField();
            JTextField forenameField = new JTextField();
            JTextField surnameField = new JTextField();
            JTextField emailField = new JTextField();

            //creating an array for the combo box

            String[] roles = {"", "Student"};
            String[] titles = {"", "Master", "Mr", "Miss", "Mrs", "Ms", "Mx"};

            //creating the combo box and checking which item (role) should be selected for the selected User
            JComboBox rolesCombo = new JComboBox(roles);
            JComboBox titlesCombo = new JComboBox(titles);
            //rolesCombo.setSelectedIndex(0);
            titlesCombo.setSelectedIndex(0);

            //action when edit is clicked
            add.addActionListener(e -> {

                //checking if you updated the user with new information
                if(!usernameFiled.getText().contains(" ") && !usernameFiled.getText().equals("") &&
                        !passwordField.getText().contains(" ") && !passwordField.getText().equals("") &&
                        titlesCombo.getSelectedIndex() != 0 &&
                        !forenameField.getText().contains(" ") && !forenameField.getText().equals("") &&
                        !surnameField.getText().contains(" ") && !surnameField.getText().equals("") &&
                        !emailField.getText().contains(" ") && !emailField.getText().equals("")) /*&&
                rolesCombo.getSelectedIndex() != 0*/{

                    String id = null;

                    //updating the selected user's information
                    try {
                        database.addUser(Main.connection,usernameFiled.getText(), passwordField.getText(),
                                String.valueOf(titlesCombo.getSelectedItem()), forenameField.getText(),
                                surnameField.getText(), emailField.getText(), String.valueOf(rolesCombo.getSelectedItem()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        id = database.getUserID(Main.statement, usernameFiled.getText(), passwordField.getText());
                        ((DefaultTableModel) tableModel).addRow(new Object[]{id, usernameFiled.getText(),
                                passwordField.getText(), String.valueOf(titlesCombo.getSelectedItem()),
                                forenameField.getText(), surnameField.getText(), emailField.getText(),
                                "Student"});

                        //resizing the table again
                        //fitExactly();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    //closing the windows after this is proceed
                    addDialog.dispose();

                    //information message
                    JOptionPane.showMessageDialog(addDialog,
                            "You added the following User Account ID: " + id,
                            "Successfully Added",
                            JOptionPane.INFORMATION_MESSAGE);

                }
                //if the information is not changed but you still clicked on edit
                else{
                    //error message
                    JOptionPane.showMessageDialog(addDialog,
                            "You need to fill all the fields! And make sure to not have any space in them!",
                            "Fill all fields",
                            JOptionPane.ERROR_MESSAGE);
                }

            });

            //close the actual window when pressing on the cancel button
            cancel.addActionListener(e -> {
                addDialog.dispose();
            });


            //adding the labels and fields to the information panel
            infoPanel.add(usernameLabel);
            infoPanel.add(usernameFiled);
            infoPanel.add(passwordLabel);
            infoPanel.add(passwordField);
            infoPanel.add(titleLabel);
            infoPanel.add(titlesCombo);
            infoPanel.add(forenameLabel);
            infoPanel.add(forenameField);
            infoPanel.add(surnameLabel);
            infoPanel.add(surnameField);
            infoPanel.add(emailLabel);
            infoPanel.add(emailField);
            infoPanel.add(roleLabel);
            infoPanel.add(rolesCombo);

            //adding the buttons to the buttons panel
            buttonsPanel.add(add);
            buttonsPanel.add(cancel);

            //setting panel's layout
            editContainer.add(dialogScroll, BorderLayout.NORTH);
            editContainer.add(buttonsPanel, BorderLayout.CENTER);

            //centre the edit Window
            MyFrame.centreWindow(addDialog, 500, 700);

            //adding the edit container to the edit dialog
            addDialog.getContentPane().add(editContainer);

            //setting the size of the edit dialog
            addDialog.setSize(500, 700);

            //don't allow the frame to be resized
            addDialog.setResizable(false);

            //setting the visibility of dialog
            addDialog.setVisible(true);

        });


        //creating the scroll pane in order to have a scrollable table
        JScrollPane scrollPane = new JScrollPane(table);

        //setting the layout and borders for the buttons panel
        buttonsPanel.setLayout(new GridLayout(3, 2, 10, 10));
        buttonsPanel.setBorder(new EmptyBorder(10, 200, 10, 200));
        buttonsPanel.add(edit);
        buttonsPanel.add(add);
        buttonsPanel.add(delete);

        //setting the layout for the main container
        mainContainer.setLayout(new BorderLayout());

        //adding to the main container the scroll pane and buttons panel
        mainContainer.add(scrollPane, BorderLayout.NORTH);
        mainContainer.add(buttonsPanel, BorderLayout.CENTER);

        //center the window
        MyFrame.centreWindow(frame, width, height);

        //adding the main container to the frame
        frame.getContentPane().add(mainContainer);

        //setting the width and the height of the frame
        frame.setSize(width,height);

        //don't allow the frame to be resized
        frame.setResizable(false);

        //making the frame visible
        frame.setVisible(true);


    }

}
