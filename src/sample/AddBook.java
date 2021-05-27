package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddBook {

    @FXML
    Button addBookButton;
    @FXML
    TextField nameTextField;
    @FXML
    TextField authorTextField;
    @FXML
    Label warningLabel1;
    @FXML
    Label warningLabel2;


     @FXML
    public void onButtonClicked(ActionEvent event) throws SQLException {
         if (event.getSource().equals(addBookButton)) {
             try {
                 warningLabel1.setTextFill(Color.rgb(46, 64, 83));
                 warningLabel2.setTextFill(Color.rgb(46, 64, 83));

                 String name = nameTextField.getText();
                 String author = authorTextField.getText();

                 //Checking if there is input
                 nameTextField.getText().trim();
                 authorTextField.getText().trim();
                 boolean isString1 = true;
                 boolean isString2 = true;

                 //Checking if input is String
                 try {
                     int n = Integer.parseInt(name);
                     isString1 = false;
                     warningLabel2.setTextFill(Color.WHITE);
                     warningLabel2.setText("Invalid Input");
                 } catch (NumberFormatException e) {
                     isString1 = true;
                 }
                 try {
                     int n = Integer.parseInt(author);
                     isString2 = false;
                     warningLabel2.setTextFill(Color.WHITE);
                     warningLabel2.setText("Invalid Input");
                 } catch (NumberFormatException e) {
                     isString2 = true;
                 }
                 if (!nameTextField.getText().isEmpty() && !authorTextField.getText().isEmpty() && isString1 && isString2) {
                     MyConnection.newBook(name, author);
                     MainWindowController ctrl = new MainWindowController();
                     ctrl.initialize();
                     Stage stage = (Stage) addBookButton.getScene().getWindow();
                     stage.close();
                     } else {
                     warningLabel2.setTextFill(Color.WHITE);
                     warningLabel2.setText("Invalid input");
                 }
             } catch (Exception e) {
                 warningLabel2.setText("Invalid Input");
                 warningLabel2.setTextFill(Color.WHITE);
             }
         }
     }
}
