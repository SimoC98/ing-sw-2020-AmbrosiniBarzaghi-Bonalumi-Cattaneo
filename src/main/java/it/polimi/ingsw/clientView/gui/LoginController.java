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
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

public class LoginController {

    @FXML
    private TextField txt;

    @FXML
    private Button btn;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label waitLabel;

    private static ClientView clientView;

    private Stage stage;

    private Scene nextScene;

    @FXML
    public void initialize() {
        btn.setOnMouseClicked(e -> {
            System.out.print("click");
            handleLogin();
        });

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
        waitLabel.setVisible(true);




        clientView.loginQuestion(txt.getText());
    }







}
