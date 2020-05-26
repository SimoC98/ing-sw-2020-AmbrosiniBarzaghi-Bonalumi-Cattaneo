package it.polimi.ingsw;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ProvaController {

    @FXML
    private GridPane grid;

    @FXML
    public void initialize() {
        fillGrid(grid);
    }

    public void fillGrid(GridPane grid) {
        for(int i=0;i<grid.getColumnCount();i++) {
            for(int j=0;j<grid.getRowCount();j++) {
                StackPane s = new StackPane();

                grid.add(s,i,j);

                s.setOnMouseClicked((e) -> {
                    System.out.println(GridPane.getColumnIndex(s) + " " + GridPane.getRowIndex(s));
                });


            }
        }
    }

}



