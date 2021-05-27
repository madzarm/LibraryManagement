package sample;

import java.sql.*;
import java.time.LocalDate;


public class MyConnection {
    private static Statement statement;
    static {
        try {
            statement =DriverManager.getConnection("jdbc:mysql://localhost:3306/giraffe", "root", "eKc92Bud").createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static ResultSet checkAdmin(String username, String password) throws SQLException {
            ResultSet myRs = statement.executeQuery("SELECT * FROM admin WHERE name = " +"'" + username + "'" +
                    " AND admin_password = '" + password + "'");
            return myRs;
    }
    public static ResultSet getUsers() throws SQLException {
        ResultSet myRs = statement.executeQuery("SELECT * FROM user");
        return myRs;
    }
    public static ResultSet getBooks() throws SQLException {
        ResultSet myRs = statement.executeQuery("Select * from books");
        return myRs;
    }//"select * from issues"
    public static ResultSet getIssues() throws SQLException {
        ResultSet myRs = statement.executeQuery("select * from issues");
        return myRs;
    }
    public static ResultSet checkUserInIssues(int user_ID) throws SQLException {
        ResultSet myRs = statement.executeQuery("select * from issues where user_ID = '" + user_ID + "'");
        return  myRs;
    }
    public static ResultSet checkBookInIssues(int book_ID) throws SQLException {
        ResultSet myRs = statement.executeQuery("select * from issues where book_ID = '" + book_ID + "'");
        return  myRs;
    }
    public static ResultSet checkForUser(String email) throws SQLException {
        ResultSet myRs = statement.executeQuery("select * from user where email_address = " +
                "'" + email + "'");
        return myRs;
    }
    public static void newUser(String name, String email) throws SQLException {
        int i = statement.executeUpdate("insert into user(name, email_address) values ('" +
                name + "','" + email+"')");
    }

    public static void newBook(String name, String author) throws SQLException {
        int i = statement.executeUpdate("Insert into books(name, author) values ('" +
                name + "','" + author + "')");

    }

    public static void newIssue(int user_ID, int book_ID, LocalDate issueDate, LocalDate returnDate) throws SQLException {
        String issueDateString = issueDate.toString();
        String returnDateString = returnDate.toString();
        int i = statement.executeUpdate("Insert into issues(user_id, book_id, issueDate, returnDate)" +
                " values(" + user_ID + ", " + book_ID + ", '"+ issueDateString + "' ,'"+ returnDateString + "')");
    }

    public static void newAdmin(String name, String email_address, String admin_password) throws SQLException {
        int i = statement.executeUpdate("INSERT INTO admin(name, email_address,admin_password) VALUES (" +
                "'"+ name+ "'," +"'" + email_address + "'," + "'" + admin_password + "')" );
        System.out.println("delivered");
    }

    public static void removeIssue(int user_id) throws SQLException {
        int i = statement.executeUpdate("Delete from issues where user_id = " + user_id);
    }
    public static void deleteUser(int user_id) throws SQLException {
        int i = statement.executeUpdate("delete from user where ID = " + user_id);
    }
    public static void deleteBook(int book_id) throws SQLException {
        int i = statement.executeUpdate("delete from books where book_id =" + book_id);
    }

}
