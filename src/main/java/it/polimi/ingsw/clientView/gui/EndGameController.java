package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static java.lang.System.exit;

public class EndGameController {

    @FXML
    private VBox vBox1;
    @FXML
    private VBox vBox2;
    @FXML
    private Button quit;
    @FXML
    private Button newGame;

    private static ClientView clientView;
    private static GUI gui;

    @FXML
    public void initialize() {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(4.0);

        vBox1.getStylesheets().add(getClass().getResource("/css/endBack.css").toExternalForm());
        vBox1.getStylesheets().add(getClass().getResource("/css/btn.css").toExternalForm());

        vBox2.prefWidthProperty().bind(vBox1.widthProperty().divide(10));
        vBox2.prefHeightProperty().bind(vBox1.heightProperty().divide(1.45));

        quit.prefWidthProperty().bind(vBox2.widthProperty().divide(12.5));
        quit.prefHeightProperty().bind(vBox2.heightProperty().divide(14.5));

        newGame.prefWidthProperty().bind(vBox2.widthProperty().divide(12.5));
        newGame.prefHeightProperty().bind(vBox2.heightProperty().divide(14.5));

        //vBox2.getStyleClass().add("loser");

        quit.getStyleClass().add("coral");
        quit.getStyleClass().add("whiteTxt");
        newGame.getStyleClass().add("blue");
        newGame.getStyleClass().add("whiteTxt");

        quit.setOnMouseEntered((e) -> {
            quit.setEffect(lighting);
        });

        quit.setOnMouseExited((e) -> {
            quit.setEffect(null);
        });

        newGame.setOnMouseEntered((e) -> {
            newGame.setEffect(lighting);
        });

        newGame.setOnMouseExited((e) -> {
            newGame.setEffect(null);
        });

        quit.setOnMouseClicked((e) -> {
            exit(0);
        });

        newGame.setOnMouseClicked((e) -> {
            try {
                clientView.resetBoard();
                gui.loadGUI();
            } catch (IOException ioException) {
                //ioException.printStackTrace();
            }
            clientView.startConnection();
        });



    }

    public static void setClientView(ClientView clientView) {
        EndGameController.clientView = clientView;
    }

    public static void setGui(GUI gui) {
        EndGameController.gui = gui;
        EndGameController.clientView = gui.getClientView();
    }

    public void setWinner() {
        vBox2.getStyleClass().add("winner");
    }

    public void setLoser() {
        vBox2.getStyleClass().add("loser");
    }
}
