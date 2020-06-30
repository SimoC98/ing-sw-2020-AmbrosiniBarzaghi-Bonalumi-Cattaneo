package it.polimi.ingsw.Client.gui;

import it.polimi.ingsw.Client.ClientView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

import static java.lang.System.exit;

/**
 * Class to manage the GUI for the login. The aim is to show a welcome interface and to get the player's username
 * in order to connect to the server through {@link ClientView#loginQuestion(String)}
 */
public class LoginController {

    @FXML
    private VBox vBoxSup;

    @FXML
    private VBox vBoxInf;

    @FXML
    private TextField txt;

    @FXML
    private Button btn;

    @FXML
    private HBox hBox;

    @FXML
    private HBox messages;

    @FXML
    private Label username;

    @FXML
    private VBox root;

    private static ClientView clientView;
    private static GUI gui;

    private DoubleProperty fontSize = new SimpleDoubleProperty(10);
    Font santoriniFont = Font.loadFont(getClass().getResource("/font/LillyBelle.ttf").toExternalForm(),18);
    Lighting lighting;

    /**
     * Assigns gui and clientView(proxy)
     * @param gui
     */
    public static void setGui(GUI gui) {
        LoginController.gui = gui;
        LoginController.clientView = gui.getClientView();
    }

    /**
     * Creates a window from file and decorates it and adds a text form and a button to input the player's username.
     */
    @FXML
    public void initialize() {
        root.getStylesheets().add(getClass().getResource("/css/btn.css").toExternalForm());

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);

        this.lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(4.0);

        btn.getStyleClass().add("blue");
        btn.getStyleClass().add("whiteTxt");
        btn.setOnMouseEntered((e) -> {
            btn.setEffect(lighting);
        });
        btn.setOnMouseExited((e) -> {
            btn.setEffect(null);
        });

        fontSize.bind(root.widthProperty().add(root.heightProperty()).divide(100));
        txt.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        btn.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        username.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        messages.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));

        vBoxSup.prefHeightProperty().bind(root.heightProperty().divide(2));
        vBoxSup.prefWidthProperty().bind(root.widthProperty());

        vBoxInf.prefHeightProperty().bind(root.heightProperty().divide(2));
        vBoxInf.prefWidthProperty().bind(root.widthProperty());

        vBoxSup.setAlignment(Pos.CENTER);

        ImageView logo = new ImageView();
        Image img = new Image(getClass().getResource("/graphics/logo2.png").toExternalForm());
        logo.setImage(img);
        logo.fitHeightProperty().bind(root.heightProperty().divide(1.3));
        logo.fitWidthProperty().bind(root.widthProperty().divide(1.7));
        vBoxSup.getChildren().add(logo);

        txt.setFont(new Font(18));

        hBox.prefHeightProperty().bind(vBoxInf.heightProperty().divide(2.3));
        hBox.prefWidthProperty().bind(vBoxInf.widthProperty());

        messages.prefHeightProperty().bind(vBoxInf.heightProperty().divide(2));
        messages.prefWidthProperty().bind(vBoxInf.widthProperty());

        btn.prefHeightProperty().bind(hBox.heightProperty().divide(2));
        btn.prefWidthProperty().bind(hBox.widthProperty().divide(14));

        txt.prefHeightProperty().bind(hBox.heightProperty().divide(2));
        txt.prefWidthProperty().bind(hBox.widthProperty().divide(9));

        txt.setOnKeyPressed((e) -> {
            if(e.getCode().equals(KeyCode.ENTER)) {
                handleLogin();
            }
        });

        username.setFont(santoriniFont);

        txt.setFont(santoriniFont);
    }

    public static void setClientView(ClientView clientView) {
        LoginController.clientView = clientView;
    }

    /**
     * Manages the wrong inputs and sends the login request. After sending the request the form is not available to fill
     * anymore.
     */
    public void handleLogin() {

        String username = txt.getText();

        Label label = new Label();
        label.setFont(santoriniFont);
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

    /**
     * Method to menage the input of an already taken username. After the server finds a double of the previously sent
     * username it sends back a list of logged users. This method shows such list.
     * @param loggedUsers list of taken username
     */
    public void invalidUsername(List<String> loggedUsers) {
        //Font santoriniFont = Font.loadFont(getClass().getResource("/font/LillyBelle.ttf").toExternalForm(),18);

        hBox.setVisible(true);

        StringBuilder s = new StringBuilder();

        s.append("USERNAME ALREADY TAKEN! PLEASE CHOOSE ANOTHER AVOIDING: \n");

        for(int i=0;i<loggedUsers.size();i++) {
            s.append("\t-" + loggedUsers.get(i) + "\n");
        }

        messages.getChildren().clear();
        Text l = new Text(s.toString());
        l.setFont(santoriniFont);
        l.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        messages.getChildren().add(l);
    }

    /**
     * Called when the user correctly joins a lobby. A message is displayed and a ProgressIndicator appears.
     */
    public void inLobby() {
        messages.getChildren().clear();

        Text inLobbyMessage = new Text();
        inLobbyMessage.setText("THE GAME IS STARTING...");
        //inLobbyMessage.setFont(new Font(16));
        inLobbyMessage.setFont(santoriniFont);
        inLobbyMessage.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));


        messages.getChildren().add(inLobbyMessage);

        ProgressIndicator progress = new ProgressIndicator();
        progress.setMaxSize(50,50);
        progress.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));

        messages.getChildren().add(progress);

        messages.setSpacing(10);
    }

    /**
     * Called if the user attempted to join a full lobby. An interactive button to quit appears.
     */
    public void lobbyFull() {
        messages.getChildren().clear();
        messages.setSpacing(20);
        String s = "LOBBY IS FULL! YOU CAN'T JOIN THIS MATCH!";
        Text lobbyFull = new Text(s);
        lobbyFull.setFont(santoriniFont);
        lobbyFull.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));


        Button exit = new Button("QUIT");
        exit.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        exit.getStyleClass().add("coral");
        exit.getStyleClass().add("whiteTxt");

        exit.setOnMouseEntered((e) -> {
            exit.setEffect(lighting);
        });

        exit.setOnMouseExited((e) -> {
            exit.setEffect(null);
        });

        exit.setOnMouseClicked((e) -> {
            Platform.exit();
            exit(0);
        });

        messages.getChildren().addAll(lobbyFull,exit);

    }

    /**
     * Called when the lobby filled with players and the game is going to start. A message is shown and the progress
     * indicator appears again.
     */
    public void gameStart() {
        messages.getChildren().clear();
        Text t = new Text();
        t.setFont(santoriniFont);
        t.setText("THE GAME IS STARTING! PLEASE WAIT...");
        t.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));

        ProgressIndicator progress = new ProgressIndicator();
        progress.setMaxSize(50,50);
        progress.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));

        messages.getChildren().addAll(t,progress);
    }

}
