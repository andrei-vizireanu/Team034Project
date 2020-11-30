package com.company;

import com.sun.tools.javac.util.Name;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

public class AdminUserAccounts {

    // declaring the variables
    private JFrame frame;
    private JPanel panel;
    private Container mainContainer;
    private JTable table;
    private JButton delete;
    private Database database;
    private final int width = 800;
    private final int height = 600;

    public AdminUserAccounts(String title) throws SQLException {

        //initializing the variables
        frame = new JFrame(title);
        panel = new JPanel();
        mainContainer = new Container();
        delete = new JButton("Delete");
        database = new Database();

        // Column Names
        String[] columnNames = { "Username", "Password", "Title", "Forename", "Surname", "Email", "Role"};
        String[][] rows = database.getInfoUser(Main.statement);

        TableModel tableModel = new DefaultTableModel(rows, columnNames);
        table = new JTable(tableModel);

        JScrollPane scrollpane = new JScrollPane(table);

        delete.addActionListener(ae -> {

            System.out.println(table.getModel());

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




        mainContainer.setLayout(new BorderLayout());

        //panel.setLayout(new GridLayout(2, 2, 10, 10));

        mainContainer.add(scrollpane, BorderLayout.NORTH);
        mainContainer.add(delete, BorderLayout.SOUTH);

        //center the window
        MyFrame.centreWindow(frame, width, height);

        //adding the main container to the frame
        //frame.getContentPane().add(buttonPanel);

        frame.getContentPane().add(mainContainer);

        //setting the width and the height of the frame
        frame.setSize(width,height);

        //don't allow the frame to be resized
        //frame.setResizable(false);

        //making the frame visible
        frame.setVisible(true);

    }

}
