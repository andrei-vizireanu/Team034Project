package com.company;

import javax.swing.table.DefaultTableModel;
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

    //getting the info for all of the departments
    public String[][] getInfoDepartment(Statement statement) throws SQLException {

        String sql = ("SELECT * FROM Department;");
        ResultSet rs = statement.executeQuery(sql);
        rs.last();
        String[][] data = new String[rs.getRow()][];
        int i = 0;

        rs.beforeFirst();
        while(rs.next()) {

            String id = rs.getString("DepartmentID");
            String depCode = rs.getString("DepartmentCode");
            String depName = rs.getString("DepartmentName");

            data[i] = new String[]{id, depCode, depName};
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

    //updating the department with the new info
    public void updateDepartment(Connection connection, String depCode, String depName, String id) {

        try{
            String sql = "UPDATE Department SET DepartmentCode = ?, " +
                    "DepartmentName = ? " + "WHERE DepartmentID = " + id;

            PreparedStatement pstmt = connection.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, depCode);
            pstmt.setString(2, depName);
            //pstmt.setString(3, id);

            // update
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }


    }

    public String[][] getStudentInfo(Statement statement) throws SQLException {

        String sql = ("SELECT * FROM Grading;");
        ResultSet rs = statement.executeQuery(sql);
        rs.last();
        String[][] data = new String[rs.getRow()][];
        int i = 0;

        rs.beforeFirst();
        while(rs.next()) {

            String gradingID = rs.getString("GradingID");
            String moduleCode = rs.getString("ModuleCode");
            String username = rs.getString("Username");
            String teacher = rs.getString("Teacher");
            String grade = rs.getString("Grade");
            String regNo = rs.getString("RegNo");
            String pass = String.valueOf(rs.getInt("pass"));
            String moduleID = rs.getString("ModuleID");

            data[i] = new String[]{gradingID, moduleCode, username, teacher, grade,
                    regNo, pass, moduleID};
            i++;
        }

        return data;
    }


    public void UpdateStudent(Connection connection, double grade,
                              boolean pass, String regNo) throws SQLException {

        String sql = "UPDATE Grading SET grade = ?, " +
                "pass = ? " +
                "WHERE RegNo = ?";

        PreparedStatement pstmt = connection.prepareStatement(sql);

        // set the corresponding param
        pstmt.setDouble(1, grade);

        if (grade>=70) {
            pstmt.setBoolean(2, true);
            //pstmt.setDouble(3, 0.0);
        }
        else {
            pstmt.setBoolean(2, false);
            //pstmt.setDouble(3, resit);
        }
        pstmt.setString(3, regNo);

        // update
        pstmt.executeUpdate();

    }

    //getting the user id
    public String getUserID(Statement statement, String username, String password) throws SQLException {

        String sql = ("SELECT * FROM User;");
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()) {

            String userID = rs.getString("UserID");
            String user = rs.getString("UserName");
            String pass = rs.getString("Password");

            if(username.equals(user) && password.equals(pass))
                return userID;

        }

        return null;

    }

    //getting the department id
    public String getDepartmentID(Statement statement, String depCode, String depName){

        String sql = ("SELECT * FROM Department;");

        try {

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {

                String depID = rs.getString("DepartmentID");
                String departmentCode = rs.getString("DepartmentCode");
                String departmentName = rs.getString("DepartmentName");

                if(depCode.equals(departmentCode) && depName.equals(departmentName))
                    return depID;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;

    }

    //adding a user to the database
    public void addUser(Connection connection, String username, String password, String title, String forename,
                        String surname, String email, String role) throws SQLException {

        // the mysql insert statement
        String query = " insert into User (Username, Password, Title, Forename, Surname, Email, Role)"
                + " values (?, ?, ?, ?, ?, ?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, username);
        preparedStmt.setString(2, password);
        preparedStmt.setString(3, title);
        preparedStmt.setString(4, forename);
        preparedStmt.setString(5, surname);
        preparedStmt.setString(6, email);
        preparedStmt.setString(7, role);

        // execute the preparedstatement
        preparedStmt.execute();

    }

    //adding a department to the databse
    public void addDepartment(Connection connection, String depCode, String depName) {

        // the mysql insert statement
        String query = " insert into Department (DepartmentCode, DepartmentName)"
                + " values (?, ?)";

        try {
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, depCode);
            preparedStmt.setString(2, depName);

            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //deleting a user by its ID
    public void deleteUser(Connection connection, String userID){
        String sql = "DELETE FROM User WHERE UserID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, Integer.parseInt(userID));
            // execute the delete statement
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //deleting a department by its ID
    public void deleteDepartment(Connection connection, String depID){
        String sql = "DELETE FROM Department WHERE DepartmentID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // set the corresponding param
            pstmt.setInt(1, Integer.parseInt(depID));
            // execute the delete statement
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
