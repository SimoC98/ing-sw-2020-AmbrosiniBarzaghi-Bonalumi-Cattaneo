package it.polimi.ingsw.clientView.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class DivinitySelectionController {

    @FXML
    private SplitPane mainPane;

    @FXML
    private AnchorPane leftPane;

    @FXML
    private AnchorPane rightPane;

    @FXML
    private GridPane godGrid;

    @FXML
    private Button confirmBtn;

    @FXML
    private TextArea descriptionsTxt;


    private List<String> divinities;
    private List<String> descriptions;





    public void setDivinityOnGrid(List<String> divinities, List<String> descriptions) {

        this.divinities = new ArrayList<>(divinities);
        this.descriptions = new ArrayList<>(descriptions);

        int count=0;


        for(int r=0;r<godGrid.getColumnCount();r++) {
            for(int c=0;c<godGrid.getRowCount();c++) {

                Image godImage = new Image("resources/" + divinities.get(count) + ".png");

                ImageView god = new ImageView();
                god.setImage(godImage);

                addCell(god,r,c);

                count++;
            }
        }
    }

    private void addCell(Node node, int x, int y) {
        godGrid.add(node,x,y);

        //add event listener
    }



}
