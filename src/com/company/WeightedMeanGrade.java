package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

public class WeightedMeanGrade {

    //variables
    private JFrame frame;
    private JPanel buttonsPanel;
    private Container mainContainer;
    private JTable table;
    private JButton calculateBtn;
    private JButton goBackBtn;
    private Database database;
    private final int width = 800;
    private final int height = 600;

    public WeightedMeanGrade(String title) throws SQLException {

        //initializing the variables
        frame = new JFrame(title);
        buttonsPanel = new JPanel();
        mainContainer = new Container();
        calculateBtn = new JButton("Calculate the mean grade");
        goBackBtn = new JButton("Go back");
        database = new Database();

        String[] columnNames = {"Grading ID", "Module Code", "Username", "Teacher name",
                "Grade", "Registration Number", "Pass", "Module ID"};
        String[][] rows = database.getStudentInfo(Main.statement);

        TableModel tableModel = new DefaultTableModel(rows, columnNames);
        table = new JTable(tableModel);

        //when clicking this button, a new window should open
        calculateBtn.addActionListener(ae -> {

            //an existing row in the table is clicked
            if (table.getSelectedRowCount() == 1) {

                //declaring and initializing the objects, setting the layout of the container
                //and info panel, getting the value from the rows that contain the module
                //code and the reg number, creating label object and fields for each value
                Container calculateContainer = new Container();
                JFrame dialogFrame = new JFrame();
                JPanel infoPanel = new JPanel();
                JPanel buttonsPanel = new JPanel();
                JDialog calculateDialog = new JDialog(dialogFrame, "Calculate");
                JButton calculateGradeBtn = new JButton("Calculate");
                JButton cancelBtn = new JButton("Cancel");
                EmptyBorder borderLabels = new EmptyBorder(15, 0, 0, 0);
                JScrollPane dialogScroll = new JScrollPane(infoPanel);

                calculateContainer.setLayout(new BorderLayout());
                infoPanel.setLayout(new GridLayout(14, 2, 10, 10));

                String moduleCode = (String) table.getValueAt(table.getSelectedRow(), 1);
                String regNo = (String) table.getValueAt(table.getSelectedRow(), 5);

                JLabel calculateLbl = new JLabel("Calculate");
                calculateLbl.setForeground(Color.BLUE);
                calculateLbl.setBorder(borderLabels);

                JTextField regNoTxtField = new JTextField(regNo);
                JTextField moduleCodeTxtField = new JTextField(moduleCode);

                //when the calculate button is pressed, the teacher should type in the reg no
                //and the module for which they want to see the weighted mean grade
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

                //close the window when pressing the cancel button
                cancelBtn.addActionListener(e -> {
                    calculateDialog.dispose();
                });

                //adding the fields and labels to the information panel, as well as the buttons
                //to the button panel, setting the panel's layout and then adding everything to
                //the calculate dialog
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

        //action listener for the go back button
        goBackBtn.addActionListener(ae -> {

            Teacher teacher = new Teacher("Teacher");
            frame.dispose();
        });

        //creating the scroll pane so that the table is scrollable, then adding the buttons
        //to the buttons panel, setting the layout for the main container and adding everything
        //to the frame, allowing it to be visible eventually
        JScrollPane scrollPane = new JScrollPane(table);

        buttonsPanel.add(calculateBtn);
        buttonsPanel.add(goBackBtn);

        mainContainer.setLayout(new BorderLayout());

        mainContainer.add(scrollPane, BorderLayout.NORTH);
        mainContainer.add(buttonsPanel, BorderLayout.CENTER);

        MyFrame.centreWindow(frame, width, height);

        frame.getContentPane().add(mainContainer);

        frame.setSize(width, height);

        frame.setVisible(true);
    }

}
