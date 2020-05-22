package it.polimi.ingsw;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class Prova2Controller {

    @FXML
    private GridPane gridmane;

    @FXML
    private TextField txt;

    @FXML
    private HBox hbox;



    public Prova2Controller() {

    }

    public void cellClicked(javafx.scene.input.MouseEvent event) {


        txt.setText("ciao");
       GridPane pane = (GridPane) event.getTarget();


       hbox.setSpacing(50);

       Button b1 = new Button();
       b1.setText("b1");
       Button b2 = new Button();
       b2.setText("b2");

       hbox.getChildren().add(b1);
       hbox.getChildren().add(b2);


    }
}
