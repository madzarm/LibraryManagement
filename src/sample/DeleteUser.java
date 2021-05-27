package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DeleteUser {

    @FXML
    Button deleteUserButton;
    @FXML
    Label warningLabel1;
    @FXML
    Label warningLabel2;
    @FXML
    TextField userIDTextField;

    MainWindowController ctrl = new MainWindowController();

    @FXML
    public void onButtonClicked(ActionEvent event){
        if (event.getSource().equals(deleteUserButton)){
            try {
                warningLabel1.setTextFill(Color.rgb(46, 64, 83));
                warningLabel2.setTextFill(Color.rgb(46, 64, 83));

                String s = userIDTextField.getText();
                int ID = Integer.parseInt(userIDTextField.getText());
                boolean userExists = false;
                boolean hasIssue = false;
                s.trim();

                if (ctrl.findInUserList(ID) != null){
                    userExists = true;
                } else {
                    warningLabel1.setTextFill(Color.WHITE);
                    warningLabel1.setText("User does not exist");
                }

                if (ctrl.findInIssuesList(ID) != null){
                    warningLabel1.setText("Book has to be returned first!");
                    warningLabel1.setTextFill(Color.WHITE);
                    hasIssue = true;
                }
                if (!hasIssue && userExists && !s.isEmpty()){
                    MyConnection.deleteUser(ID);
                    ctrl.initialize();
                    Stage stage = (Stage) deleteUserButton.getScene().getWindow();
                    stage.close();
                }

            }
            catch (Exception e){
                warningLabel1.setText("Invalid input");
                warningLabel1.setTextFill(Color.WHITE);
            }
        }
    }
}
