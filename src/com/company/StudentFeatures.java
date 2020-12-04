package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

public class StudentFeatures {

    private JFrame frame;
    private JPanel panel;
    private Container mainContainer;
    private JTable table;
    private JButton viewResults;
    private JButton viewModules;
    private JButton selectOptionalModules;
    private Database database;
    private final int width = 800;
    private final int height = 600;

    public StudentFeatures(String title) throws SQLException {

        //initializing variables
        frame = new JFrame(title);
        panel = new JPanel();
        mainContainer = new Container();
        viewModules = new JButton("View Compulsory Modules");
        selectOptionalModules = new JButton("Select Optional Modules");
        viewResults = new JButton("View Results");
        JTextField registrationNoField = new JTextField(20);
        database = new Database();

        //column names
        String[] columnNames = {"Module Code","Module Name", "Credits", "Grade", "Pass", "Resit"};
        String[][] rows = database.getStudentInfo(Main.statement);

        TableModel tableModel = new DefaultTableModel(rows, columnNames);
        table = new JTable(tableModel);

        viewModules.addActionListener(ae -> {
            if(table.getRowCount() == 0){
               // database.viewStudentModules(Main.statement, usernameFiled.getText(), passwordField.getText(),);
            }


        });

        selectOptionalModules.addActionListener(ae -> {

            if (table.getSelectedRowCount() == 1) {

                Container addGradeContainer = new Container();
                JFrame dialogFrame = new JFrame();
                JPanel infoPanel = new JPanel();
                JPanel buttonsPanel = new JPanel();
                EmptyBorder borderLabels = new EmptyBorder(15, 0, 0, 0);
                JScrollPane dialogScroll = new JScrollPane(infoPanel);
            }

        });

        viewResults.addActionListener(ae -> {

            if (table.getSelectedRowCount() == 1) {

                Container addGradeContainer = new Container();
                JFrame dialogFrame = new JFrame();
                JPanel infoPanel = new JPanel();
                JPanel buttonsPanel = new JPanel();
                EmptyBorder borderLabels = new EmptyBorder(15, 0, 0, 0);
                JScrollPane dialogScroll = new JScrollPane(infoPanel);

                AbstractButton showMyResultContainer = null;
                showMyResultContainer.setLayout(new BorderLayout());
                infoPanel.setLayout(new GridLayout(14, 2, 10, 10));


                String moduleCode = (String) table.getValueAt(table.getSelectedRow(), 0);
                double grade = Double.parseDouble((String) table.getValueAt(table.getSelectedRow(), 2));
                int pass = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 4));
                double resit = Double.parseDouble((String) table.getValueAt(table.getSelectedRow(), 5));

                JLabel resultsFS = new JLabel("Results");
                resultsFS.setForeground(Color.BLUE);
                resultsFS.setBorder(borderLabels);
            }
        });
    }
}

