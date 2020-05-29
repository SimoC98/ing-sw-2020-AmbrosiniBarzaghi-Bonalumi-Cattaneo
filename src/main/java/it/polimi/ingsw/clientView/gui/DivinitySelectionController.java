package it.polimi.ingsw.clientView.gui;

import it.polimi.ingsw.clientView.ClientView;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DivinitySelectionController {

    private static ClientView clientView;

    @FXML
    private VBox mainPane;

    @FXML
    private GridPane godGrid;

    @FXML
    private Button confirmBtn;

    @FXML
    private Label labelTxt;

    private List<ImageView> godsImages = new ArrayList<>();

    private List<String> divinities;
    private List<String> descriptions;
    private int playerNumber;

    private List<String> chosenGods = new ArrayList<>();


    @FXML
    public void initialize() {
        godGrid.setAlignment(Pos.CENTER);
        godGrid.setHgap(10);
        godGrid.setVgap(50);

        godGrid.setPadding(new Insets(10,10,10,10));

    }

    public static void setClientView(ClientView clientView) {
        DivinitySelectionController.clientView = clientView;
    }

    public void setDivinityOnGrid(List<String> divinities, List<String> descriptions, int playerNumber) {

        this.divinities = new ArrayList<>(divinities);
        this.descriptions = new ArrayList<>(descriptions);
        this.playerNumber = playerNumber;

        int count=0;


        for(int c=0;c<godGrid.getRowCount();c++) {
            for(int r=0;r<godGrid.getColumnCount();r++) {

                if(count<divinities.size()) {
                    ImageView god = new ImageView();

                    Image godImage = new Image("/graphics/" + divinities.get(count).toLowerCase() + ".png");


                    god.setImage(godImage);

                    god.setFitHeight(156);
                    god.setFitWidth(100);


                   // god.setFitHeight(mainPane.getHeight()/20);
                   // god.setFitWidth(mainPane.getWidth()/20);

                    addCell(god,r,c,count);

                    count++;
                }

            }
        }


    }

    private void addCell(Node node, int x, int y,int count) {
        godGrid.add(node,x,y);
        godsImages.add((ImageView)node);

        GridPane.setHalignment(node, HPos.CENTER);
        GridPane.setValignment(node, VPos.CENTER);

        node.setOnMouseClicked((e1)-> {
            Light.Distant light = new Light.Distant();
            light.setAzimuth(-135.0);

// Create lighting effect
            Lighting lighting = new Lighting();
            lighting.setLight(light);
            lighting.setSurfaceScale(4.0);

             labelTxt.setText(descriptions.get(count));
             labelTxt.setWrapText(true);

             confirmBtn.setOnMouseClicked((e2)->{

                 if(chosenGods.contains(divinities.get(count))) {
                     ImageView img = (ImageView) e1.getTarget();
                     img.setEffect(null);
                     chosenGods.remove(divinities.get(count));
                     labelTxt.setText("God deselected ");
                 }
                 else {
                     chosenGods.add(divinities.get(count));

                     ImageView img = (ImageView) e1.getTarget();
                     img.setEffect(lighting);
                 }

                 if(chosenGods.size()==playerNumber) {
                     confirmBtn.setOnAction(null);

                     labelTxt.setText("WAIT YOUR TURN...");

                     confirmBtn.setVisible(false);

                     String s = "You chose: " + chosenGods.get(0) + " " + chosenGods.get(1) + " " + chosenGods.get(2);
                     labelTxt.setText(s);

                     godsImages.stream().forEach(i -> i.setOnMouseClicked(null));

                     clientView.playableDivinitiesSelection(chosenGods);
                 }
             });

        });

        //add event listener
    }



}
