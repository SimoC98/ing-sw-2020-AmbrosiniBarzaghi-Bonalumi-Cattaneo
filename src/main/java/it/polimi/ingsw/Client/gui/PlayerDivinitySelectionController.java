package it.polimi.ingsw.Client.gui;

import it.polimi.ingsw.Client.ClientView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class PlayerDivinitySelectionController {

    private static ClientView clientView;
    private static GUI gui;

    @FXML
    private BorderPane bPane;

    @FXML
    private HBox hBox;

    @FXML
    private VBox vBox;

    @FXML
    private VBox vBoxInf;

    @FXML
    private HBox sceneTitle;

    private List<String> divinities;
    private List<ImageView> godImages = new ArrayList<>();
    private String chosenGod = null;

    private DoubleProperty fontSize = new SimpleDoubleProperty(10);
    private DoubleProperty fontSizeTitle = new SimpleDoubleProperty(20);
    private DoubleProperty fontSizeDescriptions = new SimpleDoubleProperty(20);

    private Text godDescription;
    private Button chooseGod;




    public static void setClientView(ClientView clientView) {
        PlayerDivinitySelectionController.clientView = clientView;
    }

    public static void setGui(GUI gui) {
        PlayerDivinitySelectionController.gui = gui;
        PlayerDivinitySelectionController.clientView = gui.getClientView();
    }

    @FXML
    public void initialize() {
        fontSize.bind(bPane.widthProperty().add(bPane.heightProperty()).divide(100));
        fontSizeTitle.bind(bPane.widthProperty().add(bPane.heightProperty()).divide(80));
        fontSizeDescriptions.bind(bPane.widthProperty().add(bPane.heightProperty()).divide(150));

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(4.0);

        Font santorini = Font.loadFont(getClass().getResource("/font/LillyBelle.ttf").toExternalForm(), 24);

        sceneTitle.setAlignment(Pos.CENTER);
        Text title = new Text("CHOOSE GODS THAT WILL BE USED IN THIS MATCH:");
        title.setFont(santorini);
        title.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeTitle.asString(), ";"));
        sceneTitle.getChildren().add(title);

        bPane.getStylesheets().add(getClass().getResource("/css/btn.css").toExternalForm());

        hBox.prefWidthProperty().bind(bPane.widthProperty());
        hBox.prefHeightProperty().bind(bPane.heightProperty().divide(4.4));
        hBox.spacingProperty().bind(bPane.widthProperty().divide(10));

        vBoxInf.prefWidthProperty().bind(bPane.widthProperty());

        godDescription = new Text();
        godDescription.wrappingWidthProperty().bind(bPane.widthProperty().subtract(50));
        godDescription.setFont(santorini);
        godDescription.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        godDescription.setVisible(false);
        godDescription.setTextAlignment(TextAlignment.CENTER);

        chooseGod = new Button("SELECT");
        chooseGod.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSizeDescriptions.asString(), ";"));
        chooseGod.prefWidthProperty().bind(bPane.widthProperty().divide(10));
        chooseGod.prefHeightProperty().bind(bPane.heightProperty().divide(20));
        chooseGod.getStyleClass().add("blue");
        chooseGod.getStyleClass().add("whiteTxt");
        //chooseGod.setFont(santorini);
        chooseGod.setVisible(false);
        chooseGod.setOnMouseEntered((e) -> {
            chooseGod.setEffect(lighting);
        });
        chooseGod.setOnMouseExited((e) -> {
            chooseGod.setEffect(null);
        });

        vBoxInf.getChildren().addAll(godDescription,chooseGod);
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
            godDescription.setVisible(true);
            String desc = clientView.getBoard().getDivinities().get(divinities.get(count));
            godDescription.setText(desc);
            //label.setWrapText(true);

            chooseGod.setVisible(true);

            chooseGod.setOnMouseClicked((e2) -> {
                ImageView img = (ImageView) e1.getTarget();
                img.setEffect(lighting);

                chosenGod = divinities.get(count);

                chooseGod.setVisible(false);

                godImages.stream().forEach(i -> i.setOnMouseClicked(null));

                godDescription.setText("you chose " + chosenGod + "... Wait other player's choice!");

                clientView.divinitySelection(divinities.get(count));

            });



        });

    }

}
