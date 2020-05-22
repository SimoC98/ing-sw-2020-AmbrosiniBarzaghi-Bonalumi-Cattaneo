package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txt;

    @FXML
    private Button btn;

    private static ClientView clientView;

    private Stage stage;

    private Scene nextScene;




    public static void setClientView(ClientView clientView) {
        LoginController.clientView = clientView;
    }

    public LoginController() {
        System.out.print("first");
    }

    @FXML
    public void initialize() {
        System.out.println("second");

        btn.setOnAction(e -> {
            System.out.print("click");
        });


    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }







}
