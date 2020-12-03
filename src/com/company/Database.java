package com.company;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Arrays;

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

    //getting the names of the degrees
    public String[][] getDegreeIDsNames(Statement statement) {

        String[][] data = null;
        try {
            String sql = ("SELECT * FROM Degree;");
            ResultSet rs = statement.executeQuery(sql);
            rs.last();
            data = new String[2][rs.getRow()];
            int i = 0;

            //System.out.println(data);
            rs.beforeFirst();
            while(rs.next()) {

                String id = rs.getString("DegreeID");
                String degreeName = rs.getString("DegreeName");

                //System.out.println(Arrays.deepToString(data));
                data[0][i] = id;
                data[1][i] = degreeName;
                i++;
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return data;

    }

    //getting the info for all of the users
    public String[][] getInfoUser(Statement statement) throws SQLException {

        String sql = ("SELECT * FROM User;");
        ResultSet rs = statement.executeQuery(sql);
        rs.last();
        String[][] data = new String[rs.getRow()][];
        int i = 0;

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

    //getting the info for all of the modules
    public String[][] getInfoModule(Connection connection, Statement statement) throws SQLException {

        try{
            String sql = ("SELECT * FROM Module;");
            ResultSet rs = statement.executeQuery(sql);
            rs.last();
            String[][] data = new String[rs.getRow()][];
            int i = 0;

            rs.beforeFirst();
            while(rs.next()) {

                String id = rs.getString("ModuleID");
                String moduleCode = rs.getString("ModuleCode");
                String moduleName = rs.getString("ModuleName");
                String level = rs.getString("Level");
                String core = rs.getString("Core");
                String credit = rs.getString("Credit");
                String degreeID = rs.getString("DegreeID");

                if(!degreeID.equals("0")){


                    Statement statement2 = connection.createStatement();
                    String degreeName = getDegreeNameByID(statement2, degreeID);

                    data[i] = new String[]{id, moduleCode, moduleName, level, core, credit, degreeName};
                    i++;

                    if (statement2 != null) {
                        statement2.close();
                    }

                }
                else{
                    data[i] = new String[]{id, moduleCode, moduleName, level, core, credit, "-"};
                    i++;
                }

            }
            return data;
        }catch (SQLException e){
            System.out.println("getInfoModule from Database class" + e);
        }

        return null;

    }

    public String getDegreeNameByID(Statement statement, String degreeID) throws SQLException {

        String sql2 = "SELECT * FROM Degree WHERE DegreeID = " + degreeID;
        ResultSet rs = statement.executeQuery(sql2);

        while(rs.next()) {
            String degreeName = rs.getString("DegreeName");
            String degreeID2 = rs.getString("DegreeID");

            if(degreeID2.equals(degreeID)){
                return degreeName;
            }

        }
        return null;

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

    //updating the department with the new info
    public void updateModule(Connection connection, String moduleCode, String moduleName, String level, String core,
                             String credit, String[][] IDsDegrees, String degreeSelected, String id) {

        try{
            String sql = "UPDATE Module SET ModuleCode = ?, " +
                    "ModuleName = ?, " +
                    "Level = ?, " +
                    "Core = ?, " +
                    "Credit = ?, " +
                    "DegreeID = ? " + "WHERE ModuleID = " + id;

            PreparedStatement pstmt = connection.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, moduleCode);
            pstmt.setString(2, moduleName);
            pstmt.setString(3, level);
            pstmt.setString(4, core);
            pstmt.setString(5, credit);

            String degreeID = null;

            for(int i = 0; i < IDsDegrees[1].length; i++){

                if(IDsDegrees[1][i].equals(degreeSelected)){

                    degreeID = IDsDegrees[0][i];

                }

            }

            pstmt.setString(6, degreeID);

            // update
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("updateModule method from Database class" + e);
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

    public double CalculateGrade(Statement statement, String regNo,
                                 String moduleCode) throws SQLException {

        String sql = ("SELECT grade FROM Grading WHERE (RegNo = " + regNo +
                ") AND (ModuleCode = " + moduleCode + ");");
        ResultSet rs = statement.executeQuery(sql);
        rs.last();
        double[] data = new double[rs.getRow()];
        int i = 0;

        rs.beforeFirst();
        while (rs.next()) {

            double grade = rs.getDouble("Grade");

            data[i] = grade;
            i++;
        }

        double sum = 0;
        int numberOfGrades = 0;
        for (i=0; i<data.length; i++) {
            sum = sum + data[i];
            numberOfGrades++;
        }

        return sum/numberOfGrades;
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

    //getting the module id
    public String getModuleID(Statement statement, String modCode, String modName, String level, String core, String credit,
                            String degreeID){

        String sql = ("SELECT * FROM Module;");

        try{

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {

                String moduleID = rs.getString("ModuleID");
                String moduleCode = rs.getString("ModuleCode");
                String moduleName = rs.getString("ModuleName");
                String moduleLevel = rs.getString("Level");
                String moduleCore = rs.getString("Core");
                String moduleCredit = rs.getString("Credit");
                String moduleDegreeID = rs.getString("DegreeID");

                if(moduleCode.equals(modCode) && moduleName.equals(modName) && moduleLevel.equals(level) &&
                        moduleCore.equals(core) && moduleCredit.equals(credit) && moduleDegreeID.equals(degreeID))
                    return moduleID;

            }

        } catch (SQLException throwables) {
            System.out.println("getModuleID " + throwables);
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

    //adding a department to the database
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

    //adding a department to the database
    public void addModule(Connection connection, String modCode, String modName, String level, String core, String credit,
                          String degreeTitle, String[][] IDsDegrees) {

        // the mysql insert statement
        String query = " insert into Module (ModuleCode, ModuleName, Level, Core, Credit, DegreeID)"
                + " values (?, ?, ?, ?, ?, ?)";

        try {
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, modCode);
            preparedStmt.setString(2, modName);
            preparedStmt.setString(3, level);
            preparedStmt.setString(4, core);
            preparedStmt.setString(5, credit);

            String degreeID = null;

            for(int i = 0; i < IDsDegrees[1].length; i++){

                if(IDsDegrees[1][i].equals(degreeTitle)){

                    degreeID = IDsDegrees[0][i];

                }

            }

            preparedStmt.setString(6, degreeID);

            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException throwables) {
            System.out.println("addModule method from Database class " + throwables);
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

    //deleting a module by its ID
    public void deleteModule(Connection connection, String modID){
        String sql = "DELETE FROM Module WHERE ModuleID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            // set the corresponding param
            pstmt.setInt(1, Integer.parseInt(modID));
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
