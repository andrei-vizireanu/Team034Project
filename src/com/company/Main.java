package com.company;
import java.sql.*;

public class Main {
    private static Connection con = null;
    private static Statement stmt = null;
    private static Statement stmt2 = null;

    public static void main(String[] args) {

        //connect to database
        connectionToDatabase();

        //open the Window
        MyFrame frame = new MyFrame("Login Page");

    }

    private static void connectionToDatabase(){

        // creating the Connection and Statement objects


        try {
            //making the connection to the database
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team034", "team034", "4228b661");
            Statement stmt = con.createStatement();
            stmt2 = stmt;

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

    public static Statement getConnection(){
        System.out.println(stmt2);
        return stmt2;

    }

}
