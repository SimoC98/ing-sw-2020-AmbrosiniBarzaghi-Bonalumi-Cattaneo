package it.polimi.ingsw.clientView.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txt;

    @FXML
    private Button btn;

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



}
