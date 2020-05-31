package it.polimi.ingsw.clientView.aaaaGUITesting;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Stack;

public class Test extends Application {

    @FXML
    TilePane tilePane;


//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setTitle("Hello World!");
//
//        StackPane root = new StackPane();
//        GridPane gridPane = new GridPane();
//
//        gridPane.prefWidthProperty().bind(root.widthProperty());
//        gridPane.prefHeightProperty().bind(root.heightProperty());
//        root.getChildren().add(gridPane);
//
//        for(int i=0; i<25; i++) {
//            Button b = new Button("" + i);
////            b.prefHeightProperty().bind(tilePane.heightProperty());
////            b.prefWidthProperty().bind(tilePane.widthProperty());
//            b.setMinHeight(20);
//            b.setMinWidth(20);
////            Rectangle r = new Rectangle(tilePane.getWidth()/5,tilePane.getHeight()/5);
////            if(i%2 == 0)
////                b.co(Color.DARKRED);
////            else
////                b.setFill(Color.BLUEVIOLET);
////            StackPane s = new StackPane(r);
////            tilePane.getChildren().add(r);
//            gridPane.getChildren().add(b);
//
//        }
//
//
////        root.prefWidthProperty().bind(primaryStage.widthProperty());
////        root.prefHeightProperty().bind(primaryStage.heightProperty());
//
//
//
//        root.getChildren().add(new Label("" + root.getWidth() + " aaand " + gridPane.getWidth() + " then " + primaryStage.getWidth()));
//
////        Rectangle rect = new Rectangle(root.getHeight()/4, root.getWidth()/4, root.getHeight()/2, root.getWidth()/2);
////        rect.setFill(Color.AQUAMARINE);
//
////        root.getChildren().add(rect);
//        primaryStage.setScene(new Scene(root, 300, 250));
//
//        primaryStage.show();
//    }

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();

        GridPane board = new GridPane();
        final int size = 5 ;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col ++) {
                StackPane square = new StackPane();
                String color ;
                if ((row + col) % 2 == 0) {
                    color = "white";
                } else {
                    color = "black";
                }
                square.setStyle("-fx-background-color: "+color+";");
                board.add(square, col, row);
            }
        }
        for (int i = 0; i < size; i++) {
            board.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            board.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        primaryStage.setScene(new Scene(board, 400, 400));
        primaryStage.show();
    }

//    @FXML


}
