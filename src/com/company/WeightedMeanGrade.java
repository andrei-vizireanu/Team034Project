package com.company;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;

public class WeightedMeanGrade {

    //variables
    private JFrame frame;
    private JPanel buttonsPanel;
    private Container mainContainer;
    private JTable table;
    private TableModel tableModel;
    private JButton calculate;
    private Database database;
    private final int width = 800;
    private final int height = 600;

    public WeightedMeanGrade(String title) throws SQLException {

        frame = new JFrame(title);
        buttonsPanel = new JPanel();
        mainContainer = new Container();
        calculate = new JButton("Calculate the mean grade");
        database = new Database();

        String[] columnNames = {" "};
    }

}
