package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class WeightedMeanGrade {

    //variables
    private JFrame frame;
    private JPanel buttonsPanel;
    private Container mainContainer;
    private JTable table;
    private TableModel tableModel;
    private JButton calculateBtn;
    private JButton goBackBtn;
    private Database database;
    private final int width = 800;
    private final int height = 600;

    public WeightedMeanGrade(String title) throws SQLException {

        frame = new JFrame(title);
        buttonsPanel = new JPanel();
        mainContainer = new Container();
        calculateBtn = new JButton("Calculate the mean grade");
        database = new Database();

        String[] columnNames = {"Grading ID", "Module Code", "Username", "Teacher name",
                "Grade", "Registration Number", "Pass", "Module ID"};
        String[][] rows = database.getStudentInfo(Main.statement);

        TableModel tableModel = new DefaultTableModel(rows, columnNames);
        table = new JTable(tableModel);

        calculateBtn.addActionListener(ae -> {

            if (table.getSelectedRowCount() == 1) {
                Container calculateContainer = new Container();
                JFrame dialogFrame = new JFrame();
                JPanel infoPanel = new JPanel();
                JPanel buttonsPanel = new JPanel();
                JDialog calculateDialog = new JDialog(dialogFrame, "Calculate");
                JButton calculateGradeBtn = new JButton("Calculate");
                JButton editGradeBtn = new JButton("Edit");
                JButton cancelBtn = new JButton("Cancel");
                EmptyBorder borderLabels = new EmptyBorder(15, 0, 0, 0);
                JScrollPane dialogScroll = new JScrollPane(infoPanel);

                calculateContainer.setLayout(new BorderLayout());
                infoPanel.setLayout(new GridLayout(14, 2, 10, 10));

                String gradingID = (String) table.getValueAt(table.getSelectedRow(), 0);
                String moduleCode = (String) table.getValueAt(table.getSelectedRow(), 1);
                String username = (String) table.getValueAt(table.getSelectedRow(), 2);
                String teacherName = (String) table.getValueAt(table.getSelectedRow(), 3);
                double grade = Double.parseDouble((String)table.getValueAt(table.getSelectedRow(), 4));
                String regNo = (String) table.getValueAt(table.getSelectedRow(), 5);
                boolean pass = Boolean.parseBoolean((String)table.getValueAt(table.getSelectedRow(), 6));
                String moduleID = (String)table.getValueAt(table.getSelectedRow(), 7);

                JLabel calculateLbl = new JLabel("Calculate");
                calculateLbl.setForeground(Color.BLUE);
                calculateLbl.setBorder(borderLabels);

                JTextField regNoTxtField = new JTextField(regNo);
                JTextField moduleCodeTxtField = new JTextField(moduleCode);

                calculateGradeBtn.addActionListener(e -> {

                    double weightedGrade = 0.0;

                    if(!regNoTxtField.getText().isEmpty() && !moduleCodeTxtField.getText().isEmpty()) {
                        try {
                            weightedGrade = (database.CalculateGrade(Main.statement, regNoTxtField.getText(),
                                    moduleCodeTxtField.getText()));
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        calculateDialog.dispose();

                        JOptionPane.showMessageDialog(calculateDialog,
                                "The weighted mean grade for Student registered with " +
                                        regNo + " is " + weightedGrade,
                                "Successfully Calculated", JOptionPane.INFORMATION_MESSAGE);
                    }
                });

                cancelBtn.addActionListener(e -> {
                    calculateDialog.dispose();
                });

                infoPanel.add(calculateLbl);
                infoPanel.add(regNoTxtField);
                infoPanel.add(moduleCodeTxtField);

                buttonsPanel.add(calculateGradeBtn);
                buttonsPanel.add(cancelBtn);

                calculateContainer.add(dialogScroll, BorderLayout.NORTH);
                calculateContainer.add(buttonsPanel, BorderLayout.CENTER);

                MyFrame.centreWindow(calculateDialog, 500, 700);

                calculateDialog.getContentPane().add(calculateContainer);

                calculateDialog.setSize(500, 700);

                calculateDialog.setResizable(false);

                calculateDialog.setVisible(true);
            }
            else
            {
                if(table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(frame,
                            "The table is empty",
                            "Empty table",
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

        JScrollPane scrollPane = new JScrollPane(table);

        buttonsPanel.add(calculateBtn);

        mainContainer.setLayout(new BorderLayout());

        mainContainer.add(scrollPane, BorderLayout.NORTH);
        mainContainer.add(buttonsPanel, BorderLayout.CENTER);

        MyFrame.centreWindow(frame, width, height);

        frame.getContentPane().add(mainContainer);

        frame.setSize(width, height);

        frame.setVisible(true);
    }

}
