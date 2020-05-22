package it.polimi.ingsw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.MouseEvent;

public class ProvaController {

    @FXML
    private TextField textFieldLogin;

    @FXML
    private Button btn;

    /*public void pressButton() {


        btn.setOnMouseClicked((e)-> {
            System.out.print(textFieldLogin.getText());
        });


    }*/


    public void pressButton(ActionEvent event) {

        System.out.print(textFieldLogin.getText());

    }





}
