package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class NewUserController {
    @FXML
    private Button signInButton;
    @FXML
    private Button createButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField firstPasswordField;
    @FXML
    private PasswordField secondPasswordField;
    @FXML Label passLabel;


    @FXML
    public void initialize() {
        signInButton.hoverProperty().addListener(
                (observable, oldValue, newValue) ->
                        changeButtonColor(newValue, signInButton));
        createButton.hoverProperty().addListener(
                (observable, oldValue, newValue) ->
                        changeButtonColor(newValue, createButton));
    }


    public void changeButtonColor(Boolean newValue, Button button) {
        if (button.equals(createButton)) {
            if (newValue)
                createButton.setStyle("-fx-background-radius: 30;-fx-background-color: #5DADE2;-fx-cursor: hand");
            else createButton.setStyle("-fx-background-radius: 30;-fx-background-color: #2E86C1;-fx-cursor: hand");
        } else if (button.equals(signInButton)) {
            if (newValue)
                signInButton.setStyle("-fx-background-radius: 30;-fx-background-color: #AED6F1;-fx-cursor: hand");
            else signInButton.setStyle("-fx-background-radius: 30; -fx-background-color: #5DADE2;-fx-cursor: hand");
        }

    }
    @FXML
    public void onButtonPressed(ActionEvent e) throws IOException, SQLException {
        //Loading the login screen
        if (e.getSource().equals(signInButton)) {
            Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(parent, 350, 600);
            Stage stage = (Stage) signInButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if(e.getSource().equals(createButton)){

            //Check if passwords match
            String password1 = firstPasswordField.getText();
            String password2 = secondPasswordField.getText();
            boolean isEmpty = nameTextField.getText().trim().isEmpty();
            if (password1.equals(password2) && !isEmpty){

                //Passwords match, getting input for database
                String name = nameTextField.getText();
                String email = emailTextField.getText();
                String password = firstPasswordField.getText();

                //Delivering input to database
                MyConnection.newAdmin(name,email,password);

                //Loads login window after signing up
                Parent parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
                Scene scene = new Scene(parent,350,600);
                Stage stage = (Stage)((Button)e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } else {
                passLabel.setText("Passwords do not match");
                nameTextField.setText("");
                emailTextField.setText("");
                firstPasswordField.setText("");
                secondPasswordField.setText("");
            }

        }
    }
}
