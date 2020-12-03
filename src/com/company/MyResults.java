package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

public class MyResults {

    private JFrame frame;
    private JPanel panel;
    private Container mainContainer;
    private JTable table;
    private JButton viewResults;
    private Database database;
    private final int width = 800;
    private final int height = 600;

    public MyResults(String title) throws SQLException {

        //initializing variables
        frame = new JFrame(title);
        panel = new JPanel();
        mainContainer = new Container();
        viewResults = new JButton("View Results");
        database = new Database();

        //column names
        String[] columnNames = {"Module Code", "Grade", "Pass", "Resit"};
        String[][] rows = database.getStudentInfo(Main.statement);

        TableModel tableModel = new DefaultTableModel(rows, columnNames);
        table = new JTable(tableModel);

        viewResults.addActionListener(ae -> {

            if (table.getSelectedRowCount() == 1) {

                Container addGradeContainer = new Container();
                JFrame dialogFrame = new JFrame();
                JPanel infoPanel = new JPanel();
                JPanel buttonsPanel = new JPanel();
                EmptyBorder borderLabels = new EmptyBorder(15, 0, 0, 0);
                JScrollPane dialogScroll = new JScrollPane(infoPanel);

                addGradeContainer.setLayout(new BorderLayout());
                infoPanel.setLayout(new GridLayout(14, 2, 10, 10));


                String moduleCode = (String) table.getValueAt(table.getSelectedRow(), 0);
                double grade = Double.parseDouble((String)table.getValueAt(table.getSelectedRow(), 2));
                int pass = Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 4));
                double resit = Double.parseDouble((String)table.getValueAt(table.getSelectedRow(), 5));

                JLabel resultsFS = new JLabel("Results");
                resultsFS.setForeground(Color.BLUE);
                resultsFS.setBorder(borderLabels);

//                viewResults2.addActionListener(e -> {
//
//                    if(!) && teacherNameTxtField.getText().equals(teacherName)) {
//                        try {
//                            database.UpdateStudent(Main.connection, Double.parseDouble(gradeTxtField.getText()),
//                                    pass, resit);
//                        } catch (SQLException throwables) {
//                            throwables.printStackTrace();
//                        }
//
//                        table.setValueAt(gradeTxtField.getText(), table.getSelectedRow(), 2);
//
//                        editDialog.dispose();
//                    }
//                });

            }
        });

    }
}

