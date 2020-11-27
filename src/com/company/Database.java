package com.company;

import java.sql.*;

public class Database {

    private static Connection connection = null;
    private static Statement statement = null;

    //Database constructor
    public Database(){

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

    //checking the credentials by username and password
    public boolean checkCredentials(String username, String password) throws SQLException {

        String sql = ("SELECT * FROM User;");
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()) {

            String user = rs.getString("UserName");
            String pass = rs.getString("Password");

            if(username.equals(user) && password.equals(pass))
                return true;

        }

        return false;

    }

    //getting the role of a user by username and password
    public String getRole(String username, String password) throws SQLException {

        String sql = ("SELECT * FROM User;");
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()) {

            String user = rs.getString("UserName");
            String pass = rs.getString("Password");
            String role = rs.getString("Role");

            if(username.equals(user) && password.equals(pass))
                return role;

        }

        return null;

    }

    //deleting a user by its ID
    public void delete(int userID) throws SQLException {
        String sql = "DELETE FROM User WHERE UserID = ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, userID);
            // execute the delete statement
            pstmt.executeUpdate();


    }

    //closing the connection to the database
    public void close() {
        try {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

}
