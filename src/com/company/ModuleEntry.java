package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

public class ModuleEntry {

    private JFrame frame;
    private JPanel panel;
    private Container mainContainer;
    private JTable table;
    private JButton delete;
    private JButton add;
    private JButton edit;
    private Database database;
    private final int width = 800;
    private final int height = 600;

    public ModuleEntry(String title) throws SQLException {

        //initializing the variables
        frame = new JFrame(title);
        panel = new JPanel();
        mainContainer = new Container();
        delete = new JButton("Delete");
        add = new JButton("Add");
        edit = new JButton("Edit");
        database = new Database();

        // Column Names
        String[] columnNames = { "UserID", "Registration No.", "Degree Code", "Level", "Period", "Personal Tutor"};
        String[][] rows = database.getStudentTable(Main.statement);

        TableModel tableModel = new DefaultTableModel(rows, columnNames);
        table = new JTable(tableModel);

        //generating a Table Model with the rows and columns from above
        tableModel = new DefaultTableModel(rows, columnNames);

        //initializing the table linked to the tableModel
        table = new JTable(tableModel);

        //disable the auto-resizing of the table
        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF);

        //resizing the table in order to fit every information from it
        fitExactly();



        JScrollPane scrollpane = new JScrollPane(table);


        mainContainer.setLayout(new BorderLayout());

        //panel.setLayout(new GridLayout(2, 2, 10, 10));

        mainContainer.add(scrollpane, BorderLayout.NORTH);
        mainContainer.add(delete, BorderLayout.WEST);
        mainContainer.add(add, BorderLayout.EAST);
        mainContainer.add(edit, BorderLayout.CENTER);

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

    //resizing the cells of the table in order to fit perfectly the values from it
    public void fitExactly() {

        //making the info from the table to fit in exactly
        for (int column = 0; column < table.getColumnCount(); column++) {

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
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //checking if the actual row's width is smaller than the biggest one
                if (preferredWidth <= maxWidth) {
                    preferredWidth = maxWidth;
                }
            }

            //after finding the maximum width, we sum to it 20 in order to have some space between columns
            tableColumn.setPreferredWidth(preferredWidth + 20);
        }

    }

}
