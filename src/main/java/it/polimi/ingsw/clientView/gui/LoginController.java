package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.util.List;

import static java.lang.System.exit;

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

    public static void setGui(GUI gui) {
        LoginController.gui = gui;
        LoginController.clientView = gui.getClientView();
    }

    @FXML
    public void initialize() {
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

        Font santoriniFont = Font.loadFont(getClass().getResource("/font/LillyBelle.ttf").toExternalForm(),18);
        username.setFont(santoriniFont);

        txt.setFont(santoriniFont);


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
        label.setFont(new Font(18));
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
        Label l = new Label(s.toString());
        l.setFont(new Font(18));
        messages.getChildren().add(l);
    }

    public void inLobby() {
        messages.getChildren().clear();

        Label inLobbyMessage = new Label("WAIT THE GAME START...");
        inLobbyMessage.setFont(new Font(16));

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

    public void gameStart() {
        messages.getChildren().clear();
        Label label = new Label();
        label.setFont(new Font(16));
        label.setText("YOUR GAME IS STARTING! PLEASE WAIT...");

        ProgressIndicator progress = new ProgressIndicator();
        progress.setMaxSize(50,50);

        messages.getChildren().addAll(label,progress);
    }

}
