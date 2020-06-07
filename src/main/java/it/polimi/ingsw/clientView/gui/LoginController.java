package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.util.List;

public class LoginController {

    @FXML
    private TextField txt;

    @FXML
    private Button btn;

    @FXML
    private HBox hBox;

    @FXML
    private HBox messages;

    @FXML
    private VBox root;

    @FXML
    private Label label;

    private static ClientView clientView;


    @FXML
    public void initialize() {
       // String image = "/graphics/title_sky.png";

        /*vBox.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");*/

    }




    public static void setClientView(ClientView clientView) {
        LoginController.clientView = clientView;
    }

    public LoginController() {
        System.out.print("first");
    }


    public void handleLogin() {

        String username = txt.getText();

       if(username.length()<3) {
           label.setText("INVALID USERNAME! Username must have at least 3 characters");
           label.setVisible(true);
       }
       else if(username.length()>15) {
           label.setText("INVALID USERNAME! Username must have at most 15 characters");
           label.setVisible(true);
       }
       else if(username.contains(" ")) {
           label.setText("INVALID USERNAME! Username can't contain blank spaces");
           label.setVisible(true);
       }
       else {
           btn.setOnMouseClicked(null);

           hBox.setVisible(false);
           label.setVisible(false);
           //waitLabel.setText("wait the game start...");

           //invalidUsernameLabel.setVisible(false);
           //waitLabel.setVisible(true);


           clientView.loginQuestion(txt.getText());
       }

    }

    public void invalidUsername(List<String> loggedUsers) {

        hBox.setVisible(true);
        //waitLabel.setVisible(false);

        StringBuilder s = new StringBuilder();

        s.append("USERNAME ALREADY TAKEN! PLEASE CHOOSE ANOTHER AVOIDING: \n");

        for(int i=0;i<loggedUsers.size();i++) {
            s.append("\t-" + loggedUsers.get(i) + "\n");
        }

        label.setWrapText(true);
        label.setText(s.toString());

        label.setVisible(true);
    }

    public void inLobby() {
        messages.getChildren().clear();

        Label inLobbyMessage = new Label("WAIT THE GAME START...");
        inLobbyMessage.setFont(new Font(18));

        messages.getChildren().add(inLobbyMessage);

        ProgressIndicator progress = new ProgressIndicator();
        progress.setMaxSize(50,50);

        messages.getChildren().add(progress);

        messages.setSpacing(10);
       //progress.setVisible(true);
    }

    public void lobbyFull() {
        //TODO
    }

}
