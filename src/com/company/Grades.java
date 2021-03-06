package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

public class Grades {

    //declaring the variables
    private JFrame frame;
    private JPanel btnPanel;
    private Container mainContainer;
    private JTable table;
    private JButton addGradeBtn;
    private JButton goBackBtn;
    private Database database;
    private final int width = 800;
    private final int height = 600;

    public Grades(String title){

        //initializing variables
        frame = new JFrame(title);
        btnPanel = new JPanel();
        mainContainer = new Container();
        addGradeBtn = new JButton("Add Grade");
        goBackBtn = new JButton("Go back");
        database = new Database();

        //column names
        String[] columnNames = {"Grading ID", "Module Code", "Username", "Teacher name",
                "Grade", "Registration Number", "Pass", "Module ID"};
        String[][] rows = database.getStudentInfo(Main.statement);

        TableModel tableModel = new DefaultTableModel(rows, columnNames);
        table = new JTable(tableModel);

        addGradeBtn.addActionListener(ae -> {

            if(table.getSelectedRowCount() == 1) {

                Container addGradeContainer = new Container();
                JFrame dialogFrame = new JFrame();
                JPanel infoPanel = new JPanel();
                JPanel buttonsPanel = new JPanel();
                JDialog editDialog = new JDialog(dialogFrame, "Add Grade");
                JButton addGradeBtn2 = new JButton("Add");
                JButton editGradeBtn = new JButton("Edit");
                JButton cancelBtn = new JButton("Cancel");
                EmptyBorder borderLabels = new EmptyBorder(15, 0, 0, 0);
                JScrollPane dialogScroll = new JScrollPane(infoPanel);

                addGradeContainer.setLayout(new BorderLayout());
                infoPanel.setLayout(new GridLayout(14, 2, 10, 10));


                String gradingID = (String) table.getValueAt(table.getSelectedRow(), 0);
                String moduleCode = (String) table.getValueAt(table.getSelectedRow(), 1);
                String username = (String) table.getValueAt(table.getSelectedRow(), 2);
                String teacherName = (String) table.getValueAt(table.getSelectedRow(), 3);
                double grade = Double.parseDouble((String)table.getValueAt(table.getSelectedRow(), 4));
                String regNo = (String) table.getValueAt(table.getSelectedRow(), 5);
                boolean pass = Boolean.parseBoolean((String)table.getValueAt(table.getSelectedRow(), 6));
                String moduleID = (String)table.getValueAt(table.getSelectedRow(), 7);

                JLabel gradeLbl = new JLabel("Grade");
                gradeLbl.setForeground(Color.BLUE);
                gradeLbl.setBorder(borderLabels);

                JTextField usernameTxtField = new JTextField(username);
                usernameTxtField.setEditable(false);
                JTextField gradeTxtField = new JTextField((int) grade);

                addGradeBtn2.addActionListener(e -> {

                    if (usernameTxtField.getText().equals(username))

                        if(!(Double.parseDouble(gradeTxtField.getText()) == grade)) {

                            database.UpdateStudent(Main.connection, Double.parseDouble(gradeTxtField.getText()),
                                        pass, regNo);


                            table.setValueAt(gradeTxtField.getText(), table.getSelectedRow(), 5);

                            editDialog.dispose();

                            JOptionPane.showMessageDialog(editDialog,
                                    "You updated the grade for Student registered with " +
                                            regNo, "Successfully Updated", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else {
                            JOptionPane.showMessageDialog(editDialog,
                                    "You didn't add or update a grade",
                                    "Nothing Changed", JOptionPane.ERROR_MESSAGE);
                        }

                    });

                    cancelBtn.addActionListener(e -> {
                        editDialog.dispose();
                    });

                infoPanel.add(gradeLbl);
                infoPanel.add(gradeTxtField);

                infoPanel.add(usernameTxtField);

                buttonsPanel.add(addGradeBtn2);
                buttonsPanel.add(cancelBtn);

                addGradeContainer.add(dialogScroll, BorderLayout.NORTH);
                addGradeContainer.add(buttonsPanel, BorderLayout.CENTER);

                MyFrame.centreWindow(editDialog, 500, 700);

                editDialog.getContentPane().add(addGradeContainer);

                editDialog.setSize(500, 700);

                editDialog.setResizable(false);

                editDialog.setVisible(true);

            }
            else
            {
                if (table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(frame,
                            "The table is empty",
                            "Empty Table",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(frame,
                            "You need to select the item first",
                            "Select an item",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        goBackBtn.addActionListener(ae -> {

            Teacher teacher = new Teacher("Teacher");
            frame.dispose();

        });

        table.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(table);

        btnPanel.add(addGradeBtn);
        btnPanel.add(goBackBtn);
        mainContainer.setLayout(new BorderLayout());

        mainContainer.add(scrollPane, BorderLayout.NORTH);
        mainContainer.add(btnPanel, BorderLayout.CENTER);

        MyFrame.centreWindow(frame, width, height);

        frame.getContentPane().add(mainContainer);

        frame.setSize(width, height);

        frame.setVisible(true);
    }

}
