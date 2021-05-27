package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Controller {

    @FXML
    private Label title;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private RadioButton darkModeRadioButton;
    @FXML
    private RadioButton lightModeRadioButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button closeButton;
    @FXML
    private Hyperlink myHyperlink;
    ;

    public void initialize(){
        loginButton.hoverProperty().addListener(
                (observable, oldValue, newValue) ->{
                if (newValue) loginButton.setStyle("-fx-background-radius: 30;-fx-background-color: #5DADE2;-fx-cursor: hand");
                else loginButton.setStyle("-fx-background-radius: 30;-fx-background-color: #2E86C1;-fx-cursor: hand");});

    }

    @FXML
    public void onButtonClicked(ActionEvent e) throws IOException, SQLException {
        // closing the console
        if (e.getSource().equals(closeButton)) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
        //logging in to the main screen if user found
        else if (e.getSource().equals(loginButton)){
            String username = usernameField.getText();
            String password = passwordField.getText();
            ResultSet myRs = MyConnection.checkAdmin(username,password);
            if (myRs.next()) {
                Parent parent = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
                Scene scene = new Scene(parent, 800, 600);
                Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                Rectangle2D stageScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((stageScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((stageScreenBounds.getHeight() - stage.getHeight()) / 2);

            } else System.out.println("No user found");
        }
    }
    @FXML
    public void onHyperlinkClicked() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("newUser.fxml"));
        Scene scene = new Scene(parent,350,600);
        Stage stage = (Stage) myHyperlink.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onRadioButtonClicked(ActionEvent e){
       if (e.getSource().equals(lightModeRadioButton)){
           anchorPane.setStyle("-fx-background-color: #7FB3D5");
           loginButton.setTextFill(Paint.valueOf("#2E4053"));
           loginButton.setStyle("-fx-background-radius: 30;  -fx-background-color: #1F618D;");
           darkModeRadioButton.setTextFill(Color.BLACK);
           lightModeRadioButton.setTextFill(Color.BLACK);
           usernameField.setStyle("-fx-background-color: #2E4053; -fx-border-color: #1F618D;-fx-border-width: 0px 0px 3px 0px; " +
                   "-fx-background-radius: 10; -fx-border-radius: 7; -fx-prompt-text-fill: #20638d" );
           passwordField.setStyle("-fx-background-color: #2E4053;-fx-border-color: #1F618D;-fx-border-width: 0px 0px 3px 0px; " +
                   "-fx-background-radius: 10; -fx-border-radius: 7; -fx-prompt-text-fill: #20638d");
           Stop[] stops = new Stop[] { new Stop(0.5, Color.color(0.137, 0.431, 0.623)), new Stop(1, Color.color(0.1, 0.3215, 0.462))};
           title.setTextFill(new LinearGradient(0.5,0.68,0.44,0.6, true, CycleMethod.NO_CYCLE, stops));

       }
       else if(e.getSource().equals(darkModeRadioButton)){
           anchorPane.setStyle("-fx-background-color: #2E4053");
           loginButton.setTextFill(Color.WHITE);
           loginButton.setStyle("-fx-background-radius: 30;  -fx-background-color: #2E86C1;");
           darkModeRadioButton.setTextFill(Color.WHITE);
           lightModeRadioButton.setTextFill(Color.WHITE);
           usernameField.setStyle("-fx-border-color: #3498DB;-fx-border-width: 0px 0px 3px 0px; -fx-background-radius: 10; -fx-border-radius: 7; ");
           passwordField.setStyle("-fx-border-color: #3498DB;-fx-border-width: 0px 0px 3px 0px; -fx-background-radius: 10; -fx-border-radius: 7; ");
           Stop[] stops = new Stop[] { new Stop(0.5, Color.color(0.134,0.6165,0.75)), new Stop(1, Color.color(0.032,0.485,0.88))};
           title.setTextFill(new LinearGradient(0.5,0.68,0.44,0.6, true, CycleMethod.NO_CYCLE, stops));
       }
       }
    }

