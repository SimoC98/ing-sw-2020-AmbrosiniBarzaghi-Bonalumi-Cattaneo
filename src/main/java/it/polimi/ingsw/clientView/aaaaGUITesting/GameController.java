package it.polimi.ingsw.clientView.aaaaGUITesting;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameController {

    @FXML
    private GridPane board;

    private int [][]boardInt = new int[5][5];
    private final int width = 120;

    @FXML
    public void initialize() {
        createBoard();
    }

    public void createBoard() {
        for(int i=0; i<board.getColumnCount(); i++) {
            for(int j=0; j<board.getRowCount(); j++) {
                StackPane s = new StackPane();

                for(int k=0; k<3; k++) {
                    ImageView lvl = new ImageView();
                    lvl.setFitHeight(width-20*k);
                    lvl.setFitWidth(width-20*k);
                    s.getChildren().add(lvl);
                }

                board.add(s, i, j);

                s.setOnMouseClicked((e) -> build(s));


            }
        }
    }

    public void build(StackPane s) {
        int x = GridPane.getRowIndex(s);
        int y = GridPane.getColumnIndex(s);

        System.out.println(x + "-" + y);
        boardInt[x][y] ++;
        System.out.println(boardInt[x][y]);


        Image image;
        ImageView imageView;
        try {
            switch(boardInt[x][y]) {
                case 1:
                    image = new Image(new FileInputStream("src\\main\\java\\it\\polimi\\ingsw\\clientView\\aaaaGUITesting\\pics\\lvl1.png"));
                    imageView = (ImageView) s.getChildren().get(0);
                    imageView.setImage(image);
                    break;

                case 2:
                    image = new Image(new FileInputStream("src\\main\\java\\it\\polimi\\ingsw\\clientView\\aaaaGUITesting\\pics\\lvl2bis.png"));
                    imageView = (ImageView) s.getChildren().get(1);
                    imageView.setImage(image);
                    break;

                case 3:
                    image = new Image(new FileInputStream("src\\main\\java\\it\\polimi\\ingsw\\clientView\\aaaaGUITesting\\pics\\lvl3.png"));
                    imageView = (ImageView) s.getChildren().get(2);
                    imageView.setImage(image);
                    break;

                case 4:
                    Circle circle = new Circle();
                    circle.centerXProperty().bind(s.layoutXProperty().add(s.getWidth()/2));
                    circle.centerYProperty().bind(s.layoutYProperty().add(s.getHeight()/2));
                    circle.setRadius(width/4);
                    circle.setFill(Color.BLUE);
                    s.getChildren().add(circle);
                    break;

                default:
                    System.out.println("boh");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
