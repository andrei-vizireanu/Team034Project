package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.sql.SQLException;

public class Grades {

    //declaring the variables

    private JFrame frame;
    private JPanel panel;
    private Container mainContainer;
    private JTable table;
    private JButton addGradeBtn;
    private Database database;
    private final int width = 800;
    private final int height = 600;

    public Grades(String title) throws SQLException {

        //initializing variables
        frame = new JFrame(title);
        panel = new JPanel();
        mainContainer = new Container();
        addGradeBtn = new JButton("Add Grade");
        database = new Database();

        //column names
        String[] columnNames = {"Module Code", "Teacher", "Grade", "Registration Number",
                            "Pass", "Resit"};
        String[][] rows = database.getStudentInfo(Main.statement);

        TableModel tableModel = new DefaultTableModel(rows, columnNames);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        mainContainer.setLayout(new BorderLayout());

        mainContainer.add(scrollPane, BorderLayout.NORTH);

        MyFrame.centreWindow(frame, width, height);

        frame.getContentPane().add(mainContainer);

        frame.setSize(width, height);

        frame.setVisible(true);

        System.out.println("Test");
    }
}
