package com.company;
import java.sql.*;

public class Main {

    public static void main(String[] args) {

        //connect to database
        connectionToDatabase();

        //open the Window
        MyFrame frame = new MyFrame("Login Page");

    }

    private static void connectionToDatabase(){

        // creating the Connection and Statement objects
        Connection con = null;
        Statement stmt = null;

        try {
            //making the connection to the database
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team034", "team034", "4228b661");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    con.close();
            }
            catch(SQLException se){
                // do nothing
            }
            try{
                if(con!=null)
                    con.close();
            }
            catch(SQLException se){
                se.printStackTrace();
            }

        }

    }

}
