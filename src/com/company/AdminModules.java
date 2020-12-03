package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

public class AdminModules {

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
    private final int dialogHeight = 600;
    private final int width = 800;
    private final int height = 600;

    public AdminModules(String title) throws SQLException {

        //initializing the variables
        frame = new JFrame(title);
        buttonsPanel = new JPanel();
        mainContainer = new Container();
        edit = new JButton("Edit the module's information");
        add = new JButton("Add a new module");
        delete = new JButton("Delete the module");
        goBack = new JButton("<- Go Back");
        database = new Database();

        //headers for the table
        String[] columnNames = { "ID", "Module Code", "Module Name", "Level", "Core", "Credit", "Degree"};
        //generating the rows with the Users' information
        String[][] rows = database.getInfoModule(Main.connection, Main.statement);

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
                infoPanel.setLayout(new GridLayout(12, 2, 10, 10));

                //getting each value from the row selected
                String id = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
                String moduleCode = String.valueOf(table.getValueAt(table.getSelectedRow(), 1));
                String moduleName = String.valueOf(table.getValueAt(table.getSelectedRow(), 2).toString());
                String level = String.valueOf(table.getValueAt(table.getSelectedRow(), 3).toString());
                String core = String.valueOf(table.getValueAt(table.getSelectedRow(), 4).toString());
                String credit = String.valueOf(table.getValueAt(table.getSelectedRow(), 5).toString());
                String degree = String.valueOf(table.getValueAt(table.getSelectedRow(), 6).toString());

                //creating the labels objects and setting their text colors and borders
                JLabel modCodeLabel = new JLabel("Module Code");
                modCodeLabel.setForeground(Color.BLUE);
                modCodeLabel.setBorder(borderLabels);
                JLabel modNameLabel = new JLabel("Module Name");
                modNameLabel.setForeground(Color.BLUE);
                modNameLabel.setBorder(borderLabels);
                JLabel levelLabel = new JLabel("Level");
                levelLabel.setForeground(Color.BLUE);
                levelLabel.setBorder(borderLabels);
                JLabel coreLabel = new JLabel("Core");
                coreLabel.setForeground(Color.BLUE);
                coreLabel.setBorder(borderLabels);
                JLabel creditLabel = new JLabel("Credit");
                creditLabel.setForeground(Color.BLUE);
                creditLabel.setBorder(borderLabels);
                JLabel degreeLabel = new JLabel("Degree");
                degreeLabel.setForeground(Color.BLUE);
                degreeLabel.setBorder(borderLabels);

                //creating the fields for each value
                JTextField modCodeFiled = new JTextField(moduleCode);
                JTextField modNameField = new JTextField(moduleName);
                JTextField levelField = new JTextField(level);
                String[] cores = {"Obligatory", "Optional"};
                JComboBox coresCombo = new JComboBox(cores);
                JTextField creditField = new JTextField(credit);
                String[][] IDsDegrees = database.getDegreeIDsNames(Main.statement);
                String[] degreeTitles = IDsDegrees[1];
                JComboBox degreeTitlesCombo = new JComboBox(degreeTitles);

                //checking which item (degreeName and core) should be selected for the selected Module
                for(int i = 0; i < degreeTitles.length; i++){
                    if(degree.equals(degreeTitles[i])){
                        degreeTitlesCombo.setSelectedIndex(i);
                    }
                }

                for(int i = 0; i < cores.length; i++){
                    if(core.equals(cores[i])){
                        coresCombo.setSelectedIndex(i);
                    }
                }

                //action when edit is clicked
                edit.addActionListener(e -> {

                    //checking if you updated the user with new information
                    if(!modCodeFiled.getText().equals(moduleCode) || !modNameField.getText().equals(moduleName) ||
                            !levelField.getText().equals(level) || !String.valueOf(coresCombo.getSelectedItem()).equals(core) ||
                            !creditField.getText().equals(credit) || !String.valueOf(degreeTitlesCombo.getSelectedItem()).equals(degree)){

                        //updating the selected user's information
                        database.updateModule(Main.connection, modCodeFiled.getText(), modNameField.getText(),
                                levelField.getText(), String.valueOf(coresCombo.getSelectedItem()), creditField.getText(),
                                IDsDegrees, String.valueOf(degreeTitlesCombo.getSelectedItem()), id);

                        //refreshing the table with the new values
                        table.setValueAt(modCodeFiled.getText(), table.getSelectedRow(), 1);
                        table.setValueAt(modNameField.getText(), table.getSelectedRow(), 2);
                        table.setValueAt(levelField.getText(), table.getSelectedRow(), 3);
                        table.setValueAt(String.valueOf(coresCombo.getSelectedItem()), table.getSelectedRow(), 4);
                        table.setValueAt(creditField.getText(), table.getSelectedRow(), 5);
                        table.setValueAt(String.valueOf(degreeTitlesCombo.getSelectedItem()), table.getSelectedRow(), 6);

                        //resizing the table again
                        fitExactly();

                        //closing the windows after this is proceed
                        editDialog.dispose();

                        //information message
                        JOptionPane.showMessageDialog(editDialog,
                                "You updated the following Module ID: " + id,
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
                infoPanel.add(modCodeLabel);
                infoPanel.add(modCodeFiled);
                infoPanel.add(modNameLabel);
                infoPanel.add(modNameField);
                infoPanel.add(levelLabel);
                infoPanel.add(levelField);
                infoPanel.add(coreLabel);
                infoPanel.add(coresCombo);
                infoPanel.add(creditLabel);
                infoPanel.add(creditField);
                infoPanel.add(degreeLabel);
                infoPanel.add(degreeTitlesCombo);

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
            infoPanel.setLayout(new GridLayout(12, 2, 10, 10));

            //creating the labels objects and setting their text colors and borders
            JLabel modCodeLabel = new JLabel("Module Code");
            modCodeLabel.setForeground(Color.BLUE);
            modCodeLabel.setBorder(borderLabels);
            JLabel modNameLabel = new JLabel("Module Name");
            modNameLabel.setForeground(Color.BLUE);
            modNameLabel.setBorder(borderLabels);
            JLabel levelLabel = new JLabel("Level (Numerical)");
            levelLabel.setForeground(Color.BLUE);
            levelLabel.setBorder(borderLabels);
            JLabel coreLabel = new JLabel("Core");
            coreLabel.setForeground(Color.BLUE);
            coreLabel.setBorder(borderLabels);
            JLabel creditLabel = new JLabel("Credit (Numerical)");
            creditLabel.setForeground(Color.BLUE);
            creditLabel.setBorder(borderLabels);
            JLabel degreeLabel = new JLabel("Degree");
            degreeLabel.setForeground(Color.BLUE);
            degreeLabel.setBorder(borderLabels);

            //creating the fields for each value
            JTextField modCodeFiled = new JTextField();
            JTextField modNameField = new JTextField();
            JTextField levelField = new JTextField();
            JTextField creditField = new JTextField();
            String[][] IDsDegrees = database.getDegreeIDsNames(Main.statement);
            String[] degreeTitles = IDsDegrees[1];
            JComboBox degreeTitlesCombo = new JComboBox(degreeTitles);
            degreeTitlesCombo.setSelectedIndex(0);
            String[] cores = {"Obligatory", "Optional"};
            JComboBox coresCombo = new JComboBox(cores);
            coresCombo.setSelectedIndex(0);

            System.out.println(checkString(creditField.getText()));

            //action when edit is clicked
            add.addActionListener(e -> {

                //checking if you updated the user with new information
                if(!modCodeFiled.getText().contains(" ") && !modCodeFiled.getText().equals("") &&
                        !modNameField.getText().equals("") &&
                        !levelField.getText().contains(" ") && !levelField.getText().equals("") &&
                        checkString(creditField.getText()) && checkString(levelField.getText())){

                    String id = null;

                    //adding the user's information
                    database.addModule(Main.connection, modCodeFiled.getText(), modNameField.getText(),
                            levelField.getText(), String.valueOf(coresCombo.getSelectedItem()), creditField.getText(),
                            String.valueOf(degreeTitlesCombo.getSelectedItem()), IDsDegrees);


                    String degreeID = null;

                    for(int i = 0; i < IDsDegrees[1].length; i++){

                        if(IDsDegrees[1][i].equals(String.valueOf(degreeTitlesCombo.getSelectedItem()))){
                            degreeID = IDsDegrees[0][i];
                        }

                    }

                    id = database.getModuleID(Main.statement, modCodeFiled.getText(), modNameField.getText(),
                            levelField.getText(), String.valueOf(coresCombo.getSelectedItem()), creditField.getText(),
                            degreeID);

                    ((DefaultTableModel) tableModel).addRow(new Object[]{id, modCodeFiled.getText(),
                            modNameField.getText(), levelField.getText(), String.valueOf(coresCombo.getSelectedItem()),
                            creditField.getText(), String.valueOf(degreeTitlesCombo.getSelectedItem())});

                    //resizing the table again
                    fitExactly();

                    //closing the windows after this is proceed
                    addDialog.dispose();

                    //information message
                    JOptionPane.showMessageDialog(addDialog,
                            "You added the following Module ID: " + id,
                            "Successfully Added",
                            JOptionPane.INFORMATION_MESSAGE);

                }
                //if the information is not changed but you still clicked on edit
                else{
                    //error message
                    JOptionPane.showMessageDialog(addDialog,
                            "You need to fill all the fields! And make sure to not have any space in them! " +
                                    "(Module Code, Level, Credit)\n Moreover, Level and Credit need to be numeric!",
                            "Fill all fields",
                            JOptionPane.ERROR_MESSAGE);
                }

            });

            //close the actual window when pressing on the cancel button
            cancel.addActionListener(e -> {
                addDialog.dispose();
            });


            //adding the labels and fields to the information panel
            infoPanel.add(modCodeLabel);
            infoPanel.add(modCodeFiled);
            infoPanel.add(modNameLabel);
            infoPanel.add(modNameField);
            infoPanel.add(levelLabel);
            infoPanel.add(levelField);
            infoPanel.add(coreLabel);
            infoPanel.add(coresCombo);
            infoPanel.add(creditLabel);
            infoPanel.add(creditField);
            infoPanel.add(degreeLabel);
            infoPanel.add(degreeTitlesCombo);

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
                        "Are you sure you want to delete the following Module ID: " + id + " ?",
                        "Delete Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, null, null);
                if (confirm1 == JOptionPane.YES_OPTION) {

                    //deleting the user account
                    database.deleteModule(Main.connection, id);

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

    public boolean checkString(String word){

        if(word.matches("[0-9]+")){
            return true;
        }
        else return false;

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
