package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class PlayerDivinitySelectionController {

    private static ClientView clientView;

    @FXML
    private BorderPane bPane;

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

    private String chosenGod = null;



    public static void setClientView(ClientView clientView) {
        PlayerDivinitySelectionController.clientView = clientView;
    }

    @FXML
    public void initialize() {

        hBox.prefWidthProperty().bind(bPane.widthProperty());
        hBox.prefHeightProperty().bind(bPane.heightProperty().divide(4.4));

    }


    public void selectPlayerDivinity(List<String> divinities) {

        this.divinities = divinities;

        for(int i=0;i<divinities.size();i++) {

            ImageView god = new ImageView();
            Image godImage = new Image("/graphics/" + divinities.get(i).toLowerCase() + ".png");
            god.setImage(godImage);

            god.fitHeightProperty().bind(bPane.heightProperty().divide(2.4));
            god.fitWidthProperty().bind(bPane.widthProperty().divide(6.25));

            /*god.setFitHeight(350);
            god.setFitWidth(240);*/

            addDivinityToHbox(god,i);

        }

        System.out.println("eccomi");

    }

    private void addDivinityToHbox(Node node, int count) {
        //light effect
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(4.0);

        //background of cards
        String back = "/graphics/clp_bg.png";
        ImageView b = new ImageView(new Image(back));

        b.fitWidthProperty().bind(bPane.widthProperty().divide(6.25).add(30));
        b.fitHeightProperty().bind(bPane.heightProperty().divide(2.4).add(40));

        /*b.setFitHeight(370);
        b.setFitWidth(260);*/

        hBox.getChildren().add(new StackPane(b,node));
        godImages.add((ImageView)node);

        node.setOnMouseEntered((e)->{
            node.setEffect(lighting);
        });

        node.setOnMouseExited((e)->{
            if(chosenGod==null || !chosenGod.equals(divinities.get(count))) node.setEffect(null);
        });

        node.setOnMouseClicked((e1)-> {
            label.setVisible(true);
            label.setText("ciao");
            String desc = clientView.getBoard().getDivinities().get(divinities.get(count));
            label.setText(desc);
            label.setWrapText(true);

            btn.setVisible(true);

            btn.setOnMouseClicked((e2) -> {
                ImageView img = (ImageView) e1.getTarget();
                img.setEffect(lighting);

                chosenGod = divinities.get(count);

                btn.setVisible(false);

                godImages.stream().forEach(i -> i.setOnMouseClicked(null));

                label.setText("you chose " + chosenGod + "... Wait other player's choice!");


                clientView.divinitySelection(divinities.get(count));

            });



        });

    }

}
