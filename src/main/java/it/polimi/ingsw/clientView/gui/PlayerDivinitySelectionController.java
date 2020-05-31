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

import java.util.ArrayList;
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

    private List<ImageView> godImages = new ArrayList<>();



    public static void setClientView(ClientView clientView) {
        PlayerDivinitySelectionController.clientView = clientView;
    }


    public void selectPlayerDivinity(List<String> divinities) {

        this.divinities = divinities;

        for(int i=0;i<divinities.size();i++) {

            ImageView god = new ImageView();

            Image godImage = new Image("/graphics/" + divinities.get(i).toLowerCase() + ".png");

            god.setImage(godImage);

            god.setFitHeight(200);
            god.setFitWidth(150);

            addDivinityToHbox(god,i);

        }

        System.out.println("eccomi");

    }

    private void addDivinityToHbox(Node node, int count) {

        hBox.getChildren().add(node);
        godImages.add((ImageView)node);

        node.setOnMouseClicked((e1)-> {

            Light.Distant light = new Light.Distant();
            light.setAzimuth(-135.0);

// Create lighting effect
            Lighting lighting = new Lighting();
            lighting.setLight(light);
            lighting.setSurfaceScale(4.0);

            label.setVisible(true);
            label.setText("ciao");
            String desc = clientView.getBoard().getDivinities().get(divinities.get(count));
            label.setText(desc);
            label.setWrapText(true);

            btn.setVisible(true);

            btn.setOnMouseClicked((e2) -> {
                ImageView img = (ImageView) e1.getTarget();
                img.setEffect(lighting);

                btn.setVisible(false);

                godImages.stream().forEach(i -> i.setOnMouseClicked(null));

                label.setText("you chose " + divinities.get(count) + "... Wait other player's choice!");


                clientView.divinitySelection(divinities.get(count));

            });



        });

    }

}
