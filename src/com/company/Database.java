package com.company;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Arrays;

public class Database {

    //checking the credentials by username and password
    public boolean checkCredentials(Statement statement, String username, String password) throws SQLException {

        try {

            String sql = "SELECT * FROM User WHERE Username = '" + username + "'";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {

                String user = rs.getString("UserName");
                String pass = rs.getString("Password");
                String salt = rs.getString("PasswordSalt");

                boolean passwordMatch = PasswordHashingUtilityFunction.verifyUserPassword(password, pass, salt);

                if (passwordMatch && username.equals(user)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println("checkCredentials method from Database class " + e);
        }
        return false;
    }

    //getting the role of a user by username and password
    public String getRole(Statement statement, String username){

        String sql = ("SELECT * FROM User;");

        try {
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {

                String user = rs.getString("UserName");
                String role = rs.getString("Role");

                if(username.equals(user))
                    return role;

            }
        }catch (SQLException e){
            System.out.println("getRole method from Database class " + e);
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

                data[0][i] = id;
                data[1][i] = degreeName;
                i++;
            }
        }catch (SQLException e){
            System.out.println("checkCredentials method from Database class " + e);
        }

        return data;

    }

    //getting the info for all of the users
    public String[][] getInfoUser(Statement statement){

        String sql = ("SELECT * FROM User;");
        try {
            ResultSet rs = statement.executeQuery(sql);
            rs.last();
            String[][] data = new String[rs.getRow()][];
            int i = 0;

            rs.beforeFirst();
            while(rs.next()) {

                String id = rs.getString("UserID");
                String user = rs.getString("Username");
                String title = rs.getString("Title");
                String forename = rs.getString("Forename");
                String surname = rs.getString("Surname");
                String email = rs.getString("Email");
                String role = rs.getString("Role");

                data[i] = new String[]{id, user, title, forename, surname, email, role};
                i++;
            }
            return data;
        }catch (SQLException e){
            System.out.println("getInfoUser method from Database class " + e);
        }

        return null;
    }

    //getting the info for all of the departments
    public String[][] getInfoDepartment(Statement statement){

        String sql = ("SELECT * FROM Department;");

        try {
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

        }catch (SQLException e){
            System.out.println("getInfoDepartment method from Database class " + e);
        }

        return null;
    }

    //getting the info for all of the modules
    public String[][] getInfoModule(Connection connection, Statement statement){

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

    //getting the info for all of the degree courses
    public String[][] getInfoDegree(Statement statement){

        String sql = ("SELECT * FROM Degree;");

        try{
            ResultSet rs = statement.executeQuery(sql);
            rs.last();
            String[][] data = new String[rs.getRow()][];
            int i = 0;
            rs.beforeFirst();

            while(rs.next()) {

                String id = rs.getString("DegreeID");
                String degCode = rs.getString("DegreeCode");
                String degName = rs.getString("DegreeName");
                String degPartner = rs.getString("Partner");
                String degLead = rs.getString("Lead");

                data[i] = new String[]{id, degCode, degName, degPartner, degLead};
                i++;
            }
            return data;
        }catch (SQLException e){
            System.out.println("getInfoDegree method from Database" + e);
        }

        return null;
    }

    //getting degree names by using the ids
    public String getDegreeNameByID(Statement statement, String degreeID){

        String sql2 = "SELECT * FROM Degree WHERE DegreeID = " + degreeID;
        try {
            ResultSet rs = statement.executeQuery(sql2);

            while(rs.next()) {
                String degreeName = rs.getString("DegreeName");
                String degreeID2 = rs.getString("DegreeID");

                if(degreeID2.equals(degreeID)){
                    return degreeName;
                }

            }
            return null;
        }catch (SQLException e){
            System.out.println("getDegreeNameByID method from Database" + e);
        }

        return null;
    }

    //getting degree names by using the ids
    public String[] getDepartmentCodes(Statement statement){

        String sql2 = "SELECT * FROM Department";

        try{
            ResultSet rs = statement.executeQuery(sql2);
            rs.last();
            String[] data = new String[rs.getRow()];
            int i = 0;
            rs.beforeFirst();

            while(rs.next()) {
                String degreeName = rs.getString("DepartmentCode");
                data[i] = degreeName;
                i++;
            }

            return data;

        }catch (SQLException e){
            System.out.println("getDepartmentCodes method from Database" + e);
        }

        return null;
    }

    //updating the user with the new info
    public void updateUser(Connection connection, String username, String title,
            String forename, String surname, String email, String role, String id){

        String sql = "UPDATE User SET Username = ?, " +
                "Title = ?, " +
                "Forename = ?, " +
                "Surname = ?, " +
                "Email = ?, " +
                "Role = ? " + "WHERE UserID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, username);
            pstmt.setString(2, title);
            pstmt.setString(3, forename);
            pstmt.setString(4, surname);
            pstmt.setString(5, email);
            pstmt.setString(6, role);
            pstmt.setString(7, id);

            // update
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("updateUser method from Database" + e);
        }

    }

    //updating the degree with the new info
    public void updateDegree(Connection connection, String degCode, String degName, String partner, String lead, String id){

        String sql = "UPDATE Degree SET DegreeCode = ?, " +
                "DegreeName = ?, " +
                "Partner = ?, " +
                "Lead = ? " + "WHERE DegreeID = ?";

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // set the corresponding param
            pstmt.setString(1, degCode);
            pstmt.setString(2, degName);
            pstmt.setString(3, partner);
            pstmt.setString(4, lead);
            pstmt.setString(5, id);

            // update
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("updateDegree method from Database class " + e);
        }

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
            System.out.println("updateDepartment method from Database class " + e);
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

    public String[][] getStudentInfo(Statement statement){

        String sql = ("SELECT * FROM Grading;");
        try {
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
        }catch (SQLException e){
            System.out.println("getStudentInfo method from Database class" + e);
        }

        return null;
    }


    public void UpdateStudent(Connection connection, double grade,
                              boolean pass, String regNo){

        String sql = "UPDATE Grading SET grade = ?, " +
                "pass = ? " +
                "WHERE RegNo = ?";

        try {
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
        }catch (SQLException e){
            System.out.println("UpdateStudent method from Database class" + e);
        }

    }

    public double CalculateGrade(Statement statement, String regNo, String moduleCode){

        String sql = ("SELECT * FROM Grading;");
        try {
            ResultSet rs = statement.executeQuery(sql);
            rs.last();
            double[] data = new double[rs.getRow()];
            int i = 0;
            int len=0;

            rs.beforeFirst();
            while (rs.next()) {

                String regNumber = rs.getString("RegNo");
                String modCode = rs.getString("ModuleCode");
                double grade = rs.getDouble("grade");

                if (regNumber.equals(regNo) && modCode.equals(moduleCode)) {
                    data[i] = grade;
                    len++;
                }
                i++;
            }

            double sum = 0;
            int numberOfGrades = 0;
            for (int j=0; j<len; j++) {
                sum = sum + data[j];
                numberOfGrades++;
            }

            return sum/numberOfGrades;

        }catch (SQLException e){
            System.out.println("CalculateGrade method from Database class" + e);
        }

        return 0.0;
    }

    //getting the user id
    public String getUserID(Statement statement, String userName, String forName, String surName){

        String sql = ("SELECT * FROM User;");

        try {
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {

                String id = rs.getString("UserID");
                String username = rs.getString("Username");
                String foreame = rs.getString("Forename");
                String surname = rs.getString("Surname");
                //String pass = rs.getString("Password");

                if(username.equals(userName) && foreame.equals(forName) && surname.equals(surName)){
                    return id;
                }

            }
        } catch (SQLException throwables) {
            System.out.println("getUserID method from the Database" + throwables);
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
            System.out.println("getDepartmentID method from Database class " + throwables);
        }

        return null;
    }

    //getting the department id
    public String getDegreeID(Statement statement, String degreeCode, String degreeName, String partner, String lead){

        String sql = ("SELECT * FROM Degree;");

        try{
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {

                String depID = rs.getString("DegreeID");
                String degCode = rs.getString("DegreeCode");
                String degName = rs.getString("DegreeName");
                String partner1 = rs.getString("Partner");
                String lead1 = rs.getString("Lead");

                if(degCode.equals(degreeCode) && degName.equals(degreeName) && partner1.equals(partner) && lead1.equals(lead))
                    return depID;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;

    }

    //adding a user to the database
    public void addUser(Connection connection, String username, String password, String salt, String title, String forename,
                        String surname, String email, String role){

        // the mysql insert statement
        String query = " insert into User (Username, Password, PasswordSalt, Title, Forename, Surname, Email, Role)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, password);
            preparedStmt.setString(3, salt);
            preparedStmt.setString(4, title);
            preparedStmt.setString(5, forename);
            preparedStmt.setString(6, surname);
            preparedStmt.setString(7, email);
            preparedStmt.setString(8, role);

            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException throwables) {
            System.out.println("addUser method from the Database " + throwables);
        }

    }

    //adding a user(Student) to the database
    public void addUserStudent(Connection connection, String id, String regNo, String personalTutor){

        // the mysql insert statement
        String query = " insert into Student (User_ID, RegNo, PersonalTutor)"
                + " values (?, ?, ?)";

        try {
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, id);
            preparedStmt.setString(2, regNo);
            preparedStmt.setString(3, personalTutor);

            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException throwables) {
            System.out.println("addUserStudent method from the Database " + throwables);
        }

    }

    //adding a user(Student) to the database
    public void addUserTeacher(Connection connection, String id, String moduleCode){

        // the mysql insert statement
        String query = " insert into Teacher (UserID, ModuleCode)"
                + " values (?, ?)";

        try {
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, id);
            preparedStmt.setString(2, moduleCode);

            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException throwables) {
            System.out.println("addUserTeacher method from the Database " + throwables);
        }

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
            System.out.println("addDepartment method from the Database " + throwables);
        }

    }

    //adding a department to the database
    public void addDegreeCourse(Connection connection, String degreeCode, String degreeName, String partner, String lead) {

        // the mysql insert statement
        String query = " insert into Degree (DegreeCode, DegreeName, Partner, Lead)"
                + " values (?, ?, ?, ?)";

        try {
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, degreeCode);
            preparedStmt.setString(2, degreeName);
            preparedStmt.setString(3, partner);
            preparedStmt.setString(4, lead);

            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException throwables) {
            System.out.println("addDegreeCourse method from the Database " + throwables);
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
    public void deleteUser(Connection connection, String userID, String role){
        String sql = "DELETE FROM User WHERE UserID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);

            if(role.equals("Student")){

                String sql2 = "DELETE FROM Student WHERE User_ID = ?";
                PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                // set the corresponding param
                pstmt2.setInt(1, Integer.parseInt(userID));
                // execute the delete statement
                pstmt2.executeUpdate();

            }
            else if(role.equals("Teacher")){
                String sql2 = "DELETE FROM Teacher WHERE UserID = ?";
                PreparedStatement pstmt2 = connection.prepareStatement(sql2);
                // set the corresponding param
                pstmt2.setInt(1, Integer.parseInt(userID));
                // execute the delete statement
                pstmt2.executeUpdate();
            }

            // set the corresponding param
            pstmt.setInt(1, Integer.parseInt(userID));
            // execute the delete statement
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("deleteUser method from Database class " + throwables);
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
            System.out.println("deleteDepartment method from the Database " + throwables);
        }

    }

    //deleting a degree course by its ID
    public void deleteDegree(Connection connection, String degreeID){
        String sql = "DELETE FROM Degree WHERE DegreeID = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            // set the corresponding param
            pstmt.setInt(1, Integer.parseInt(degreeID));
            // execute the delete statement
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println("deleteDegree method from the Database " + throwables);
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
            System.out.println("deleteModule method from the Database " + throwables);
        }

    }

    //REGISTRAR
    //geting user table with only students
    public String[][] getStudentUser(Statement statement){

        String sql = ("SELECT * FROM User WHERE Role = 'Student';");
        try {
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

                data[i] = new String[]{id, user, pass, title, forename, surname, email,role};
                i++;
            }

            return data;
        } catch (SQLException throwables) {
            System.out.println("getStudentUser method from the Database " + throwables);
        }

        return null;
    }

    public void addStudent(Connection connection, String username, String password, String title, String forename,
                           String surname, String email, String role){

        // the mysql insert statement
        String query = " insert into User (Username, Password, Title, Forename, Surname, Email, Role)"
                + " values (?, ?, ?, ?, ?, ?,'Student')";

        try {
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, password);
            preparedStmt.setString(3, title);
            preparedStmt.setString(4, forename);
            preparedStmt.setString(5, surname);
            preparedStmt.setString(6, email);
            //preparedStmt.setString(7, role);

            // execute the preparedstatement
            preparedStmt.execute();

        } catch (SQLException throwables) {
            System.out.println("addStudent method from the Database " + throwables);
        }

    }

    //student table from db
    public String[][] getStudentTable(Statement statement){

        String sql = ("SELECT * FROM Student;");
        try {
            ResultSet rs = statement.executeQuery(sql);
            rs.last();
            String[][] data = new String[rs.getRow()][];
            int i = 0;
            //int j = 0;

            rs.beforeFirst();
            while(rs.next()) {

                String id = rs.getString("User_ID");
                String regNo = rs.getString("RegNo");
                String degreeCode = rs.getString("DegreeCode");
                String level = rs.getString("LevelOfStudy");
                String entry = rs.getString("Entry");
                String period = rs.getString("PeriodOfStudy");
                String tutor = rs.getString("PersonalTutor");

                data[i] = new String[]{id, regNo, degreeCode, level, entry, period, tutor};
                i++;
            }

            return data;
        } catch (SQLException throwables) {
            System.out.println("getStudentTable method from the Database " + throwables);
        }

        return null;
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

    public String getModuleEntryID(Statement statement, String modCode){

        String sql = ("SELECT * FROM module_entry;");

        try {

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {

                String modEntryID = rs.getString("ModuleEntryID");
                String moduleCode = rs.getString("ModuleCode");


                if(modCode.equals(moduleCode))
                    return modEntryID;


                        }

        } catch (SQLException throwables) {
            System.out.println("getModuleEntryID method from the Database " + throwables);
        }

        return null;
    }

    public String getModuleID(Statement statement, String modCode, String modName){

        String sql = ("SELECT * FROM Module;");

        try {

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {

                String moduleID = rs.getString("ModuleID");
                String moduleName = rs.getString("ModuleName");
                String moduleCode = rs.getString("ModuleCode");


                if(modCode.equals(moduleCode) && modName.equals(moduleName) )
                    return moduleID;


            }

        } catch (SQLException throwables) {
            System.out.println("getModuleID method from the Database " + throwables);
        }

        return null;
    }

    public void viewStudentModules (Statement statement, String regNo, String obligatory, int level ){

        String sql = ("SELECT ModuleCode FROM Module WHERE (Level = " + level + "); SELECT ModuleName FROM Module WHERE (Core = " + obligatory + "); " +
                "SELECT Credit FROM Module WHERE (Level = " + level + "); SELECT RegNo FROM Grading WHERE (RegNo = " + regNo + ");");
        try {
            ResultSet rs = statement.executeQuery(sql);
        } catch (SQLException throwables) {
            System.out.println("viewStudentModules method from the Database " + throwables);
        }
    }

}


