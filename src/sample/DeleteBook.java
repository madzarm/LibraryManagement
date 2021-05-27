package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DeleteBook {
    @FXML
    Button deleteBookButton;
    @FXML
    Label warningLabel1;
    @FXML
    Label warningLabel2;
    @FXML
    TextField bookIDTextField;

    MainWindowController ctrl = new MainWindowController();

    @FXML
    public void onButtonClicked(ActionEvent event){
        if (event.getSource().equals(deleteBookButton)){
            try {
                warningLabel1.setTextFill(Color.rgb(46, 64, 83));
                warningLabel2.setTextFill(Color.rgb(46, 64, 83));

                String s = bookIDTextField.getText();
                int ID = Integer.parseInt(bookIDTextField.getText());
                boolean bookExists = false;
                boolean hasIssue = false;
                s.trim();

                if (ctrl.findInBookList(ID) != null){
                    bookExists = true;
                } else{
                    warningLabel1.setTextFill(Color.WHITE);
                    warningLabel1.setText("Book does not exist");
                }
                if (ctrl.findInIssuedBooksList(ID) != null){
                    hasIssue = true;
                    warningLabel1.setTextFill(Color.WHITE);
                    warningLabel1.setText("Book is issued");
                }
                if (!hasIssue && bookExists && !s.isEmpty()){
                    MyConnection.deleteBook(ID);
                    ctrl.initialize();
                    Stage stage = (Stage) deleteBookButton.getScene().getWindow();
                    stage.close();
                }

            }catch (Exception e){
                warningLabel1.setText("Invalid input");
                warningLabel1.setTextFill(Color.WHITE);
            }
        }
    }
}
