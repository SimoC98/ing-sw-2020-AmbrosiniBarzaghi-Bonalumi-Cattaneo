package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class PlayerDivinitySelectionController {

    private static ClientView clientView;


    @FXML
    private HBox hBox;

    @FXML
    private VBox vBox;

    @FXML
    private Label label;

    @FXML
    private Button btn;

    private List<String> divinities;



    public static void setClientView(ClientView clientView) {
        PlayerDivinitySelectionController.clientView = clientView;
    }


    public void selectPlayerDivinity(List<String> divinities) {

        this.divinities = divinities;

        for(int i=0;i<divinities.size();i++) {

            ImageView god = new ImageView();

            Image godImage = new Image("/graphics/" + divinities.get(i).toLowerCase() + ".png");

            god.setImage(godImage);

            god.setFitHeight(156);
            god.setFitWidth(100);

            addDivinityToHbox(god,i);

        }

    }

    private void addDivinityToHbox(Node node, int count) {

        hBox.getChildren().add(node);

        node.setOnMouseClicked((e1)-> {
            label.setVisible(true);
            label.setText("ciao");
            String desc = clientView.getBoard().getDivinities().get(divinities.get(count));
            label.setText(desc);
            label.setWrapText(true);

            btn.setVisible(true);

            btn.setOnMouseClicked((e2) -> {
                ImageView img = (ImageView) e1.getTarget();

                clientView.divinitySelection(divinities.get(count));

                label.setText("you chose " + divinities.get(count));
            });



        });

    }

}
