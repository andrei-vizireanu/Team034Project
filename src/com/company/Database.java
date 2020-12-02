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

            String id = rs.getString("UserID");
            String user = rs.getString("Username");
            String pass = rs.getString("Password");
            String title = rs.getString("Title");
            String forename = rs.getString("Forename");
            String surname = rs.getString("Surname");
            String email = rs.getString("Email");
            String role = rs.getString("Role");

            data[i] = new String[]{id, user, pass, title, forename, surname, email, role};
            i++;
        }

        return data;

    }

    //updating the user with the new info
    public void updateUser(Connection connection, String username, String password, String title,
            String forename, String surname, String email, String role, String id) throws SQLException {

        String sql = "UPDATE User SET Username = ?, " +
                "Password = ?, " +
                "Title = ?, " +
                "Forename = ?, " +
                "Surname = ?, " +
                "Email = ?, " +
                "Role = ? " + "WHERE UserID = ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);

        // set the corresponding param
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        pstmt.setString(3, title);
        pstmt.setString(4, forename);
        pstmt.setString(5, surname);
        pstmt.setString(6, email);
        pstmt.setString(7, role);
        pstmt.setString(8, id);

        // update
        pstmt.executeUpdate();

    }

    public String[][] getStudentInfo(Statement statement) throws SQLException {

        String sql = ("SELECT * FROM Grading;");
        ResultSet rs = statement.executeQuery(sql);
        rs.last();
        String[][] data = new String[rs.getRow()][];
        int i = 0;

        rs.beforeFirst();
        while(rs.next()) {

            String moduleCode = rs.getString("ModuleCode");
            String teacher = rs.getString("Teacher");
            String grade = rs.getString("Grade");
            String regNo = rs.getString("RegNo");
            String pass = String.valueOf(rs.getInt("pass"));
            String resit = String.valueOf(rs.getDouble("resit"));

            data[i] = new String[]{moduleCode, teacher, grade, regNo, pass, resit};
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
