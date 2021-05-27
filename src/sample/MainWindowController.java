package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;


public class MainWindowController {
    @FXML
    Button logoutButton;
    @FXML
    Button viewUsersButton;
    @FXML
    Button viewBooksButton;
    @FXML
    Button viewIssuedBooksButton;
    @FXML
    Button issueBookButton;
    @FXML
    Button addUserButton;
    @FXML
    Button addBookButton;
    @FXML
    Button returnBookButton;
    @FXML
    Button deleteUserButton;
    @FXML
    Button deleteBookButton;
    @FXML
    TableView tableView;

    //list of all the users from the table
    static ObservableList<User> userList = FXCollections.observableArrayList();

    //list of all the books from the table
    static ObservableList<Book> bookList = FXCollections.observableArrayList();

    //list of all the issues from the table
    static ObservableList<Issue> issuesList = FXCollections.observableArrayList();

    //list of all the issued books
    static ObservableList<Book> issuedBooksList = FXCollections.observableArrayList();



    @FXML
    public void initialize() throws SQLException {

        getUserList();
        getBookList();
        getIssuesList();
        issuedBooksList.clear();

        //updating users from user list if they have issued books
        for (int i = 0; i < issuesList.size(); i++) {
            Book tempBook = null;

            //creating a temp book object
            for (int j = 0; j < bookList.size(); j++) {
                tempBook = bookList.get(j);
                if (issuesList.get(i).getBook_id() == bookList.get(j).getID() && findInIssuedBooksList(tempBook.getID()) == null) {
                    tempBook.setIssueDate(issuesList.get(i).getIssueDate());
                    tempBook.setReturnDate(issuesList.get(i).getReturnDate());
                    tempBook.setIssuedByID(issuesList.get(i).getUser_id());
                    String name = "/";
                    for (User user : userList) {
                        if (user.getID() == issuesList.get(i).getUser_id()) {
                            name = user.getName();
                        }
                    }
                    tempBook.setIssuedByName(name);
                    issuedBooksList.add(tempBook);
                }
            }
            //Finding users from the issuesList by comparing IDs
            for (int k = 0; k < userList.size(); k++) {
                Issue tempIssue = issuesList.get(i);
                User tempUser = userList.get(k);
                for (Book book : bookList) {
                    if (book.getID() == tempIssue.getBook_id()) {
                        tempBook = book;
                    }
                }

                //Updating users from the userList if they match
                if (tempIssue.getUser_id() == tempUser.getID()) {
                    userList.get(k).setIssuedBook(new Book(tempBook.getName(),
                            tempBook.getAuthor(), tempBook.getID()));
                    userList.get(k).setIssueDate(tempIssue.getIssueDate());
                    userList.get(k).setReturnDate(tempIssue.getReturnDate());
                    userList.get(k).setIssuedBookName(tempBook.getName());
                }
            }
        }
    }

    @FXML
    public void onButtonClicked(ActionEvent event) throws IOException, SQLException {
        //Return to the login screen
        if (event.getSource().equals(logoutButton)) {
            Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(parent, 350, 600);
            stage.setScene(scene);
            stage.show();
        }

        //Showing all of the users on the tableview
        else if (event.getSource().equals(viewUsersButton)) {

            //clearing the screen by removing all items and columns
            tableView.getItems().removeAll();
            tableView.getColumns().clear();

            //name column
            TableColumn<User, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setMinWidth(80);
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            //email column
            TableColumn<User, String> emailColumn = new TableColumn<>("Email Address");
            emailColumn.setMinWidth(80);
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email_address"));

            //issued book column
            TableColumn<User,String> bookIssuedColumn = new TableColumn<>("Book Issued");
            bookIssuedColumn.setMinWidth(80);
            bookIssuedColumn.setCellValueFactory(new PropertyValueFactory<>("issuedBookName"));

            //id column
            TableColumn<User, Integer> IDColumn = new TableColumn<>("ID");
            IDColumn.setMinWidth(80);
            IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

            tableView.setItems(userList);
            tableView.getColumns().addAll(nameColumn, emailColumn, IDColumn, bookIssuedColumn);
        }

        //showing all of the books on the tableview
        else if (event.getSource().equals(viewBooksButton)){

            //clearing the tableview
            tableView.getItems().removeAll();
            tableView.getColumns().clear();

            //name column
            TableColumn<Book, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            //author column
            TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
            authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

            //IssuedBy column
            TableColumn<Book, String> issuedByColumn = new TableColumn<>("Issued By");
            issuedByColumn.setCellValueFactory(new PropertyValueFactory<>("issuedByName"));

            //ID column
            TableColumn<Book, Integer> IDColumn = new TableColumn<>("ID");
            IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

            tableView.setItems(bookList);
            tableView.getColumns().addAll(nameColumn, authorColumn, issuedByColumn, IDColumn);


        }

        //Showing all of the issued books on the tableview
        else if (event.getSource().equals(viewIssuedBooksButton)){
            tableView.getItems().removeAll();
            tableView.getColumns().clear();


            //name column
            TableColumn<Book, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            //author column
            TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
            authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

            //ID column
            TableColumn<Book, Integer> IDColumn = new TableColumn<>("ID");
            IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

            //issuedDate column
            TableColumn<Book, Date> issuedDateColumn = new TableColumn<>("Date Issued");
            issuedDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));

            //returnDate column
            TableColumn<Book, Date> returnDateColumn = new TableColumn<>("Return Date");
            returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

            tableView.setItems(issuedBooksList);
            tableView.getColumns().addAll(nameColumn, authorColumn, issuedDateColumn,returnDateColumn,IDColumn);
        }
        //Issue a book
        else if (event.getSource().equals(issueBookButton)){
            Parent parent = FXMLLoader.load(getClass().getResource("issueBook.fxml"));
            Scene scene = new Scene(parent,300,350);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        //Adding user
        //otvoriti novi stage koji će primati name, email_address
        //
        else if (event.getSource().equals(addUserButton)){
            Parent parent = FXMLLoader.load(getClass().getResource("addUser.fxml"));
            Scene scene = new Scene(parent,300,350);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        else if (event.getSource().equals(addBookButton)){
            Parent parent = FXMLLoader.load(getClass().getResource("addBook.fxml"));
            Scene scene = new Scene(parent,300,350);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        else if (event.getSource().equals(returnBookButton)){
            Parent parent = FXMLLoader.load(getClass().getResource("returnBook.fxml"));
            Scene scene = new Scene(parent,300,300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        else if (event.getSource().equals(deleteUserButton)){
            Parent parent = FXMLLoader.load(getClass().getResource("deleteUser.fxml"));
            Scene scene = new Scene(parent,300,300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
        else if (event.getSource().equals(deleteBookButton)){
            Parent parent = FXMLLoader.load(getClass().getResource("deleteBook.fxml"));
            Scene scene = new Scene(parent,300,300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
    }


    //adding users from the table to the observable arraylist
    public void getUserList() throws SQLException {
        ResultSet myRs = MyConnection.getUsers();
        userList.clear();
        while (myRs.next()) {
            int ID = myRs.getInt(1);
            String name = myRs.getString(2);
            String email_address = myRs.getString(3);
            User tempUser = new User(ID, name, email_address);
            userList.add(tempUser);
        }

        }

    //adding books from the table to the observable arraylist
    public void getBookList() throws SQLException {
        ResultSet myRs = MyConnection.getBooks();
        bookList.clear();
        while (myRs.next()) {
                int ID = myRs.getInt(1);
                String name = myRs.getString(2);
                String author = myRs.getString(3);
                Book tempBook = new Book(name, author, ID);
                bookList.add(tempBook);
        }
    }


    //adding issues from the table to the observable arraylist
    public void getIssuesList() throws SQLException {
        ResultSet myRs = MyConnection.getIssues();
        issuesList.clear();
        while (myRs.next()) {
            int user_id = myRs.getInt(1);
            int book_id = myRs.getInt(2);
            Date issueDate = myRs.getDate(3);
            Date returnDate = myRs.getDate(4);
            LocalDate issuedLocalDate = java.sql.Date.valueOf(String.valueOf(issueDate)).toLocalDate();
            LocalDate returnLocalDate = java.sql.Date.valueOf(String.valueOf(returnDate)).toLocalDate();
            issuesList.add(new Issue(user_id,book_id,issuedLocalDate,returnLocalDate));
        }
    }
    public User findInUserList(int userID){
        User user1 = null;
        for (User user : userList){
            if (user.getID() == userID){
               user1 = user;
            }
        }
        return user1;
    }
    public Book findInBookList(int id){
        Book book = null;
        for (int i = bookList.size() -1; i >= 0; i--){
            if (bookList.get(i).getID() == id){
               book = bookList.get(i);
            }
        }
        return book;
    }
    public Issue findInIssuesList(int ID){
        Issue issue = null;
        for (int i = 0; i<issuesList.size(); i++){
            if (issuesList.get(i).getUser_id() == ID){
                issue = issuesList.get(i);
            }
        }
        return issue;
    }


    public Book findInIssuedBooksList(int bookID){
        Book book1 = null;
        for (Book book : issuedBooksList){
            if (book.getID() == bookID){
                book1 = book;
            }
        }
        return book1;
    }
}
