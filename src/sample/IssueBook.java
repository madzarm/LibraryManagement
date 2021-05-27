package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class IssueBook {
    @FXML
    TextField userIDTextField;
    @FXML
    TextField bookIDTextField;
    @FXML
    Button issueButton;
    @FXML
    Label warningLabel1;
    @FXML
    Label warningLabel2;
    @FXML
    DatePicker datePicker;

    MainWindowController ctrl = new MainWindowController();

    ObservableList<User> userList = MainWindowController.userList;
    ObservableList<Book> bookList = MainWindowController.bookList;

    //ID lists
    ArrayList<Integer> userIDList = new ArrayList<>();
    ArrayList<Integer> bookIDList = new ArrayList<>();

    @FXML
    public void initialize() {

        //Disabling future dates in the date picker
        datePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate localDate, boolean empty) {
                super.updateItem(localDate, empty);
                setDisable(empty || localDate.compareTo(LocalDate.now()) < 0);
            }
        });

        //Populating ID lists
        for (User user : userList) {
            userIDList.add(user.getID());
        }
        for (Book book : bookList) {
            bookIDList.add(book.getID());
        }
    }

    @FXML
    public void onButtonClicked(ActionEvent event) throws SQLException {
        try {
            if (event.getSource().equals(issueButton)) {

                //Resetting Label after each Button click
                warningLabel1.setTextFill(Color.rgb(46, 64, 83));
                warningLabel2.setTextFill(Color.rgb(46, 64, 83));

                int userID = Integer.parseInt(userIDTextField.getText());

                //Checking if user has already borrowed a book
                ResultSet myUserRs = MyConnection.checkUserInIssues(userID);
                boolean close1 = false;
                boolean close2 = false;
                boolean close3 = false;
                boolean close4 = false;

                //Checking if the result set is empty - user hasn't borrowed a book
                if (!myUserRs.next()) {
                    close1 = true;
                } else {
                    warningLabel1.setText("That user already has a book");
                    warningLabel1.setTextFill(Color.WHITE);
                }
                myUserRs.close();

                //Checking if the user exists
                if (userIDList.contains(userID)) {
                    close3 = true;
                } else {
                    warningLabel1.setText("That user does not exist");
                    warningLabel1.setTextFill(Color.WHITE);
                }

                int bookID = Integer.parseInt(bookIDTextField.getText());

                //Checking if the book is borrowed
                ResultSet myBookRs = MyConnection.checkBookInIssues(bookID);

                //Checking if the result set is empty - book has not been borrowed
                if (!myBookRs.next()) {
                    close2 = true;
                } else {
                    warningLabel2.setText("That book is already issued");
                    warningLabel2.setTextFill(Color.WHITE);
                }

                //Checking if the book exists
                if (bookIDList.contains(bookID)) {
                    close4 = true;
                } else {
                    warningLabel2.setText("That book does not exist");
                    warningLabel2.setTextFill(Color.WHITE);
                }

                //Closing if both exist and are not in issues
                if (close1 && close2 && close3 && close4) {
                    LocalDate issueDate = LocalDate.now();
                    MyConnection.newIssue(userID, bookID, issueDate, datePicker.getValue());
                    Stage stage = (Stage) issueButton.getScene().getWindow();
                    stage.close();
                    ctrl.initialize();
                }
            }
        } catch (Exception e) {
            warningLabel1.setText("Invalid input");
            warningLabel1.setTextFill(Color.WHITE);

        }
    }
}
