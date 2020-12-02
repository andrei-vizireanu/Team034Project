package com.company;

import javax.swing.*;
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
    }
}
