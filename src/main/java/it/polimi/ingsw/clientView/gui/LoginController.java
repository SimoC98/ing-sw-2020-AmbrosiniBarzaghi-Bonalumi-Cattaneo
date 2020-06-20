package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private StackPane sPaneInf;
    @FXML
    private HBox hBoxCloudSup;
    @FXML
    private ImageView cloudSupImage;
    @FXML
    private StackPane titleStackPane;
    @FXML
    private HBox titleHBox;
    @FXML
    private ImageView titleCloudLeft;
    @FXML
    private ImageView titleCloudRight;
    @FXML
    private ImageView santoriniTitle;

    @FXML
    private TextField txt;

    @FXML
    private Button btn;

    @FXML
    private HBox hBox;

    @FXML
    private HBox messages;

    @FXML
    private VBox root;

    private static ClientView clientView;


    @FXML
    public void initialize() {
        vBoxSup.prefWidthProperty().bind(root.widthProperty());
        vBoxSup.prefHeightProperty().bind(root.heightProperty().multiply(0.463));

        sPaneInf.prefWidthProperty().bind(root.widthProperty().divide(1.36));
        sPaneInf.prefHeightProperty().bind(root.heightProperty().divide(2.6));

        hBoxCloudSup.prefWidthProperty().bind(vBoxSup.widthProperty());
        hBoxCloudSup.prefHeightProperty().bind(vBoxSup.heightProperty().divide(2.12));

        Image cloudSup = new Image(getClass().getResource("/graphics/title_cloud_back_right.png").toExternalForm());
        cloudSupImage.setImage(cloudSup);
        cloudSupImage.fitHeightProperty().bind(hBoxCloudSup.heightProperty());
        cloudSupImage.fitWidthProperty().bind(hBoxCloudSup.widthProperty().divide(3.9));

        titleStackPane.prefWidthProperty().bind(root.widthProperty());
        titleStackPane.prefHeightProperty().bind(root.heightProperty().divide(4.1));

        titleHBox.prefHeightProperty().bind(titleStackPane.heightProperty());
        titleHBox.prefWidthProperty().bind(titleStackPane.widthProperty().divide(1.27));

        titleCloudLeft.fitWidthProperty().bind(titleHBox.widthProperty().divide(3.45));
        titleCloudLeft.fitHeightProperty().bind(titleHBox.heightProperty());

        titleCloudRight.fitWidthProperty().bind(titleHBox.widthProperty().divide(3.45));
        titleCloudRight.fitHeightProperty().bind(titleHBox.heightProperty());

        santoriniTitle.fitHeightProperty().bind(titleHBox.heightProperty());
        santoriniTitle.fitWidthProperty().bind(titleHBox.widthProperty().divide(1.75));




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
        messages.getChildren().add(new Label(s.toString()));
    }

    public void inLobby() {
        messages.getChildren().clear();

        Label inLobbyMessage = new Label("WAIT THE GAME START...");
        inLobbyMessage.setFont(new Font(18));

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
        label.setFont(new Font(18));
        label.setText("YOUR GAME IS STARTING! PLEASE WAIT...");

        ProgressIndicator progress = new ProgressIndicator();
        progress.setMaxSize(50,50);

        messages.getChildren().addAll(label,progress);
    }

}
