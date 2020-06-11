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

import static java.lang.System.exit;

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

    private static ClientView clientView;


    @FXML
    public void initialize() {
    }


    public static void setClientView(ClientView clientView) {
        LoginController.clientView = clientView;
    }

    public LoginController() {
        System.out.print("first");
    }


    public void handleLogin() {

        String username = txt.getText();

        Label label = new Label();
        label.setVisible(false);
        messages.getChildren().clear();
        messages.getChildren().add(label);

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
           //btn.setOnMouseClicked(null);

           hBox.setVisible(false);
           label.setVisible(false);


           clientView.loginQuestion(txt.getText());
       }

    }

    public void invalidUsername(List<String> loggedUsers) {

        hBox.setVisible(true);

        StringBuilder s = new StringBuilder();

        s.append("USERNAME ALREADY TAKEN! PLEASE CHOOSE ANOTHER AVOIDING: \n");

        for(int i=0;i<loggedUsers.size();i++) {
            s.append("\t-" + loggedUsers.get(i) + "\n");
        }

        messages.getChildren().clear();
        messages.getChildren().add(new Label(s.toString()));
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
    }

    public void lobbyFull() {
        messages.getChildren().clear();
        messages.getChildren().add(new Label("LOBBY IS FULL! YOU CAN'T JOIN THIS MATCH!"));

    }

}
