package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ReturnBook {

    @FXML
    Button returnBookButton;
    @FXML
    Label warningLabel1;
    @FXML
    Label warningLabel2;
    @FXML
    TextField userIDTextField;
    MainWindowController ctrl = new MainWindowController();

    @FXML
    public void onButtonClicked(ActionEvent event){
        if (event.getSource().equals(returnBookButton)){
            try {
                warningLabel1.setTextFill(Color.rgb(46, 64, 83));
                warningLabel2.setTextFill(Color.rgb(46, 64, 83));

                String s = userIDTextField.getText();
                s.trim();
                int ID = Integer.parseInt(userIDTextField.getText());
                boolean userExists = false;
                boolean userHasIssue = false;
                if (ctrl.findInUserList(ID) != null){
                    userExists = true;
                }
                if (ctrl.findInIssuesList(ID) != null){
                    userHasIssue = true;
                }

                if (!s.isEmpty() && userExists && userHasIssue){
                    MyConnection.removeIssue(ID);
                    ctrl.initialize();
                    Stage stage = (Stage) returnBookButton.getScene().getWindow();
                    stage.close();

                }
                else {
                    if (s.isEmpty()){
                        warningLabel1.setText("You left it blank");
                        warningLabel1.setTextFill(Color.WHITE);

                    }
                    else if (!userExists){
                        warningLabel1.setText("User with that ID does not exist");
                        warningLabel1.setTextFill(Color.WHITE);

                    }
                    else {
                        warningLabel1.setText("User does not have an issue");
                        warningLabel1.setTextFill(Color.WHITE);
                    }

                }
            }
            catch (Exception e){
                warningLabel2.setText("Invalid Input");
                warningLabel2.setTextFill(Color.WHITE);
                e.printStackTrace();
            }
        }
    }
}
