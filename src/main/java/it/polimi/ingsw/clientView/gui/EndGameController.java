package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
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
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(4.0);


        exit.setText("EXIT");
        exit.getStyleClass().add("blue");
        exit.getStylesheets().add("/css/btn.css");

        newGame.setText("NEW GAME");
        newGame.getStyleClass().add("blue");
        newGame.getStylesheets().add("/css/btn.css");

        exit.setOnMouseClicked((e) -> {
            exit(0);
        });

        exit.setOnMouseEntered((e) -> {
            exit.setEffect(lighting);
        });

        exit.setOnMouseExited((e) -> {
            exit.setEffect(null);
        });

        newGame.setOnMouseClicked((e) -> {
            clientView.startConnection();
        });

        newGame.setOnMouseEntered((e) -> {
            newGame.setEffect(lighting);
        });

        newGame.setOnMouseExited((e) -> {
            newGame.setEffect(null);
        });
    }


    public static void setClientView(ClientView clientView) {
        EndGameController.clientView = clientView;
    }
}
