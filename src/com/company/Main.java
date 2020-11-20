package com.company;
import java.sql.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("\nDrivers loaded as properties:");
        System.out.println(System.getProperty("jdbc.drivers"));
        System.out.println("\nDrivers loaded by DriverManager:");
        Enumeration<Driver> list = DriverManager.getDrivers();
        while (list.hasMoreElements())

            System.out.println(list.nextElement());

        Connection con = null; // a Connection object
        Statement stmt = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team034", "team034", "4228b661");
            // use the open connection
            // for several queries

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    con.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(con!=null)
                    con.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

        MyFrame frame = new MyFrame("Login Page");

    }
}
