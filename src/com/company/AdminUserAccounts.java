package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.sql.SQLException;

public class AdminUserAccounts {

    // declaring the variables
    private JFrame frame;
    private JPanel buttonsPanel;
    private Container mainContainer;
    private JTable table;
    private TableModel tableModel;
    private JButton edit;
    private JButton add;
    private JButton delete;
    private Database database;
    private final int width = 800;
    private final int height = 600;

    public AdminUserAccounts(String title) throws SQLException {

        //initializing the variables
        frame = new JFrame(title);
        buttonsPanel = new JPanel();
        mainContainer = new Container();
        edit = new JButton("Edit the user's information");
        add = new JButton("Add a new user");
        delete = new JButton("Delete the user");
        database = new Database();

        //headers for the table
        String[] columnNames = { "ID", "Username", "Password", "Title", "Forename", "Surname", "Email", "Role"};
        //generating the rows with the Users' information
        String[][] rows = database.getInfoUser(Main.statement);

        //generating a Table Model with the rows and columns from above
        tableModel = new DefaultTableModel(rows, columnNames);

        //initializing the table linked to the tableModel
        table = new JTable(tableModel);

        //disable the auto-resizing of the table
        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF);

        //resizing the table in order to fit every information from it
        fitExactly();

        //action when edit button is clicked
        edit.addActionListener(ae -> {

            //you selected an existing row from the table
            if(table.getSelectedRowCount() == 1){

                //declaring and initiating the objects
                Container editContainer = new Container();
                JFrame dialogFrame = new JFrame();
                JPanel infoPanel = new JPanel();
                JPanel buttonsPanel = new JPanel();
                JDialog editDialog = new JDialog(dialogFrame, "Edit Dialog");
                JButton edit = new JButton("Edit");
                JButton cancel = new JButton("Cancel");
                EmptyBorder borderLabels = new EmptyBorder(15, 0, 0, 0);
                JScrollPane dialogScroll = new JScrollPane(infoPanel);

                //setting the layouts of the container and infoPanel
                editContainer.setLayout(new BorderLayout());
                infoPanel.setLayout(new GridLayout(14, 2, 10, 10));

                //getting each value from the row selected
                String id = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
                String username = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
                String password = String.valueOf(table.getValueAt(table.getSelectedRow(), 2).toString());
                String userTitle = String.valueOf(table.getValueAt(table.getSelectedRow(), 3).toString());
                String forename = String.valueOf(table.getValueAt(table.getSelectedRow(), 4).toString());
                String surname = String.valueOf(table.getValueAt(table.getSelectedRow(), 5).toString());
                String email = String.valueOf(table.getValueAt(table.getSelectedRow(), 6).toString());
                String role = String.valueOf(table.getValueAt(table.getSelectedRow(), 7).toString());

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
                JTextField usernameFiled = new JTextField(username);
                JTextField passwordField = new JTextField(password);
                JTextField userTitleField = new JTextField(userTitle);
                JTextField forenameField = new JTextField(forename);
                JTextField surnameField = new JTextField(surname);
                JTextField emailField = new JTextField(email);

                //creating an array for the combo box
                String[] roles = { "Student", "Teacher", "Registrar", "Administrator"};

                //creating the combo box and checking which item (role) should be selected for the selected User
                JComboBox rolesCombo = new JComboBox(roles);
                for(int i = 0; i < roles.length; i++){
                    if(role.equals(roles[i])){
                        rolesCombo.setSelectedIndex(i);
                    }
                }

                //action when edit is clicked
                edit.addActionListener(e -> {

                    //checking if you updated the user with new information
                    if(!usernameFiled.getText().equals(username) || !passwordField.getText().equals(password) ||
                            !userTitleField.getText().equals(userTitle) || !forenameField.getText().equals(forename) ||
                            !surnameField.getText().equals(surname) || !emailField.getText().equals(email) ||
                            !String.valueOf(rolesCombo.getSelectedItem()).equals(role)){

                        //updating the selected user's information
                        try {
                            database.updateUser(Main.connection,usernameFiled.getText(), passwordField.getText(),
                                    userTitleField.getText(), forenameField.getText(), surnameField.getText(),
                                    emailField.getText(), String.valueOf(rolesCombo.getSelectedItem()), id);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        //refreshing the table with the new values
                        table.setValueAt(usernameFiled.getText(), table.getSelectedRow(), 1);
                        table.setValueAt(passwordField.getText(), table.getSelectedRow(), 2);
                        table.setValueAt(userTitleField.getText(), table.getSelectedRow(), 3);
                        table.setValueAt(forenameField.getText(), table.getSelectedRow(), 4);
                        table.setValueAt(surnameField.getText(), table.getSelectedRow(), 5);
                        table.setValueAt(emailField.getText(), table.getSelectedRow(), 6);
                        table.setValueAt(String.valueOf(rolesCombo.getSelectedItem()), table.getSelectedRow(), 7);

                        //closing the windows after this is proceed
                        editDialog.dispose();

                        //information message
                        JOptionPane.showMessageDialog(editDialog,
                                "You updated the following User Account ID: " + id,
                                "Successfully Updated",
                                JOptionPane.INFORMATION_MESSAGE);

                    }
                    //if the information is not changed but you still clicked on edit
                    else{
                        //error message
                        JOptionPane.showMessageDialog(editDialog,
                                "Nothing will be updated because you didn't change anything",
                                "Nothing Changed",
                                JOptionPane.ERROR_MESSAGE);
                    }

                });

                //close the actual window when pressing on the cancel button
                cancel.addActionListener(e -> {
                    editDialog.dispose();
                });


                //adding the labels and fields to the information panel
                infoPanel.add(usernameLabel);
                infoPanel.add(usernameFiled);
                infoPanel.add(passwordLabel);
                infoPanel.add(passwordField);
                infoPanel.add(titleLabel);
                infoPanel.add(userTitleField);
                infoPanel.add(forenameLabel);
                infoPanel.add(forenameField);
                infoPanel.add(surnameLabel);
                infoPanel.add(surnameField);
                infoPanel.add(emailLabel);
                infoPanel.add(emailField);
                infoPanel.add(roleLabel);
                infoPanel.add(rolesCombo);

                //adding the buttons to the buttons panel
                buttonsPanel.add(edit);
                buttonsPanel.add(cancel);

                //setting panel's layout
                editContainer.add(dialogScroll, BorderLayout.NORTH);
                editContainer.add(buttonsPanel, BorderLayout.CENTER);

                //centre the edit Window
                MyFrame.centreWindow(editDialog, 500, 700);

                //adding the edit container to the edit dialog
                editDialog.getContentPane().add(editContainer);

                //setting the size of the edit dialog
                editDialog.setSize(500, 700);

                //don't allow the frame to be resized
                editDialog.setResizable(false);

                //setting the visibility of dialog
                editDialog.setVisible(true);

            }
            else{
                //the table is empty
                if(table.getRowCount() == 0){
                    //custom title, error icon
                    JOptionPane.showMessageDialog(frame,
                            "The table is empty so you cannot edit anything!",
                            "Empty Table",
                            JOptionPane.ERROR_MESSAGE);
                }
                //the table is not empty but you didn't select anything
                else{
                    JOptionPane.showMessageDialog(frame,
                            "You need to select the item first in order to edit it!",
                            "Select an item",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        //TO DO
        //action when clicking the delete button
        delete.addActionListener(ae -> {

            if(table.getSelectedRowCount() == 1){
                ((DefaultTableModel) tableModel).removeRow(table.getSelectedRow());
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

    //resizing the cells of the table in order to fit perfectly the values from it
    public void fitExactly(){

        //making the info from the table to fit in exactly
        for (int column = 0; column < table.getColumnCount(); column++){

            //creating the objects tableColumn, rend, rendCol in order to take some info regarding the width of the cells
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            TableCellRenderer rend = table.getTableHeader().getDefaultRenderer();
            TableCellRenderer rendCol = tableColumn.getHeaderRenderer();
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth;

            if (rendCol == null) rendCol = rend;
            Component header = rendCol.getTableCellRendererComponent(table, tableColumn.getHeaderValue(), false, false, 0, column);
            maxWidth = header.getPreferredSize().width;

            //checking each row's width and storing it if it's the biggest
            for (int row = 0; row < table.getRowCount(); row++){
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //checking if the actual row's width is smaller than the biggest one
                if (preferredWidth <= maxWidth){
                    preferredWidth = maxWidth;
                }
            }

            //after finding the maximum width, we sum to it 20 in order to have some space between columns
            tableColumn.setPreferredWidth(preferredWidth + 20);
        }

    }

}
