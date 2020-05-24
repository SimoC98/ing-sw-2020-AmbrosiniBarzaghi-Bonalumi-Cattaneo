package it.polimi.ingsw.clientView.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DivinitySelectionController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private GridPane godGrid;

    @FXML
    private Button confirmBtn;

    @FXML
    private Label labelTxt;


    private List<String> divinities;
    private List<String> descriptions;
    private int playerNumber;





    public void setDivinityOnGrid(List<String> divinities, List<String> descriptions, int playerNumber) {

        this.divinities = new ArrayList<>(divinities);
        this.descriptions = new ArrayList<>(descriptions);
        this.playerNumber = playerNumber;

        int count=0;


        for(int c=0;c<godGrid.getRowCount();c++) {
            for(int r=0;r<godGrid.getColumnCount();r++) {

                if(count<divinities.size()) {
                    ImageView god = new ImageView();


                    String path = "/graphics/" + divinities.get(count).toLowerCase() + ".png";






                    Image godImage = new Image("/graphics/" + divinities.get(count).toLowerCase() + ".png");


                    god.setImage(godImage);

                    god.setFitHeight(140);
                    god.setFitWidth(100);

                    addCell(god,r,c,count);

                    count++;
                }

            }
        }


    }

    private void addCell(Node node, int x, int y,int count) {
        godGrid.add(node,x,y);

        node.setOnMouseClicked((e)-> {
             labelTxt.setText(descriptions.get(count));
             labelTxt.setWrapText(true);

        });

        //add event listener
    }



}
