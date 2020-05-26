package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private VBox vBox;

    @FXML
    private Label waitLabel;

    @FXML
    private Label invalidUsernameLabel;


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
        btn.setOnMouseClicked(null);

        waitLabel.setText("wait the game start...");

        invalidUsernameLabel.setVisible(false);
        waitLabel.setVisible(true);


        clientView.loginQuestion(txt.getText());
    }

    public void invalidUsername(List<String> loggedUsers) {

        waitLabel.setVisible(false);

        StringBuilder s = new StringBuilder();

        s.append("USERNAME ALREADY TAKEN! PLEASE CHOOSE ANOTHER AVOIDING: \n");

        for(int i=0;i<loggedUsers.size();i++) {
            s.append("\t-" + loggedUsers.get(i) + "\n");
        }

        invalidUsernameLabel.setWrapText(true);
        invalidUsernameLabel.setText(s.toString());

        invalidUsernameLabel.setVisible(true);
    }







}
