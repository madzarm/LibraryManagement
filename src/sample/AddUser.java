package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddUser {
    @FXML
    TextField nameTextField;
    @FXML
    TextField emailTextField;
    @FXML
    Button addUserButton;
    @FXML
    Label warningLabel1;
    @FXML
    Label warningLabel2;


    @FXML
    public void onButtonClicked(ActionEvent event) throws SQLException {
        if (event.getSource().equals(addUserButton)) {
            try {
                warningLabel1.setTextFill(Color.rgb(46, 64, 83));
                warningLabel2.setTextFill(Color.rgb(46, 64, 83));


                String name = nameTextField.getText();
                String email = emailTextField.getText();

                //Checking if there is input
                nameTextField.getText().trim();
                emailTextField.getText().trim();
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
                    int n = Integer.parseInt(email);
                    isString2 = false;
                    warningLabel2.setTextFill(Color.WHITE);
                    warningLabel2.setText("Invalid Input");
                } catch (NumberFormatException e) {
                    isString2 = true;
                }
                if (!nameTextField.getText().isEmpty() && !emailTextField.getText().isEmpty() && isString1 && isString2) {
                    ResultSet myRs = MyConnection.checkForUser(email);
                    if (!myRs.next()) {
                        MyConnection.newUser(name, email);
                        MainWindowController ctrl = new MainWindowController();
                        ctrl.initialize();
                        Stage stage = (Stage) addUserButton.getScene().getWindow();
                        stage.close();
                    } else {
                        warningLabel1.setText("User is already in the database");
                        warningLabel1.setTextFill(Color.WHITE);
                    }
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
