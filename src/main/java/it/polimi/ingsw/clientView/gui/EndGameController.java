package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import static java.lang.System.exit;

public class EndGameController {

    private static ClientView clientView;

    @FXML
    private VBox panel;

    @FXML
    private Button exit;

    @FXML
    private Button newGame;

    @FXML
    public void initialize() {
        exit.setText("EXIT");
        exit.getStyleClass().add("blue");
        exit.getStylesheets().add("/css/btn.css");

        newGame.setText("NEW GAME");
        newGame.getStyleClass().add("blue");
        newGame.getStylesheets().add("/css/btn.css");

        exit.setOnMouseClicked((e) -> {
            exit(0);
        });

        newGame.setOnMouseClicked((e) -> {
            clientView.startConnection();
        });
    }


    public static void setClientView(ClientView clientView) {
        EndGameController.clientView = clientView;
    }
}
