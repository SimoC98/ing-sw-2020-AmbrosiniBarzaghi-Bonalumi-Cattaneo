package it.polimi.ingsw.Client.gui;

import it.polimi.ingsw.Client.ClientView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

import static java.lang.System.exit;

/**
 * Class to manage what to show when a user disconnects. The {@code initialize} method is automatically called when
 * the class is instantiated in the {@link GUI}. It displays the name of the disconnected player and it gives the
 * possibility to reconnect to begin a new game or to exit the game.
 */
public class DisconnectionController {

    private static ClientView clientView;
    private static GUI gui;

    @FXML
    private VBox root;

    private Text disconnectionText;

    private VBox buttons;

    private DoubleProperty fontSize = new SimpleDoubleProperty(10);
    private DoubleProperty fontSizeBtn = new SimpleDoubleProperty(10);
    Font santoriniFont = Font.loadFont(getClass().getResource("/font/LillyBelle.ttf").toExternalForm(),18);


    public static void setClientView(ClientView clientView) {
        DisconnectionController.clientView = clientView;
    }

    /**
     * Assigns the gui and retrieves the clientView
     * @param gui {@link GUI}
     */
    public static void setGui(GUI gui) {
        DisconnectionController.gui = gui;
        DisconnectionController.clientView = gui.getClientView();
    }

    /**
     * It extracts an fxml file to configure a button to exit the game or to create a new one.
     * These buttons are decorated (colors, lighting, etc) and they are interactive: if the player presses exit
     * the client is aborted; if the connect button is pressed, it clears the {@link it.polimi.ingsw.Client.BoardRepresentation}
     * and restores the connection and all the gui parameters, invoking {@link GUI#loadGUI()}
     */
    @FXML
    public void initialize() {
        root.getStylesheets().add(getClass().getResource("/css/btn.css").toExternalForm());
        fontSize.bind(root.widthProperty().add(root.heightProperty()).divide(70));
        fontSizeBtn.bind(root.widthProperty().add(root.heightProperty()).divide(130));

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(4.0);

        disconnectionText = new Text();
        disconnectionText.setFont(santoriniFont);
        disconnectionText.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));

        Button exit = new Button("EXIT");
        exit.getStyleClass().addAll("coral","whiteTxt");
        Button connect = new Button("NEW GAME");
        connect.getStyleClass().addAll("blue","whiteTxt");
        this.buttons = new VBox();
        buttons.getChildren().addAll(connect,exit);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);

        connect.prefWidthProperty().bind(buttons.widthProperty().divide(12));
        connect.prefHeightProperty().bind(buttons.heightProperty().divide(20));
        connect.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeBtn.asString(), ";"));
        exit.prefWidthProperty().bind(buttons.widthProperty().divide(12));
        exit.prefHeightProperty().bind(buttons.heightProperty().divide(20));
        exit.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeBtn.asString(), ";"));

        root.getChildren().addAll(disconnectionText,buttons);

        exit.getStyleClass().addAll("coral","whiteTxt");
        connect.getStyleClass().addAll("blue","whiteTxt");
        exit.getStylesheets().add(getClass().getResource("/css/btn.css").toExternalForm());
        connect.getStylesheets().add(getClass().getResource("/css/btn.css").toExternalForm());

        connect.setOnMouseEntered((e)-> {
            connect.setEffect(lighting);
        });

        connect.setOnMouseExited((e) -> {
            connect.setEffect(null);
        });

        exit.setOnMouseEntered((e) -> {
            exit.setEffect(lighting);
        });

        exit.setOnMouseExited((e) -> {
            exit.setEffect(null);
        });

        exit.setOnMouseClicked((e) -> {
            Platform.exit();
            //exit(0);
        });

        connect.setOnMouseClicked((e) -> {
            try {
                clientView.resetBoard();
                gui.loadGUI();
            } catch (IOException ioException) {
                //ioException.printStackTrace();
            }
            clientView.startConnection();
        });
    }

    /**
     * Creates the text with the disconnected player
     * @param player username of the disconnected player.
     */
    public void disconnect(String player) {
        disconnectionText.setText("User " + player + " has left the game... this match is ended");
    }

    public void serverDisconnection() {
        disconnectionText.setText("The server is no longer reachable!");

        buttons.getChildren().remove(0);
    }
}
