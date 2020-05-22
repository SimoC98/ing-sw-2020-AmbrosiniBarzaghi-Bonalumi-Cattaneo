package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WelcomeController {

    @FXML
    private AnchorPane pane;

    @FXML
    private Button btn;

    private static ClientView clientView;

    private Stage stage;
    private Scene loginScene;



    public WelcomeController() {
        System.out.println("first");
    }


    public static void setClientView(ClientView clientView) {
       WelcomeController.clientView = clientView;
    }

    @FXML
    public void pressButton(ActionEvent event) {
        System.out.println("connection");
//        clientView.startConnection();

        clientView.startConnection();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public void changeScene() {
        this.stage.setScene(loginScene);
    }
}
