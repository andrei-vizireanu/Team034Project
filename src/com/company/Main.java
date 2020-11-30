package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {

    public static Connection connection = null;
    public static Statement statement = null;

    public static void main(String[] args) {

        //open the Window
        MyFrame frame = new MyFrame("Login Page");
        //Administrator frame = new Administrator("Admin");

        //connecting to the database
        try {
            //making the connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team034", "team034", "4228b661");
            statement = connection.createStatement();

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
