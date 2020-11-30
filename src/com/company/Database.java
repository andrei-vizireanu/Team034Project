package com.company;

import java.sql.*;

public class Database {

    //checking the credentials by username and password
    public boolean checkCredentials(Statement statement, String username, String password) throws SQLException {

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
    public String getRole(Statement statement, String username, String password) throws SQLException {

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

    //getting the info for all of the users
    public String[][] getInfoUser(Statement statement) throws SQLException {

        String sql = ("SELECT * FROM User;");
        ResultSet rs = statement.executeQuery(sql);
        rs.last();
        String[][] data = new String[rs.getRow()][];
        int i = 0;
        //int j = 0;

        rs.beforeFirst();
        while(rs.next()) {

            String user = rs.getString("UserName");
            String pass = rs.getString("Password");
            String title = rs.getString("Title");
            String forename = rs.getString("Forename");
            String surname = rs.getString("Surname");
            String email = rs.getString("Email");
            String role = rs.getString("Role");

            data[i] = new String[]{user, pass, title, forename, surname, email, role};
            i++;
        }

        return data;

    }

    //deleting a user by its ID
    public void delete(Connection connection, int userID) throws SQLException {
        String sql = "DELETE FROM User WHERE UserID = ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, userID);
            // execute the delete statement
            pstmt.executeUpdate();


    }

    //closing the connection to the database
    public void close(Statement statement, Connection connection) {
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
