package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class DisconnectionController {

    private static ClientView clientView;

    @FXML
    private VBox root;

    @FXML
    private Label disconnectionLabel;

    @FXML
    private VBox buttons;

    @FXML
    private Button exit;

    @FXML
    private Button connect;


    public static void setClientView(ClientView clientView) {
        DisconnectionController.clientView = clientView;
    }

    public void disconnect(String player) {
        disconnectionLabel.setText("User " + player + " has left the game... this match is ended");

        exit.setOnMouseClicked((e) -> {
            exit(0);
        });

        connect.setOnMouseClicked((e) -> {
            try {
                GUI gui = (GUI) clientView.getUi();
                gui.loadGUI();
            } catch (IOException ioException) {
                //ioException.printStackTrace();
            }
            clientView.startConnection();
        });
    }
}
