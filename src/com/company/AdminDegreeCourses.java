package com.company;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

public class AdminDegreeCourses {

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
    private final int dialogWidth = 500;
    private final int dialogHeight = 425;
    private final int width = 800;
    private final int height = 600;

    public AdminDegreeCourses(String title) throws SQLException {

        //initializing the variables
        frame = new JFrame(title);
        buttonsPanel = new JPanel();
        mainContainer = new Container();
        edit = new JButton("Edit the the degree course's information");
        add = new JButton("Add a new degree course");
        delete = new JButton("Delete the degree course");
        goBack = new JButton("<- Go Back");
        database = new Database();

        //headers for the table
        String[] columnNames = {"ID", "Degree Code", "Degree Name", "Partner", "Lead"};
        //generating the rows with the Users' information
        String[][] rows = database.getInfoDegree(Main.statement);

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
                infoPanel.setLayout(new GridLayout(8, 2, 10, 10));

                //getting each value from the row selected
                String id = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
                String degreeCode = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
                String degreeName = String.valueOf(table.getValueAt(table.getSelectedRow(), 2));
                String partner = String.valueOf(table.getValueAt(table.getSelectedRow(), 3));
                String lead = String.valueOf(table.getValueAt(table.getSelectedRow(), 4));

                //creating the labels objects and setting their text colors and borders
                JLabel degreeCodeLabel = new JLabel("Degree Code");
                degreeCodeLabel.setForeground(Color.BLUE);
                degreeCodeLabel.setBorder(borderLabels);
                JLabel degreeNameLabel = new JLabel("Degree Name");
                degreeNameLabel.setForeground(Color.BLUE);
                degreeNameLabel.setBorder(borderLabels);
                JLabel partnerLabel = new JLabel("Partner");
                partnerLabel.setForeground(Color.BLUE);
                partnerLabel.setBorder(borderLabels);
                JLabel leadLabel = new JLabel("Lead");
                leadLabel.setForeground(Color.BLUE);
                leadLabel.setBorder(borderLabels);

                //creating the fields for each value
                JTextField degreeCodeFiled = new JTextField(degreeCode);
                JTextField degreeNameField = new JTextField(degreeName);
                String[] departmentCodes = database.getDepartmentCodes(Main.statement);
                JComboBox departCodesPartnerCombo = new JComboBox(departmentCodes);
                JComboBox departCodesLeadCombo = new JComboBox(departmentCodes);

                //checking which item (partner and lead) should be selected for the selected Degree
                for(int i = 0; i < departmentCodes.length; i++){
                    if(partner.equals(departmentCodes[i])){
                        departCodesPartnerCombo.setSelectedIndex(i);
                    }
                }

                for(int i = 0; i < departmentCodes.length; i++){
                    if(lead.equals(departmentCodes[i])){
                        departCodesLeadCombo.setSelectedIndex(i);
                    }
                }


                //action when edit is clicked
                edit.addActionListener(e -> {

                    //checking if you updated the degree with new information
                    if(!degreeCodeFiled.getText().equals(degreeCode) || !degreeNameField.getText().equals(degreeName) ||
                            !String.valueOf(departCodesLeadCombo.getSelectedItem()).equals(lead) ||
                            !String.valueOf(departCodesPartnerCombo.getSelectedItem()).equals(partner)){

                        //updating the selected degree's information
                        database.updateDegree(Main.connection, degreeCodeFiled.getText(), degreeNameField.getText(),
                                String.valueOf(departCodesPartnerCombo.getSelectedItem()),
                                String.valueOf(departCodesLeadCombo.getSelectedItem()), id);

                        //refreshing the table with the new values
                        table.setValueAt(degreeCodeFiled.getText(), table.getSelectedRow(), 1);
                        table.setValueAt(degreeNameField.getText(), table.getSelectedRow(), 2);
                        table.setValueAt(String.valueOf(departCodesPartnerCombo.getSelectedItem()),table.getSelectedRow(), 3);
                        table.setValueAt(String.valueOf(departCodesLeadCombo.getSelectedItem()), table.getSelectedRow(), 4);

                        //resizing the table again
                        fitExactly();

                        //closing the windows after this is proceed
                        editDialog.dispose();

                        //information message
                        JOptionPane.showMessageDialog(editDialog,
                                "You updated the following Degree Course ID: " + id,
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
                infoPanel.add(degreeCodeLabel);
                infoPanel.add(degreeCodeFiled);
                infoPanel.add(degreeNameLabel);
                infoPanel.add(degreeNameField);
                infoPanel.add(partnerLabel);
                infoPanel.add(departCodesPartnerCombo);
                infoPanel.add(leadLabel);
                infoPanel.add(departCodesLeadCombo);

                //setting the border to the buttonsPanel
                buttonsPanel.setBorder(borderLabels);

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
            infoPanel.setLayout(new GridLayout(8, 2, 10, 10));

            //creating the labels objects and setting their text colors and borders
            JLabel degreeCodeLabel = new JLabel("Degree Code");
            degreeCodeLabel.setForeground(Color.BLUE);
            degreeCodeLabel.setBorder(borderLabels);
            JLabel degreeNameLabel = new JLabel("Degree Name");
            degreeNameLabel.setForeground(Color.BLUE);
            degreeNameLabel.setBorder(borderLabels);
            JLabel partnerLabel = new JLabel("Partner");
            partnerLabel.setForeground(Color.BLUE);
            partnerLabel.setBorder(borderLabels);
            JLabel leadLabel = new JLabel("Lead");
            leadLabel.setForeground(Color.BLUE);
            leadLabel.setBorder(borderLabels);

            //creating the fields for each value
            JTextField degreeCodeFiled = new JTextField();
            JTextField degreeNameField = new JTextField();
            String[] departmentCodes = database.getDepartmentCodes(Main.statement);
            JComboBox departCodesPartnerCombo = new JComboBox(departmentCodes);
            JComboBox departCodesLeadCombo = new JComboBox(departmentCodes);
            departCodesPartnerCombo.setSelectedIndex(0);
            departCodesLeadCombo.setSelectedIndex(0);

            //action when edit is clicked
            add.addActionListener(e -> {

                //checking if you updated the degree course with new information
                if(!degreeCodeFiled.getText().contains(" ") && !degreeCodeFiled.getText().equals("") &&
                        !degreeNameField.getText().equals("")){

                    String id = null;

                    //adding the degree course's information
                    database.addDegreeCourse(Main.connection, degreeCodeFiled.getText(), degreeNameField.getText(),
                            String.valueOf(departCodesPartnerCombo.getSelectedItem()),
                            String.valueOf(departCodesLeadCombo.getSelectedItem()));

                    //getting the id of the added degree course
                    id = database.getDegreeID(Main.statement, degreeCodeFiled.getText(), degreeNameField.getText(),
                            String.valueOf(departCodesPartnerCombo.getSelectedItem()),
                            String.valueOf(departCodesLeadCombo.getSelectedItem()));

                    //adding the information of the added degree course into the table
                    ((DefaultTableModel) tableModel).addRow(new Object[]{id, degreeCodeFiled.getText(),
                            degreeNameField.getText(), String.valueOf(departCodesPartnerCombo.getSelectedItem()),
                            String.valueOf(departCodesLeadCombo.getSelectedItem())});

                    //resizing the table again
                    fitExactly();

                    //closing the windows after this is proceed
                    addDialog.dispose();

                    //information message
                    JOptionPane.showMessageDialog(addDialog,
                            "You added the following Degree Course ID: " + id,
                            "Successfully Added",
                            JOptionPane.INFORMATION_MESSAGE);

                }
                //if the information is not changed but you still clicked on edit
                else{
                    //error message
                    JOptionPane.showMessageDialog(addDialog,
                            "You need to fill all the fields! And make sure to not have any space in Degree Code! ",
                            "Fill all fields",
                            JOptionPane.ERROR_MESSAGE);
                }

            });

            //close the actual window when pressing on the cancel button
            cancel.addActionListener(e -> {
                addDialog.dispose();
            });


            //adding the labels and fields to the information panel
            infoPanel.add(degreeCodeLabel);
            infoPanel.add(degreeCodeFiled);
            infoPanel.add(degreeNameLabel);
            infoPanel.add(degreeNameField);
            infoPanel.add(partnerLabel);
            infoPanel.add(departCodesPartnerCombo);
            infoPanel.add(leadLabel);
            infoPanel.add(departCodesLeadCombo);

            //setting the border to the buttonsPanel
            buttonsPanel.setBorder(borderLabels);

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

                String id = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));

                int confirm1 = JOptionPane.showOptionDialog(frame,
                        "Are you sure you want to delete the following Degree Course ID: " + id + " ?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, null, null);
                if (confirm1 == JOptionPane.YES_OPTION) {

                    //deleting the degree course
                    database.deleteDegree(Main.connection, id);

                    //removing the row from the table
                    ((DefaultTableModel) tableModel).removeRow(table.getSelectedRow());

                    //resizing the table again
                    fitExactly();

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
            tableColumn.setPreferredWidth(preferredWidth + 15);
        }

    }

}
