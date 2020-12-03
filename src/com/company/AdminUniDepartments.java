package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

public class AdminUniDepartments {

    // declaring the variables
    private JFrame frame;
    private JPanel buttonsPanel;
    private Container mainContainer;
    private JTable table;
    private TableModel tableModel;
    private JButton edit;
    private JButton add;
    private JButton delete;
    private JButton goBack;
    private Database database;
    private final int width = 800;
    private final int height = 600;
    private final int dialogWidth = 500;
    private final int dialogHeight = 250;

    public AdminUniDepartments(String title) throws SQLException {

        //initializing the variables
        frame = new JFrame(title);
        buttonsPanel = new JPanel();
        mainContainer = new Container();
        edit = new JButton("Edit the departments' information");
        add = new JButton("Add a new department");
        delete = new JButton("Delete the department");
        goBack = new JButton("<- Go Back");
        database = new Database();

        //headers for the table
        String[] columnNames = {"ID", "Department Code", "Department Name"};
        //generating the rows with the University Departments' information
        String[][] rows = database.getInfoDepartment(Main.statement);

        //generating a Table Model with the rows and columns from above
        tableModel = new DefaultTableModel(rows, columnNames);

        //initializing the table linked to the tableModel
        table = new JTable(tableModel);

        //disable the auto-resizing of the table
        //table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF);

        //resizing the table in order to fit every information from it
        //fitExactly();

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
                infoPanel.setLayout(new GridLayout(4, 2, 10, 10));

                //getting each value from the row selected
                String depID = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
                String depCode = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
                String depName = String.valueOf(table.getValueAt(table.getSelectedRow(), 2));
                //System.out.println(depID);

                //creating the labels objects and setting their text colors and borders
                JLabel depCodeLabel = new JLabel("Department Code");
                depCodeLabel.setForeground(Color.BLUE);
                depCodeLabel.setBorder(borderLabels);
                JLabel depNameLabel = new JLabel("Department Name");
                depNameLabel.setForeground(Color.BLUE);
                depNameLabel.setBorder(borderLabels);

                //creating the fields for each value
                JTextField depCodeFiled = new JTextField(depCode);
                JTextField depNameField = new JTextField(depName);

                //action when edit is clicked
                edit.addActionListener(e -> {

                    //checking if you updated the user with new information
                    if(!depCodeFiled.getText().equals(depCode) || !depNameField.getText().equals(depName)){

                        //updating the selected user's information
                        database.updateDepartment(Main.connection,depCodeFiled.getText(), depNameField.getText(), depID);

                        //refreshing the table with the new values
                        table.setValueAt(depCodeFiled.getText(), table.getSelectedRow(), 1);
                        table.setValueAt(depNameField.getText(), table.getSelectedRow(), 2);

                        //closing the windows after this is proceed
                        editDialog.dispose();

                        //information message
                        JOptionPane.showMessageDialog(editDialog,
                                "You updated the following Department ID: " + depID,
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
                infoPanel.add(depCodeLabel);
                infoPanel.add(depCodeFiled);
                infoPanel.add(depNameLabel);
                infoPanel.add(depNameField);

                //adding the buttons to the buttons panel
                buttonsPanel.add(edit);
                buttonsPanel.add(cancel);

                //setting panel's layout
                editContainer.add(dialogScroll, BorderLayout.NORTH);
                editContainer.add(buttonsPanel, BorderLayout.CENTER);

                //centre the edit Window
                MyFrame.centreWindow(editDialog, dialogWidth, dialogHeight);

                //adding the edit container to the edit dialog
                editDialog.getContentPane().add(editContainer);

                //setting the size of the edit dialog
                editDialog.setSize(dialogWidth, dialogHeight);

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

        //action when clicking the delete button
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
            infoPanel.setLayout(new GridLayout(4, 2, 10, 10));

            //creating the labels objects and setting their text colors and borders
            //creating the labels objects and setting their text colors and borders
            JLabel depCodeLabel = new JLabel("Department Code");
            depCodeLabel.setForeground(Color.BLUE);
            depCodeLabel.setBorder(borderLabels);
            JLabel depNameLabel = new JLabel("Department Name");
            depNameLabel.setForeground(Color.BLUE);
            depNameLabel.setBorder(borderLabels);

            //creating the fields for each value
            JTextField depCodeFiled = new JTextField();
            JTextField depNameField = new JTextField();

            //action when edit is clicked
            add.addActionListener(e -> {

                //checking if you updated the user with new information
                if(!depCodeFiled.getText().contains(" ") && !depCodeFiled.getText().equals("") &&
                        !depNameField.getText().contains(" ") && !depNameField.getText().equals("")){

                    String depID = null;

                    //adding the department's information
                    database.addDepartment(Main.connection,depCodeFiled.getText(), depNameField.getText());

                    depID = database.getDepartmentID(Main.statement, depCodeFiled.getText(), depNameField.getText());
                    ((DefaultTableModel) tableModel).addRow(new Object[]{depID, depCodeFiled.getText(), depNameField.getText()});


                    //closing the windows after this is proceed
                    addDialog.dispose();

                    //information message
                    JOptionPane.showMessageDialog(addDialog,
                            "You added the following University Department ID: " + depID,
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
            infoPanel.add(depCodeLabel);
            infoPanel.add(depCodeFiled);
            infoPanel.add(depNameLabel);
            infoPanel.add(depNameField);

            //adding the buttons to the buttons panel
            buttonsPanel.add(add);
            buttonsPanel.add(cancel);

            //setting panel's layout
            editContainer.add(dialogScroll, BorderLayout.NORTH);
            editContainer.add(buttonsPanel, BorderLayout.CENTER);

            //centre the edit Window
            MyFrame.centreWindow(addDialog, dialogWidth, dialogHeight);

            //adding the edit container to the edit dialog
            addDialog.getContentPane().add(editContainer);

            //setting the size of the edit dialog
            addDialog.setSize(dialogWidth, dialogHeight);

            //don't allow the frame to be resized
            addDialog.setResizable(false);

            //setting the visibility of dialog
            addDialog.setVisible(true);

        });

        //action when clicking the delete button
        delete.addActionListener(ae -> {

            if(table.getSelectedRowCount() == 1){

                String depID = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));

                int confirm1 = JOptionPane.showOptionDialog(frame,
                        "Are you sure you want to delete the following University Department ID: " + depID + " ?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, null, null);
                if (confirm1 == JOptionPane.YES_OPTION) {

                    //deleting the university department
                    database.deleteDepartment(Main.connection, depID);

                    //removing the row from the table
                    ((DefaultTableModel) tableModel).removeRow(table.getSelectedRow());

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

        //action when clicking the go back button
        goBack.addActionListener(ae -> {

            //going back to the previous page
            Administrator admin = new Administrator("Admin");
            frame.dispose();

        });

        table.setDefaultEditor(Object.class, null);

        //creating the scroll pane in order to have a scrollable table
        JScrollPane scrollPane = new JScrollPane(table);

        //setting the layout and borders for the buttons panel
        buttonsPanel.setLayout(new GridLayout(4, 2, 10, 10));
        buttonsPanel.setBorder(new EmptyBorder(5, 200, 5, 200));
        buttonsPanel.add(edit);
        buttonsPanel.add(add);
        buttonsPanel.add(delete);
        buttonsPanel.add(goBack);

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
